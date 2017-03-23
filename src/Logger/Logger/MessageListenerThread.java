package Logger.Logger;

import java.util.Queue;
import java.util.Scanner;

/**
 * This class is dedicated to listening to a system message feed. One of these threads is spawned for each
 * system in SystemsList.java.
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

    /**
     * loops endlessly listening to the message stream and grabbing each message from the stream and adding it
     * into the queue that is being emptied by the LogThread until endLoop is called, which sets keepLooping to
     * false and breaks the loop.
     */
    public void run() {
        try {
            while (keepLooping) {
                toWrite.add(sysID + " " + inStream.nextLine());
            }
        } catch (Exception ex) {
            System.out.println("Error in " + threadName);
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
