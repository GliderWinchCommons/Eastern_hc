package Logger.Logger;

import java.io.PrintWriter;
import java.util.Queue;

/**
 * Created by gm14793 on 11/14/16.
 */
public class LogThread implements Runnable {

    private Thread thread;
    private String threadName;
    private Queue<String> toWrite;
    private boolean keepLooping;
    private PrintWriter fout;
    private String logIndex;

    public LogThread(String threadName, Queue<String> toWrite, PrintWriter fout) {
        this.fout = fout;
        this.toWrite = toWrite;
        this.threadName = threadName;
        this.keepLooping = true;
        this.logIndex = "\n\n-------------------------------------------------------\n";
    }

    public void run() {
        try {
            int tracker = 0;
            while (keepLooping) {
                while (!toWrite.isEmpty()) {
                    fout.println(toWrite.poll());
                    fout.flush();
                    if (toWrite.size() > tracker) {
                        tracker = toWrite.size();
                    }
                }
            }
            //Second while loop makes sure the queue is empty before the loop closes.
            while (!toWrite.isEmpty()) {
                fout.println(toWrite.poll());
                fout.flush();
            }
            fout.println(logIndex);
            fout.flush();
            resetIndex();
            System.out.println(tracker);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public void endLoop() {
        this.keepLooping = false;
    }

    private void resetIndex() {
        this.logIndex = "";
    }

}
