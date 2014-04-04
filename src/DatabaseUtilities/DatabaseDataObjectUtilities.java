/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DatabaseUtilities;

import DataObjects.Pilot;
import DataObjects.Sailplane;
import ParameterSelection.Capability;
import ParameterSelection.Preference;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author awilliams5
 */
public class DatabaseDataObjectUtilities {
    private static String databaseConnectionName = "jdbc:derby:WinchCommonsTest12DataBase;";
    private static String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String clientDriverName = "org.apache.derby.jdbc.ClientDriver";

    
    public static void addPilotToDB(Pilot thePilot) throws SQLException, ClassNotFoundException {
        //Check for DB drivers
        try{
            //Class derbyClass = RMIClassLoader.loadClass("lib/", "derby.jar");
            Class.forName(driverName);
            Class.forName(clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(databaseConnectionName)) {
            PreparedStatement pilotInsertStatement = connect.prepareStatement(
                "INSERT INTO Pilot(name, weight, capability, preference,"
                        + " emergency_contact_info, emergency_medical_info)"
                        + "values (?,?,?,?,?,?)");
            pilotInsertStatement.setString(1, thePilot.getFirstName() + " " +
                thePilot.getLastName());
            pilotInsertStatement.setString(2, String.valueOf(thePilot.getWeight()));
            pilotInsertStatement.setString(3, String.valueOf(Capability.convertCapabilityStringToNum(thePilot.getCapability())));
            pilotInsertStatement.setString(4, String.valueOf(Preference.convertPreferenceStringToNum(thePilot.getPreference())));
            pilotInsertStatement.setString(5, thePilot.getEmergencyContact());
            pilotInsertStatement.setString(6, thePilot.getMedInfo());
            pilotInsertStatement.executeUpdate();
            pilotInsertStatement.close();
        }catch(SQLException e) {
            throw e;
        }
    }
    
    public static void addSailplaneToDB(Sailplane theSailplane) throws SQLException, ClassNotFoundException {
        //Check for DB drivers
        try{
            Class.forName(driverName);
            Class.forName(clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try (Connection connect = DriverManager.getConnection(databaseConnectionName)){
            PreparedStatement pilotInsertStatement = connect.prepareStatement(
                    "INSERT INTO Sailplane(n_number, type, owner, contact_info,"
                            + " max_gross_weight, empty_weight, indicated_stall_speed,"
                            + "max_winching_speed, max_weak_link_strength, max_tension)"
                            + "values (?,?,?,?,?,?,?,?,?,?)");
            pilotInsertStatement.setString(1, theSailplane.getNumber());
            pilotInsertStatement.setString(2, theSailplane.getType());
            pilotInsertStatement.setString(3, theSailplane.getOwner());
            pilotInsertStatement.setString(4, theSailplane.getContactInformation());
            pilotInsertStatement.setString(5, String.valueOf(theSailplane.getMaximumGrossWeight()));
            pilotInsertStatement.setString(6, String.valueOf(theSailplane.getEmptyWeight()));
            pilotInsertStatement.setString(7, String.valueOf(theSailplane.getIndicatedStallSpeed()));
            pilotInsertStatement.setString(8, String.valueOf(theSailplane.getMaximumWinchingSpeed()));
            pilotInsertStatement.setString(9, String.valueOf(theSailplane.getMaximumAllowableWeakLinkStrength()));
            pilotInsertStatement.setString(10, String.valueOf(theSailplane.getMaximumTension()));
            pilotInsertStatement.executeUpdate();
            pilotInsertStatement.close();
        }catch(SQLException e) {
            throw e;
        }
    }
    
    public static List<Pilot> getPilots() throws SQLException, ClassNotFoundException {        
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
            ResultSet thePilots = stmt.executeQuery("SELECT name, weight, capability, "
                    + "preference, emergency_contact_info, emergency_medical_info "
                    + "FROM Pilot ORDER BY name");
            List pilots = new ArrayList<Pilot>();
            
            while(thePilots.next()) {
                String pilotName = thePilots.getString(1);
                String[] names = pilotName.split("\\s+");
                int weight = 0; 
                int capability = 1;
                int preference = 1;
                try {
                    weight = Integer.parseInt(thePilots.getString(2));
                    capability = Integer.parseInt(thePilots.getString(3));
                    preference = Integer.parseInt(thePilots.getString(4));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
                Pilot newPilot = new Pilot(names[0], names[1], weight , Capability.convertCapabilityNumToString(capability), Preference.convertPreferenceNumToString(preference), thePilots.getString(5), thePilots.getString(6));
                pilots.add(newPilot);
            }
            thePilots.close();
            stmt.close();
            connect.close();
            return pilots;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public static List<Sailplane> getSailplanes() throws SQLException, ClassNotFoundException {        
        try{
            Class.forName(driverName);
            Class.forName(clientDriverName);
        }catch(java.lang.ClassNotFoundException e) {
            throw e;
        }
        
        try {
            Connection connect = DriverManager.getConnection(databaseConnectionName);
            Statement stmt = connect.createStatement();
            ResultSet theSailplanes = stmt.executeQuery("SELECT n_number, type, owner,"
                    + "contact_info, max_gross_weight, empty_weight, indicated_stall_speed,"
                    + "max_winching_speed, max_weak_link_strength, max_tension "
                    + "FROM Sailplane ORDER BY n_number");
            List sailplanes = new ArrayList<Sailplane>();
            
            while(theSailplanes.next()) {
                String nNumber = theSailplanes.getString(1);
                String type = theSailplanes.getString(2);
                String owner = theSailplanes.getString(3);
                String contactInfo = theSailplanes.getString(4);
                int maxGrossWeight = 0; 
                int emptyWeight = 0;
                int stallSpeed = 0;
                int maxWinchingSpeed = 0;
                int maxWeakLinkStrength = 0;
                int maxTension = 0;
                try {
                    maxGrossWeight = Integer.parseInt(theSailplanes.getString(5));
                    emptyWeight = Integer.parseInt(theSailplanes.getString(6));
                    stallSpeed = Integer.parseInt(theSailplanes.getString(7));
                    maxWinchingSpeed = Integer.parseInt(theSailplanes.getString(8));
                    maxWeakLinkStrength = Integer.parseInt(theSailplanes.getString(9));
                    maxTension = Integer.parseInt(theSailplanes.getString(10));
                }catch(NumberFormatException e) {
                    //TODO What happens when the Database sends back invalid data
                    JOptionPane.showMessageDialog(null, "Number Format Exception in reading from DB");
                }
                Sailplane newSailplane = new Sailplane(nNumber, type, owner, contactInfo, maxGrossWeight, emptyWeight, stallSpeed, maxWinchingSpeed, maxWeakLinkStrength, maxTension);
                sailplanes.add(newSailplane);
                theSailplanes.close();
                stmt.close();
                connect.close();
            }
            return sailplanes;
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public static boolean checkForTable(Connection dbConnection) throws SQLException {
        try {
            Statement s = dbConnection.createStatement();
            s.execute("UPDATE Pilot SET name = 'A Name', Weight = '000', capability='1', preference='1', emergency_contact_info='None', emergency_medical_info = 'None' where 1=3"); 
        }catch(SQLException sqle) {
            String theError = (sqle).getSQLState();
            if (theError.equals("42X05"))
                return false;
            else if(theError.equals("42X14") || theError.equals("42821")){
                //TODO find a good way to camcle and rerun init program
                throw sqle;
            }
            else
                throw sqle;
        }
        return true;            
    }
}