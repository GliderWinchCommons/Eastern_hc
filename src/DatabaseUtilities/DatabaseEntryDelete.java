/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseUtilities;

import static Communications.ErrorLogger.logError;
import java.sql.*;

import DataObjects.*;
import static DatabaseUtilities.DatabaseInitialization.connect;
import javafx.scene.control.Alert;

/**
 *
 * @author dbennett3
 */
public class DatabaseEntryDelete {

    //workhorse method, does the actual deleting
    private static boolean Delete(PreparedStatement ps) throws SQLException {
        //Update the value given
        ps.execute();
        ps.close();
        return true;
    }

    /* Long list of overloaded methods called DeleteEntry
     * they are used to delete table entries that have a specific data object
     * attached to it. return's true if delete was successful, false if not
     */
    public static boolean DeleteEntry(Pilot pilot) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM PILOT WHERE pilot_id = ?");
            stmt.setInt(1, pilot.getPilotId());
            return Delete(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not delete Pilot, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    public static boolean DeleteEntry(Sailplane sailplane) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM GLIDER WHERE glider_id = ?");
            stmt.setInt(1, sailplane.getId());
            return Delete(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not delete Glider, Check Error Log").showAndWait();
            logError(e);
        }
        return false;

    }

    public static boolean DeleteEntry(Airfield airfield) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM AIRFIELD WHERE airfield_id = ?");
            stmt.setInt(1, airfield.getId());
            return Delete(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not delete Airfield, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    public static boolean DeleteEntry(Runway runway) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM RUNWAY WHERE runway_id = ?");
            stmt.setInt(1, runway.getId());
            return Delete(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not delete Runway, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    public static boolean DeleteEntry(GliderPosition position) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM GLIDERPOSITION WHERE glider_position_id = ?");
            stmt.setInt(1, position.getId());
            return Delete(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not delete Glider Position, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    public static boolean DeleteEntry(WinchPosition position) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM WINCHPOSITION WHERE winch_position_id = ?");
            stmt.setInt(1, position.getId());
            return Delete(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not delete Winch Position, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    public static boolean DeleteEntry(Drum drum) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM DRUM WHERE drum_id = ?");
            stmt.setInt(1, drum.getId());
            return Delete(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not delete Drum, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }

    public static boolean DeleteEntry(Parachute parachute) {
        try (Connection conn = connect()) {
            if (conn == null) {
                return false;
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM PARACHUTE WHERE parachute_id = ?");
            stmt.setInt(1, parachute.getParachuteId());
            return Delete(stmt);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not delete Parachute, Check Error Log").showAndWait();
            logError(e);
        }
        return false;
    }
}
