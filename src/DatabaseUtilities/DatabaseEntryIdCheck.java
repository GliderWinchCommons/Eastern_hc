/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseUtilities;

import static Communications.ErrorLogger.logError;
import java.sql.*;

import DataObjects.*;
import static DatabaseUtilities.DatabaseInitialization.connectEx;
import javafx.scene.control.Alert;

/**
 *
 * @author nfujioka
 */
public class DatabaseEntryIdCheck {

    //workhorse method, does the actual checking of the ids
    private static boolean IdCheck(PreparedStatement stmt) throws SQLException {
        boolean result;
        //Update the value given
        ResultSet theIds = stmt.executeQuery();
        result = theIds.next();
        stmt.close();
        return result;
    }

    /* These methods will make sure that the randomly generated IDs 
     * for each data object is a unique primary key
     */
    public static boolean IdCheck(Pilot pilot) throws SQLException, ClassNotFoundException {
        try (Connection conn = connectEx()) {
            if (conn == null) {
                throw new SQLException();
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PILOT WHERE pilot_id = ?");
            stmt.setInt(1, pilot.getPilotId());

            return IdCheck(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            logError(e);
            throw e;
        }
    }

    public static boolean IdCheck(Sailplane sailplane) throws SQLException, ClassNotFoundException {
        try (Connection conn = connectEx()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM GLIDER WHERE glider_id = ?");
            stmt.setInt(1, sailplane.getId());

            return IdCheck(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            logError(e);
            throw e;
        }
    }

    public static boolean IdCheck(Airfield airfield) throws SQLException, ClassNotFoundException {
        try (Connection conn = connectEx()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AIRFIELD WHERE airfield_id = ?");
            stmt.setInt(1, airfield.getId());

            return IdCheck(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            logError(e);
            throw e;
        }
    }

    public static boolean IdCheck(Runway runway) throws SQLException, ClassNotFoundException {
        try (Connection conn = connectEx()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RUNWAY WHERE runway_id = ?");
            stmt.setInt(1, runway.getId());

            return IdCheck(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            logError(e);
            throw e;
        }
    }

    public static boolean IdCheck(GliderPosition position) throws SQLException, ClassNotFoundException {
        try (Connection conn = connectEx()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM GLIDERPOSITION WHERE glider_position_id = ?");
            stmt.setInt(1, position.getId());

            return IdCheck(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            logError(e);
            throw e;
        }
    }

    public static boolean IdCheck(WinchPosition position) throws SQLException, ClassNotFoundException {
        try (Connection conn = connectEx()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM WINCHPOSITION WHERE winch_position_id = ?");
            stmt.setInt(1, position.getId());

            return IdCheck(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            logError(e);
            throw e;
        }
    }

    public static boolean IdCheck(Drum drum) throws SQLException, ClassNotFoundException {
        try (Connection conn = connectEx()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DRUM WHERE drum_id = ?");
            stmt.setInt(1, drum.getId());

            return IdCheck(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            logError(e);
            throw e;
        }
    }

    public static boolean IdCheck(Parachute parachute) throws SQLException, ClassNotFoundException {
        try (Connection conn = connectEx()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PARACHUTE WHERE parachute_id = ?");
            stmt.setInt(1, parachute.getParachuteId());

            return IdCheck(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            logError(e);
            throw e;
        }
    }

    public static boolean IdCheck(Operator operator) throws SQLException, ClassNotFoundException {
        try (Connection conn = connectEx()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Operator WHERE operator_id = ?");
            stmt.setInt(1, operator.getID());

            return IdCheck(stmt);
        } catch (SQLException | ClassNotFoundException e) {
            logError(e);
            throw e;
        }
    }

    static boolean IdCheck(int Airfield_Key) throws SQLException, ClassNotFoundException {
        {
            try (Connection conn = connectEx()) {
                if (conn == null) {
                    return false;
                }
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PreviousAirfieldInfo WHERE table_id = ?");
                stmt.setInt(1, Airfield_Key);

                return IdCheck(stmt);
            } catch (SQLException | ClassNotFoundException e) {
                logError(e);
                throw e;
            }
        }
    }

    /* The password matching method, since operators don't know what thier password is
     * (for obvious security reasons) so the operator and the password to check against
     * are matched here to see if it matches the hash.
     */
    public static boolean matchPassword(Operator operator, String check)
            throws SQLException, ClassNotFoundException {
        String salt, pass;
        try (Connection conn = connectEx()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT salt, hash "
                    + "FROM Operator WHERE operator_id = ?");
            stmt.setInt(1, operator.getID());
            ResultSet op = stmt.executeQuery();
            op.next();
            salt = op.getString("salt");
            pass = op.getString("hash");

            check = salt + check;
            byte[] hashedInput = new byte[check.length() + 224];

            Whirlpool w = new Whirlpool();
            w.NESSIEinit();
            w.NESSIEadd(check);
            w.NESSIEfinalize(hashedInput);
            String hash = w.display(hashedInput);
            return hash.compareTo(pass) == 0;
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "There was a problem in the database, Check Error Log").showAndWait();
            logError(e);
            throw e;
        }
    }
}
