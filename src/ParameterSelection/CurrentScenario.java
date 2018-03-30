/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParameterSelection;

import AddEditPanels.*;
import Communications.Observer;
import Configuration.ProfileManagementFrame;
import Configuration.UnitLabelUtilities;
import DataObjects.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Johnny White
 */
public class CurrentScenario implements Observer {

    @FXML
    private SubScene pilotPanel;
    @FXML
    private SubScene gliderPanel;
    @FXML
    private SubScene airfieldPanel;
    @FXML
    private SubScene runwayPanel;
    @FXML
    private SubScene gliderPosPanel;
    @FXML
    private SubScene winchPosPanel;
    @FXML
    private SubScene drumPanel;
    @FXML
    private SubScene parachutePanel;
    @FXML
    private GridPane scenarioHomePanel;
    @FXML
    private AnchorPane flightDashboardPanel;

    @FXML
    private SubScene pilotAddEditPanel;
    @FXML
    private SubScene gliderAddEditPanel;
    @FXML
    private SubScene airfieldAddEditPanel;
    @FXML
    private SubScene runwayAddEditPanel;
    @FXML
    private SubScene gliderPositionAddEditPanel;
    @FXML
    private SubScene winchPositionAddEditPanel;
    @FXML
    private SubScene drumAddEditPanel;
    @FXML
    private SubScene parachuteAddEditPanel;

    @FXML
    private Rectangle pilotGridBackground;
    @FXML
    private Rectangle gliderGridBackground;
    @FXML
    private Rectangle runDescriptionGridBackground;
    @FXML
    private Rectangle drumGridBackground;

    @FXML
    private Label pilotNameLabel;
    @FXML
    private Label nNumberLabel;
    @FXML
    private Label totalWeightLabel;
    @FXML
    private Label totalWeightUnitsLabel;
    @FXML
    private Label maxWeakLinkStrengthLabel;
    @FXML
    private Label maxWeakLinkStrengthUnitsLabel;
    @FXML
    private Label airfieldNameLabel;
    @FXML
    private Label designatorLabel;
    @FXML
    private Label airfieldElevationLabel;
    @FXML
    private Label airfieldElevationUnitsLabel;
    @FXML
    private Label runwayLabel;
    @FXML
    private Label gliderPositionLabel;
    @FXML
    private Label winchPositionLabel;

    private CurrentDataObjectSet currentData;
    private ProfileManagementFrame ProfileManagementFrame;
    private ArrayList<Observer> observers;

    /**
     * Creates new form CurrentScenario
     */
    public CurrentScenario() throws IOException {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        currentData.attach(this);
        observers = new ArrayList<Observer>();
    }

    @FXML
    public void EditAirFieldButton_Click(javafx.event.ActionEvent e) {
        airfieldPanel.toFront();
    }

    @FXML
    public void PilotButton_Click(javafx.event.ActionEvent e) {
        pilotPanel.toFront();
    }

    @FXML
    public void GliderButton_Click(javafx.event.ActionEvent e) {
        gliderPanel.toFront();
    }

    @FXML
    public void DrumButton_Click(javafx.event.ActionEvent e) {
        drumPanel.toFront();
    }

    @FXML
    protected void initialize() throws IOException {
        //initialize the addEdit classes to be passed to the view panels
        //then attach the view panels to the add edit panels so they can be updated
        AddEditAirfieldFrame editAirfield = new AddEditAirfieldFrame(airfieldPanel);
        AirfieldPanel airfield = new AirfieldPanel(airfieldAddEditPanel, runwayPanel, editAirfield);

        AddEditRunwayFrame editRunway = new AddEditRunwayFrame(runwayPanel);
        RunwayPanel runway = new RunwayPanel(runwayAddEditPanel, gliderPosPanel, editRunway);

        AddEditGliderPosFrame editGliderPos = new AddEditGliderPosFrame(gliderPosPanel);
        GliderPositionPanel gliderPos = new GliderPositionPanel(gliderPositionAddEditPanel, winchPosPanel, editGliderPos);

        AddEditWinchPosFrame editWinchPos = new AddEditWinchPosFrame(winchPosPanel);
        WinchPositionPanel winchPos = new WinchPositionPanel(winchPositionAddEditPanel, scenarioHomePanel, editWinchPos);

        AddEditPilotPanel editPilot = new AddEditPilotPanel(pilotPanel);
        PilotPanel pilot = new PilotPanel(pilotAddEditPanel, scenarioHomePanel, editPilot);

        AddEditGlider editGlider = new AddEditGlider(gliderPanel);
        SailplanePanel sailplane = new SailplanePanel(gliderAddEditPanel, scenarioHomePanel, editGlider);

        AddEditDrumPanel editDrum = new AddEditDrumPanel(drumPanel);
        DrumPanel drum = new DrumPanel(drumAddEditPanel, parachutePanel, editDrum);

        AddEditParachutePanel editParachute = new AddEditParachutePanel(parachutePanel);
        ParachutePanel parachute = new ParachutePanel(parachuteAddEditPanel, scenarioHomePanel, editParachute);

        //Load the display panes
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParameterSelection/PilotScene.fxml"));
        loader.setController(pilot);
        Parent root = loader.load();
        pilotPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/SailplaneScene.fxml"));
        loader.setController(sailplane);
        root = loader.load();
        gliderPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/AirfieldScene.fxml"));
        loader.setController(airfield);
        root = loader.load();
        airfieldPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/RunwayScene.fxml"));
        loader.setController(runway);
        root = loader.load();
        runwayPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/GliderPositionScene.fxml"));
        loader.setController(gliderPos);
        root = loader.load();
        gliderPosPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/WinchPositionScene.fxml"));
        loader.setController(winchPos);
        root = loader.load();
        winchPosPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/DrumScene.fxml"));
        loader.setController(drum);
        root = loader.load();
        drumPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/ParachuteScene.fxml"));
        loader.setController(parachute);
        root = loader.load();
        parachutePanel.setRoot(root);

        //Load the Add/Edit Panes
        loader = new FXMLLoader(getClass().getResource("/AddEditPanels/AddEditPilotPanel.fxml"));
        loader.setController(editPilot);
        root = loader.load();
        pilotAddEditPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/AddEditPanels/AddEditGliderPanel.fxml"));
        loader.setController(editGlider);
        root = loader.load();
        gliderAddEditPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/AddEditPanels/AddEditAirfieldPanel.fxml"));
        loader.setController(editAirfield);
        root = loader.load();
        airfieldAddEditPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/AddEditPanels/AddEditRunwayPanel.fxml"));
        loader.setController(editRunway);
        root = loader.load();
        runwayAddEditPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/AddEditPanels/AddEditGliderPositionPanel.fxml"));
        loader.setController(editGliderPos);
        root = loader.load();
        gliderPositionAddEditPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/AddEditPanels/AddEditWinchPositionPanel.fxml"));
        loader.setController(editWinchPos);
        root = loader.load();
        winchPositionAddEditPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/AddEditPanels/AddEditDrumPanel.fxml"));
        loader.setController(editDrum);
        root = loader.load();
        drumAddEditPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/AddEditPanels/AddEditParachutePanel.fxml"));
        loader.setController(editParachute);
        root = loader.load();
        parachuteAddEditPanel.setRoot(root);

        //editAirfield.attach(airfield);
        currentData.attach(editAirfield);
        currentData.attach(airfield);

        //editRunway.attach(runway);
        currentData.attach(editRunway);
        currentData.attach(runway);

        //editGliderPos.attach(gliderPos);
        currentData.attach(editGliderPos);
        currentData.attach(gliderPos);

        //editWinchPos.attach(winchPos);
        currentData.attach(editWinchPos);
        currentData.attach(winchPos);

        //editPilot.attach(pilot);
        currentData.attach(editPilot);
        currentData.attach(pilot);

        //editGlider.attach(sailplane);
        currentData.attach(editGlider);
        currentData.attach(sailplane);

        //editDrum.attach(drum);
        currentData.attach(editDrum);
        currentData.attach(drum);

        //editParachute.attach(parachute);
        currentData.attach(editParachute);
        currentData.attach(parachute);

        loadScenario();
    }

    private void loadScenario() {
        //Stop light red
        Color unstartedBackground = Color.web("#fb122f");
        //traffic light green
        Color completeBackground = Color.web("#27e833");
        //Luminious Vivid Amber
        Color incompleteBackground = Color.web("#FFBF00");
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        Pilot pilot = currentData.getCurrentPilot();
        if (pilot == null) {
            pilotNameLabel.setText("");
            pilotGridBackground.setFill(unstartedBackground);
        } else {
            pilotNameLabel.setText(pilot.getFirstName() + " " + pilot.getMiddleName() + " " + pilot.getLastName());
            pilotGridBackground.setFill(completeBackground);
        }

        Sailplane glider = currentData.getCurrentSailplane();
        if (glider == null) {
            nNumberLabel.setText("");
            totalWeightLabel.setText("");
            totalWeightUnitsLabel.setText("");
            maxWeakLinkStrengthLabel.setText("");
            maxWeakLinkStrengthUnitsLabel.setText("");
            gliderGridBackground.setFill(unstartedBackground);
        } else {
            nNumberLabel.setText(glider.getRegNumber());
            //totalWeightLabel.setText();
            //totalWeightUnitsLabel.setText("");
            maxWeakLinkStrengthLabel.setText(df.format(glider.getMaxWeakLinkStrength()));
            maxWeakLinkStrengthUnitsLabel.setText("°");  //Set to degrees for now
            gliderGridBackground.setFill(completeBackground);
        }
        Airfield airfield = currentData.getCurrentAirfield();
        Runway runway = currentData.getCurrentRunway();
        GliderPosition position = currentData.getCurrentGliderPosition();
        WinchPosition winch = currentData.getCurrentWinchPosition();
        if (airfield == null) {
            airfieldNameLabel.setText("");
            designatorLabel.setText("");
            airfieldElevationLabel.setText("");
            airfieldElevationUnitsLabel.setText("");
            runwayLabel.setText("");
            gliderPositionLabel.setText("");
            winchPositionLabel.setText("");
            runDescriptionGridBackground.setFill(unstartedBackground);
        } else {
            runDescriptionGridBackground.setFill(incompleteBackground);
            airfieldNameLabel.setText(airfield.getName());
            designatorLabel.setText(airfield.getDesignator());
            airfieldElevationLabel.setText(Float.toString(airfield.getElevation()));
            int airfieldAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldAltitude");
            airfieldElevationUnitsLabel.setText(UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID));
            if (runway == null) {
                runwayLabel.setText("");
                gliderPositionLabel.setText("");
                winchPositionLabel.setText("");
            } else {
                runwayLabel.setText(Float.toString(runway.getMagneticHeading()));
                if (position == null) {
                    gliderPositionLabel.setText("");
                } else {
                    gliderPositionLabel.setText(position.getName());
                }
                if (winch == null) {
                    winchPositionLabel.setText("");
                } else {
                    winchPositionLabel.setText(winch.getName());
                }
                if (winch != null && position != null) {
                    runDescriptionGridBackground.setFill(completeBackground);
                }
            }
        }
        Drum drum = currentData.getCurrentDrum();
        //TODO update summary panel information for drums and parachutes
        if (drum == null) {
            drumGridBackground.setFill(unstartedBackground);
        } else {
            drumGridBackground.setFill(completeBackground);
        }
        Operator profile = currentData.getCurrentProfile();

        for (Observer o : observers) {
            o.update();
        }
    }

    public void update() {
        loadScenario();
    }

    public void update(String s) {
    }
}
