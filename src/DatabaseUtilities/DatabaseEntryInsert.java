/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseUtilities;

import static Communications.ErrorLogger.logError;
import DataObjects.*;
import static DatabaseUtilities.DatabaseInitialization.WINCH_PRAM_VERSION;
import static DatabaseUtilities.DatabaseInitialization.connect;
import ParameterSelection.Capability;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Random;
import javafx.scene.control.Alert;

/**
 * This class provides the methods that allow a user to add and retrieve Pilots,
 * Sail planes and Airfields from the database as well as update and delete
 * Pilots
 *
 * @author Alex Williams, Noah Fujioka, dbennett3
 */
public class DatabaseEntryInsert {

    /**
     * Adds the relevant data for a pilot to the database
     *
     * @param thePilot the pilot to add to the database
     * @return false if add fails
     */
    public static boolean addPilotToDB(Pilot thePilot) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement pilotInsertStatement = connect.prepareStatement(
                    "INSERT INTO Pilot(pilot_id, first_name, last_name, middle_name, "
                    + "flight_weight, capability, preference, "
                    + "emergency_contact_name, emergency_contact_phone, optional_info)"
                    + "values (?,?,?,?,?,?,?,?,?,?)");
            pilotInsertStatement.setInt(1, thePilot.getPilotId());
            pilotInsertStatement.setString(2, thePilot.getFirstName());
            pilotInsertStatement.setString(3, thePilot.getLastName());
            pilotInsertStatement.setString(4, thePilot.getMiddleName());
            pilotInsertStatement.setFloat(5, thePilot.getWeight());
            pilotInsertStatement.setInt(6,
                    Capability.convertCapabilityStringToNum(thePilot.getCapability()));
            pilotInsertStatement.setFloat(7, thePilot.getPreference());
            pilotInsertStatement.setString(8, thePilot.getEmergencyName());
            pilotInsertStatement.setString(9, thePilot.getEmergencyPhone());
            pilotInsertStatement.setString(10, thePilot.getOptionalInfo());
            pilotInsertStatement.executeUpdate();
            pilotInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Pilot to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
/**
     * Adds the relevant data for a pilot to the database
     *
     * @param thePilot the pilot to add to the database
     * @return false if add fails
     */
    public static boolean addPilotToTempDB(Pilot thePilot) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement pilotInsertStatement = connect.prepareStatement(
                    "INSERT INTO TempPilot(pilot_id, first_name, last_name, middle_name, "
                    + "flight_weight, capability, preference, "
                    + "emergency_contact_name, emergency_contact_phone, optional_info)"
                    + "values (?,?,?,?,?,?,?,?,?,?)");
            pilotInsertStatement.setInt(1, thePilot.getPilotId());
            pilotInsertStatement.setString(2, thePilot.getFirstName());
            pilotInsertStatement.setString(3, thePilot.getLastName());
            pilotInsertStatement.setString(4, thePilot.getMiddleName());
            pilotInsertStatement.setFloat(5, thePilot.getWeight());
            pilotInsertStatement.setInt(6,
                    Capability.convertCapabilityStringToNum(thePilot.getCapability()));
            pilotInsertStatement.setFloat(7, thePilot.getPreference());
            pilotInsertStatement.setString(8, thePilot.getEmergencyName());
            pilotInsertStatement.setString(9, thePilot.getEmergencyPhone());
            pilotInsertStatement.setString(10, thePilot.getOptionalInfo());
            pilotInsertStatement.executeUpdate();
            pilotInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Pilot to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    
    
    public static boolean mergePilot() {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO Pilot o "
                                + "USING tempPilot t "
                                + "ON o.pilot_id = t.pilot_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.first_name = t.first_name, o.last_name = t.last_name, o.middle_name = t.middle_name, "
                                    + "o.flight_weight = t.flight_weight, o.capability = t.capability, o.preference = t.preference, "
                                    + "o.emergency_contact_name = t.emergency_contact_name, o.emergency_contact_phone = t.emergency_contact_phone, "
                                    + "o.optional_info = t.optional_info "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.pilot_id, t.first_name, t.last_name, t.middle_name, t.flight_weight, t.capability, t.preference, "
                                    + "t.emergency_contact_name, t.emergency_contact_phone, t.optional_info)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge Pilot to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    
    /**
     * Adds the relevant data for a parachute to the database
     * @param theParachute 
     * @return 
     */
    public static boolean addParachuteToDB(Parachute theParachute) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement parachuteInsertStatement = connect.prepareStatement(
                    "INSERT INTO Parachute(parachute_id, name, lift, drag, weight, optional_info)"
                    + "values (?,?,?,?,?,?)");
            parachuteInsertStatement.setInt(1, theParachute.getParachuteId());
            parachuteInsertStatement.setString(2, theParachute.getName());
            parachuteInsertStatement.setFloat(3, theParachute.getLift());
            parachuteInsertStatement.setFloat(4, theParachute.getDrag());
            parachuteInsertStatement.setFloat(5, theParachute.getWeight());
            parachuteInsertStatement.setString(6, theParachute.getInfo());
            parachuteInsertStatement.executeUpdate();
            parachuteInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Parachute to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    
    public static boolean mergeParachute() {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO Parachute o "
                                + "USING tempParachute t "
                                + "ON o.parachute_id = t.parachute_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.name = t.name, o.lift = t.lift, o.drag = t.drag, o.weight = t.weight, o.optional_info = t.optional_info "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.parachute_id, t.name, t.lift, t.drag, t.weight, t.optional_info)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge Parachute to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    
    /**
     * Adds the relevant data for a sailplane to the database
     *
     * @param theSailplane the sailplane to add to the database
     * @return false if add fails
     */
    public static boolean addSailplaneToDB(Sailplane theSailplane) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement sailplaneInsertStatement;
            sailplaneInsertStatement = connect.prepareStatement(
                    "INSERT INTO Glider(glider_id, reg_number, common_name, owner, type, "
                    + "max_gross_weight, empty_weight, indicated_stall_speed, "
                    + "max_winching_speed, max_weak_link_strength, max_tension, "
                    + "cable_release_angle, carry_ballast, multiple_seats, "
                    + "optional_info)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            sailplaneInsertStatement.setInt(1, theSailplane.getId());
            sailplaneInsertStatement.setString(2, theSailplane.getRegNumber());
            sailplaneInsertStatement.setString(3, theSailplane.getName());
            sailplaneInsertStatement.setString(4, theSailplane.getOwner());
            sailplaneInsertStatement.setString(5, theSailplane.getType());
            sailplaneInsertStatement.setFloat(6, theSailplane.getMaxGrossWeight());
            sailplaneInsertStatement.setFloat(7, theSailplane.getEmptyWeight());
            sailplaneInsertStatement.setFloat(8, theSailplane.getIndicatedStallSpeed());
            sailplaneInsertStatement.setFloat(9, theSailplane.getMaxWinchingSpeed());
            sailplaneInsertStatement.setFloat(10, theSailplane.getMaxWeakLinkStrength());
            sailplaneInsertStatement.setFloat(11, theSailplane.getMaxTension());
            sailplaneInsertStatement.setFloat(12, theSailplane.getCableReleaseAngle());
            sailplaneInsertStatement.setBoolean(13, theSailplane.getCarryBallast());
            sailplaneInsertStatement.setBoolean(14, theSailplane.getMultipleSeats());
            sailplaneInsertStatement.setString(15, theSailplane.getOptionalInfo());
            sailplaneInsertStatement.executeUpdate();
            sailplaneInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Glider to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    public static boolean addSailplaneToTempDB(Sailplane theSailplane) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement sailplaneInsertStatement;
            sailplaneInsertStatement = connect.prepareStatement(
                    "INSERT INTO tempGlider(glider_id, reg_number, common_name, owner, type, "
                    + "max_gross_weight, empty_weight, indicated_stall_speed, "
                    + "max_winching_speed, max_weak_link_strength, max_tension, "
                    + "cable_release_angle, carry_ballast, multiple_seats, "
                    + "optional_info)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            sailplaneInsertStatement.setInt(1, theSailplane.getId());
            sailplaneInsertStatement.setString(2, theSailplane.getRegNumber());
            sailplaneInsertStatement.setString(3, theSailplane.getName());
            sailplaneInsertStatement.setString(4, theSailplane.getOwner());
            sailplaneInsertStatement.setString(5, theSailplane.getType());
            sailplaneInsertStatement.setFloat(6, theSailplane.getMaxGrossWeight());
            sailplaneInsertStatement.setFloat(7, theSailplane.getEmptyWeight());
            sailplaneInsertStatement.setFloat(8, theSailplane.getIndicatedStallSpeed());
            sailplaneInsertStatement.setFloat(9, theSailplane.getMaxWinchingSpeed());
            sailplaneInsertStatement.setFloat(10, theSailplane.getMaxWeakLinkStrength());
            sailplaneInsertStatement.setFloat(11, theSailplane.getMaxTension());
            sailplaneInsertStatement.setFloat(12, theSailplane.getCableReleaseAngle());
            sailplaneInsertStatement.setBoolean(13, theSailplane.getCarryBallast());
            sailplaneInsertStatement.setBoolean(14, theSailplane.getMultipleSeats());
            sailplaneInsertStatement.setString(15, theSailplane.getOptionalInfo());
            sailplaneInsertStatement.executeUpdate();
            sailplaneInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Glider to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    public static boolean mergeGlider(){
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO Glider o "
                                + "USING tempGlider t "
                                + "ON o.glider_id = t.glider_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.reg_number = t.reg_number, "
                                    + "o.common_name = t.common_name, o.owner = t.owner, "
                                    + "o.type = t.type, o.max_gross_weight = t.max_gross_weight, o.empty_weight = t.empty_weight, "
                                    + "o.indicated_stall_speed = t.indicated_stall_speed, o.max_winching_speed = t.max_winching_speed, "
                                    + "o.max_weak_link_strength = t.max_weak_link_strength, o.max_tension = t.max_tension, "
                                    + "o.cable_release_angle = t.cable_release_angle, o.carry_ballast = t.carry_ballast, "
                                    + "o.multiple_seats = t.multiple_seats, o.optional_info = t.optional_info "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.glider_id, t.reg_number, t.common_name, t.owner, t.type, "
                                    + "t.max_gross_weight, t.empty_weight, t.indicated_stall_speed, "
                                    + "t.max_winching_speed, t.max_weak_link_strength, t.max_tension, "
                                    + "t.cable_release_angle, t.carry_ballast, t.multiple_seats, "
                                    + "t.optional_info)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge Glider to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    
    
    
    /**
     * Adds the relevant data for an airfield to the database
     *
     * @param theAirfield the airfield to add to the database
     * @return false if add fails
     */
    public static boolean addAirfieldToDB(Airfield theAirfield) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement AirfieldInsertStatement = connect.prepareStatement(
                    "INSERT INTO Airfield(airfield_id, name, designator, elevation, "
                    + "magnetic_variation, latitude, longitude, utc_offset, optional_info) "
                    + "values (?,?,?,?,?,?,?,?,?)");
            AirfieldInsertStatement.setInt(1, theAirfield.getId());
            AirfieldInsertStatement.setString(2, theAirfield.getName());
            AirfieldInsertStatement.setString(3, theAirfield.getDesignator());
            AirfieldInsertStatement.setFloat(4, theAirfield.getElevation());
            AirfieldInsertStatement.setFloat(5, theAirfield.getMagneticVariation());
            AirfieldInsertStatement.setFloat(6, theAirfield.getLatitude());
            AirfieldInsertStatement.setFloat(7, theAirfield.getLongitude());
            AirfieldInsertStatement.setInt(8, theAirfield.getUTC());
            AirfieldInsertStatement.setString(9, theAirfield.getOptionalInfo());
            AirfieldInsertStatement.executeUpdate();
            AirfieldInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Airfield to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    /**
     * Adds the relevant data for an airfield to the database
     *
     * @param theAirfield the airfield to add to the database
     * @return false if add fails
     */
    public static boolean addAirfieldToTempDB(Airfield theAirfield) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement AirfieldInsertStatement = connect.prepareStatement(
                    "INSERT INTO TempAirfield(airfield_id, name, designator, elevation, "
                    + "magnetic_variation, latitude, longitude, utc_offset, optional_info) "
                    + "values (?,?,?,?,?,?,?,?,?)");
            AirfieldInsertStatement.setInt(1, theAirfield.getId());
            AirfieldInsertStatement.setString(2, theAirfield.getName());
            AirfieldInsertStatement.setString(3, theAirfield.getDesignator());
            AirfieldInsertStatement.setFloat(4, theAirfield.getElevation());
            AirfieldInsertStatement.setFloat(5, theAirfield.getMagneticVariation());
            AirfieldInsertStatement.setFloat(6, theAirfield.getLatitude());
            AirfieldInsertStatement.setFloat(7, theAirfield.getLongitude());
            AirfieldInsertStatement.setInt(8, theAirfield.getUTC());
            AirfieldInsertStatement.setString(9, theAirfield.getOptionalInfo());
            AirfieldInsertStatement.executeUpdate();
            AirfieldInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Airfield to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    public static boolean mergeAirfield() {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO Airfield o "
                                + "USING tempAirfield t "
                                + "ON o.airfield_id = t.airfield_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.name = t.name, o.designator = t.designator, "
                                    + "o.elevation = t.elevation, o.magnetic_variation = t.magnetic_variation, "
                                    + "o.latitude = t.latitude, o.longitude = t.longitude, "
                                    + "o.utc_offset = t.utc_offset, o.optional_info = t.optional_info "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.airfield_id, t.name, t.designator, t.elevation, t.magnetic_variation, t.latitude, t.longitude, "
                                    + "t.utc_offset, t.optional_info)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge Airfield to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    
    /**
     * Adds the relevant data for a runway to the database
     *
     * @param theRunway the runway to add to the database
     * @return false if add fails
     */
    public static boolean addRunwayToDB(Runway theRunway) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement RunwayInsertStatement = connect.prepareStatement(
                    "INSERT INTO TempRunway(runway_id, parent_id, runway_name, magnetic_heading, "
                    + "optional_info) "
                    + "values (?,?,?,?,?)");
            RunwayInsertStatement.setInt(1, theRunway.getId());
            RunwayInsertStatement.setInt(2, theRunway.getParentId());
            RunwayInsertStatement.setString(3, theRunway.getName());
            RunwayInsertStatement.setFloat(4, theRunway.getMagneticHeading());
            RunwayInsertStatement.setString(5, theRunway.getOptionalInfo());
            RunwayInsertStatement.executeUpdate();
            RunwayInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Runway to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    /**
     * Adds the relevant data for a runway to the database
     *
     * @param theRunway the runway to add to the database
     * @return false if add fails
     */
    public static boolean addRunwayToTempDB(Runway theRunway) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement RunwayInsertStatement = connect.prepareStatement(
                    "INSERT INTO TempRunway(runway_id, parent_id, runway_name, magnetic_heading, "
                    + "optional_info) "
                    + "values (?,?,?,?,?)");
            RunwayInsertStatement.setInt(1, theRunway.getId());
            RunwayInsertStatement.setInt(2, theRunway.getParentId());
            RunwayInsertStatement.setString(3, theRunway.getName());
            RunwayInsertStatement.setFloat(4, theRunway.getMagneticHeading());
            RunwayInsertStatement.setString(5, theRunway.getOptionalInfo());
            RunwayInsertStatement.executeUpdate();
            RunwayInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Runway to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    public static boolean mergeRunway() {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO Runway o "
                                + "USING tempRunway t "
                                + "ON o.runway_id = t.runway_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.parent_id = t.parent_id, o.runway_name = t.runway_name, o.magnetic_heading = t.magnetic_heading, "
                                    + "o.optional_info = t.optional_info "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.runway_id, t.parent_id, t.runway_name, t.magnetic_heading, t.optional_info)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge Runway to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    /**
     * Adds the relevant data for a glider position to the database
     *
     * @param theGliderPosition the runway to add to the database
     * @return false if add fails
     */
    public static boolean addGliderPositionToTempDB(GliderPosition theGliderPosition) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement GliderPositionInsertStatement = connect.prepareStatement(
                    "INSERT INTO TempGliderPosition(glider_position_id, parent_id, "
                    + "position_name, elevation, latitude, longitude, optional_info) "
                    + "values (?,?,?,?,?,?,?)");
            GliderPositionInsertStatement.setInt(1, theGliderPosition.getId());
            GliderPositionInsertStatement.setInt(2, theGliderPosition.getRunwayParentId());
            GliderPositionInsertStatement.setString(3, theGliderPosition.getName());
            GliderPositionInsertStatement.setFloat(4, theGliderPosition.getElevation());
            GliderPositionInsertStatement.setFloat(5, theGliderPosition.getLatitude());
            GliderPositionInsertStatement.setFloat(6, theGliderPosition.getLongitude());
            GliderPositionInsertStatement.setString(7, theGliderPosition.getOptionalInfo());
            GliderPositionInsertStatement.executeUpdate();
            GliderPositionInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Glider Position to Database, Check Error Log")
                    .showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    /**
     * Adds the relevant data for a glider position to the database
     *
     * @param theGliderPosition the runway to add to the database
     * @return false if add fails
     */
    public static boolean addGliderPositionToDB(GliderPosition theGliderPosition) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement GliderPositionInsertStatement = connect.prepareStatement(
                    "INSERT INTO GliderPosition(glider_position_id, parent_id, "
                    + "position_name, elevation, latitude, longitude, optional_info) "
                    + "values (?,?,?,?,?,?,?)");
            GliderPositionInsertStatement.setInt(1, theGliderPosition.getId());
            GliderPositionInsertStatement.setInt(2, theGliderPosition.getRunwayParentId());
            GliderPositionInsertStatement.setString(3, theGliderPosition.getName());
            GliderPositionInsertStatement.setFloat(4, theGliderPosition.getElevation());
            GliderPositionInsertStatement.setFloat(5, theGliderPosition.getLatitude());
            GliderPositionInsertStatement.setFloat(6, theGliderPosition.getLongitude());
            GliderPositionInsertStatement.setString(7, theGliderPosition.getOptionalInfo());
            GliderPositionInsertStatement.executeUpdate();
            GliderPositionInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Glider Position to Database, Check Error Log")
                    .showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    public static boolean mergeGliderPosition() {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO GliderPosition o "
                                + "USING tempGliderPosition t "
                                + "ON o.glider_position_id = t.glider_position_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.parent_id = t.parent_id, o.position_name = t.position_name, o.elevation = t.elevation, "
                                    + "o.latitude = t.latitude, o.longitude = t.longitude, o.optional_info = t.optional_info "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.glider_position_id, t.parent_id, t.position_name, t.elevation, t.latitude, "
                                    + "t.longitude, t.optional_info)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge GliderPosition to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    
    
    /**
     * Adds the relevant data for a winch position to the database
     *
     * @param theWinchPosition the runway to add to the database
     * @return false if add fails
     */
    public static boolean addWinchPositionToDB(WinchPosition theWinchPosition) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement WinchPositionInsertStatement = connect.prepareStatement(
                    "INSERT INTO WinchPosition(winch_position_id, parent_id, position_name, "
                    + "elevation, latitude, longitude, optional_info) "
                    + "values (?,?,?,?,?,?,?)");
            WinchPositionInsertStatement.setInt(1, theWinchPosition.getId());
            WinchPositionInsertStatement.setInt(2, theWinchPosition.getRunwayParentId());
            WinchPositionInsertStatement.setString(3, theWinchPosition.getName());
            WinchPositionInsertStatement.setFloat(4, theWinchPosition.getElevation());
            WinchPositionInsertStatement.setFloat(5, theWinchPosition.getLatitude());
            WinchPositionInsertStatement.setFloat(6, theWinchPosition.getLongitude());
            WinchPositionInsertStatement.setString(7, theWinchPosition.getOptionalInfo());
            WinchPositionInsertStatement.executeUpdate();
            WinchPositionInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add WInch Position to Database, Check Error Log")
                    .showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
 /**
     * Adds the relevant data for a winch position to the database
     *
     * @param theWinchPosition the runway to add to the database
     * @return false if add fails
     */
    public static boolean addWinchPositionToTempDB(WinchPosition theWinchPosition) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement WinchPositionInsertStatement = connect.prepareStatement(
                    "INSERT INTO TempWinchPosition(winch_position_id, parent_id, position_name, "
                    + "elevation, latitude, longitude, optional_info) "
                    + "values (?,?,?,?,?,?,?)");
            WinchPositionInsertStatement.setInt(1, theWinchPosition.getId());
            WinchPositionInsertStatement.setInt(2, theWinchPosition.getRunwayParentId());
            WinchPositionInsertStatement.setString(3, theWinchPosition.getName());
            WinchPositionInsertStatement.setFloat(4, theWinchPosition.getElevation());
            WinchPositionInsertStatement.setFloat(5, theWinchPosition.getLatitude());
            WinchPositionInsertStatement.setFloat(6, theWinchPosition.getLongitude());
            WinchPositionInsertStatement.setString(7, theWinchPosition.getOptionalInfo());
            WinchPositionInsertStatement.executeUpdate();
            WinchPositionInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add WInch Position to Database, Check Error Log")
                    .showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    public static boolean mergeWinchPosition() {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO WinchPosition o "
                                + "USING tempWinchPosition t "
                                + "ON o.winch_position_id = t.winch_position_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.parent_id = t.parent_id, o.position_name = t.position_name, o.elevation = t.elevation, "
                                    + "o.latitude = t.latitude, o.longitude = t.longitude, o.optional_info = t.optional_info "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.winch_position_id, t.parent_id, t.position_name, t.elevation, "
                                    + "t.latitude, t.longitude, t.optional_info)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge WinchPosition to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    
    
    
    /**
     * Adds the relevant data for a parachute to the database
     *
     * @param theWinch the winch to add to the database
     * @return false if add fails
     */
    public static boolean addWinchToDB(Winch theWinch) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement WinchInsertStatement = connect.prepareStatement(
                    "INSERT INTO Winch "
                    + "(winch_id, name, owner, wc_version, "
                    + "w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, "
                    + "w13, w14, w15, w16, meteorological_check_time, "
                    + "meteorological_verify_time, run_orientation_tolerance, optional_info) "
                    + "values ("
                    + "?,?,?,?,?,?,?,?,?,?," //10
                    + "?,?,?,?,?,?,?,?,?,?," //10
                    + "?,?,?,?" //4
                    + ")");
            WinchInsertStatement.setInt(1, theWinch.getId());
            WinchInsertStatement.setString(2, theWinch.getName());
            WinchInsertStatement.setString(3, theWinch.getOwner());
            WinchInsertStatement.setString(4, WINCH_PRAM_VERSION);
            WinchInsertStatement.setFloat(5, theWinch.getW1());
            WinchInsertStatement.setFloat(6, theWinch.getW2());
            WinchInsertStatement.setFloat(7, theWinch.getW3());
            WinchInsertStatement.setFloat(8, theWinch.getW4());
            WinchInsertStatement.setFloat(9, theWinch.getW5());
            WinchInsertStatement.setFloat(10, theWinch.getW6());
            WinchInsertStatement.setFloat(11, theWinch.getW7());
            WinchInsertStatement.setFloat(12, theWinch.getW8());
            WinchInsertStatement.setFloat(13, theWinch.getW9());
            WinchInsertStatement.setFloat(14, theWinch.getW10());
            WinchInsertStatement.setFloat(15, theWinch.getW11());
            WinchInsertStatement.setFloat(16, theWinch.getW12());
            WinchInsertStatement.setFloat(17, theWinch.getW13());
            WinchInsertStatement.setFloat(18, theWinch.getW14());
            WinchInsertStatement.setFloat(19, theWinch.getW15());
            WinchInsertStatement.setFloat(20, theWinch.getW16());
            WinchInsertStatement.setInt(21, theWinch.meteorologicalCheckTime());
            WinchInsertStatement.setInt(22, theWinch.meteorologicalVerifyTime());
            WinchInsertStatement.setFloat(23, theWinch.runOrientationTolerance());
            WinchInsertStatement.setString(24, theWinch.getOptionalInfo());
            WinchInsertStatement.executeUpdate();
            WinchInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Winch to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    /**
     * Adds the relevant data for a parachute to the database
     *
     * @param theWinch the winch to add to the database
     * @return false if add fails
     */
    public static boolean addWinchToTempDB(Winch theWinch) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement WinchInsertStatement = connect.prepareStatement(
                    "INSERT INTO TempWinch "
                    + "(winch_id, name, owner, wc_version, "
                    + "w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, "
                    + "w13, w14, w15, w16, meteorological_check_time, "
                    + "meteorological_verify_time, run_orientation_tolerance, optional_info) "
                    + "values ("
                    + "?,?,?,?,?,?,?,?,?,?," //10
                    + "?,?,?,?,?,?,?,?,?,?," //10
                    + "?,?,?,?" //4
                    + ")");
            WinchInsertStatement.setInt(1, theWinch.getId());
            WinchInsertStatement.setString(2, theWinch.getName());
            WinchInsertStatement.setString(3, theWinch.getOwner());
            WinchInsertStatement.setString(4, WINCH_PRAM_VERSION);
            WinchInsertStatement.setFloat(5, theWinch.getW1());
            WinchInsertStatement.setFloat(6, theWinch.getW2());
            WinchInsertStatement.setFloat(7, theWinch.getW3());
            WinchInsertStatement.setFloat(8, theWinch.getW4());
            WinchInsertStatement.setFloat(9, theWinch.getW5());
            WinchInsertStatement.setFloat(10, theWinch.getW6());
            WinchInsertStatement.setFloat(11, theWinch.getW7());
            WinchInsertStatement.setFloat(12, theWinch.getW8());
            WinchInsertStatement.setFloat(13, theWinch.getW9());
            WinchInsertStatement.setFloat(14, theWinch.getW10());
            WinchInsertStatement.setFloat(15, theWinch.getW11());
            WinchInsertStatement.setFloat(16, theWinch.getW12());
            WinchInsertStatement.setFloat(17, theWinch.getW13());
            WinchInsertStatement.setFloat(18, theWinch.getW14());
            WinchInsertStatement.setFloat(19, theWinch.getW15());
            WinchInsertStatement.setFloat(20, theWinch.getW16());
            WinchInsertStatement.setInt(21, theWinch.meteorologicalCheckTime());
            WinchInsertStatement.setInt(22, theWinch.meteorologicalVerifyTime());
            WinchInsertStatement.setFloat(23, theWinch.runOrientationTolerance());
            WinchInsertStatement.setString(24, theWinch.getOptionalInfo());
            WinchInsertStatement.executeUpdate();
            WinchInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Winch to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    public static boolean mergeWinch() {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO Winch o "
                                + "USING tempWinch t "
                                + "ON o.winch_id = t.winch_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.name = t.name, o.owner = t.owner, o.wc_version = t.wc_version, o.w1 = t.w1, o.w2 = t.w2, "
                                    + "o.w3 = t.w3, o.w4 = t.w4, o.w5 = t.w5, o.w6 = t.w6, o.w7 = t.w7, o.w8 = t.w8, o.w9 = t.w9, "
                                    + "o.w10 = t.w10, o.w11 = t.w11, o.w12 = t.w12, o.w13 = t.w13, o.w14 = t.w14, o.w15 = t.w15, "
                                    + "o.w16 = t.w16, o.meteorological_check_time = t.meteorological_check_time, "
                                    + "o.meteorological_verify_time = t.meteorological_verify_time, "
                                    + "o.run_orientation_tolerance = t.run_orientation_tolerance, "
                                    + "o.optional_info = t.optional_info "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.winch_id, t.name, t.owner, t.wc_version, t.w1, t.w2, t.w3, t.w4, t.w5, t.w6, t.w7, t.w8, t.w9, t.w10, "
                                    + "t.w11, t.w12, t.w13, t.w14, t.w15, t.w16, t.meteorological_check_time, t.meteorological_verify_time, "
                                    + "t.run_orientation_tolerance, t.optional_info)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge Winch to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    
    
    

    /**
     * Adds the relevant data for a parachute to the database
     *
     * @param theDrum the drum to add to the database
     * @return false if add fails
     */
    public static boolean addDrumToDB(Drum theDrum) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement DrumInsertStatement = connect.prepareStatement(
                    "INSERT INTO Drum(drum_id, drum_name, drum_number, core_diameter, kfactor, "
                    + "spring_const, cable_length, cable_density, drum_system_emass, number_of_launches, "
                    + "maximum_working_tension, winch_id, optional_info) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            DrumInsertStatement.setInt(1, theDrum.getId());
            DrumInsertStatement.setString(2, theDrum.getName());
            DrumInsertStatement.setInt(3, theDrum.getDrumNumber());
            DrumInsertStatement.setFloat(4, theDrum.getCoreDiameter());
            DrumInsertStatement.setFloat(5, theDrum.getKFactor());
            DrumInsertStatement.setFloat(6, theDrum.getSpringConstant());
            DrumInsertStatement.setFloat(7, theDrum.getCableLength());
            DrumInsertStatement.setFloat(8, theDrum.getCableDensity());
            DrumInsertStatement.setFloat(9, theDrum.getSystemEquivalentMass());
            DrumInsertStatement.setInt(10, theDrum.getNumLaunches());
            DrumInsertStatement.setFloat(11, theDrum.getMaxTension());
            DrumInsertStatement.setInt(12, theDrum.getWinchId());
            DrumInsertStatement.setString(13, theDrum.getOptionalInfo());
            DrumInsertStatement.executeUpdate();
            DrumInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Drum to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    /**
     * Adds the relevant data for a parachute to the database
     *
     * @param theDrum the drum to add to the database
     * @return false if add fails
     */
    public static boolean addDrumToTempDB(Drum theDrum) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement DrumInsertStatement = connect.prepareStatement(
                    "INSERT INTO Drum(drum_id, drum_name, drum_number, core_diameter, kfactor, "
                    + "spring_const, cable_length, cable_density, drum_system_emass, number_of_launches, "
                    + "maximum_working_tension, winch_id, optional_info) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            DrumInsertStatement.setInt(1, theDrum.getId());
            DrumInsertStatement.setString(2, theDrum.getName());
            DrumInsertStatement.setInt(3, theDrum.getDrumNumber());
            DrumInsertStatement.setFloat(4, theDrum.getCoreDiameter());
            DrumInsertStatement.setFloat(5, theDrum.getKFactor());
            DrumInsertStatement.setFloat(6, theDrum.getSpringConstant());
            DrumInsertStatement.setFloat(7, theDrum.getCableLength());
            DrumInsertStatement.setFloat(8, theDrum.getCableDensity());
            DrumInsertStatement.setFloat(9, theDrum.getSystemEquivalentMass());
            DrumInsertStatement.setInt(10, theDrum.getNumLaunches());
            DrumInsertStatement.setFloat(11, theDrum.getMaxTension());
            DrumInsertStatement.setInt(12, theDrum.getWinchId());
            DrumInsertStatement.setString(13, theDrum.getOptionalInfo());
            DrumInsertStatement.executeUpdate();
            DrumInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Drum to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    public static boolean mergeDrum() {
                try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO Drum o "
                                + "USING tempDrum t "
                                + "ON o.drum_id = t.drum_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.winch_id = t.winch_id, o.drum_name = t.drum_name, "
                                    + "o.drum_number = t.drum_number, o.core_diameter = t.core_diameter, o.kfactor = t.kfactor, "
                                    + "o.spring_const = t.spring_const, o.cable_length = t.cable_length, o.cable_density = t.cable_density, "
                                    + "o.drum_system_emass = t.drum_system_emass, o.number_of_launches = t.number_of_launches, "
                                    + "o.maximum_working_tension = t.maximum_working_tension, o.optional_info = t.optional_info "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.drum_id, t.winch_id, t.drum_name, t.drum_number, t.core_diameter, t.kfactor, t.spring_const, "
                                    + "t.cable_length, t.cable_density, t.drum_system_emass, t.number_of_launches, t.maximum_working_tension, "
                                    + "t.optional_info)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge Drum to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    
    
    
    /**
     * Adds the relevant data for a parachute to the database
     *
     * @param theParachute the runway to add to the database
     * @return false if add fails
     */
    public static boolean addParachuteToTempDB(Parachute theParachute) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement ParachuteInsertStatement = connect.prepareStatement(
                    "INSERT INTO TempParachute(parachute_id, name, lift, drag, weight, optional_info) "
                    + "values (?,?,?,?,?,?)");
            ParachuteInsertStatement.setInt(1, theParachute.getParachuteId());
            ParachuteInsertStatement.setString(2, theParachute.getName());
            ParachuteInsertStatement.setFloat(3, theParachute.getLift());
            ParachuteInsertStatement.setFloat(4, theParachute.getDrag());
            ParachuteInsertStatement.setFloat(5, theParachute.getWeight());
            ParachuteInsertStatement.setFloat(6, theParachute.getWeight());
            ParachuteInsertStatement.executeUpdate();
            ParachuteInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Parachute to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }

    /**
     * Adds the relevant data for a profile to the database
     *
     * @param theOperator the profile to add to the database
     * @return false if add fails
     */
    public static boolean addOperatorToDB(Operator theOperator) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement ProfileInsertStatement = connect.prepareStatement(
                    "INSERT INTO Operator(operator_id, first_name, middle_name, last_name, admin,"
                    + "salt, hash, optional_info, unitSettings)"
                    + "values (?,?,?,?,?,?,?,?,?)");
            ProfileInsertStatement.setInt(1, theOperator.getID());
            ProfileInsertStatement.setString(2, theOperator.getFirst());
            ProfileInsertStatement.setString(3, theOperator.getMiddle());
            ProfileInsertStatement.setString(4, theOperator.getLast());
            ProfileInsertStatement.setBoolean(5, theOperator.getAdmin());
            ProfileInsertStatement.setString(6, "");
            ProfileInsertStatement.setString(7, "");
            ProfileInsertStatement.setString(8, theOperator.getInfo());
            ProfileInsertStatement.setString(9, theOperator.getUnitSettingsForStorage());
            ProfileInsertStatement.executeUpdate();
            ProfileInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Operator to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    
    /**
     * used to import an operator from a file
     *
     * @param operator the profile to add to the database
     * @param salt salt from a database backup
     * @param hash hash from a database backup
     * @return false if add fails
     */
    public static boolean addOperatorToDB(Operator operator, String salt, String hash) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement ProfileInsertStatement = connect.prepareStatement(
                    "INSERT INTO Operator(operator_id, first_name, middle_name, last_name, admin,"
                    + "salt, hash, optional_info, unitSettings)"
                    + "values (?,?,?,?,?,?,?,?,?)");
            ProfileInsertStatement.setInt(1, operator.getID());
            ProfileInsertStatement.setString(2, operator.getFirst());
            ProfileInsertStatement.setString(3, operator.getMiddle());
            ProfileInsertStatement.setString(4, operator.getLast());
            ProfileInsertStatement.setBoolean(5, operator.getAdmin());
            ProfileInsertStatement.setString(6, salt);
            ProfileInsertStatement.setString(7, hash);
            ProfileInsertStatement.setString(8, operator.getInfo());
            ProfileInsertStatement.setString(9, operator.getUnitSettingsForStorage());
            ProfileInsertStatement.executeUpdate();
            ProfileInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Operator to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    /**
     * used to import an operator from a file
     *
     * @param operator the profile to add to the database
     * @param salt salt from a database backup
     * @param hash hash from a database backup
     * @return false if add fails
     */
    public static boolean addOperatorToTempDB(Operator operator, String salt, String hash) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement ProfileInsertStatement = connect.prepareStatement(
                    "INSERT INTO tempOperator(operator_id, first_name, middle_name, last_name, admin,"
                    + "salt, hash, optional_info, unitSettings)"
                    + "values (?,?,?,?,?,?,?,?,?)");
            ProfileInsertStatement.setInt(1, operator.getID());
            ProfileInsertStatement.setString(2, operator.getFirst());
            ProfileInsertStatement.setString(3, operator.getMiddle());
            ProfileInsertStatement.setString(4, operator.getLast());
            ProfileInsertStatement.setBoolean(5, operator.getAdmin());
            ProfileInsertStatement.setString(6, salt);
            ProfileInsertStatement.setString(7, hash);
            ProfileInsertStatement.setString(8, operator.getInfo());
            ProfileInsertStatement.setString(9, operator.getUnitSettingsForStorage());
            ProfileInsertStatement.executeUpdate();
            ProfileInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Operator to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }
    
    public static boolean mergeOperator()
    {
         try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            String statement = "MERGE INTO Operator o "
                                + "USING tempOperator t "
                                + "ON o.operator_id = t.operator_id "
                                + "WHEN MATCHED THEN UPDATE SET "
                                    + "o.first_name = t.first_name, "
                                    + "o.middle_name = t.middle_name, o.last_name = t.last_name, "
                                    + "o.admin = t.admin, o.salt = t.salt, o.hash = t.hash, "
                                    + "o.optional_info = t.optional_info, o.unitsettings = t.unitsettings "
                                + "WHEN NOT MATCHED THEN INSERT "
                                    + "values (t.operator_id, t.first_name, t.middle_name, t.last_name, t.admin, t.salt, t.hash, "
                                    + "t.optional_info, t.unitsettings)";
            Statement mergeStatement = connect.createStatement();
            mergeStatement.execute(statement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not merge Operator to Database, Check Error Log").showAndWait();
            //logError(e);
            return false;
        }
        return true;
    }
    

    /**
     * Adds the relevant data for a profile to the database with a password
     *
     * @param operator the profile to add to the database
     * @param pass the password for the operator
     * @return false if add fails
     */
    public static boolean addOperatorToDB(Operator operator, String pass) {
        byte[] bsalt = new byte[pass.length()];
        String salt = "";
        SecureRandom ran = new SecureRandom();
        ran.nextBytes(bsalt);
        for (byte b : bsalt) {
            salt += b;
        }
        pass = salt + pass;

        byte[] hashedInput = new byte[pass.length() + 224];

        Whirlpool w = new Whirlpool();
        w.NESSIEinit();
        w.NESSIEadd(pass);
        w.NESSIEfinalize(hashedInput);
        String hash = w.display(hashedInput);

        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement ProfileInsertStatement = connect.prepareStatement(
                    "INSERT INTO Operator(operator_id, first_name, middle_name, last_name, admin,"
                    + "salt, hash, optional_info, unitSettings)"
                    + "values (?,?,?,?,?,?,?,?,?)");
            ProfileInsertStatement.setInt(1, operator.getID());
            ProfileInsertStatement.setString(2, operator.getFirst());
            ProfileInsertStatement.setString(3, operator.getMiddle());
            ProfileInsertStatement.setString(4, operator.getLast());
            ProfileInsertStatement.setBoolean(5, operator.getAdmin());
            ProfileInsertStatement.setString(6, salt);
            ProfileInsertStatement.setString(7, hash);
            ProfileInsertStatement.setString(8, operator.getInfo());
            ProfileInsertStatement.setString(9, operator.getUnitSettingsForStorage());
            ProfileInsertStatement.executeUpdate();
            ProfileInsertStatement.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Operator to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }

    //Special Key for keeping a reference from previous launch to previous airfield
    private static int Airfield_Key = -1;

    /**
     * Adds the relevant data for a Launch to the database based on the current
     * loaded information
     *
     * Make sure you keep track of when the run description changes
     *
     * @param startTime the start of the launch
     * @param endTime the end of the launch
     * @param airfieldChanged if airfield information has changed
     * @return false if add fails
     */
    public static boolean addLaunchToDB(double startTime, double endTime, boolean airfieldChanged) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement PreviousLaunchesInsert = connect.prepareStatement(
                    "INSERT INTO PreviousLaunchesInfo("
                    + "start_timestamp, "
                    + "end_timestamp, "
                    //Pilot info
                    + "first_name, "
                    + "last_name, "
                    + "middle_name, "
                    + "flight_weight, "
                    + "capability, "
                    + "preference, "
                    + "emergency_contact_name, "
                    + "emergency_contact_phone, "
                    + "pilot_optional_info, "
                    //Glider Info
                    + "reg_number, "
                    + "common_name, "
                    + "glider_owner, "
                    + "type, "
                    + "max_gross_weight, "
                    + "empty_weight, "
                    + "indicated_stall_speed, "
                    + "max_winching_speed,"
                    + "max_weak_link_strength,"
                    + "max_tension,"
                    + "cable_release_angle, "
                    + "carry_ballast, "
                    + "multiple_seats, "
                    + "glider_optional_info, "
                    //Enviroment info
                    + "wind_direction_winch, "
                    + "wind_average_speed_winch, "
                    + "wind_gust_speed_winch, "
                    + "wind_direction_glider, "
                    + "wind_average_speed_glider, "
                    + "wind_gust_speed_glider, "
                    + "density_altitude, "
                    + "temperature, "
                    + "altimeter_setting, "
                    //Drum
                    + "drum_name, "
                    + "drum_number, "
                    + "core_diameter, "
                    + "kfactor, "
                    + "spring_const, "
                    + "cable_length, "
                    + "cable_density, "
                    + "drum_system_emass, " //Drum System Equivalent Mass
                    + "number_of_launches, "
                    + "maximum_working_tension, "
                    //Parachute
                    + "parachute_name, "
                    + "parachute_lift, "
                    + "parachute_drag, "
                    + "parachute_weight, "
                    //Additional info
                    + "ballast, "
                    + "baggage, "
                    + "passenger_weight, "
                    + "airfield_info)"
                    + "values ("
                    + "?,?,?,?,?,?,?,?,?,?,"//10
                    + "?,?,?,?,?,?,?,?,?,?,"//10
                    + "?,?,?,?,?,?,?,?,?,?,"//10
                    + "?,?,?,?,?,?,?,?,?,?,"//10
                    + "?,?,?,?,?,?,?,?,?,?,?,?)");//12
            CurrentDataObjectSet currentDataObjectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
            PreviousLaunchesInsert.setTimestamp(1, new Timestamp((long) startTime));
            PreviousLaunchesInsert.setTimestamp(2, new Timestamp((long) endTime));
            //Pilot
            PreviousLaunchesInsert.setString(3, currentDataObjectSet.getCurrentPilot().getFirstName());
            PreviousLaunchesInsert.setString(4, currentDataObjectSet.getCurrentPilot().getLastName());
            PreviousLaunchesInsert.setString(5, currentDataObjectSet.getCurrentPilot().getMiddleName());
            PreviousLaunchesInsert.setFloat(6, currentDataObjectSet.getCurrentPilot().getWeight());
            PreviousLaunchesInsert.setInt(7,
                    Capability.convertCapabilityStringToNum(currentDataObjectSet.getCurrentPilot().getCapability()));
            PreviousLaunchesInsert.setFloat(8, currentDataObjectSet.getCurrentPilot().getPreference());
            PreviousLaunchesInsert.setString(9, currentDataObjectSet.getCurrentPilot().getEmergencyName());
            PreviousLaunchesInsert.setString(10, currentDataObjectSet.getCurrentPilot().getEmergencyPhone());
            PreviousLaunchesInsert.setString(11, currentDataObjectSet.getCurrentPilot().getOptionalInfo());
            //Glider
            PreviousLaunchesInsert.setString(12, currentDataObjectSet.getCurrentSailplane().getRegNumber());
            PreviousLaunchesInsert.setString(13, currentDataObjectSet.getCurrentSailplane().getName());
            PreviousLaunchesInsert.setString(14, currentDataObjectSet.getCurrentSailplane().getOwner());
            PreviousLaunchesInsert.setString(15, currentDataObjectSet.getCurrentSailplane().getType());
            PreviousLaunchesInsert.setFloat(16, currentDataObjectSet.getCurrentSailplane().getMaxGrossWeight());
            PreviousLaunchesInsert.setFloat(17, currentDataObjectSet.getCurrentSailplane().getEmptyWeight());
            PreviousLaunchesInsert.setFloat(18, currentDataObjectSet.getCurrentSailplane().getIndicatedStallSpeed());
            PreviousLaunchesInsert.setFloat(19, currentDataObjectSet.getCurrentSailplane().getMaxWinchingSpeed());
            PreviousLaunchesInsert.setFloat(20, currentDataObjectSet.getCurrentSailplane().getMaxWeakLinkStrength());
            PreviousLaunchesInsert.setFloat(21, currentDataObjectSet.getCurrentSailplane().getMaxTension());
            PreviousLaunchesInsert.setFloat(22, currentDataObjectSet.getCurrentSailplane().getCableReleaseAngle());
            PreviousLaunchesInsert.setBoolean(23, currentDataObjectSet.getCurrentSailplane().getCarryBallast());
            PreviousLaunchesInsert.setBoolean(24, currentDataObjectSet.getCurrentSailplane().getMultipleSeats());
            PreviousLaunchesInsert.setString(25, currentDataObjectSet.getCurrentSailplane().getOptionalInfo());

            CurrentLaunchInformation currentLaunchInformation = CurrentLaunchInformation.getCurrentLaunchInformation();

            PreviousLaunchesInsert.setFloat(26, currentLaunchInformation.getWindDirectionWinch());
            PreviousLaunchesInsert.setFloat(27, currentLaunchInformation.getWindSpeedWinch());
            PreviousLaunchesInsert.setFloat(28, currentLaunchInformation.getWindGustWinch());
            PreviousLaunchesInsert.setFloat(29, currentLaunchInformation.getWindDirectionGlider());
            PreviousLaunchesInsert.setFloat(30, currentLaunchInformation.getWindSpeedGlider());
            PreviousLaunchesInsert.setFloat(31, currentLaunchInformation.getWindGustGlider());
            PreviousLaunchesInsert.setFloat(32, currentLaunchInformation.getDensityAltitude());
            PreviousLaunchesInsert.setFloat(33, currentLaunchInformation.getTemperature());
            PreviousLaunchesInsert.setFloat(34, currentLaunchInformation.getAltimeter());
            //Drum
            PreviousLaunchesInsert.setString(35, currentDataObjectSet.getCurrentDrum().getName());
            PreviousLaunchesInsert.setInt(36, currentDataObjectSet.getCurrentDrum().getDrumNumber());
            PreviousLaunchesInsert.setFloat(37, currentDataObjectSet.getCurrentDrum().getCoreDiameter());
            PreviousLaunchesInsert.setFloat(38, currentDataObjectSet.getCurrentDrum().getKFactor());
            PreviousLaunchesInsert.setFloat(39, currentDataObjectSet.getCurrentDrum().getSpringConstant());
            PreviousLaunchesInsert.setFloat(40, currentDataObjectSet.getCurrentDrum().getCableLength());
            PreviousLaunchesInsert.setFloat(41, currentDataObjectSet.getCurrentDrum().getCableDensity());
            PreviousLaunchesInsert.setFloat(42, currentDataObjectSet.getCurrentDrum().getSystemEquivalentMass());
            PreviousLaunchesInsert.setFloat(43, currentDataObjectSet.getCurrentDrum().getNumLaunches());
            PreviousLaunchesInsert.setFloat(44, currentDataObjectSet.getCurrentDrum().getMaxTension());
            //Parachute
            PreviousLaunchesInsert.setString(45, currentDataObjectSet.getCurrentDrum().getParachute().getName());
            PreviousLaunchesInsert.setFloat(46, currentDataObjectSet.getCurrentDrum().getParachute().getLift());
            PreviousLaunchesInsert.setFloat(47, currentDataObjectSet.getCurrentDrum().getParachute().getDrag());
            PreviousLaunchesInsert.setFloat(48, currentDataObjectSet.getCurrentDrum().getParachute().getWeight());
            //Additional info
            PreviousLaunchesInsert.setFloat(49, currentLaunchInformation.getGliderBallast());
            PreviousLaunchesInsert.setFloat(50, currentLaunchInformation.getGliderBaggage());
            PreviousLaunchesInsert.setFloat(51, currentLaunchInformation.getPassengerWeight());

            if (airfieldChanged || Airfield_Key == -1) {
                Random randomId = new Random();
                Airfield_Key = randomId.nextInt(100000000);
                while (DatabaseEntryIdCheck.IdCheck(Airfield_Key)) {
                    Airfield_Key = randomId.nextInt(100000000);
                }
                PreparedStatement PreviousAirfieldInsert = connect.prepareStatement(
                        "INSERT INTO PreviousAirfieldInfo("
                        + "table_id, "
                        //Airfield
                        + "airfield_name, "
                        + "airfield_designator, "
                        + "airfield_elevation, "
                        + "airfield_magnetic_variation, "
                        + "airfield_latitude, "
                        + "airfield_longitude, "
                        + "airfield_utc_offset, "
                        //Runway
                        + "runway_name, "
                        + "runway_magnetic_heading, "
                        //Glider Position
                        + "glider_position_name, "
                        + "glider_position_elevation, "
                        + "glider_position_latitude, "
                        + "glider_position_longitude, "
                        //Winch
                        + "winch_position_name, "
                        + "winch_position_elevation, "
                        + "winch_position_latitude, "
                        + "winch_position_longitude, "
                        //Winch
                        + "winch_name, "
                        + "winch_owner, "
                        + "values (?,?,?,?,?,?,?,?,?,?," //10
                        + "?,?,?,?,?,?,?,?,?,?)"); //10
                PreviousAirfieldInsert.setInt(1, Airfield_Key);
                //Airfield
                PreviousAirfieldInsert.setString(2, currentDataObjectSet.getCurrentAirfield().getName());
                PreviousAirfieldInsert.setString(3, currentDataObjectSet.getCurrentAirfield().getDesignator());
                PreviousAirfieldInsert.setFloat(4, currentDataObjectSet.getCurrentAirfield().getElevation());
                PreviousAirfieldInsert.setFloat(5, currentDataObjectSet.getCurrentAirfield().getMagneticVariation());
                PreviousAirfieldInsert.setFloat(6, currentDataObjectSet.getCurrentAirfield().getLatitude());
                PreviousAirfieldInsert.setFloat(7, currentDataObjectSet.getCurrentAirfield().getLongitude());
                PreviousAirfieldInsert.setFloat(8, currentDataObjectSet.getCurrentAirfield().getUTC());
                //Runway
                PreviousAirfieldInsert.setString(9, currentDataObjectSet.getCurrentRunway().getName());
                PreviousAirfieldInsert.setFloat(10, currentDataObjectSet.getCurrentRunway().getMagneticHeading());
                //Glider Position
                PreviousAirfieldInsert.setString(11, currentDataObjectSet.getCurrentGliderPosition().getName());
                PreviousAirfieldInsert.setFloat(12, currentDataObjectSet.getCurrentGliderPosition().getElevation());
                PreviousAirfieldInsert.setFloat(13, currentDataObjectSet.getCurrentGliderPosition().getLatitude());
                PreviousAirfieldInsert.setFloat(14, currentDataObjectSet.getCurrentGliderPosition().getLongitude());
                //WinchPosition
                PreviousAirfieldInsert.setString(15, currentDataObjectSet.getCurrentWinchPosition().getName());
                PreviousAirfieldInsert.setFloat(16, currentDataObjectSet.getCurrentWinchPosition().getElevation());
                PreviousAirfieldInsert.setFloat(17, currentDataObjectSet.getCurrentWinchPosition().getLatitude());
                PreviousAirfieldInsert.setFloat(18, currentDataObjectSet.getCurrentWinchPosition().getLongitude());
                //Winch
                PreviousAirfieldInsert.setString(19, currentDataObjectSet.getCurrentWinch().getName());
                PreviousAirfieldInsert.setString(20, currentDataObjectSet.getCurrentWinch().getOwner());

                PreviousAirfieldInsert.executeUpdate();
                PreviousAirfieldInsert.close();
            }

            PreviousLaunchesInsert.setInt(52, Airfield_Key);
            PreviousLaunchesInsert.executeUpdate();
            PreviousLaunchesInsert.close();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Could not add Launch to Database, Check Error Log").showAndWait();
            logError(e);
            return false;
        }
        return true;
    }

    public static void addMessageToFlightMessages(long time, String message)
            throws SQLException, ClassNotFoundException {
        try (Connection connect = connect()) {
            PreparedStatement insertStatement = connect.prepareStatement(
                    "INSERT INTO Messages(timestamp, message)"
                    + "values(?,?)");
            insertStatement.setString(1, String.valueOf(time));
            insertStatement.setString(2, message);

            insertStatement.executeUpdate();
            insertStatement.close();
        } catch (SQLException e) {
            System.out.println("Error 2");
            e.printStackTrace();
            throw e;
        }
    }

}
