package Restaurant.Food.FoodMachine;

import Restaurant.Util.Timestamp;

public class FriesMachine extends FoodMachine{

    public FriesMachine(Timestamp workDuration)
    {
        super(workDuration);
        super.SetMachineType("Fries Machine");
    }

}