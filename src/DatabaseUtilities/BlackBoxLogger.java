/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DatabaseUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Class allows the program to save all incoming CanBus messages to table 
 * in the database in a multi-threaded fashion
 * 
 * @author Alex Williams
 */
public class BlackBoxLogger {
    //Last timestamp with which the most recent messages get associated
    private long currenttimestamp;
    //Buffer of messages waiting to be saved
    private LinkedList<String> messages = new LinkedList<String>();
    //Mulitthreaded logging mechanism
    private InternalLogger logger;
    //Thread on which to run the logger
    private Thread loggingThread;
    
    private Connection connection;
    
    private static String databaseConnectionName = "jdbc:derby:WinchCommonsTest12DataBase;";
    private static String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String clientDriverName = "org.apache.derby.jdbc.ClientDriver";
    
    public BlackBoxLogger() {
        currenttimestamp = 0L;
        logger = new InternalLogger();
        loggingThread = new Thread(logger);
        loggingThread.start();

        //TODO Connect to Database
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(driverName);
            Class.forName(clientDriverName);
            connection = DriverManager.getConnection(databaseConnectionName);
        }catch(java.lang.ClassNotFoundException e) {
            Logger.getLogger(BlackBoxLogger.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(BlackBoxLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method to be called to add the message to buffer of messages to log, and
     * invoke the logger to do the logging
     * 
     * @param message the message to be logged
     */
    public void loggMessage(String message) {
        messages.add(message);
        synchronized(logger){
            logger.run();
        }
    }
    
    /**
     * Method to be called to add the message to buffer of messages to log, set 
     * the new timestamp and invoke the logger to do the logging
     * 
     * @param timeStamp the new timestamp
     * @param message the message to be logged
     */
    public void loggMessage(int timeStamp, String message) {
        currenttimestamp = timeStamp;
        messages.add(message);
        synchronized(logger){
            logger.run();
        }
    }
    
    /**
     * This class implements the threaded logger that does the storing of
     * messages to the "Black Box" of raw CanBus messages
     */    
    private class InternalLogger implements Runnable {
        @Override
        public void run() {
            //try {
                if(!messages.isEmpty() && connection != null) {
                    try {
                        PreparedStatement messageInsertStatement = connection.prepareStatement(
                                "INSERT INTO BlackBoxMessages(timestamp, message)"
                                    + "values (?,?)");
                        messageInsertStatement.setString(1, String.valueOf(currenttimestamp));
                        messageInsertStatement.setString(2, messages.remove(0));
                        messageInsertStatement.executeUpdate();
                        messageInsertStatement.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                        Logger.getLogger(BlackBoxLogger.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //System.out.println(messages.get(0));
                }
                //wait();
            //} catch (InterruptedException ex) {
            //    Logger.getLogger(BlackBoxLogger.class.getName()).log(Level.SEVERE, null, ex);
            //}
        }
    }
}
