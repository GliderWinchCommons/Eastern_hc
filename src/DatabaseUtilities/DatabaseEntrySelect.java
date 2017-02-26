/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseUtilities;

import static Communications.ErrorLogger.logError;
import DataObjects.*;
import static DatabaseUtilities.DatabaseInitialization.connect;
import ParameterSelection.Capability;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author gene
 */
public class DatabaseEntrySelect {

    /**
     * Pulls the list of Profiles (and relevant data) from the database
     *
     * @return the list of profiles in the database
     */
    public static List<Operator> getOperators() {
        List<Operator> profiles = new ArrayList();
        try (Connection connect = connect()) {
            if (connect == null) {
                return profiles;
            }
            Statement stmt = connect.createStatement();
            ResultSet theProfiles = stmt.executeQuery("SELECT * "
                    + "FROM Operator ORDER BY first_name");
            while (theProfiles.next()) {
                int id = theProfiles.getInt("operator_id");
                String firstName = theProfiles.getString("first_name");
                String middleName = theProfiles.getString("middle_name");
                String lastName = theProfiles.getString("last_name");
                boolean adimn = theProfiles.getBoolean("admin");
                String info = theProfiles.getString("optional_info");
                String unitSettings = theProfiles.getString("unitSettings");
                Operator newProfile = new Operator(id, firstName, middleName,
                        lastName, adimn, info, unitSettings);
                profiles.add(newProfile);
            }
            theProfiles.close();
            stmt.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Operators from Database").showAndWait();
            logError(e);
        }
        return profiles;
    }

    /**
     * Pulls the list of pilots (and relevant data) from the database
     *
     * @return the list of pilots in the database
     */
    public static List<Pilot> getPilots() {
        List<Pilot> pilots = new ArrayList();
        try (Connection connect = connect()) {
            if (connect == null) {
                return pilots;
            }
            Statement stmt = connect.createStatement();
            ResultSet thePilots = stmt.executeQuery("SELECT * "
                    + "FROM Pilot ORDER BY first_name");
            while (thePilots.next()) {
                int pilotId = thePilots.getInt("pilot_id");
                String pilotFirstName = thePilots.getString("first_name");
                String pilotLastName = thePilots.getString("last_name");
                String pilotMiddleName = thePilots.getString("middle_name");
                float weight = thePilots.getFloat("flight_weight");
                int capability = thePilots.getInt("capability");
                float preference = thePilots.getInt("preference");
                String emergency_name = thePilots.getString("emergency_contact_name");
                String emergency_phone = thePilots.getString("emergency_contact_phone");
                String info = thePilots.getString("optional_info");
                Pilot newPilot = new Pilot(pilotId, pilotFirstName, pilotLastName,
                        pilotMiddleName, weight, Capability.convertCapabilityNumToString(capability),
                        preference, emergency_name, emergency_phone, info);
                pilots.add(newPilot);
            }
            thePilots.close();
            stmt.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Pilots from Database").showAndWait();
            logError(e);
        }
        return pilots;
    }

    /**
     * Pulls the list of Parachutes (and relevant data) from the database
     *
     * @return the list of parachutes in the database
     */
    public static List<Drum> getDrum() {
        List<Drum> drums = new ArrayList();
        try (Connection connect = connect()) {
            if (connect == null) {
                return drums;
            }
            Statement stmt = connect.createStatement();
            ResultSet theDrums = stmt.executeQuery("SELECT * "
                    + "FROM Drum ORDER BY drum_name");
            while (theDrums.next()) {
                int id = theDrums.getInt("drum_id");
                String drum_name = theDrums.getString("drum_name");
                int drum_number = theDrums.getInt("drum_number");
                float core_diameter = theDrums.getFloat("core_diameter");
                float kfactor = theDrums.getFloat("kfactor");
                float spring_const = theDrums.getFloat("spring_const");
                float cable_length = theDrums.getFloat("cable_length");
                float cable_density = theDrums.getFloat("cable_density");
                float drum_system_emass = theDrums.getFloat("drum_system_emass");
                int launch_number = theDrums.getInt("number_of_launches");
                float max_tension = theDrums.getFloat("maximum_working_tension");
                int winchId = theDrums.getInt("winch_id");
                String info = theDrums.getString("optional_info");
                Drum newDrum = new Drum(id, winchId, drum_name, drum_number, core_diameter,
                        kfactor, spring_const, cable_length, cable_density, drum_system_emass,
                        launch_number, max_tension, info);
                drums.add(newDrum);
            }
            theDrums.close();
            stmt.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Drums from Database").showAndWait();
            logError(e);
        }
        return drums;
    }

    /**
     * Pulls the list of Sailplanes (and relevant data) from the database
     *
     * @return the list of sailplanes in the database
     */
    public static List<Sailplane> getSailplanes() {
        List<Sailplane> sailplanes = new ArrayList();
        try (Connection connect = connect()) {
            if (connect == null) {
                return sailplanes;
            }
            Statement stmt = connect.createStatement();
            ResultSet theSailplanes = stmt.executeQuery("SELECT * "
                    + "FROM Glider ORDER BY common_name");
            while (theSailplanes.next()) {
                int id = theSailplanes.getInt("glider_id");
                String regNumber = theSailplanes.getString("reg_number");
                String name = theSailplanes.getString("common_name");
                String owner = theSailplanes.getString("owner");
                String type = theSailplanes.getString("type");
                float maxGrossWeight = theSailplanes.getFloat("max_gross_weight");
                float emptyWeight = theSailplanes.getFloat("empty_weight");
                float stallSpeed = theSailplanes.getFloat("indicated_stall_speed");
                float maxWinchingSpeed = theSailplanes.getFloat("max_winching_speed");
                float maxWeakLinkStrength = theSailplanes.getFloat("max_weak_link_strength");
                float maxTension = theSailplanes.getFloat("max_tension");
                float cableAngle = theSailplanes.getFloat("cable_release_angle");
                boolean ballast = theSailplanes.getBoolean("carry_ballast");
                boolean multipleSeats = theSailplanes.getBoolean("multiple_seats");
                Sailplane newSailplane = new Sailplane(id, regNumber, name, owner,
                        type, maxGrossWeight, emptyWeight, stallSpeed, maxWinchingSpeed,
                        maxWeakLinkStrength, maxTension, cableAngle, ballast, multipleSeats,
                        theSailplanes.getString("optional_info"));
                sailplanes.add(newSailplane);
            }
            theSailplanes.close();
            stmt.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Gliders from Database").showAndWait();
            logError(e);
        }
        return sailplanes;
    }

    /**
     * Pulls the summary list of flights from the database
     *
     * @return the list of flights in the database
     */
    public static List<FlightSummary> getFlights() {
        List<FlightSummary> flights = new ArrayList<>();
        try (Connection connect = connect()) {
            Statement stmt = connect.createStatement();
            ResultSet theFlights = stmt.executeQuery("SELECT start_timestamp, "
                    + "first_name, last_name, middle_name, reg_number "
                    + "FROM PreviousLaunches ORDER BY start_timestamp");

            while (theFlights.next()) {
                Timestamp startTimestamp = theFlights.getTimestamp(1);
                String pilotFirstName = theFlights.getString(2);
                String pilotLastName = theFlights.getString(3);
                String pilotMiddleName = theFlights.getString(4);
                String gliderNnumber = theFlights.getString(5);

                FlightSummary newFlight = new FlightSummary(startTimestamp,
                        pilotFirstName, pilotLastName, pilotMiddleName, gliderNnumber);
                flights.add(newFlight);
            }
            theFlights.close();
            stmt.close();
            return flights;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Flights from Database").showAndWait();
            logError(e);
        }
        return flights;
    }

    /**
     * Pulls the list of Airfields (and relevant data) from the database
     *
     * @return the list of airfields in the database
     */
    public static List<Airfield> getAirfields() {
        List<Airfield> airfields = new ArrayList();
        try (Connection connect = connect()) {
            if (connect == null) {
                return airfields;
            }
            Statement stmt = connect.createStatement();
            ResultSet theAirfields = stmt.executeQuery("SELECT * "
                    + "FROM Airfield ORDER BY name");
            while (theAirfields.next()) {
                int id = theAirfields.getInt("airfield_id");
                String name = theAirfields.getString("name");
                String designator = theAirfields.getString("designator");
                float elevation = theAirfields.getFloat("elevation");
                float magneticVariation = theAirfields.getFloat("magnetic_variation");
                float latitude = theAirfields.getFloat("latitude");
                float longitude = theAirfields.getFloat("longitude");
                int utc = theAirfields.getInt("utc_offset");
                String optional = theAirfields.getString("optional_info");
                Airfield newAirfield = new Airfield(id, name, designator, elevation,
                        magneticVariation, latitude, longitude, utc, optional);
                airfields.add(newAirfield);
            }
            theAirfields.close();
            stmt.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Airfields from Database").showAndWait();
            logError(e);
        }
        return airfields;
    }

    /**
     * sets currentDataObjectSet to the given flight
     *
     * @param flightInformation contains the summary information of the flight
     * @post the current information contains the past flight information
     */
    public static void setCurrentDataObjectSetToFlight(FlightSummary flightInformation) {
        try (Connection connect = connect()) {
            PreparedStatement stmt = connect.prepareStatement("SELECT * "
                    + "FROM PreviousLaunches "
                    + "WHERE start_timestamp = ?");
            stmt.setTimestamp(1, flightInformation.getStartTimestamp());

            ResultSet theFlight = stmt.executeQuery();
            CurrentDataObjectSet currentDataObjectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
            if (theFlight.next()) {
                //Create Pilot
                String pilotFirstName = theFlight.getString("first_name");
                String pilotLastName = theFlight.getString("last_name");
                String pilotMiddleName = theFlight.getString("middle_name");
                float pilotWeight = theFlight.getFloat("flight_weight");
                int pilotCapability = theFlight.getInt("capability");
                float pilotPreference = theFlight.getFloat("preference");
                String emergencyName = theFlight.getString("emergency_contact_name");
                String emergencyPhone = theFlight.getString("emergency_contact_phone");
                String optionalInfo = theFlight.getString("pilot_optional_info");

                Pilot newPilot = new Pilot(0, pilotFirstName, pilotLastName, pilotMiddleName, pilotWeight,
                        Capability.convertCapabilityNumToString(pilotCapability), pilotPreference,
                        emergencyName, emergencyPhone, optionalInfo);
                currentDataObjectSet.setCurrentPilot(newPilot);

                //Create Glider
                String gliderNNumber = theFlight.getString("reg_number");
                String commonName = theFlight.getString("common_name");
                String gliderOwner = theFlight.getString("glider_owner");
                String gliderType = theFlight.getString("type");

                float gliderMaxGrossWeight = theFlight.getFloat("max_gross_weight");
                float gliderEmptyWeight = theFlight.getFloat("empty_weight");
                float gliderStallSpeed = theFlight.getFloat("indicated_stall_speed");
                float gliderMaxWinchingSpeed = theFlight.getFloat("max_winching_speed");
                float gliderMaxWeakLinkStrength = theFlight.getFloat("max_weak_link_strength");
                float gliderMaxTension = theFlight.getFloat("max_tension");
                float gliderCableAngle = theFlight.getFloat("cable_release_angle");

                boolean ballast = theFlight.getBoolean("carry_ballast");
                boolean multipleSeats = theFlight.getBoolean("multiple_seats");
                optionalInfo = theFlight.getString("glider_optional_info");

                Sailplane newSailplane = new Sailplane(gliderNNumber, commonName, gliderOwner, gliderType,
                        gliderMaxGrossWeight, gliderEmptyWeight, gliderStallSpeed, gliderMaxWinchingSpeed,
                        gliderMaxWeakLinkStrength, gliderMaxTension, gliderCableAngle,
                        ballast, multipleSeats, optionalInfo);
                currentDataObjectSet.setCurrentGlider(newSailplane);

                CurrentLaunchInformation currentLaunchInformation
                        = CurrentLaunchInformation.getCurrentLaunchInformation();
                currentLaunchInformation.setGliderBallast(theFlight.getFloat("ballast"));
                currentLaunchInformation.setPassengerWeight(theFlight.getFloat("passenger_weight"));
                currentLaunchInformation.setGliderBaggage(theFlight.getFloat("baggage"));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Could not repopulate current scenario,\n\rCheck Error Log").showAndWait();
            logError(e);
        }

    }

    /**
     * Pulls the list of Glider positions (and relevant data) from the database
     *
     * @return the list of glider positions in the database
     */
    public static List<GliderPosition> getGliderPositions() {
        List<GliderPosition> positions = new ArrayList();
        try (Connection connect = connect()) {
            if (connect == null) {
                return positions;
            }
            Statement stmt = connect.createStatement();
            ResultSet theGliderPositions = stmt.executeQuery("SELECT * "
                    + "FROM GliderPosition ORDER BY position_name");
            while (theGliderPositions.next()) {
                int id = theGliderPositions.getInt("glider_position_id");
                String name = theGliderPositions.getString("position_name");
                int runwayId = theGliderPositions.getInt("parent_id");
                float elevation = theGliderPositions.getFloat("elevation");
                float latitude = theGliderPositions.getFloat("latitude");
                float longitude = theGliderPositions.getFloat("longitude");
                String optional = theGliderPositions.getString("optional_info");
                GliderPosition newGliderPosition = new GliderPosition(id, runwayId,
                        name, elevation, latitude, longitude, optional);
                positions.add(newGliderPosition);
            }
            theGliderPositions.close();
            stmt.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Glider Positions from Database").showAndWait();
            logError(e);
        }
        return positions;
    }

    /**
     * Pulls the list of Winch positions (and relevant data) from the database
     *
     * @return the list of winch positions in the database
     */
    public static List<WinchPosition> getWinchPositions() {
        List<WinchPosition> positions = new ArrayList();
        try (Connection connect = connect()) {
            if (connect == null) {
                return positions;
            }
            Statement stmt = connect.createStatement();
            ResultSet theWinchPositions = stmt.executeQuery("SELECT * "
                    + "FROM WinchPosition ORDER BY position_name");
            while (theWinchPositions.next()) {
                int id = theWinchPositions.getInt("winch_position_id");
                String name = theWinchPositions.getString("position_name");
                int runwayId = theWinchPositions.getInt("parent_id");
                float elevation = theWinchPositions.getFloat("elevation");
                float latitude = theWinchPositions.getFloat("latitude");
                float longitude = theWinchPositions.getFloat("longitude");
                String optional = theWinchPositions.getString("optional_info");
                WinchPosition newWinchPosition = new WinchPosition(id, runwayId, name,
                        elevation, latitude, longitude, optional);
                positions.add(newWinchPosition);
            }
            theWinchPositions.close();
            stmt.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Winch Positions from Database").showAndWait();
            logError(e);
        }
        return positions;
    }

    /**
     * Pulls the list of Runways (and relevant data) from the database
     *
     * @return the list of runways in the database
     */
    public static List<Runway> getRunways() {
        List<Runway> runways = new ArrayList();
        try (Connection connect = connect()) {
            if (connect == null) {
                return runways;
            }
            Statement stmt = connect.createStatement();
            ResultSet theRunways = stmt.executeQuery("SELECT * "
                    + "FROM Runway ORDER BY runway_name");
            while (theRunways.next()) {
                int id = theRunways.getInt("runway_id");
                String name = theRunways.getString("runway_name");
                float magneticHeading = theRunways.getFloat("magnetic_heading");
                int parentid = theRunways.getInt("parent_id");
                String optional = theRunways.getString("optional_info");
                Runway newRunway = new Runway(id, parentid, name, magneticHeading, optional);
                runways.add(newRunway);
            }
            theRunways.close();
            stmt.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Runways from Database").showAndWait();
            logError(e);
        }
        return runways;
    }

    /**
     * Pulls the list of Parachutes (and relevant data) from the database
     *
     * @return the list of parachutes in the database
     */
    public static List<Parachute> getParachutes() {
        List<Parachute> parachutes = new ArrayList();
        try (Connection connect = connect()) {
            if (connect == null) {
                return parachutes;
            }
            Statement stmt = connect.createStatement();
            ResultSet theParachutes = stmt.executeQuery("SELECT * "
                    + "FROM Parachute ORDER BY name");
            while (theParachutes.next()) {
                int id = theParachutes.getInt("parachute_id");
                String name = theParachutes.getString("name");
                float lift = theParachutes.getFloat("lift");
                float drag = theParachutes.getFloat("drag");
                float weight = theParachutes.getFloat("weight");
                String info = theParachutes.getString("optional_info");
                Parachute newParachute = new Parachute(id, name, lift, drag, weight, info);
                parachutes.add(newParachute);
            }
            theParachutes.close();
            stmt.close();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Could not retrieve Parachutes from Database").showAndWait();
            logError(e);
        }
        return parachutes;
    }

    public static List<String> getTables() throws SQLException, ClassNotFoundException {
        try (Connection connect = DatabaseInitialization.connectEx()) {
            Statement stmt = connect.createStatement();
            ResultSet theTables = stmt.executeQuery("SELECT * FROM SYS.SYSTABLES");
            List<String> tables = new ArrayList();
            while (theTables.next()) {
                //CHECKS TO SEE IF IT IS A SYSTEM TABLE OR A UNITS TABLE, WHICH WILL BE EXCLUDED
                if (!theTables.getString(2).contains("SYS") && !theTables.getString(2).contains("UNITS")) {
                    tables.add(theTables.getString(2));
                }
            }
            return tables;
        } catch (Exception e) {
            logError(e);
            throw e;
        }
    }
}
