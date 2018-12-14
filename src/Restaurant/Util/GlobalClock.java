package Restaurant.Util;

public class GlobalClock extends Thread {

    public static Timestamp timestamp = new Timestamp(0);
    private static final int SLEEPUNIT = 100;

    private static synchronized void IncreaseTimeByOne()
    {
        timestamp.AddTime(new Timestamp((1)));
    }

    @Override
    public void run() {
        super.run();
        while(true)
        {
            try {
                Thread.sleep(SLEEPUNIT);
                IncreaseTimeByOne();
                synchronized(this) {
                    notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // for simulating machine work duration and diner eating duration
    public static void Sleep(Timestamp duration, GlobalClock globalClock) {
        Timestamp wakeTime = new Timestamp(GlobalClock.timestamp.GetTimestamp());
        wakeTime.AddTime(duration);
        while (wakeTime.compareTo(GlobalClock.timestamp) > 0) {
            synchronized (globalClock) {
                try {
                    globalClock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}