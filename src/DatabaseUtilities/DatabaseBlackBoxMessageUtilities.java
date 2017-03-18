/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DatabaseUtilities;

import DataObjects.Pilot;
import ParameterSelection.Capability;
import ParameterSelection.Preference;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex Williams
 */
public class DatabaseBlackBoxMessageUtilities {
    private static String databaseConnectionName = "jdbc:derby:WinchCommonsTest12DataBase;";
    private static String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String clientDriverName = "org.apache.derby.jdbc.ClientDriver";
    
    public static void getBlackBoxMessgaes(File outputFile, long startTimestamp, long endTimestamp) throws ClassNotFoundException, SQLException {
        BufferedWriter buffWriter = null;
        if(!outputFile.exists()){
            try {
                outputFile.createNewFile();
                FileWriter fileWriter = new FileWriter(outputFile);
                buffWriter = new BufferedWriter(fileWriter);
            } catch (IOException ex) {
                Logger.getLogger(DatabaseBlackBoxMessageUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(outputFile);
            buffWriter = new BufferedWriter(fileWriter);
        } catch (IOException ex) {
            Logger.getLogger(DatabaseBlackBoxMessageUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(driverName);
            Class.forName(clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try {
            Connection connect = DriverManager.getConnection(databaseConnectionName);
            Statement stmt = connect.createStatement();
            ResultSet theMessages = stmt.executeQuery("SELECT timestamp, message "
                    + "FROM BlackBoxMessages ORDER BY timestamp");
            List pilots = new ArrayList<Pilot>();
            
            int i = 0;
            
            while(theMessages.next() && i < 1000) {
                String timestamp = theMessages.getString(1);
                String message = theMessages.getString(2);
                i += 1;
                if(buffWriter != null) {
                    try {
                        buffWriter.write(timestamp + '\t' + message);
                        buffWriter.newLine();
                    } catch (IOException ex) {
                        Logger.getLogger(DatabaseBlackBoxMessageUtilities.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            buffWriter.close();
            theMessages.close();
            stmt.close();
            connect.close();
        } catch (SQLException e) {
            throw e;
        } catch (IOException ex) {
            Logger.getLogger(DatabaseBlackBoxMessageUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteBlackBoxMessgaes( long startTimestamp, long endTimestamp) throws ClassNotFoundException, SQLException {
        try{
            Class.forName(driverName);
            Class.forName(clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try {
            Connection connect = DriverManager.getConnection(databaseConnectionName);
            Statement deleteMessagesStatement = connect.createStatement();
            String deleteMessagesString = "DELETE FROM BlackBoxMessages "
                    + "WHERE timestamp >= " + startTimestamp 
                    + " AND timestamp <= " + endTimestamp;        
            deleteMessagesStatement.executeUpdate(deleteMessagesString);
            deleteMessagesStatement.close();
            connect.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
