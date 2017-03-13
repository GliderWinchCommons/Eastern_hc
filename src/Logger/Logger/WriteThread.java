package Logger.Logger;

import java.io.PrintWriter;
import java.util.Queue;

/**
 * Created by gm14793 on 11/7/16.
 */
public class WriteThread implements Runnable {

    String threadName;
    Queue<String> toWrite;
    PrintWriter fout;
    Thread t;

    public WriteThread(String name, Queue<String> toWrite, PrintWriter fout) {
        this.threadName = name;
        this.toWrite = toWrite;
        this.fout = fout;
    }

    public void run() {
        try {
            int index = 0;
            while (toWrite.isEmpty()) {
            }
            while (index < 1000) {
                fout.println(toWrite.poll());
                index++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            fout.close();
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
