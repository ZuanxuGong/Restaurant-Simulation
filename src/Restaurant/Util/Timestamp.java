package Restaurant.Util;

public class Timestamp implements Comparable<Timestamp>{

    private int timestamp;

    private static String stringFormat = "%02d:%02d";

    public Timestamp(int timestamp)
    {
        this.timestamp = timestamp;
    }

    public int GetTimestamp()
    {
        return this.timestamp;
    }

    public String GetTimeInClockFormat()
    {
        return String.format(stringFormat, timestamp / 60, timestamp % 60);
    }

    public void AddTime(Timestamp timestamp)
    {
        this.timestamp += timestamp.GetTimestamp();
    }

    public int compareTo(Timestamp timestamp)
    {
        return this.timestamp - timestamp.GetTimestamp();
    }
}