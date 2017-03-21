package DatabaseUtilities;

import static Communications.ErrorLogger.logError;
import static DatabaseUtilities.DatabaseInitialization.*;
import java.sql.*;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author gene
 */
public class DatabaseVerification {

    //Verify if the database is complete
    public static boolean verifyDatabase() {
        try (Statement stmt = connect().createStatement()) {
            stmt.executeQuery("SELECT * FROM Operator");
            stmt.executeQuery("SELECT * FROM Pilot");
            stmt.executeQuery("SELECT * FROM Winch");
            stmt.executeQuery("SELECT * FROM Drum");
            stmt.executeQuery("SELECT * FROM Glider");
            stmt.executeQuery("SELECT * FROM PreviousLaunches NATURAL JOIN PreviousAirfieldInfo");
            stmt.executeQuery("SELECT * FROM Airfield");
            stmt.executeQuery("SELECT * FROM GliderPosition");
            stmt.executeQuery("SELECT * FROM WinchPosition");
            stmt.executeQuery("SELECT * FROM Runway");
            stmt.executeQuery("SELECT * FROM Parachute");
            stmt.close();
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error detected in the database,\nattpmpting to repair.").showAndWait();
            return repairDatabase();
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, "Critical Error connecting to database,\n"
                    + "Program is unable to execute").showAndWait();
        }
        return false;
    }

    private static boolean repairDatabase() {
        Connection conn = connect();
        if (conn == null) {
            return false;//If connection failed it should of been caught in the above method
        }
        Statement stmt;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Critical Error connecting to database,\n"
                    + "Program is unable to execute").showAndWait();
            logError(e);
            return false;//If creating the statment fails then theres really nothing you can do
        }

        try {
            //You might be able to get rid of these tables if you wanted
            stmt.execute("SELECT * FROM PilotUnits");

            stmt.execute("SELECT * FROM GliderUnits");

            stmt.execute("SELECT * FROM AirfieldUnits");

            stmt.execute("SELECT * FROM PositionUnits");

            stmt.execute("SELECT * FROM DashboardUnits");

            stmt.execute("SELECT * FROM EnvironmentalUnits");

            stmt.execute("SELECT * FROM DistanceUnits");

            stmt.execute("SELECT * FROM TensionUnits");

            stmt.execute("SELECT * FROM WeightUnits");

            stmt.execute("SELECT * FROM VelocityUnits");

            stmt.execute("SELECT * FROM TemperatureUnits");

            stmt.execute("SELECT * FROM PressureUnits");

            stmt.execute("SELECT * FROM Messages");
        } catch (SQLException e) {
            logError(e);
            /*
            Optional<ButtonType> choice = new Alert(Alert.AlertType.WARNING, "One or more of the "
                    + "Unit Tables are missing,\n"
                    + "These Tables are probably not importatnt.\n"
                    + "Do you want to rebuild the whole database?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (choice.get() == ButtonType.NO) {
                return false;
            }
             */
            buildDatabase(conn);//build the whole database
        }

        //the rest of these will attempt to fix any broken tables.
        //some are nested to maintain the table hiarchy
        try {
            stmt.execute("SELECT * FROM Capability");//Would also want to get rid of this
            try {
                stmt.executeQuery("SELECT * FROM Pilot");
            } catch (SQLException e) {
                logError(e);
                cleanPilot(conn);
            }
            try {
                stmt.executeQuery("SELECT * FROM PreviousLaunches NATURAL JOIN PreviousAirfieldInfo");
            } catch (SQLException e) {
                logError(e);
                cleanPrevAirfield(conn);
            }
        } catch (SQLException e) {
            logError(e);
            cleanCapability(conn);
        }
        try {
            stmt.executeQuery("SELECT * FROM Operator");
        } catch (SQLException e) {
            logError(e);
            cleanOperator(conn);
        }
        try {
            stmt.executeQuery("SELECT * FROM Winch");
            try {
                stmt.executeQuery("SELECT * FROM Drum");
            } catch (SQLException e) {
                logError(e);
                cleanDrum(conn);
            }
        } catch (SQLException e) {
            logError(e);
            cleanWinch(conn);
        }
        try {
            stmt.executeQuery("SELECT * FROM Glider");
        } catch (SQLException e) {
            logError(e);
            cleanGlider(conn);
        }
        try {
            stmt.executeQuery("SELECT * FROM Airfield");
            try {
                stmt.executeQuery("SELECT * FROM Runway");
                try {
                    stmt.executeQuery("SELECT * FROM GliderPosition");
                } catch (SQLException e) {
                    logError(e);
                    cleanGliderPosition(conn);
                }
                try {
                    stmt.executeQuery("SELECT * FROM WinchPosition");
                } catch (SQLException e) {
                    logError(e);
                    cleanWinchPosition(conn);
                }
            } catch (SQLException e) {
                logError(e);
                cleanRunway(conn);
            }
        } catch (SQLException e) {
            logError(e);
            cleanAirfield(conn);
        }
        try {
            stmt.executeQuery("SELECT * FROM Parachute");
        } catch (SQLException e) {
            logError(e);
            cleanParachute(conn);
        }

        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            logError(e);
        }
        return true;
    }
}
