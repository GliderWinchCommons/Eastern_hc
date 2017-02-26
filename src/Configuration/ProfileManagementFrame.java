package Configuration;

import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.util.Random;
import Communications.Observer;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntrySelect;
import ParameterSelection.CurrentScenario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class ProfileManagementFrame {

    @FXML SubScene profilePilotPanel;
    @FXML SubScene profileGliderPanel;
    @FXML SubScene profileAirfieldPanel;
    @FXML SubScene profileRunwayPanel;
    @FXML SubScene profileGliderPositionPanel;
    @FXML SubScene profileWinchPositionPanel;
    @FXML SubScene profileOtherPanel;

    private Observer parent;
    private ProfilePilotPanel ProfilePilotPanel;
    private ProfileGliderPanel ProfileGliderPanel;
    private ProfileAirfieldPanel ProfileAirfieldPanel;
    private ProfileRunwayPanel ProfileRunwayPanel;
    private ProfileGliderPositionPanel ProfileGliderPositionPanel;
    private ProfileWinchPositionPanel ProfileWinchPositionPanel;
    private ProfileDisplayPanel ProfileDisplayPanel;
    private ProfileOtherPanel ProfileOtherPanel;
    private SaveAsNewFrame SaveAsNewFrame;
    private List<Operator> names = new ArrayList<Operator>();
    private JScrollPane profileScrollPane;
    private JList profileJList;
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
    private JButton saveButton;
    private JButton saveAsNewButton;
    private JButton addNewButton;
    
    public void setParent(Observer p)
    {
        parent = p;
    }

    public ProfileManagementFrame()
    {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        initProfileList();
    }

    private ProfileManagementFrame getCurrentProfileManagementFrame()
    {
        return this;
    }

    public void getUnitsForProfile()
    {
        flightWeightUnits = (String)ProfilePilotPanel.flightWeightComboBox.getValue();
        airfieldAltitudeUnits = (String)ProfileAirfieldPanel.airfieldAltitudeComboBox.getValue();
        gliderPosAltitudeUnits = (String)ProfileGliderPositionPanel.gliderPosAltitudeComboBox.getValue();
        runwayMagneticHeadingUnits = (String) ProfileRunwayPanel.runwayMagneticHeadingComboBox.getValue();
        winchPosAltitudeUnits = (String) ProfileWinchPositionPanel.winchPosAltitudeComboBox.getValue();
        emptyWeightUnits = (String)ProfileGliderPanel.emptyWeightComboBox.getValue();
        maxGrossWeightUnits = (String)ProfileGliderPanel.maxGrossWeightComboBox.getValue();
        stallSpeedUnits = (String)ProfileGliderPanel.stallSpeedComboBox.getValue();
        weakLinkStrengthUnits = (String)ProfileGliderPanel.weakLinkStrengthComboBox.getValue();
        maxWinchingSpeedUnits = (String)ProfileGliderPanel.maxWinchingSpeedComboBox.getValue();
        maxTensionUnits = (String)ProfileGliderPanel.maxTensionComboBox.getValue();
        ballastWeightUnits = (String)ProfileGliderPanel.ballastWeightComboBox.getValue();
        baggageWeightUnits = (String)ProfileGliderPanel.baggageWeightComboBox.getValue();
        passengerWeightUnits = (String)ProfileGliderPanel.passengerWeightComboBox.getValue();
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
    
    public void selectButtonClicked()
    {
        if(profileJList.getSelectedIndex() >= 0){
            Operator selectedProfile = (Operator)profileJList.getSelectedValue();
            currentData.setCurrentProfile(selectedProfile);
            parent.update();
        }
    }

    public void saveButtonClicked()
    {
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
        parent.update();
        try {
            DatabaseEntryEdit.UpdateEntry(currentProfile_); 
        } catch (Exception e) {
            e.printStackTrace();
            //do nothing for now...
        }
        //System.out.println(currentData.getCurrentProfile().getUnitSettingsForStorage());
    }

    public void addNewButtonClicked()
    {
        getUnitsForProfile();
        Random randomId = new Random();
        int temp = randomId.nextInt(100000000);
        Operator newProfile = new Operator(temp,"{}","{}");
        
        newProfile.setUnitSetting("flightWeight", 1);
        newProfile.setUnitSetting("emptyWeight", 1);
        newProfile.setUnitSetting("maxGrossWeight", 1);
        newProfile.setUnitSetting("stallSpeed", 1);
        newProfile.setUnitSetting("ballastWeight", 1);
        newProfile.setUnitSetting("baggageWeight", 1);
        newProfile.setUnitSetting("passengerWeight", 1);
        newProfile.setUnitSetting("maxTension", 1);
        newProfile.setUnitSetting("weakLinkStrength", 1);
        newProfile.setUnitSetting("winchingSpeed", 1);
        newProfile.setUnitSetting("airfieldAltitude", 1);
        newProfile.setUnitSetting("gliderPosAltitude", 1);
        newProfile.setUnitSetting("runwayMagneticHeading", 1);
        newProfile.setUnitSetting("winchPosAltitude", 1);
        newProfile.setUnitSetting("avgWindSpeed", 1);
        newProfile.setUnitSetting("crosswind", 1);
        newProfile.setUnitSetting("gustWindSpeed", 1);
        newProfile.setUnitSetting("headwind", 1);
        newProfile.setUnitSetting("launchWeight", 1);
        newProfile.setUnitSetting("densityAltitude", 1);
        newProfile.setUnitSetting("runLength", 1);
        newProfile.setUnitSetting("pressure", 4);
        newProfile.setUnitSetting("temperature", 1);
        newProfile.setUnitSetting("runDirection", 1);
        newProfile.setUnitSetting("windDirection", 1);
        
        currentData.setCurrentProfile(newProfile);
        SaveAsNewFrame = new SaveAsNewFrame();
        SaveAsNewFrame.setParent(getCurrentProfileManagementFrame());
        SaveAsNewFrame.setVisible(true);
        parent.update();
    }
    
    public void saveAsNewButtonClicked()
    {
        getUnitsForProfile();
        Random randomId = new Random();
        String temp = String.valueOf(randomId.nextInt(100000000));
        Operator newProfile = new Operator(0, temp,"{}");
        newProfile.setUnitSetting("flightWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(flightWeightUnits));
        newProfile.setUnitSetting("emptyWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(emptyWeightUnits));
        newProfile.setUnitSetting("maxGrossWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(maxGrossWeightUnits));
        newProfile.setUnitSetting("ballastWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(ballastWeightUnits));
        newProfile.setUnitSetting("baggageWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(baggageWeightUnits));
        newProfile.setUnitSetting("passengerWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(passengerWeightUnits));
        newProfile.setUnitSetting("airfieldAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(airfieldAltitudeUnits));
        newProfile.setUnitSetting("gliderPosAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(gliderPosAltitudeUnits));
        newProfile.setUnitSetting("winchPosAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(winchPosAltitudeUnits));
        newProfile.setUnitSetting("runwayMagneticHeading", UnitConversionToIndexUtilities.degreesUnitStringToIndex(runwayMagneticHeadingUnits));
        newProfile.setUnitSetting("maxTension", UnitConversionToIndexUtilities.tensionUnitStringToIndex(maxTensionUnits));
        newProfile.setUnitSetting("weakLinkStrength", UnitConversionToIndexUtilities.tensionUnitStringToIndex(weakLinkStrengthUnits));
        newProfile.setUnitSetting("stallSpeed", UnitConversionToIndexUtilities.tensionUnitStringToIndex(stallSpeedUnits));
        newProfile.setUnitSetting("winchingSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(maxWinchingSpeedUnits));
        newProfile.setUnitSetting("avgWindSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(avgWindSpeedUnits));
        newProfile.setUnitSetting("crosswind", UnitConversionToIndexUtilities.velocityUnitStringToIndex(crosswindUnits));
        newProfile.setUnitSetting("gustWindSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(gustWindSpeedUnits));
        newProfile.setUnitSetting("headwind", UnitConversionToIndexUtilities.velocityUnitStringToIndex(headwindUnits));
        newProfile.setUnitSetting("launchWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(launchWeightUnits));
        newProfile.setUnitSetting("densityAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(densityAltitudeUnits));
        newProfile.setUnitSetting("runLength", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(runLengthUnits));
        newProfile.setUnitSetting("pressure", UnitConversionToIndexUtilities.pressureUnitStringToIndex(pressureUnits));
        newProfile.setUnitSetting("temperature", UnitConversionToIndexUtilities.tempUnitStringToIndex(temperatureUnits));
        newProfile.setUnitSetting("runDirection", UnitConversionToIndexUtilities.degreesUnitStringToIndex(runDirectionUnits));
        newProfile.setUnitSetting("windDirection", UnitConversionToIndexUtilities.degreesUnitStringToIndex(windDirectionUnits));
        currentData.setCurrentProfile(newProfile);
        SaveAsNewFrame = new SaveAsNewFrame();
        SaveAsNewFrame.setParent(getCurrentProfileManagementFrame());
        SaveAsNewFrame.setVisible(true);
        parent.update();
    }

    private void initProfileList() 
    {
        names = DatabaseEntrySelect.getOperators();
    }

    private void profileJListSelectionChanged(ListSelectionEvent listSelectionEvent) {
        if(profileJList.getSelectedIndex() >= 0){
            try{
                Operator selectedProfile = (Operator)profileJList.getSelectedValue();
                currentData.setCurrentProfile(selectedProfile);
                
                flightWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
                String flightWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID);
                ProfilePilotPanel.flightWeightComboBox.setValue(flightWeightUnitsString);
                //ProfilePilotPanel.flightWeightComboBox.setEnabled(true);

                airfieldAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldAltitude");
                String airfieldAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID);
                ProfileAirfieldPanel.airfieldAltitudeComboBox.setValue(airfieldAltitudeUnitsString);
                //ProfileAirfieldPanel.airfieldAltitudeComboBox.setEnabled(true);
                
                gliderPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("gliderPosAltitude");
                String gliderPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(gliderPosAltitudeUnitsID);
                ProfileGliderPositionPanel.gliderPosAltitudeComboBox.setValue(gliderPosAltitudeUnitsString);
                //ProfileAirfieldPanel.gliderPosAltitudeComboBox.setEnabled(true);
                
                runwayMagneticHeadingUnitsID = currentData.getCurrentProfile().getUnitSetting("runwayMagneticHeading");
                String runwayMagneticHeadingUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runwayMagneticHeadingUnitsID);
                ProfileRunwayPanel.runwayMagneticHeadingComboBox.setValue(runwayMagneticHeadingUnitsString);
                //ProfileAirfieldPanel.runwayMagneticHeadingComboBox.setEnabled(true);
                
                winchPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("winchPosAltitude");
                String winchPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(winchPosAltitudeUnitsID);
                ProfileWinchPositionPanel.winchPosAltitudeComboBox.setValue(winchPosAltitudeUnitsString);
                //ProfileAirfieldPanel.winchPosAltitudeComboBox.setEnabled(true);
                
                emptyWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("emptyWeight");
                String emptyWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(emptyWeightUnitsID);
                ProfileGliderPanel.emptyWeightComboBox.setValue(emptyWeightUnitsString);
                //ProfileGliderPanel.emptyWeightComboBox.setEnabled(true);
                
                maxGrossWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("maxGrossWeight");
                String maxGrossWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(maxGrossWeightUnitsID);
                ProfileGliderPanel.maxGrossWeightComboBox.setValue(maxGrossWeightUnitsString);
                //ProfileGliderPanel.maxGrossWeightComboBox.setEnabled(true);
                
                stallSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("stallSpeed");
                String stallSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(stallSpeedUnitsID);
                ProfileGliderPanel.stallSpeedComboBox.setValue(stallSpeedUnitsString);
                //ProfileGliderPanel.stallSpeedComboBox.setEnabled(true);
                
                ballastWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("ballastWeight");
                String ballastWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(ballastWeightUnitsID);
                ProfileGliderPanel.ballastWeightComboBox.setValue(ballastWeightUnitsString);
                //ProfileGliderPanel.ballastWeightComboBox.setEnabled(true);
                
                baggageWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("baggageWeight");
                String baggageWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(baggageWeightUnitsID);
                ProfileGliderPanel.baggageWeightComboBox.setValue(baggageWeightUnitsString);
                //ProfileGliderPanel.baggageWeightComboBox.setEnabled(true);

                passengerWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("passengerWeight");
                String passengerWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(passengerWeightUnitsID);
                ProfileGliderPanel.passengerWeightComboBox.setValue(passengerWeightUnitsString);
                //ProfileGliderPanel.passengerWeightComboBox.setEnabled(true);
                    
                tensionUnitsID = currentData.getCurrentProfile().getUnitSetting("maxTension");
                String tensionUnitsString = UnitLabelUtilities.tensionUnitIndexToString(tensionUnitsID);
                ProfileGliderPanel.maxTensionComboBox.setValue(tensionUnitsString);
                //ProfileGliderPanel.maxTensionComboBox.setEnabled(true);
                
                weakLinkStrengthUnitsID = currentData.getCurrentProfile().getUnitSetting("weakLinkStrength");
                String weakLinkStrengthUnitsString = UnitLabelUtilities.tensionUnitIndexToString(weakLinkStrengthUnitsID);
                ProfileGliderPanel.weakLinkStrengthComboBox.setValue(weakLinkStrengthUnitsString);
                //ProfileGliderPanel.weakLinkStrengthComboBox.setEnabled(true);
                
                winchingSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("winchingSpeed");
                String winchingSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(winchingSpeedUnitsID);
                ProfileGliderPanel.maxWinchingSpeedComboBox.setValue(winchingSpeedUnitsString);
                //ProfileGliderPanel.maxWinchingSpeedComboBox.setEnabled(true);
                
                avgWindSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("avgWindSpeed");
                String avgWindSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(avgWindSpeedUnitsID);
                ProfileOtherPanel.avgWindSpeedComboBox.setValue(avgWindSpeedUnitsString);
                //ProfileOtherPanel.avgWindSpeedComboBox.setEnabled(true);
                
                crosswindUnitsID = currentData.getCurrentProfile().getUnitSetting("crosswind");
                String crosswindUnitsString = UnitLabelUtilities.velocityUnitIndexToString(crosswindUnitsID);
                ProfileOtherPanel.crosswindComboBox.setValue(crosswindUnitsString);
                //ProfileOtherPanel.crosswindComboBox.setEnabled(true);
                
                densityAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("densityAltitude");
                String densityAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(densityAltitudeUnitsID);
                ProfileOtherPanel.densityAltitudeComboBox.setValue(densityAltitudeUnitsString);
                //ProfileOtherPanel.densityAltitudeComboBox.setEnabled(true);
                
                gustWindSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("gustWindSpeed");
                String gustWindSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(gustWindSpeedUnitsID);
                ProfileOtherPanel.gustWindSpeedComboBox.setValue(gustWindSpeedUnitsString);
                //ProfileOtherPanel.gustWindSpeedComboBox.setEnabled(true);
                
                headwindUnitsID = currentData.getCurrentProfile().getUnitSetting("headwind");
                String headwindUnitsString = UnitLabelUtilities.velocityUnitIndexToString(headwindUnitsID);
                ProfileOtherPanel.headwindComboBox.setValue(headwindUnitsString);
                //ProfileOtherPanel.headwindComboBox.setEnabled(true);
                
                launchWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("launchWeight");
                String launchWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(launchWeightUnitsID);
                ProfileOtherPanel.launchWeightComboBox.setValue(launchWeightUnitsString);
                //ProfileOtherPanel.launchWeightComboBox.setEnabled(true);
                
                pressureUnitsID = currentData.getCurrentProfile().getUnitSetting("pressure");
                String pressureUnitsString = UnitLabelUtilities.pressureUnitIndexToString(pressureUnitsID);
                ProfileOtherPanel.pressureComboBox.setValue(pressureUnitsString);
                //ProfileOtherPanel.pressureComboBox.setEnabled(true);
                
                runDirectionUnitsID = currentData.getCurrentProfile().getUnitSetting("runDirection");
                String runDirectionUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runDirectionUnitsID);
                ProfileOtherPanel.runDirectionComboBox.setValue(runDirectionUnitsString);
                //ProfileOtherPanel.runDirectionComboBox.setEnabled(true);
                
                windDirectionUnitsID = currentData.getCurrentProfile().getUnitSetting("windDirection");
                String windDirectionUnitsUnitsString = UnitLabelUtilities.degreesUnitIndexToString(windDirectionUnitsID);
                ProfileOtherPanel.windDirectionComboBox.setValue(windDirectionUnitsUnitsString);
                //ProfileOtherPanel.windDirectionComboBox.setEnabled(true);
                
                runLengthUnitsID = currentData.getCurrentProfile().getUnitSetting("runLength");
                String runLengthUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(runLengthUnitsID);
                ProfileOtherPanel.runLengthComboBox.setValue(runLengthUnitsString);
                //ProfileOtherPanel.runLengthComboBox.setEnabled(true);
                
                temperatureUnitsID = currentData.getCurrentProfile().getUnitSetting("temperature");
                String temperatureUnitsString = UnitLabelUtilities.tempUnitIndexToString(temperatureUnitsID);
                ProfileOtherPanel.temperatureComboBox.setValue(temperatureUnitsString);
                //ProfileOtherPanel.temperatureComboBox.setEnabled(true);
                
                saveButton.setEnabled(true);
                saveAsNewButton.setEnabled(true);
                parent.update();
            } catch(Exception e) {
                //TODO respond to error
            }
        }
    }

    public void Rebuild()
    {
        initProfileList();
        DefaultListModel profileModel = new DefaultListModel();
        for(Object str: names){
            profileModel.addElement(str);
        }
        profileJList.setModel(profileModel);
        profileScrollPane.setViewportView(profileJList);
                
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
    protected void initialize() throws IOException{
        ProfilePilotPanel = new ProfilePilotPanel();
        ProfileGliderPanel = new ProfileGliderPanel();
        ProfileAirfieldPanel = new ProfileAirfieldPanel();
        ProfileRunwayPanel = new ProfileRunwayPanel();
        ProfileGliderPositionPanel = new ProfileGliderPositionPanel();
        ProfileWinchPositionPanel = new ProfileWinchPositionPanel();
        ProfileDisplayPanel = new ProfileDisplayPanel();
        ProfileOtherPanel = new ProfileOtherPanel();
        profileScrollPane = new javax.swing.JScrollPane();

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

        profileJList = new javax.swing.JList();
        profileJList.setPreferredSize(new Dimension(200, 100));
        DefaultListModel profileModel = new DefaultListModel();
        for(Object str: names){
            profileModel.addElement(str);
        }
        profileJList.setModel(profileModel);
        profileJList.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent listSelectionEvent)
            {
                profileJListSelectionChanged(listSelectionEvent);
            }
        });
        profileScrollPane.setViewportView(profileJList);

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
}
