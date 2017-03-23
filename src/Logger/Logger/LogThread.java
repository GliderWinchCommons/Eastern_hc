package Logger.Logger;

import java.io.PrintWriter;
import java.util.Queue;


public class LogThread implements Runnable {

    private Thread thread;
    private String threadName;
    private Queue<String> toWrite;
    private boolean keepLooping;
    private PrintWriter fout;

    public LogThread(String threadName, Queue<String> toWrite, PrintWriter fout) {
        this.fout = fout;
        this.toWrite = toWrite;
        this.threadName = threadName;
        this.keepLooping = true;
    }

    public void run() {
        try {
            while (keepLooping) {
                //loops endlessly writing anything in the queue toWrite until endloop is called which
                //sets keepLooping to false and breaks the while loop, after which the thread will finish emptying
                //the queue and close.
                while (!toWrite.isEmpty()) {
                    fout.println(toWrite.poll());
                    fout.flush();
                }
            }
            //Second while loop makes sure the queue is empty before the thread closes.
            while (!toWrite.isEmpty()) {
                fout.println(toWrite.poll());
                fout.flush();
            }
            fout.flush();
        } catch (Exception ex) {
            System.out.println("Error in LogThread");
            System.out.println(ex.getMessage());
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


}
