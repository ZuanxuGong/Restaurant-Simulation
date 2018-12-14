package Restaurant.Food.FoodMachine;

import Restaurant.Util.Timestamp;

public class BurgerMachine extends FoodMachine{

    public BurgerMachine(Timestamp workDuration)
    {
        super(workDuration);
        super.SetMachineType("Burger Machine");
    }

}