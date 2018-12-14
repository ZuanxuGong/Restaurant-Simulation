package Restaurant;

import java.util.Deque;
import java.util.List;
import java.util.ArrayList;

import Restaurant.Food.FoodMachine.FoodMachine;
import Restaurant.Food.FoodManager;
import Restaurant.Util.GlobalClock;
import Restaurant.Util.MyLog;

// Manage cooks
public class Cook implements Runnable{

    private int id;
    private final Deque<Order> orderQueue;
    private static String useMachineFormat = "%s - Cook %d uses the %s.";
    private static String processOrderFormat = "%s - Cook %d processes Diner %d's order.";

    Cook(int id, Deque<Order> orderQueue)
    {
        this.id = id;
        this.orderQueue = orderQueue;
    }

    // cook starts to use machine
    private void UseMachine(FoodMachine foodMachine)
    {
        String str = String.format(useMachineFormat, GlobalClock.timestamp.GetTimeInClockFormat(), this.id, foodMachine.GetMachineType());
        System.out.println(str);
        MyLog.write(str);
        foodMachine.StartWorking();
    }

    // cook finishes using machine
    private void ReleaseMachine(FoodMachine foodMachine)
    {
        foodMachine.FinishWorking();
    }

    // cook get an order from the order queue
    private Order GetOrderFromQueue(Deque<Order> orderQueue)
    {
        // Keep notify all and waiting until there is an order
        Order order;
        synchronized(orderQueue)
        {
            while(orderQueue.isEmpty())
            {
                try {
                    orderQueue.notifyAll();
                    orderQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            order = orderQueue.poll();
        }
        return order;
    }

    // cook starts to process order
    private void ProcessOrder(Order order){
        order.SetOrderStatus(Order.ORDER_STATUS.PROCESSING);
        // create food list for the order
        List<FoodManager> food = new ArrayList<>();
        food.add(order.GetBurger());
        food.add(order.GetFries());
        food.add(order.GetSoda());

        // keep trying to use the machine until the order is complete
        while (!order.IsOrderComplete())
        {
            for (FoodManager curFood : food) {
                // check whether the food machine we need is idle
                if (curFood.GetCount() > 0 && curFood.GetFoodMachine().GetMachineStatus() == FoodMachine.MACHINE_STATUS.IDLE) {
                    while (curFood.GetCount() > 0) {
                        UseMachine(curFood.GetFoodMachine());
                        curFood.SetCount(curFood.GetCount() - 1);
                    }
                    ReleaseMachine(curFood.GetFoodMachine());
                }
            }
        }
    }
    public void run()
    {
        Order order;
        while (true) {
            //Get Order
            order = GetOrderFromQueue(this.orderQueue);

            //Process Order
            String str = String.format(processOrderFormat, GlobalClock.timestamp.GetTimeInClockFormat(), this.id, order.GetId());
            System.out.println(str);
            MyLog.write(str);
            ProcessOrder(order);

            //Complete Order
            order.SetOrderStatus(Order.ORDER_STATUS.COMPLETE);
        }
    }
}