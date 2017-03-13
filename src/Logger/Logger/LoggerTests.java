package Logger.Logger;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by gm14793 on 11/7/16.
 */
public class LoggerTests {

    public static void main(String[] args) {
        try {

            Socket input = new Socket("localhost", 32123);
            Scanner reader = new Scanner(new InputStreamReader(input.getInputStream()));
            Socket input2 = new Socket("localhost", 32123);
            Scanner reader2 = new Scanner(new InputStreamReader(input2.getInputStream()));
            PrintWriter fout = new PrintWriter("output.txt");
            Queue<String> feedQueue = new ConcurrentLinkedQueue<String>();
            TimeThread tt = new TimeThread("Timer", feedQueue);
            tt.start();
            InputThread iThread = new InputThread("Input", reader, feedQueue);
            OutputThread oThread = new OutputThread("Output", reader2, feedQueue);
            LogThread lThread = new LogThread("Logger", feedQueue, fout);
            lThread.start();
            iThread.start();
            oThread.start();
            int tracker = 0;
            int index = 0;
            while (index < 1000000000) {
                if (feedQueue.size() > tracker) {
                    tracker = feedQueue.size();
                }
                //System.out.println(reader.nextLine());
                //feedQueue.add(reader.nextLine());
                //fout.println("test");
                //feedQueue.add(reader.nextLine());
                //fout.println(feedQueue.poll());
                //fout.flush();
                index++;
            }
            tt.endLoop();
            iThread.endLoop();
            oThread.endLoop();
            lThread.endLoop();

            System.out.println(tracker);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
