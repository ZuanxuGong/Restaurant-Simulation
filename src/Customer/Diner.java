package Customer;

import java.util.Deque;

import Restaurant.Table;
import Restaurant.Order;
import Restaurant.Util.*;

// Manage diners
public class Diner implements Runnable{

    public static GlobalClock globalClock;
    public static Counter counter;
    public static Deque<Table> tableQueue;
    public static Deque<Order> orderQueue;

    private static String arriveFormat = "%s - Diner %d arrives.";
    private static String seatedFormat = "%s - Diner %d is seated at table %d.";
    private static String eatFormat = "%s - Diner %d's order is ready. Diner %d starts eating.";
    private static String leaveFormat = "%s - Diner %d finishes. Diner %d leaves the restaurant.";
    private static String lastLeaveFormat = "%s - The last diner leaves the restaurant.";

    private int id;
    private Order order;
    private Timestamp timestamp;

    public Diner(int id, Order order, Timestamp timestamp)
    {
        this.id = id;
        this.order = order;
        this.timestamp = timestamp;
    }

    // Get arriving time
    public Timestamp GetTimestamp()
    {
        return this.timestamp;
    }

    // Diner arrives
    public void Arrive()
    {
        String str = String.format(arriveFormat, GlobalClock.timestamp.GetTimeInClockFormat(), id);
        System.out.println(str);
        MyLog.write(str);
        Thread dinerThread = new Thread(this);
        dinerThread.start();
    }

    // Diner get seated
    private void GetSeated(int tableID)
    {
        String str = String.format(seatedFormat, GlobalClock.timestamp.GetTimeInClockFormat(), id, tableID);
        System.out.println(str);
        MyLog.write(str);
    }

    // Diner starts to eat
    private void Eat()
    {
        String str = String.format(eatFormat, GlobalClock.timestamp.GetTimeInClockFormat(), id, id);
        System.out.println(str);
        MyLog.write(str);
        GlobalClock.Sleep(new Timestamp(30), globalClock);
    }

    // Diner leaves
    private void Leave()
    {
        String str = String.format(leaveFormat, GlobalClock.timestamp.GetTimeInClockFormat(), id, id);
        System.out.println(str);
        MyLog.write(str);
        counter.DecreaseByOne();
        // Check whether it's the last diner
        if (counter.GetTotal() <= 0)
        {
            str = String.format(lastLeaveFormat, GlobalClock.timestamp.GetTimeInClockFormat());
            System.out.println(str);
            MyLog.write(str);
            MyLog.close();
            System.exit(0);
        }
    }

    // Get a table for the diner
    private Table GetTable(Deque<Table> tableQueue)
    {
        Table table;
        synchronized (tableQueue)
        {
            while (tableQueue.isEmpty())
            {
                try {
                    tableQueue.notifyAll();
                    tableQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            table = tableQueue.poll();
        }
        return table;
    }

    @Override
    public void run()
    {
        // Get Table
        Table table = this.GetTable(tableQueue);
        // Diner get seated
        GetSeated(table.GetId());
        // Add order to the order queue
        order.SetOrderStatus(Order.ORDER_STATUS.PENDING);
        orderQueue.add(order);
        // Waiting order to be completed
        while (order.GetOrderStatus()!= Order.ORDER_STATUS.COMPLETE)
        {
            synchronized(orderQueue)
            {
                try {
                    orderQueue.notifyAll();
                    orderQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Diner eats, leaves and release table
        Eat();
        Leave();
        tableQueue.add(table);
        synchronized (tableQueue) {
            tableQueue.notifyAll();
        }
    }
}