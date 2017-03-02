/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParameterSelection;

import AddEditPanels.*;
import Communications.Observer;
import Configuration.ProfileManagementFrame;
import DataObjects.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Johnny White
 */
public class CurrentScenario implements Observer {

    @FXML
    SubScene pilotPanel;
    @FXML
    SubScene gliderPanel;
    @FXML
    SubScene airfieldPanel;
    @FXML
    SubScene runwayPanel;
    @FXML
    SubScene gliderPosPanel;
    @FXML
    SubScene winchPosPanel;
    @FXML
    SubScene drumPanel;
    @FXML
    SubScene parachutePanel;
    @FXML
    GridPane scenarioHomePanel;
    @FXML
    AnchorPane flightDashboardPanel;

    @FXML
    SubScene pilotAddEditPanel;
    @FXML
    SubScene gliderAddEditPanel;
    @FXML
    SubScene airfieldAddEditPanel;
    @FXML
    SubScene runwayAddEditPanel;
    @FXML
    SubScene gliderPositionAddEditPanel;
    @FXML
    SubScene winchPositionAddEditPanel;

    @FXML
    Button pilotButton;
    @FXML
    Button gliderButton;
    @FXML
    Button airfieldButton;
    @FXML
    Button drumButton;
    @FXML
    Rectangle pilotGridBackground;
    @FXML
    Rectangle gliderGridBackground;
    @FXML
    Rectangle runDescriptionGridBackground;
    @FXML
    Rectangle drumGridBackground;

    private CurrentDataObjectSet data;
    private ProfileManagementFrame ProfileManagementFrame;

    /**
     * Creates new form CurrentScenario
     */
    public CurrentScenario() throws IOException {
        data = CurrentDataObjectSet.getCurrentDataObjectSet();
        data.attach(this);
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
        editAirfield.attach(airfield);

        AddEditRunwayFrame editRunway = new AddEditRunwayFrame(runwayPanel);
        RunwayPanel runway = new RunwayPanel(runwayAddEditPanel, gliderPosPanel, editRunway);
        editRunway.attach(runway);

        AddEditGliderPosFrame editGliderPos = new AddEditGliderPosFrame(gliderPosPanel);
        GliderPositionPanel gliderPos = new GliderPositionPanel(gliderPositionAddEditPanel, winchPosPanel, editGliderPos);
        editGliderPos.attach(gliderPos);

        AddEditWinchPosFrame editWinchPos = new AddEditWinchPosFrame(winchPosPanel);
        WinchPositionPanel winchPos = new WinchPositionPanel(winchPositionAddEditPanel, scenarioHomePanel, editWinchPos);
        editWinchPos.attach(winchPos);

        AddEditPilotPanel editPilot = new AddEditPilotPanel(pilotPanel);
        PilotPanel pilot = new PilotPanel(pilotAddEditPanel, scenarioHomePanel, editPilot);
        editPilot.attach(pilot);

        AddEditGlider editGlider = new AddEditGlider(gliderPanel);
        SailplanePanel sailplane = new SailplanePanel(gliderAddEditPanel, scenarioHomePanel, editGlider);
        editGlider.attach(sailplane);

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
        loader.setController(new DrumPanel(parachutePanel));
        root = loader.load();
        drumPanel.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/ParachuteScene.fxml"));
        loader.setController(new ParachutePanel(scenarioHomePanel));
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

        loadScenario();
    }

    private void loadScenario() {

        Pilot pilot = data.getCurrentPilot();
        Sailplane glider = data.getCurrentSailplane();
        Airfield airfield = data.getCurrentAirfield();
        GliderPosition position = data.getCurrentGliderPosition();
        Runway runway = data.getCurrentRunway();
        WinchPosition winch = data.getCurrentWinchPosition();
        Drum drum = data.getCurrentDrum();
        Operator profile = data.getCurrentProfile();
        //Stop light red
        Color unstartedBackground = Color.web("#fb122f");
        //traffic light green
        Color completeBackground = Color.web("#27e833");
        //Luminious Vivid Amber
        Color incompleteBackground = Color.web("#FFBF00");

        if (pilot == null) {
            pilotGridBackground.setFill(unstartedBackground);
        } else {
            pilotGridBackground.setFill(completeBackground);
        }

        if (airfield == null && runway == null && position == null && winch == null) {
            runDescriptionGridBackground.setFill(unstartedBackground);
        } else if (airfield != null && runway != null && position != null && winch != null) {
            runDescriptionGridBackground.setFill(completeBackground);
        } else {
            runDescriptionGridBackground.setFill(incompleteBackground);
        }

        if (glider == null) {
            gliderGridBackground.setFill(unstartedBackground);
        } else {
            gliderGridBackground.setFill(completeBackground);
        }

        if (drum == null) {
            drumGridBackground.setFill(unstartedBackground);
        } else {
            drumGridBackground.setFill(completeBackground);
            /*if (drum.getParachute() == null)
            {
                drumLabel.setForeground(new Color(255, 102, 0));
            }
            else
            {
                drumLabel.setForeground(new Color(0, 128, 0));
            }
            drumLabel.setText(drum.toString());*/
        }

        /*if(profile == null) {
            profileLabel.setText("Default Profile");
            profileLabel.setForeground(Color.RED);
        } else {
            profileLabel.setText(profile.toString());
            profileLabel.setForeground(new Color(0,128,0));
        }*/
    }

    public void update() {
        loadScenario();
    }

    public void update(String s) {
    }
}
