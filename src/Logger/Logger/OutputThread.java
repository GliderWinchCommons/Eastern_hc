package Logger.Logger;

import java.util.Queue;
import java.util.Scanner;

/**
 * Created by gm14793 on 11/14/16.
 */
public class OutputThread implements Runnable {

    private Thread thread;
    private String threadName;
    private Queue<String> toWrite;
    private Scanner inStream;
    private boolean keepLooping;

    public OutputThread(String tName, Scanner inStream, Queue<String> toWrite) {
        this.threadName = tName;
        this.toWrite = toWrite;
        this.inStream = inStream;
        this.keepLooping = true;
    }

    public void run() {
        try {
            while (keepLooping) {
                toWrite.add("++++" + inStream.nextLine());
            }
        } catch (Exception ex) {
            System.out.println("Error in OutputThread");
            System.out.println(ex.getMessage());
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
}
