package Restaurant.Food;

import Restaurant.Food.FoodMachine.FoodMachine;

// Used to manage the food ordered num and corresponding food machine
public class FoodManager {

    private int count;
    private FoodMachine foodMachine;

    public FoodManager(int count, FoodMachine foodMachine)
    {
        this.count = count;
        this.foodMachine = foodMachine;
    }

    public synchronized int GetCount()
    {
        return this.count;
    }

    public synchronized void SetCount(int count)
    {
        this.count = count;
    }

    public FoodMachine GetFoodMachine()
    {
        return this.foodMachine;
    }
}