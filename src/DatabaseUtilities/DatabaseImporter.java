/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseUtilities;

import static Communications.ErrorLogger.logError;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import DataObjects.*;
import static DatabaseUtilities.DatabaseInitialization.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author dbennett3
 */
public class DatabaseImporter {

    private static BufferedReader br; //private global
    private static Connection connection; //private global

    public static boolean importDatabase(File zipFile, List<String> importList) {
        connection = connect();
        if (connection == null) {
            return false;
        }
        try {
            importTable(zipFile, importList);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error creating file, Check Error Log").showAndWait();
            logError(e);
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error with the database, Check Error Log").showAndWait();
            logError(e);
        }

        return true;
    }

    private static void importTable(File file, List<String> importList)
            throws SQLException, FileNotFoundException, IOException, ClassNotFoundException {

        //System.out.println(importList.toString());
        ZipFile zip = new ZipFile(file);
        ZipInputStream zin = new ZipInputStream(new FileInputStream(file));
        for (ZipEntry e; (e = zin.getNextEntry()) != null;) {

            //System.out.println(importList);
            String fileName = e.toString();
            //System.out.println(fileName);

            InputStream is = zip.getInputStream(e);//zip.getInputStream(e);
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            if (importList.contains(fileName)) {
                if (fileName.contains("_VERSION_")) {
                    System.out.println("Importing VERSION");
                    if (!importVersion()) {
                        connection.close();
                        return;
                    }
                } else if (fileName.contains("_GLIDERPOSITION_")) {
                    System.out.println("Importing GLIDERPOSITION");
                    importGliderPosition();
                } else if (fileName.contains("_GLIDER_")) {
                    System.out.println("Importing SAILPLANE");
                    importGlider();
                } else if (fileName.contains("_AIRFIELD_")) {
                    System.out.println("Importing AIRFIELD");
                    importAirfield();
                } else if (fileName.contains("_RUNWAY_")) {
                    System.out.println("Importing RUNWAY");
                    importRunway();
                } else if (fileName.contains("_WINCHPOSITION_")) {
                    System.out.println("Importing WINCHPOSITION");
                    importWinchPosition();
                } else if (fileName.contains("_PARACHUTE_")) {
                    System.out.println("Importing PARACHUTE");
                    importParachute();
                } else if (fileName.contains("_PILOT_")) {
                    System.out.println("Importing PILOT");
                    importPilot();
                } else if (fileName.contains("_PROFILE_")) {
                    System.out.println("Importing PROFILE");
                    importOperator();
                } else if (fileName.contains("_DRUM_")) {
                    System.out.println("Importing Drum");
                    importDrum();
                } else if (fileName.contains("_WINCH_")) {
                    System.out.println("Importing Winch");
                    importWinch();
                } else if (fileName.contains("_PREVIOUSLAUNCHES_")) {
                    System.out.println("Importing PreviousLaunches");
                    importPreviousLaunches();
                } else if (fileName.contains("_PREVIOUSARIFIELDINFO_")) {
                    System.out.println("Importing PreviousAirfield");
                    importPreviousAirfieldInfo();
                } else if (fileName.contains("_OPERATOR_")) {
                    System.out.println("Importing operators");
                    importOperator();
                }
            }

        }
    }

    private static boolean importVersion() throws IOException, SQLException {
        String s;
        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE Version");
        stmt.execute("CREATE TABLE Version"
                + "(db_version VARCHAR(10), "
                + "hc_version VARCHAR(10),"
                + "PRIMARY KEY(db_version))");
        while ((s = br.readLine()) != null) {
            String[] messagesData = s.split(",", -1);
            String[] importVer = messagesData[0].split(".", -1);
            String[] curVer = HOSTCONTROLLER_VERSION.split(".", -1);

            if (importVer[0].compareTo(curVer[0]) == 0) {
                if (importVer[1].compareTo(curVer[1]) == 0) {
                    PreparedStatement insertStatement = connection.prepareStatement(
                            "INSERT INTO Version(db_version, hc_version) VALUES (?, ?)");
                    insertStatement.setString(1, messagesData[0]);
                    insertStatement.setString(2, messagesData[1]);
                    insertStatement.executeUpdate();
                    insertStatement.close();
                } else {
                    Optional<ButtonType> result = new Alert(Alert.AlertType.WARNING, "The database you want "
                            + "to import is a different version then the current structure.\n\r"
                            + "Are you sure you wish to continue?", ButtonType.YES, ButtonType.NO).showAndWait();
                    if (result.get() == ButtonType.YES) {
                        PreparedStatement insertStatement = connection.prepareStatement(
                                "INSERT INTO Version(db_version, hc_version) VALUES (?, ?)");
                        insertStatement.setString(1, messagesData[0]);
                        insertStatement.setString(2, messagesData[1]);
                        insertStatement.executeUpdate();
                        insertStatement.close();
                    } else {
                        return false;
                    }
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Database incompatable").showAndWait();
                return false;
            }

        }
        return true;
    }

    private static void importGlider() throws IOException {
        String s;
        cleanGlider(connection);
        while ((s = br.readLine()) != null) {
            String[] gliderData = s.split(",", -1);
            int id = Integer.parseInt(gliderData[0]);
            String reg = gliderData[1];
            String name = gliderData[2];
            String owner = gliderData[3];
            String type = gliderData[4];
            float mgw = Float.parseFloat(gliderData[5]);
            float ew = Float.parseFloat(gliderData[6]);
            float iss = Float.parseFloat(gliderData[7]);
            float mws = Float.parseFloat(gliderData[8]);
            float mwls = Float.parseFloat(gliderData[9]);
            float mt = Float.parseFloat(gliderData[10]);
            float cra = Float.parseFloat(gliderData[11]);
            boolean cb = Boolean.parseBoolean(gliderData[12]);
            boolean ms = Boolean.parseBoolean(gliderData[13]);
            String optional = gliderData[14];
            Sailplane importer = new Sailplane(id, reg, name, owner, type,
                    mgw, ew, iss, mws, mwls, mt, cra, cb, ms, optional);
            DatabaseEntryInsert.addSailplaneToDB(importer);
        }
    }

    private static void importPilot() throws IOException {
        String s;
        cleanPilot(connection);
        while ((s = br.readLine()) != null) {
            String[] pilotData = s.split(",", -1);
            int id = Integer.parseInt(pilotData[0]);
            String first = pilotData[1];
            String last = pilotData[2];
            String middle = pilotData[3];
            float weight = Float.parseFloat(pilotData[4]);
            String capabilities = pilotData[5];
            float preference = Float.parseFloat(pilotData[6]);
            String eName = pilotData[7];
            String ePhone = pilotData[8];
            String optional = pilotData[9];
            Pilot importer = new Pilot(id, first, last, middle, weight,
                    capabilities, preference, eName, ePhone, optional);
            DatabaseEntryInsert.addPilotToDB(importer);

        }
    }

    private static void importAirfield() throws IOException {
        String s;
        cleanAirfield(connection);
        while ((s = br.readLine()) != null) {
            String[] airfieldData = s.split(",", -1);
            int id = Integer.parseInt(airfieldData[0]);
            String name = airfieldData[1];
            String designator = airfieldData[2];
            float ele = Float.parseFloat(airfieldData[3]);
            float mv = Float.parseFloat(airfieldData[4]);
            float lat = Float.parseFloat(airfieldData[5]);
            float lng = Float.parseFloat(airfieldData[6]);
            int utc = Integer.parseInt(airfieldData[7]);
            String optional = airfieldData[8];
            Airfield importer = new Airfield(id, name, designator, ele, mv, lat, lng, utc, optional);
            DatabaseEntryInsert.addAirfieldToDB(importer);
        }
    }

    private static void importRunway() throws IOException {
        String s;
        cleanRunway(connection);
        while ((s = br.readLine()) != null) {
            String[] runwayData = s.split(",", -1);
            int id = Integer.parseInt(runwayData[0]);
            int pid = Integer.parseInt(runwayData[1]);
            String name = runwayData[2];
            float mh = Float.parseFloat(runwayData[3]);
            String info = runwayData[4];
            Runway importer = new Runway(id, pid, name, mh, info);
            DatabaseEntryInsert.addRunwayToDB(importer);
        }
    }

    private static void importGliderPosition() throws IOException {
        String s;
        cleanGliderPosition(connection);
        while ((s = br.readLine()) != null) {
            String[] gliderPositionData = s.split(",", -1);
            int id = Integer.parseInt(gliderPositionData[0]);
            int pid = Integer.parseInt(gliderPositionData[1]);
            String name = gliderPositionData[2];
            float elv = Float.parseFloat(gliderPositionData[3]);
            float lat = Float.parseFloat(gliderPositionData[4]);
            float lng = Float.parseFloat(gliderPositionData[5]);
            String optional = gliderPositionData[6];
            GliderPosition importer = new GliderPosition(id, pid, name, elv, lat, lng, optional);
            DatabaseEntryInsert.addGliderPositionToDB(importer);
        }
    }

    private static void importWinchPosition() throws IOException {
        String s;
        cleanWinchPosition(connection);
        while ((s = br.readLine()) != null) {
            String[] winchPositionData = s.split(",", -1);
            int id = Integer.parseInt(winchPositionData[0]);
            int pid = Integer.parseInt(winchPositionData[1]);
            String name = winchPositionData[2];
            float elv = Float.parseFloat(winchPositionData[3]);
            float lat = Float.parseFloat(winchPositionData[4]);
            float lng = Float.parseFloat(winchPositionData[5]);
            String optional = winchPositionData[6];
            WinchPosition importer = new WinchPosition(id, pid, name, elv, lat, lng, optional);

            DatabaseEntryInsert.addWinchPositionToDB(importer);
        }
    }

    private static void importParachute() throws IOException {
        String s;
        cleanParachute(connection);
        while ((s = br.readLine()) != null) {
            String[] parachuteData = s.split(",", -1);
            int id = Integer.parseInt(parachuteData[0]);
            String name = parachuteData[1];
            float lift = Float.parseFloat(parachuteData[2]);
            float drag = Float.parseFloat(parachuteData[3]);
            float weight = Float.parseFloat(parachuteData[4]);
            String optional = parachuteData[5];
            Parachute importer = new Parachute(id, name, lift, drag, weight, optional);
            DatabaseEntryInsert.addParachuteToDB(importer);
        }
    }

    private static void importOperator() throws IOException {
        String s;
        cleanOperator(connection);
        while ((s = br.readLine()) != null) {
            String[] profileData = s.split(",", -1);
            int id = Integer.parseInt(profileData[0]);
            String first = profileData[1];
            String middle = profileData[2];
            String last = profileData[3];
            boolean admin = Boolean.parseBoolean(profileData[4]);
            String salt = profileData[5];
            String hash = profileData[6];
            String optional = profileData[7];
            String settings = profileData[8];

            Operator importer = new Operator(id, first, middle, last, admin, optional, settings);
            DatabaseEntryInsert.addOperatorToDB(importer, salt, hash);
        }
    }

    private static void importDrum() throws IOException {
        String s;
        cleanDrum(connection);
        while ((s = br.readLine()) != null) {
            String[] drumData = s.split(",", -1);
            int id = Integer.parseInt(drumData[0]);
            int wid = Integer.parseInt(drumData[1]);
            String name = drumData[2];
            int number = Integer.parseInt(drumData[3]);
            float cod = Float.parseFloat(drumData[4]);
            float kf = Float.parseFloat(drumData[5]);
            float sc = Float.parseFloat(drumData[6]);
            float cal = Float.parseFloat(drumData[7]);
            float cad = Float.parseFloat(drumData[8]);
            float dse = Float.parseFloat(drumData[9]);
            int nol = Integer.parseInt(drumData[10]);
            float mwt = Float.parseFloat(drumData[11]);
            String optional = drumData[11];

            Drum importer = new Drum(id, wid, name, number, cod, kf, sc, cal, cad, dse, nol, mwt, optional);
            DatabaseEntryInsert.addDrumToDB(importer);
        }
    }

    private static void importWinch() throws IOException {
        String s;
        cleanWinch(connection); //also cleans Drum
        while ((s = br.readLine()) != null) {
            String[] winchData = s.split(",", -1);
            int id = Integer.parseInt(winchData[0]);
            String name = winchData[2];
            String owner = winchData[2];
            String wcVer = winchData[2];
            float w1 = Float.parseFloat(winchData[4]);
            float w2 = Float.parseFloat(winchData[5]);
            float w3 = Float.parseFloat(winchData[6]);
            float w4 = Float.parseFloat(winchData[7]);
            float w5 = Float.parseFloat(winchData[8]);
            float w6 = Float.parseFloat(winchData[4]);
            float w7 = Float.parseFloat(winchData[5]);
            float w8 = Float.parseFloat(winchData[6]);
            float w9 = Float.parseFloat(winchData[7]);
            float w10 = Float.parseFloat(winchData[8]);
            float w11 = Float.parseFloat(winchData[4]);
            float w12 = Float.parseFloat(winchData[5]);
            float w13 = Float.parseFloat(winchData[6]);
            float w14 = Float.parseFloat(winchData[7]);
            float w15 = Float.parseFloat(winchData[8]);
            float w16 = Float.parseFloat(winchData[8]);
            int mtc = Integer.parseInt(winchData[9]);
            int mvc = Integer.parseInt(winchData[9]);
            float rot = Float.parseFloat(winchData[8]);
            String optional = winchData[11];

            Winch importer = new Winch(id, name, owner, wcVer, w1, w2, w3, w4,
                    w5, w6, w7, w8, w9, w10, w11, w12, w13, w14, w15, w16, mtc,
                    mvc, rot, optional);
            DatabaseEntryInsert.addWinchToDB(importer);
        }
    }

    private static void importPreviousLaunches() throws IOException, SQLException {
        String s;
        cleanPrevLaunches(connection);
        PreparedStatement PreviousLaunchesInsert = connection.prepareStatement(
                "INSERT INTO PreviousLaunches("
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

        while ((s = br.readLine()) != null) {
            String[] launchData = s.split(",", -1);
            PreviousLaunchesInsert.setString(1, launchData[0]);
            PreviousLaunchesInsert.setString(2, launchData[1]);
            //Pilot
            PreviousLaunchesInsert.setString(3, launchData[2]);
            PreviousLaunchesInsert.setString(4, launchData[3]);
            PreviousLaunchesInsert.setString(5, launchData[4]);
            PreviousLaunchesInsert.setFloat(6, Float.parseFloat(launchData[5]));
            PreviousLaunchesInsert.setInt(7, Integer.parseInt(launchData[6]));
            PreviousLaunchesInsert.setFloat(8, Float.parseFloat(launchData[7]));
            PreviousLaunchesInsert.setString(9, launchData[8]);
            PreviousLaunchesInsert.setString(10, launchData[9]);
            PreviousLaunchesInsert.setString(11, launchData[10]);
            //Glider
            PreviousLaunchesInsert.setString(12, launchData[11]);
            PreviousLaunchesInsert.setString(13, launchData[12]);
            PreviousLaunchesInsert.setString(14, launchData[13]);
            PreviousLaunchesInsert.setString(15, launchData[14]);
            PreviousLaunchesInsert.setFloat(16, Float.parseFloat(launchData[15]));
            PreviousLaunchesInsert.setFloat(17, Float.parseFloat(launchData[16]));
            PreviousLaunchesInsert.setFloat(18, Float.parseFloat(launchData[17]));
            PreviousLaunchesInsert.setFloat(19, Float.parseFloat(launchData[18]));
            PreviousLaunchesInsert.setFloat(20, Float.parseFloat(launchData[19]));
            PreviousLaunchesInsert.setFloat(21, Float.parseFloat(launchData[20]));
            PreviousLaunchesInsert.setFloat(22, Float.parseFloat(launchData[21]));
            PreviousLaunchesInsert.setBoolean(23, Boolean.parseBoolean(launchData[22]));
            PreviousLaunchesInsert.setBoolean(24, Boolean.parseBoolean(launchData[23]));
            PreviousLaunchesInsert.setString(25, launchData[24]);
            //Enviroment    
            PreviousLaunchesInsert.setFloat(26, Float.parseFloat(launchData[25]));
            PreviousLaunchesInsert.setFloat(27, Float.parseFloat(launchData[26]));
            PreviousLaunchesInsert.setFloat(28, Float.parseFloat(launchData[27]));
            PreviousLaunchesInsert.setFloat(29, Float.parseFloat(launchData[28]));
            PreviousLaunchesInsert.setFloat(30, Float.parseFloat(launchData[29]));
            PreviousLaunchesInsert.setFloat(31, Float.parseFloat(launchData[30]));
            PreviousLaunchesInsert.setFloat(32, Float.parseFloat(launchData[31]));
            PreviousLaunchesInsert.setFloat(33, Float.parseFloat(launchData[32]));
            PreviousLaunchesInsert.setFloat(34, Float.parseFloat(launchData[33]));
            //Drum
            PreviousLaunchesInsert.setString(35, launchData[34]);
            PreviousLaunchesInsert.setInt(36, Integer.parseInt(launchData[35]));
            PreviousLaunchesInsert.setFloat(37, Float.parseFloat(launchData[36]));
            PreviousLaunchesInsert.setFloat(38, Float.parseFloat(launchData[37]));
            PreviousLaunchesInsert.setFloat(39, Float.parseFloat(launchData[38]));
            PreviousLaunchesInsert.setFloat(40, Float.parseFloat(launchData[39]));
            PreviousLaunchesInsert.setFloat(41, Float.parseFloat(launchData[40]));
            PreviousLaunchesInsert.setFloat(42, Float.parseFloat(launchData[41]));
            PreviousLaunchesInsert.setFloat(43, Float.parseFloat(launchData[42]));
            PreviousLaunchesInsert.setFloat(44, Float.parseFloat(launchData[43]));
            //Parachute
            PreviousLaunchesInsert.setString(45, launchData[44]);
            PreviousLaunchesInsert.setFloat(46, Float.parseFloat(launchData[45]));
            PreviousLaunchesInsert.setFloat(47, Float.parseFloat(launchData[46]));
            PreviousLaunchesInsert.setFloat(48, Float.parseFloat(launchData[47]));
            //Additional info
            PreviousLaunchesInsert.setFloat(49, Float.parseFloat(launchData[48]));
            PreviousLaunchesInsert.setFloat(50, Float.parseFloat(launchData[49]));
            PreviousLaunchesInsert.setFloat(51, Float.parseFloat(launchData[50]));
            PreviousLaunchesInsert.setInt(52, Integer.parseInt(launchData[51]));

            PreviousLaunchesInsert.executeUpdate();
        }
        PreviousLaunchesInsert.close();
    }

    private static void importPreviousAirfieldInfo() throws IOException, SQLException {
        String s;
        cleanPrevAirfield(connection); //this will also clean previous launches
        PreparedStatement PreviousAirfieldInsert = connection.prepareStatement(
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
        while ((s = br.readLine()) != null) {
            String[] airfieldData = s.split(",", -1);
            PreviousAirfieldInsert.setInt(1, Integer.parseInt(airfieldData[0]));
            //Airfield
            PreviousAirfieldInsert.setString(2, airfieldData[1]);
            PreviousAirfieldInsert.setString(3, airfieldData[2]);
            PreviousAirfieldInsert.setFloat(4, Float.parseFloat(airfieldData[3]));
            PreviousAirfieldInsert.setFloat(5, Float.parseFloat(airfieldData[4]));
            PreviousAirfieldInsert.setFloat(6, Float.parseFloat(airfieldData[5]));
            PreviousAirfieldInsert.setFloat(7, Float.parseFloat(airfieldData[6]));
            PreviousAirfieldInsert.setFloat(8, Float.parseFloat(airfieldData[7]));
            //Runway
            PreviousAirfieldInsert.setString(9, airfieldData[8]);
            PreviousAirfieldInsert.setFloat(10, Float.parseFloat(airfieldData[9]));
            //Glider Position
            PreviousAirfieldInsert.setString(11, airfieldData[10]);
            PreviousAirfieldInsert.setFloat(12, Float.parseFloat(airfieldData[11]));
            PreviousAirfieldInsert.setFloat(13, Float.parseFloat(airfieldData[12]));
            PreviousAirfieldInsert.setFloat(14, Float.parseFloat(airfieldData[13]));
            //WinchPosition
            PreviousAirfieldInsert.setString(15, airfieldData[14]);
            PreviousAirfieldInsert.setFloat(16, Float.parseFloat(airfieldData[15]));
            PreviousAirfieldInsert.setFloat(17, Float.parseFloat(airfieldData[16]));
            PreviousAirfieldInsert.setFloat(18, Float.parseFloat(airfieldData[17]));
            //Winch
            PreviousAirfieldInsert.setString(19, airfieldData[18]);
            PreviousAirfieldInsert.setString(20, airfieldData[19]);

            PreviousAirfieldInsert.executeUpdate();
        }
        PreviousAirfieldInsert.close();
    }
}
