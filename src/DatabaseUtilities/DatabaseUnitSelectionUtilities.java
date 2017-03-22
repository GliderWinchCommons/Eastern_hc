/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DatabaseUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * A class that provides the means to get and set the units for all the major 
 * components of the program
 * 
 * @author Alex Williams
 */
public class DatabaseUnitSelectionUtilities {
    
    /**
     * accessorss for retrieving the units for Pilot weight
     * 
     * @return units for Pilot weight
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getPilotWeightUnit() throws ClassNotFoundException {
        int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT weight_unit "
                + "FROM PilotUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    /**
     * accessorss for retrieving the units for Sailplane weight
     * 
     * @return units for Sailplane weight
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getSailplaneWeightUnit() throws ClassNotFoundException {
       int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT weight_unit "
                + "FROM SailplaneUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    /**
     * accessorss for retrieving the units for Sailplane velocity
     * 
     * @return units for Sailplane velocity
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getSailplaneVelocityUnit() throws ClassNotFoundException {
       int velocityUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT velocity_unit "
                + "FROM SailplaneUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    velocityUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return velocityUnit;
    }
    
    /**
     * accessors for retrieving the units for Sailplane tension
     * 
     * @return units for Sailplane tension
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getSailplaneTensionUnit() throws ClassNotFoundException {
        int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT tension_unit "
                + "FROM SailplaneUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    /**
     * accessors for retrieving the units for Airfield distance
     * 
     * @return units for Airfield distance
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getAirfieldDistanceUnit() throws ClassNotFoundException {
        int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT distance_unit "
                + "FROM AirfieldUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    /**
     * accessors for retrieving the units for Position distance
     * 
     * @return units for Position distance
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getPositionDistanceUnit() throws ClassNotFoundException {
        int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT distance_unit "
                + "FROM PositionUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    /**
     * accessors for retrieving the units for Dashboard distance
     * 
     * @return units for Dashboard distance
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getDashboardDistanceUnit() throws ClassNotFoundException {
        int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT distance_unit "
                + "FROM DashboardUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    /**
     * accessors for retrieving the units for Dashboard tension
     * 
     * @return units for Dashboard tension
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getDashboardTensionUnit() throws ClassNotFoundException {
        int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT tension_unit "
                + "FROM DashboardUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    /**
     * accessors for retrieving the units for Dashboard velocity
     * 
     * @return units for Dashboard velocity
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getDashboardVelocityUnit() throws ClassNotFoundException {
        int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT velocity_unit "
                + "FROM DashboardUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    /**
     * accessors for retrieving the units for Environmental temperature
     * 
     * @return units for Environmental temperature
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getEnvironmentalTempUnit() throws ClassNotFoundException {
        int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT temp_unit "
                + "FROM EnvironmentalUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    /**
     * accessors for retrieving the units for Environmental pressure
     * 
     * @return units for Environmental pressure
     * @throws ClassNotFoundException if Database driver classes not found
     */
    public static int getEnvironmentalPressureUnit() throws ClassNotFoundException {
        int weightUnit = 0;
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName)) {
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT pressure_unit "
                + "FROM EnvironmentalUnits "
                + "WHERE unit_set = 0");
            while(thePilots.next()) {
                
                try {
                    weightUnit = Integer.parseInt(thePilots.getString(1));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
            }
            thePilots.close();
            stmt.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;            
        }
        return weightUnit;
    }
    
    
    
    
    /**
     * Method for setting the units for a Pilot in the database
     * 
     * @param unitIndex index of the specified unit in the table (these correspond the the index of the units in the drop down box on the unit selection page)
     * @throws ClassNotFoundException if the program can't load the Apache Derby drivers
     * @throws SQLException if the table in the database can't be modified
     */
    public static void storePilotUnits(int unitIndex) throws ClassNotFoundException, SQLException {
        //Check for DB drivers
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try {
            
            Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName);
            Statement updatePilotTabelStmt = connect.createStatement();
            String updatePilotString = "UPDATE PilotUnits "
                                        + "SET weight_unit = " + unitIndex
                                        + "WHERE unit_set = 0";
            updatePilotTabelStmt.executeUpdate(updatePilotString);
            updatePilotTabelStmt.close();
            connect.close();
        }catch(SQLException e) {
            throw e;
        }
    }
    
    /**
     * Method for setting the units for a Sailplane in the database
     * 
     * @param unitIndex an array of indexes of the specified units in the table (these correspond the the index of the units in the drop down box on the unit selection page)
     * @throws ClassNotFoundException if the program can't load the Apache Derby drivers
     * @throws SQLException if the table in the database can't be modified
     */
    public static void storeSailplanesUnits(int[] unitIndex) throws ClassNotFoundException, SQLException {
        //Check for DB drivers
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try {
            
            Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName);
            Statement updatePilotTabelStmt = connect.createStatement();
            String updatePilotString = "UPDATE SailplaneUnits "
                                        + "SET weight_unit = " + unitIndex[0] + ", "
                                        + "velocity_unit= " + unitIndex[1] + ", "
                                        + "tension_unit= " + unitIndex[2]
                                        + "WHERE unit_set = 0";
            updatePilotTabelStmt.executeUpdate(updatePilotString);
            updatePilotTabelStmt.close();
            connect.close();
        }catch(SQLException e) {
            throw e;
        }
    }
    
    /**
     * Method for setting the units for an Airfield in the database
     * 
     * @param unitIndex an array of indexes of the specified units in the table (these correspond the the index of the units in the drop down box on the unit selection page)
     * @throws ClassNotFoundException if the program can't load the Apache Derby drivers
     * @throws SQLException if the table in the database can't be modified
     */
    public static void storeAirfieldUnits(int unitIndex) throws ClassNotFoundException, SQLException {
        //Check for DB drivers
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try {
            
            Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName);
            Statement updatePilotTabelStmt = connect.createStatement();
            String updatePilotString = "UPDATE AirfieldUnits "
                                        + "SET distance_unit = " + unitIndex
                                        + "WHERE unit_set = 0";
            updatePilotTabelStmt.executeUpdate(updatePilotString);
            updatePilotTabelStmt.close();
            connect.close();
        }catch(SQLException e) {
            throw e;
        }
    }
    
    /**
     * Method for setting the units for a Position in the database
     * 
     * @param unitIndex index of the specified unit in the table (these correspond the the index of the units in the drop down box on the unit selection page)
     * @throws ClassNotFoundException if the program can't load the Apache Derby drivers
     * @throws SQLException if the table in the database can't be modified
     */
    public static void storePositionUnits(int unitIndex) throws ClassNotFoundException, SQLException {
        //Check for DB drivers
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try {
            
            Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName);
            Statement updatePilotTabelStmt = connect.createStatement();
            String updatePilotString = "UPDATE PositionUnits "
                                        + "SET distance_unit = " + unitIndex
                                        + "WHERE unit_set = 0";
            updatePilotTabelStmt.executeUpdate(updatePilotString);
            updatePilotTabelStmt.close();
            connect.close();
        }catch(SQLException e) {
            throw e;
        }
    }
    
    /**
     * Method for setting the units for the Dashboard in the database
     * 
     * @param unitIndex an array of indexes of the specified units in the table (these correspond the the index of the units in the drop down box on the unit selection page)
     * @throws ClassNotFoundException if the program can't load the Apache Derby drivers
     * @throws SQLException if the table in the database can't be modified
     */
    public static void storeDashboardUnits(int[] unitIndex) throws ClassNotFoundException, SQLException {
        //Check for DB drivers
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try {
            
            Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName);
            Statement updatePilotTabelStmt = connect.createStatement();
            String updatePilotString = "UPDATE DashboardUnits "
                                        + "SET distance_unit = " + unitIndex[0] + ", "
                                        + "tension_unit= " + unitIndex[1] + ", "
                                        + "velocity_unit= " + unitIndex[3]
                                        + "WHERE unit_set = 0";
            updatePilotTabelStmt.executeUpdate(updatePilotString);
            updatePilotTabelStmt.close();
            connect.close();
        }catch(SQLException e) {
            throw e;
        }
    }
    
    /**
     * Method for setting the units for the Environmental variables in the database
     * 
     * @param unitIndex an array of indexes of the specified units in the table (these correspond the the index of the units in the drop down box on the unit selection page)
     * @throws ClassNotFoundException if the program can't load the Apache Derby drivers
     * @throws SQLException if the table in the database can't be modified
     */
    public static void storeEnvironmentalUnits(int[] unitIndex) throws ClassNotFoundException, SQLException {
        //Check for DB drivers
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(DatabaseInitialization.driverName);
            Class.forName(DatabaseInitialization.clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try {
            
            Connection connect = DriverManager.getConnection(DatabaseInitialization.databaseConnectionName);
            Statement updatePilotTabelStmt = connect.createStatement();
            String updatePilotString = "UPDATE EnvironmentalUnits "
                                        + "SET temp_unit = " + unitIndex[0] + ", "
                                        + "pressure_unit= " + unitIndex[1]
                                        + "WHERE unit_set = 0";
            updatePilotTabelStmt.executeUpdate(updatePilotString);
            updatePilotTabelStmt.close();
            connect.close();
        }catch(SQLException e) {
            throw e;
        }
    }
}
