import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

import Customer.*;
import Restaurant.Food.FoodManager;
import Restaurant.Food.FoodMachine.*;
import Restaurant.Order;
import Restaurant.Restaurant;
import Restaurant.Util.*;

public class Main {

    private static Deque<Diner> restaurantPreparation(BufferedReader bufferedReader, int dinerNum, GlobalClock globalClock) throws IOException
    {
        // Create food machines
        FoodMachine.globalClock = globalClock;
        FoodMachine burgerMachine = new BurgerMachine(new Timestamp(5));
        FoodMachine friesMachine  = new FriesMachine(new Timestamp(3));
        FoodMachine sodaMachine   = new SodaMachine(new Timestamp(1));

        Deque<Diner> dinerQueue = new ArrayDeque<>();
        Diner.counter = new Counter(dinerNum);
        // Initialize diners and their orders
        for(int i = 0; i < dinerNum; i++)
        {
            // Get arrivate time and food number
            String str = bufferedReader.readLine();
            String[] info = str.split(",");
            Timestamp arriveTime = new Timestamp(Integer.parseInt(info[0]));
            int burgerCnt = Integer.parseInt(info[1]);
            int friesCnt  = Integer.parseInt(info[2]);
            int sodaCnt   = Integer.parseInt(info[3]);

            // Create order
            FoodManager burgerManager = new FoodManager(burgerCnt, burgerMachine);
            FoodManager friesManager = new FoodManager(friesCnt, friesMachine);
            FoodManager sodaManager = new FoodManager(sodaCnt, sodaMachine);
            Order order = new Order(i + 1, burgerManager, friesManager, sodaManager);

            // Create diner
            Diner diner = new Diner(i + 1, order, arriveTime);
            dinerQueue.add(diner);
        }
        bufferedReader.close();
        return dinerQueue;
    }

    public static void main(String[] args) throws Exception
    {

        System.out.println("Please type your input filename (eg: input1.txt):");

        // Get input filename
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = bufferedReader.readLine();
        bufferedReader.close();

        // Start to read the content in the input file
        bufferedReader = new BufferedReader(new FileReader(fileName));
        int dinerNum = Integer.parseInt(bufferedReader.readLine());
        int tableNum = Integer.parseInt(bufferedReader.readLine());
        int cookNum = Integer.parseInt(bufferedReader.readLine());

        // Initialize output file
        File outputFile = new File("Out_" + fileName);
        outputFile.createNewFile();
        MyLog.bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

        // Create Global Timer
        GlobalClock globalClock = new GlobalClock();

        // Do restaurant preparation and open restaurant
        Deque<Diner> dinerQueue = restaurantPreparation(bufferedReader, dinerNum, globalClock);
        Restaurant restaurant = new Restaurant(tableNum, cookNum, dinerQueue, globalClock);
        restaurant.Start();
    }
}