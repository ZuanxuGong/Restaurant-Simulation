package Restaurant.Util;

import java.io.BufferedWriter;
import java.io.IOException;

public class MyLog {

    public static BufferedWriter bufferedWriter;

    public static synchronized void write(String str)
    {
        try {
            bufferedWriter.write(str + "\r\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void close()
    {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
