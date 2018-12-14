package Restaurant.Util;

// Used to count diner number
public class Counter {

    private int total;

    public Counter(int total)
    {
        this.total = total;
    }

    public synchronized int GetTotal()
    {
        return this.total;
    }

    public synchronized void DecreaseByOne()
    {
        this.total--;
    }
}