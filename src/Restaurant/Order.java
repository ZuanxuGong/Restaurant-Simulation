package Restaurant;

import Restaurant.Food.FoodManager;

// Manager orders
public class Order {

    private int id;
    private ORDER_STATUS orderStatus;

    private FoodManager burger;
    private FoodManager fries;
    private FoodManager soda;

    public enum ORDER_STATUS
    {
        PENDING,
        PROCESSING,
        COMPLETE
    }

    public Order(int id, FoodManager burger, FoodManager fries, FoodManager soda)
    {
        this.id = id;
        this.burger = burger;
        this.fries = fries;
        this.soda = soda;
        this.orderStatus = ORDER_STATUS.PENDING;
    }

    public synchronized boolean IsOrderComplete()
    {
        int burgerCount = this.burger.GetCount();
        int friesCount = this.fries.GetCount();
        int sodaCount = this.soda.GetCount();

        return burgerCount == 0 && friesCount == 0 && sodaCount == 0;
    }

    public int GetId()
    {
        return id;
    }

    public synchronized ORDER_STATUS GetOrderStatus()
    {
        return this.orderStatus;
    }

    public synchronized void SetOrderStatus(ORDER_STATUS orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public FoodManager GetBurger()
    {
        return this.burger;
    }

    public FoodManager GetFries()
    {
        return this.fries;
    }

    public FoodManager GetSoda()
    {
        return this.soda;
    }
}
