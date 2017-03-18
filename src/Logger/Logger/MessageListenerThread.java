package Logger.Logger;

import java.util.Queue;
import java.util.Scanner;

/**
 * Created by gm14793 on 2/1/17.
 */
public class MessageListenerThread implements Runnable {

    private Thread thread;
    private String threadName;
    private Queue<String> toWrite;
    private Scanner inStream;
    private String sysID;
    private boolean keepLooping;

    public MessageListenerThread(String tName, String SystemID, Scanner inStream, Queue<String> toWrite) {
        this.threadName = tName;
        this.toWrite = toWrite;
        this.inStream = inStream;
        this.keepLooping = true;
        this.sysID = SystemID;

    }

    public void run() {
        try {
            while (keepLooping) {
                toWrite.add(sysID + " " + inStream.nextLine());
            }
        } catch (Exception ex) {
            System.out.println("Error in " + threadName);
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
