package Restaurant;

import java.util.ArrayDeque;
import java.util.Deque;

import Customer.Diner;
import Restaurant.Util.GlobalClock;

// Manager Restaurant
public class Restaurant {

    private int tableCount;
    private int cookCount;
    private Deque<Diner> dinerQueue;
    private Deque<Order> orderQueue;
    private Deque<Table> tableQueue;
    private GlobalClock globalClock;

    public Restaurant(int tableCount, int cookCount, Deque<Diner> dinerQueue, GlobalClock globalClock)
    {
        this.tableCount = tableCount;
        this.cookCount = cookCount;
        this.dinerQueue = dinerQueue;
        this.orderQueue = new ArrayDeque<>();
        this.tableQueue = new ArrayDeque<>();
        this.globalClock = globalClock;
    }

    public void Start()
    {
        // Start the global clock
        globalClock.start();
        // Initialize table queue, cook threads and Diner
        Init();
        // Keep serving diners
        while(!dinerQueue.isEmpty())
        {
            // if the diner's arrive time >= current global clock time, we will start to care about that diner
            Diner diner = dinerQueue.peek();
            while(diner.GetTimestamp().compareTo(GlobalClock.timestamp) > 0)
            {
                synchronized(globalClock)
                {
                    try {
                        globalClock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Set the tableQueue and orderQueue to that diner and call diner arrive function
            diner = dinerQueue.poll();
            diner.Arrive();
        }
    }

    private void Init()
    {
        // Initialize table queue
        for (int i = 0; i < tableCount; i++)
        {
            Table table = new Table(i+1);
            tableQueue.add(table);
        }

        // Initialize cook threads
        for (int i = 0; i < cookCount; i++)
        {
            Cook cook = new Cook(i + 1, orderQueue);
            Thread cookThread = new Thread(cook);
            cookThread.start();
        }

        // Initialize some diner static member
        Diner.globalClock = globalClock;
        Diner.tableQueue = tableQueue;
        Diner.orderQueue = orderQueue;
    }
}
