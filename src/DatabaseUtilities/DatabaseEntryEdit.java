/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseUtilities;

import static Communications.ErrorLogger.logError;
import java.sql.*;

import DataObjects.*;
import static DatabaseUtilities.DatabaseInitialization.WINCH_PRAM_VERSION;
import static DatabaseUtilities.DatabaseInitialization.connect;
import ParameterSelection.Capability;
import java.security.SecureRandom;
import javafx.scene.control.Alert;

/**
 *
 * @author dbennett3
 */
public class DatabaseEntryEdit {

    /**
     * performs the update string created in the functions below
     *
     * @param updateString update sql string to be executed
     * @throws SQLException
     */
    private static boolean Update(PreparedStatement ps) throws SQLException {
        //Update the value given
        ps.executeUpdate();
        ps.close();
        return true;
    }

    /**
     * updates the pilot table in the database with the new pilot data in the
     * object passed in
     *
     * @param pilot pilot object that is to be updated
     * @return false if update fails
     */
    public static boolean updateEntry(Pilot pilot) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            String updateString = "UPDATE PILOT SET "
                    + "first_name = ?, middle_name = ?, last_name = ?, flight_weight = ?, "
                    + "capability = ?, preference = ?, emergency_contact_name = ?, "
                    + "emergency_contact_phone = ?, optional_info = ? "
                    + "WHERE pilot_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateString);
            stmt.setString(1, pilot.getFirstName());
            stmt.setString(2, pilot.getMiddleName());
            stmt.setString(3, pilot.getLastName());
            stmt.setFloat(4, pilot.getWeight());
            stmt.setInt(5, Capability.convertCapabilityStringToNum(pilot.getCapability()));
            stmt.setFloat(6, pilot.getPreference());
            stmt.setString(7, pilot.getEmergencyName());
            stmt.setString(8, pilot.getEmergencyPhone());
            stmt.setString(9, pilot.getOptionalInfo());
            stmt.setInt(10, pilot.getPilotId());

            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update Pilot, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * updates the operator table in the database with the new operator data in
     * the object passed in
     *
     * @param theOperator profile object that is to be updated
     * @return false if update fails
     */
    public static boolean UpdateEntry(Operator theOperator) {
        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement stmt = connect.prepareStatement(
                    "UPDATE Operator SET first_name = ?, middle_name = ?, last_name = ?, "
                    + "admin = ?, optional_info = ?, "
                    + "unitSettings = ? WHERE operator_id = ?");
            stmt.setString(1, theOperator.getFirst());
            stmt.setString(2, theOperator.getMiddle());
            stmt.setString(3, theOperator.getLast());
            stmt.setBoolean(4, theOperator.getAdmin());
            stmt.setString(5, theOperator.getInfo());
            stmt.setString(6, theOperator.getUnitSettingsForStorage());
            stmt.setInt(7, theOperator.getID());

            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update Operator, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * Changes the password of the operator passed in
     *
     * @param theOperator operator object that is to be updated
     * @param newPass new password of the current operator
     * @return false if update fails
     */
    public static boolean ChangePassword(Operator theOperator, String newPass) {
        byte[] bsalt = new byte[newPass.length()];
        String salt = "";
        SecureRandom ran = new SecureRandom();
        ran.nextBytes(bsalt);
        for (byte b : bsalt) {
            salt += b;
        }
        newPass = salt + newPass;

        byte[] hashedInput = new byte[newPass.length() + 224];

        Whirlpool w = new Whirlpool();
        w.NESSIEinit();
        w.NESSIEadd(newPass);
        w.NESSIEfinalize(hashedInput);
        String hash = w.display(hashedInput);

        try (Connection connect = connect()) {
            if (connect == null) {
                return false;
            }
            PreparedStatement stmt = connect.prepareStatement(
                    "UPDATE Operator SET salt = ?, hash = ? WHERE operator_id = ?");
            stmt.setString(1, salt);
            stmt.setString(2, hash);
            stmt.setInt(3, theOperator.getID());

            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not change operator's password, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * updates the sailplane table in the database with the new sailplane data
     * in the object passed in
     *
     * @param sailplane sailplane object that is to be updated
     * @return false if update fails
     */
    public static boolean UpdateEntry(Sailplane sailplane) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            String updateString = "UPDATE GLIDER SET "
                    + "reg_number = ?, common_name = ?, owner = ?, type = ?, "
                    + "max_gross_weight = ?, empty_weight = ?, indicated_stall_speed = ?, "
                    + "max_winching_speed = ?, max_weak_link_strength = ?, max_tension = ?, "
                    + "cable_release_angle = ?, carry_ballast = ?, multiple_seats = ?, optional_info = ? "
                    + "WHERE glider_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateString);
            stmt.setString(1, sailplane.getRegistration());
            stmt.setString(2, sailplane.getName());
            stmt.setString(3, sailplane.getOwner());
            stmt.setString(4, sailplane.getType());
            stmt.setFloat(5, sailplane.getMaxGrossWeight());
            stmt.setFloat(6, sailplane.getEmptyWeight());
            stmt.setFloat(7, sailplane.getIndicatedStallSpeed());
            stmt.setFloat(8, sailplane.getMaxWinchingSpeed());
            stmt.setFloat(9, sailplane.getMaxWeakLinkStrength());
            stmt.setFloat(10, sailplane.getMaxTension());
            stmt.setFloat(11, sailplane.getCableReleaseAngle());
            stmt.setBoolean(12, sailplane.getCarryBallast());
            stmt.setBoolean(13, sailplane.getMultipleSeats());
            stmt.setString(14, sailplane.getOptionalInfo());
            stmt.setInt(15, sailplane.getId());

            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update Glider, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * updates the airfield table in the database with the new airfield data in
     * the object passed in
     *
     * @param airfield airfield object that is to be updated
     * @return false if update fails
     */
    public static boolean UpdateEntry(Airfield airfield) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            String updateString = "UPDATE AIRFIELD SET "
                    + "name = ?, designator = ?, elevation = ?, magnetic_variation = ?, "
                    + "latitude = ?, longitude = ?, utc_offset = ?, optional_info = ? "
                    + "WHERE airfield_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateString);

            stmt.setString(1, airfield.getName());
            stmt.setString(2, airfield.getDesignator());
            stmt.setFloat(3, airfield.getElevation());
            stmt.setFloat(4, airfield.getMagneticVariation());
            stmt.setFloat(5, airfield.getLatitude());
            stmt.setFloat(6, airfield.getLongitude());
            stmt.setInt(7, airfield.getUTC());
            stmt.setString(8, airfield.getOptionalInfo());
            stmt.setInt(9, airfield.getId());

            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update Airfield, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * updates the runway table in the database with the new runway data in the
     * object passed in
     *
     * @param runway runway object that is to be updated
     * @return false if update fails
     */
    public static boolean UpdateEntry(Runway runway) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            String updateString = "UPDATE RUNWAY SET "
                    + "runway_name = ?, "
                    + "magnetic_heading = ?, "
                    + "optional_info = ? "
                    + "WHERE runway_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateString);
            stmt.setString(1, runway.getName());
            stmt.setFloat(2, runway.getMagneticHeading());
            stmt.setString(3, runway.getOptionalInfo());
            stmt.setInt(4, runway.getId());

            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update Runway, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * updates the GliderPosition table in the database with the new position
     * data in the object passed in
     *
     * @param position GliderPosition object that is to be updated
     * @return false if update fails
     */
    public static boolean UpdateEntry(GliderPosition position) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            String updateString = "UPDATE GLIDERPOSITION SET "
                    + "position_name = ?, elevation = ?, latitude = ?, "
                    + "longitude = ?, optional_info = ? "
                    + "WHERE glider_position_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateString);
            stmt.setString(1, position.getName());
            stmt.setFloat(2, position.getElevation());
            stmt.setFloat(3, position.getLatitude());
            stmt.setFloat(4, position.getLongitude());
            stmt.setString(5, position.getOptionalInfo());
            stmt.setInt(6, position.getId());

            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update Glider Position, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * updates the WinchPosition table in the database with the new position
     * data in the object passed in
     *
     * @param position WinchPosition object that is to be updated
     * @return false if update fails
     */
    public static boolean UpdateEntry(WinchPosition position) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            String updateString = "UPDATE WINCHPOSITION SET "
                    + "position_name = ?, elevation = ?, latitude = ?, "
                    + "longitude = ?, optional_info = ? "
                    + "WHERE winch_position_id = ? ";
            PreparedStatement stmt = conn.prepareStatement(updateString);

            stmt.setString(1, position.getName());
            stmt.setFloat(2, position.getElevation());
            stmt.setFloat(3, position.getLatitude());
            stmt.setFloat(4, position.getLongitude());
            stmt.setString(5, position.getOptionalInfo());
            stmt.setInt(6, position.getId());

            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update Winch Position, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * updates the parachute in the database with the new parachute data in the
     * object passed in
     *
     * @param drum drum object that is to be updated
     * @return false if update fails
     */
    public static boolean UpdateEntry(Drum drum) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            String updateString = "UPDATE DRUM SET "
                    + "drum_name = ?, "
                    + "drum_number = ?, "
                    + "core_diameter = ?, "
                    + "kfactor = ?, "
                    + "spring_const = ?, "
                    + "cable_length = ?, "
                    + "cable_density = ?, "
                    + "drum_system_emass = ?, "
                    + "number_of_launches = ?, "
                    + "maximum_working_tension = ?, "
                    + "optional_info = ? "
                    + "WHERE drum_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateString);
            stmt.setString(1, drum.getName());
            stmt.setInt(2, drum.getDrumNumber());
            stmt.setFloat(3, drum.getCoreDiameter());
            stmt.setFloat(4, drum.getKFactor());
            stmt.setFloat(5, drum.getSpringConstant());
            stmt.setFloat(6, drum.getCableLength());
            stmt.setFloat(7, drum.getCableDensity());
            stmt.setFloat(8, drum.getSystemEquivalentMass());
            stmt.setInt(9, drum.getNumLaunches());
            stmt.setFloat(10, drum.getMaxTension());
            stmt.setString(11, drum.getOptionalInfo());
            stmt.setInt(12, drum.getId());
            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update drum, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * updates the parachute in the database with the new parachute data in the
     * object passed in
     *
     * @param winch object that is to be updated
     * @return false if update fails
     */
    public static boolean UpdateEntry(Winch winch) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            String updateString = "UPDATE Winch SET "
                    + "name = ?, "
                    + "owner = ?, "
                    + "wc_version = ?, "
                    + "w1 = ?, w2 = ?, w3 = ?, w4 = ?, w5 = ?, "
                    + "w6 = ?, w7 = ?, w8 = ?, w9 = ?, w10 = ?, "
                    + "w11 = ?, w12 = ?, w13 = ?, w14 = ?, w15 = ?, "
                    + "w16 = ?, "
                    + "meteorological_check_time = ?, "
                    + "meteorological_verify_time = ?, "
                    + "run_orientation_tolerance = ?, "
                    + "optional_info = ? "
                    + "WHERE winch_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateString);

            stmt.setString(1, winch.getName());
            stmt.setString(2, winch.getOwner());
            stmt.setString(3, WINCH_PRAM_VERSION);
            stmt.setFloat(4, winch.getW1());
            stmt.setFloat(5, winch.getW2());
            stmt.setFloat(6, winch.getW3());
            stmt.setFloat(7, winch.getW4());
            stmt.setFloat(8, winch.getW5());
            stmt.setFloat(9, winch.getW6());
            stmt.setFloat(10, winch.getW7());
            stmt.setFloat(11, winch.getW8());
            stmt.setFloat(12, winch.getW9());
            stmt.setFloat(13, winch.getW10());
            stmt.setFloat(14, winch.getW11());
            stmt.setFloat(15, winch.getW12());
            stmt.setFloat(16, winch.getW13());
            stmt.setFloat(17, winch.getW14());
            stmt.setFloat(18, winch.getW15());
            stmt.setFloat(19, winch.getW16());
            stmt.setInt(20, winch.meteorologicalCheckTime());
            stmt.setInt(21, winch.meteorologicalVerifyTime());
            stmt.setFloat(22, winch.runOrientationTolerance());
            stmt.setString(23, winch.getOptionalInfo());
            stmt.setInt(24, winch.getId());

            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update Winch, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    /**
     * updates the parachute in the database with the new parachute data in the
     * object passed in
     *
     * @param parachute parachute object that is to be updated
     * @return false if update fails
     */
    public static boolean UpdateEntry(Parachute parachute) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            String updateString = "UPDATE PARACHUTE SET "
                    + "name = ?, lift = ?, drag = ?, "
                    + "weight = ?, optional_info = ? "
                    + "WHERE parachute_id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateString);
            stmt.setString(1, parachute.getName());
            stmt.setFloat(2, parachute.getLift());
            stmt.setFloat(3, parachute.getDrag());
            stmt.setFloat(4, parachute.getWeight());
            stmt.setString(5, parachute.getInfo());
            stmt.setInt(6, parachute.getParachuteId());
            return Update(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not update Parachute, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }
}
