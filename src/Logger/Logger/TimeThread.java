package Logger.Logger;

import static java.lang.Thread.sleep;
import java.util.Date;
import java.util.Queue;

/**
 * Created by gm14793 on 11/9/16.
 */
public class TimeThread implements Runnable {

    private Thread t;
    private Queue<String> feedQueue;
    private String threadName;
    private boolean keepLooping;

    public TimeThread(String tName, Queue<String> queue) {
        this.threadName = tName;
        this.feedQueue = queue;
        this.keepLooping = true;
    }

    public void run() {
        try {
            Date date;
            while (keepLooping) {
                date = new Date();
                feedQueue.add("*******************" + date.getTime());
                sleep(500);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public void endLoop() {
        this.keepLooping = false;
    }
}
