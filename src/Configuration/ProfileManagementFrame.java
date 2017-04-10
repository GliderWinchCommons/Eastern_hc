package Configuration;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import DatabaseUtilities.DatabaseEntryEdit;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;

public class ProfileManagementFrame implements Observer {

    @FXML
    private SubScene profilePilotPanel;
    @FXML
    private SubScene profileGliderPanel;
    @FXML
    private SubScene profileAirfieldPanel;
    @FXML
    private SubScene profileRunwayPanel;
    @FXML
    private SubScene profileGliderPositionPanel;
    @FXML
    private SubScene profileWinchPositionPanel;
    @FXML
    private SubScene profileOtherPanel;
    @FXML
    private Label operatorNameLabel;

    private ProfilePilotPanel ProfilePilotPanel;
    private ProfileGliderPanel ProfileGliderPanel;
    private ProfileAirfieldPanel ProfileAirfieldPanel;
    private ProfileRunwayPanel ProfileRunwayPanel;
    private ProfileGliderPositionPanel ProfileGliderPositionPanel;
    private ProfileWinchPositionPanel ProfileWinchPositionPanel;
    private ProfileDisplayPanel ProfileDisplayPanel;
    private ProfileOtherPanel ProfileOtherPanel;

    private CurrentDataObjectSet currentData;
    private String flightWeightUnits;
    private String airfieldAltitudeUnits;
    private String gliderPosAltitudeUnits;
    private String runwayMagneticHeadingUnits;
    private String winchPosAltitudeUnits;
    private String emptyWeightUnits;
    private String stallSpeedUnits;
    private String weakLinkStrengthUnits;
    private String maxWinchingSpeedUnits;
    private String maxTensionUnits;
    private String maxGrossWeightUnits;
    private String ballastWeightUnits;
    private String baggageWeightUnits;
    private String passengerWeightUnits;
    private String avgWindSpeedUnits;
    private String crosswindUnits;
    private String densityAltitudeUnits;
    private String gustWindSpeedUnits;
    private String headwindUnits;
    private String launchWeightUnits;
    private String pressureUnits;
    private String runDirectionUnits;
    private String runLengthUnits;
    private String temperatureUnits;
    private String windDirectionUnits;
    private int flightWeightUnitsID;
    private int airfieldAltitudeUnitsID;
    private int gliderPosAltitudeUnitsID;
    private int runwayMagneticHeadingUnitsID;
    private int winchPosAltitudeUnitsID;
    private int emptyWeightUnitsID;
    private int maxGrossWeightUnitsID;
    private int stallSpeedUnitsID;
    private int tensionUnitsID;
    private int weakLinkStrengthUnitsID;
    private int winchingSpeedUnitsID;
    private int ballastWeightUnitsID;
    private int baggageWeightUnitsID;
    private int passengerWeightUnitsID;
    private int avgWindSpeedUnitsID;
    private int crosswindUnitsID;
    private int densityAltitudeUnitsID;
    private int gustWindSpeedUnitsID;
    private int headwindUnitsID;
    private int launchWeightUnitsID;
    private int pressureUnitsID;
    private int runDirectionUnitsID;
    private int runLengthUnitsID;
    private int temperatureUnitsID;
    private int windDirectionUnitsID;

    private SubScene loginPanel;

    public ProfileManagementFrame(SubScene loginPanel) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.loginPanel = loginPanel;
    }

    private ProfileManagementFrame getCurrentProfileManagementFrame() {
        return this;
    }

    public void getUnitsForProfile() {
        flightWeightUnits = (String) ProfilePilotPanel.flightWeightComboBox.getValue();
        airfieldAltitudeUnits = (String) ProfileAirfieldPanel.airfieldAltitudeComboBox.getValue();
        gliderPosAltitudeUnits = (String) ProfileGliderPositionPanel.gliderPosAltitudeComboBox.getValue();
        runwayMagneticHeadingUnits = (String) ProfileRunwayPanel.runwayMagneticHeadingComboBox.getValue();
        winchPosAltitudeUnits = (String) ProfileWinchPositionPanel.winchPosAltitudeComboBox.getValue();
        emptyWeightUnits = (String) ProfileGliderPanel.emptyWeightComboBox.getValue();
        maxGrossWeightUnits = (String) ProfileGliderPanel.maxGrossWeightComboBox.getValue();
        stallSpeedUnits = (String) ProfileGliderPanel.stallSpeedComboBox.getValue();
        weakLinkStrengthUnits = (String) ProfileGliderPanel.weakLinkStrengthComboBox.getValue();
        maxWinchingSpeedUnits = (String) ProfileGliderPanel.maxWinchingSpeedComboBox.getValue();
        maxTensionUnits = (String) ProfileGliderPanel.maxTensionComboBox.getValue();
        ballastWeightUnits = (String) ProfileGliderPanel.ballastWeightComboBox.getValue();
        baggageWeightUnits = (String) ProfileGliderPanel.baggageWeightComboBox.getValue();
        passengerWeightUnits = (String) ProfileGliderPanel.passengerWeightComboBox.getValue();
        avgWindSpeedUnits = (String) ProfileOtherPanel.avgWindSpeedComboBox.getValue();
        crosswindUnits = (String) ProfileOtherPanel.crosswindComboBox.getValue();
        densityAltitudeUnits = (String) ProfileOtherPanel.densityAltitudeComboBox.getValue();
        gustWindSpeedUnits = (String) ProfileOtherPanel.gustWindSpeedComboBox.getValue();
        headwindUnits = (String) ProfileOtherPanel.headwindComboBox.getValue();
        launchWeightUnits = (String) ProfileOtherPanel.launchWeightComboBox.getValue();
        pressureUnits = (String) ProfileOtherPanel.pressureComboBox.getValue();
        runDirectionUnits = (String) ProfileOtherPanel.runDirectionComboBox.getValue();
        runLengthUnits = (String) ProfileOtherPanel.runLengthComboBox.getValue();
        temperatureUnits = (String) ProfileOtherPanel.temperatureComboBox.getValue();
        windDirectionUnits = (String) ProfileOtherPanel.windDirectionComboBox.getValue();
    }

    @FXML
    public void CancelButton_Click(ActionEvent e) {
        loginPanel.toFront();
    }

    @FXML
    public void SaveButton_Click(ActionEvent ev) {
        getUnitsForProfile();
        Operator currentProfile_ = currentData.getCurrentProfile();
        currentProfile_.setUnitSetting("flightWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(flightWeightUnits));
        currentProfile_.setUnitSetting("emptyWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(emptyWeightUnits));
        currentProfile_.setUnitSetting("maxGrossWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(maxGrossWeightUnits));
        currentProfile_.setUnitSetting("ballastWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(ballastWeightUnits));
        currentProfile_.setUnitSetting("baggageWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(baggageWeightUnits));
        currentProfile_.setUnitSetting("passengerWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(passengerWeightUnits));
        currentProfile_.setUnitSetting("airfieldAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(airfieldAltitudeUnits));
        currentProfile_.setUnitSetting("gliderPosAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(gliderPosAltitudeUnits));
        currentProfile_.setUnitSetting("winchPosAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(winchPosAltitudeUnits));
        currentProfile_.setUnitSetting("runwayMagneticHeading", UnitConversionToIndexUtilities.degreesUnitStringToIndex(runwayMagneticHeadingUnits));
        currentProfile_.setUnitSetting("maxTension", UnitConversionToIndexUtilities.tensionUnitStringToIndex(maxTensionUnits));
        currentProfile_.setUnitSetting("weakLinkStrength", UnitConversionToIndexUtilities.tensionUnitStringToIndex(weakLinkStrengthUnits));
        currentProfile_.setUnitSetting("stallSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(stallSpeedUnits));
        currentProfile_.setUnitSetting("winchingSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(maxWinchingSpeedUnits));
        currentProfile_.setUnitSetting("avgWindSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(avgWindSpeedUnits));
        currentProfile_.setUnitSetting("crosswind", UnitConversionToIndexUtilities.velocityUnitStringToIndex(crosswindUnits));
        currentProfile_.setUnitSetting("gustWindSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(gustWindSpeedUnits));
        currentProfile_.setUnitSetting("headwind", UnitConversionToIndexUtilities.velocityUnitStringToIndex(headwindUnits));
        currentProfile_.setUnitSetting("launchWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(launchWeightUnits));
        currentProfile_.setUnitSetting("densityAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(densityAltitudeUnits));
        currentProfile_.setUnitSetting("runLength", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(runLengthUnits));
        currentProfile_.setUnitSetting("pressure", UnitConversionToIndexUtilities.pressureUnitStringToIndex(pressureUnits));
        currentProfile_.setUnitSetting("temperature", UnitConversionToIndexUtilities.tempUnitStringToIndex(temperatureUnits));
        currentProfile_.setUnitSetting("runDirection", UnitConversionToIndexUtilities.degreesUnitStringToIndex(runDirectionUnits));
        currentProfile_.setUnitSetting("windDirection", UnitConversionToIndexUtilities.degreesUnitStringToIndex(windDirectionUnits));
        currentData.setCurrentProfile(currentProfile_);
        loginPanel.toFront();
        try {
            DatabaseEntryEdit.UpdateEntry(currentProfile_);
        } catch (Exception e) {
            e.printStackTrace();
            //do nothing for now...
        }
        //System.out.println(currentData.getCurrentProfile().getUnitSettingsForStorage());
    }

    public void Rebuild() {

        operatorNameLabel.setText(currentData.getCurrentProfile().getFirst() + " " + currentData.getCurrentProfile().getMiddle() + " " + currentData.getCurrentProfile().getLast());

        flightWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
        String flightWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID);
        ProfilePilotPanel.flightWeightComboBox.setValue(flightWeightUnitsString);

        airfieldAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldAltitude");
        String airfieldAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID);
        ProfileAirfieldPanel.airfieldAltitudeComboBox.setValue(airfieldAltitudeUnitsString);

        gliderPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("gliderPosAltitude");
        String gliderPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(gliderPosAltitudeUnitsID);
        ProfileGliderPositionPanel.gliderPosAltitudeComboBox.setValue(gliderPosAltitudeUnitsString);

        runwayMagneticHeadingUnitsID = currentData.getCurrentProfile().getUnitSetting("runwayMagneticHeading");
        String runwayMagneticHeadingUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runwayMagneticHeadingUnitsID);
        ProfileRunwayPanel.runwayMagneticHeadingComboBox.setValue(runwayMagneticHeadingUnitsString);

        winchPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("winchPosAltitude");
        String winchPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(winchPosAltitudeUnitsID);
        ProfileWinchPositionPanel.winchPosAltitudeComboBox.setValue(winchPosAltitudeUnitsString);

        emptyWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("emptyWeight");
        String emptyWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(emptyWeightUnitsID);
        ProfileGliderPanel.emptyWeightComboBox.setValue(emptyWeightUnitsString);

        maxGrossWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("maxGrossWeight");
        String maxGrossWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(maxGrossWeightUnitsID);
        ProfileGliderPanel.maxGrossWeightComboBox.setValue(maxGrossWeightUnitsString);

        stallSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("stallSpeed");
        String stallSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(stallSpeedUnitsID);
        ProfileGliderPanel.stallSpeedComboBox.setValue(stallSpeedUnitsString);

        ballastWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("ballastWeight");
        String ballastWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(ballastWeightUnitsID);
        ProfileGliderPanel.ballastWeightComboBox.setValue(ballastWeightUnitsString);

        baggageWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("baggageWeight");
        String baggageWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(baggageWeightUnitsID);
        ProfileGliderPanel.baggageWeightComboBox.setValue(baggageWeightUnitsString);

        passengerWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("passengerWeight");
        String passengerWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(passengerWeightUnitsID);
        ProfileGliderPanel.passengerWeightComboBox.setValue(passengerWeightUnitsString);

        tensionUnitsID = currentData.getCurrentProfile().getUnitSetting("maxTension");
        String tensionUnitsString = UnitLabelUtilities.tensionUnitIndexToString(tensionUnitsID);
        ProfileGliderPanel.maxTensionComboBox.setValue(tensionUnitsString);

        weakLinkStrengthUnitsID = currentData.getCurrentProfile().getUnitSetting("weakLinkStrength");
        String weakLinkStrengthUnitsString = UnitLabelUtilities.tensionUnitIndexToString(weakLinkStrengthUnitsID);
        ProfileGliderPanel.weakLinkStrengthComboBox.setValue(weakLinkStrengthUnitsString);

        winchingSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("winchingSpeed");
        String winchingSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(winchingSpeedUnitsID);
        ProfileGliderPanel.maxWinchingSpeedComboBox.setValue(winchingSpeedUnitsString);

        avgWindSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("avgWindSpeed");
        String avgWindSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(avgWindSpeedUnitsID);
        ProfileOtherPanel.avgWindSpeedComboBox.setValue(avgWindSpeedUnitsString);

        crosswindUnitsID = currentData.getCurrentProfile().getUnitSetting("crosswind");
        String crosswindUnitsString = UnitLabelUtilities.velocityUnitIndexToString(crosswindUnitsID);
        ProfileOtherPanel.crosswindComboBox.setValue(crosswindUnitsString);

        densityAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("densityAltitude");
        String densityAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(densityAltitudeUnitsID);
        ProfileOtherPanel.densityAltitudeComboBox.setValue(densityAltitudeUnitsString);

        gustWindSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("gustWindSpeed");
        String gustWindSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(gustWindSpeedUnitsID);
        ProfileOtherPanel.gustWindSpeedComboBox.setValue(gustWindSpeedUnitsString);

        headwindUnitsID = currentData.getCurrentProfile().getUnitSetting("headwind");
        String headwindUnitsString = UnitLabelUtilities.velocityUnitIndexToString(headwindUnitsID);
        ProfileOtherPanel.headwindComboBox.setValue(headwindUnitsString);

        launchWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("launchWeight");
        String launchWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(launchWeightUnitsID);
        ProfileOtherPanel.launchWeightComboBox.setValue(launchWeightUnitsString);

        pressureUnitsID = currentData.getCurrentProfile().getUnitSetting("pressure");
        String pressureUnitsString = UnitLabelUtilities.pressureUnitIndexToString(pressureUnitsID);
        ProfileOtherPanel.pressureComboBox.setValue(pressureUnitsString);

        runDirectionUnitsID = currentData.getCurrentProfile().getUnitSetting("runDirection");
        String runDirectionUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runDirectionUnitsID);
        ProfileOtherPanel.runDirectionComboBox.setValue(runDirectionUnitsString);

        windDirectionUnitsID = currentData.getCurrentProfile().getUnitSetting("windDirection");
        String windDirectionUnitsUnitsString = UnitLabelUtilities.degreesUnitIndexToString(windDirectionUnitsID);
        ProfileOtherPanel.windDirectionComboBox.setValue(windDirectionUnitsUnitsString);

        runLengthUnitsID = currentData.getCurrentProfile().getUnitSetting("runLength");
        String runLengthUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(runLengthUnitsID);
        ProfileOtherPanel.runLengthComboBox.setValue(runLengthUnitsString);

        temperatureUnitsID = currentData.getCurrentProfile().getUnitSetting("temperature");
        String temperatureUnitsString = UnitLabelUtilities.tempUnitIndexToString(temperatureUnitsID);
        ProfileOtherPanel.temperatureComboBox.setValue(temperatureUnitsString);
    }

    @FXML
    protected void initialize() throws IOException {
        ProfilePilotPanel = new ProfilePilotPanel();
        ProfileGliderPanel = new ProfileGliderPanel();
        ProfileAirfieldPanel = new ProfileAirfieldPanel();
        ProfileRunwayPanel = new ProfileRunwayPanel();
        ProfileGliderPositionPanel = new ProfileGliderPositionPanel();
        ProfileWinchPositionPanel = new ProfileWinchPositionPanel();
        ProfileDisplayPanel = new ProfileDisplayPanel();
        ProfileOtherPanel = new ProfileOtherPanel();

        //init pilot profile panel
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Configuration/ProfilePilotPanel.fxml"));
        loader.setController(ProfilePilotPanel);
        Parent root = loader.load();
        profilePilotPanel.setRoot(root);

        //init glider profile panel
        loader = new FXMLLoader(getClass().getResource("/Configuration/ProfileGliderPanel.fxml"));
        loader.setController(ProfileGliderPanel);
        root = loader.load();
        profileGliderPanel.setRoot(root);

        //init airfield profile panel
        loader = new FXMLLoader(getClass().getResource("/Configuration/ProfileAirfieldPanel.fxml"));
        loader.setController(ProfileAirfieldPanel);
        root = loader.load();
        profileAirfieldPanel.setRoot(root);

        //init runway profile panel
        loader = new FXMLLoader(getClass().getResource("/Configuration/ProfileRunwayPanel.fxml"));
        loader.setController(ProfileRunwayPanel);
        root = loader.load();
        profileRunwayPanel.setRoot(root);

        //init glider position profile panel
        loader = new FXMLLoader(getClass().getResource("/Configuration/ProfileGliderPositionPanel.fxml"));
        loader.setController(ProfileGliderPositionPanel);
        root = loader.load();
        profileGliderPositionPanel.setRoot(root);

        //init winch position profile panel
        loader = new FXMLLoader(getClass().getResource("/Configuration/ProfileWinchPositionPanel.fxml"));
        loader.setController(ProfileWinchPositionPanel);
        root = loader.load();
        profileWinchPositionPanel.setRoot(root);

        //inti other profile panel
        loader = new FXMLLoader(getClass().getResource("/Configuration/ProfileOtherPanel.fxml"));
        loader.setController(ProfileOtherPanel);
        root = loader.load();
        profileOtherPanel.setRoot(root);

        Rebuild();
    }

    public void ResetButton_Click(javafx.event.ActionEvent e) {
        ProfilePilotPanel.flightWeightComboBox.setValue("lbs");
        ProfileAirfieldPanel.airfieldAltitudeComboBox.setValue("ft");
        ProfileGliderPositionPanel.gliderPosAltitudeComboBox.setValue("ft");
        ProfileRunwayPanel.runwayMagneticHeadingComboBox.setValue("magnetic");
        ProfileWinchPositionPanel.winchPosAltitudeComboBox.setValue("ft");
        ProfileGliderPanel.emptyWeightComboBox.setValue("lbs");
        ProfileGliderPanel.maxGrossWeightComboBox.setValue("lbs");
        ProfileGliderPanel.stallSpeedComboBox.setValue("mph");
        ProfileGliderPanel.weakLinkStrengthComboBox.setValue("lbf");
        ProfileGliderPanel.maxWinchingSpeedComboBox.setValue("mph");;
        ProfileGliderPanel.maxTensionComboBox.setValue("lbf");
        ProfileGliderPanel.ballastWeightComboBox.setValue("lbs");
        ProfileGliderPanel.baggageWeightComboBox.setValue("lbs");
        ProfileGliderPanel.passengerWeightComboBox.setValue("lbs");
        ProfileOtherPanel.avgWindSpeedComboBox.setValue("mph");
        ProfileOtherPanel.crosswindComboBox.setValue("mph");
        ProfileOtherPanel.densityAltitudeComboBox.setValue("ft");
        ProfileOtherPanel.gustWindSpeedComboBox.setValue("mph");
        ProfileOtherPanel.headwindComboBox.setValue("mph");
        ProfileOtherPanel.launchWeightComboBox.setValue("lbs");
        ProfileOtherPanel.pressureComboBox.setValue("millibar");
        ProfileOtherPanel.runDirectionComboBox.setValue("magnetic");
        ProfileOtherPanel.runLengthComboBox.setValue("ft");
        ProfileOtherPanel.temperatureComboBox.setValue("F");
        ProfileOtherPanel.windDirectionComboBox.setValue("magnetic");
    }

    @Override
    public void update() {
        if (currentData.getCurrentProfile() != null) {
            Rebuild();
        }
    }

    @Override
    public void update(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
