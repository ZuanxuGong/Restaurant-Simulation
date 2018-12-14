package Restaurant.Food.FoodMachine;

import Restaurant.Util.Timestamp;

public class SodaMachine extends FoodMachine{

    public SodaMachine(Timestamp workDuration)
    {
        super(workDuration);
        super.SetMachineType("Soda Machine");
    }

}