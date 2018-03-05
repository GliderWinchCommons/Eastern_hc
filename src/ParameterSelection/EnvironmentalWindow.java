/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParameterSelection;

import Communications.MessagePipeline;
import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import DataObjects.CurrentLaunchInformation;
import DataObjects.Pilot;
import DataObjects.Sailplane;
import EnvironmentalWidgets.AirDensityWidget;
import EnvironmentalWidgets.AvgWindSpeedWidget;
import EnvironmentalWidgets.DensityAltitudeWidget;
import EnvironmentalWidgets.EnvironmentalWidget;
import EnvironmentalWidgets.GustWindSpeedWidget;
import EnvironmentalWidgets.HumidityWidget;
import EnvironmentalWidgets.LaunchWeightWidget;
import EnvironmentalWidgets.PressureWidget;
import EnvironmentalWidgets.TemperatureWidget;
import EnvironmentalWidgets.WindDirectionWidget;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author Jacob Troxel
 */
public class EnvironmentalWindow implements Observer {

    private MessagePipeline pipe;
    private static ArrayList<EnvironmentalWidget> widgets;
    private CurrentDataObjectSet data;
    private CurrentLaunchInformation info;

    public EnvironmentalWindow() {
        data = CurrentDataObjectSet.getCurrentDataObjectSet();
        data.attach(this);
        info = CurrentLaunchInformation.getCurrentLaunchInformation();
        info.attach(this);
        pipe = MessagePipeline.getInstance();
        widgets = new ArrayList<>();
    }

    private void addWidget(EnvironmentalWidget ew) {
        widgets.add(ew);
    }

    @Override
    public void update() {
        for (EnvironmentalWidget ew : widgets) {
            ew.update();
            ew.setupUnits();
            ew.updateTimers();
        }
        if(data.getCurrentPilot() != null) {
            Pilot p = data.getCurrentPilot();
            pilotName.setText(p.getFirstName() + " " + p.getLastName());
        }
        if(data.getCurrentSailplane() != null) {
            Sailplane glider = data.getCurrentSailplane();
            pilotName.setText(glider.getName() + " ");
        }
    }

    @Override
    public void update(String msg) {
    }

    //=================================================================================================================================================================================
    @FXML
    protected void initialize() {
        addWidget(new LaunchWeightWidget(launchWeightTextField, launchWeightUnitLabel));
        //addWidget(new RunLengthWidget());
        //addWidget(new RunDirectionWidget());
        //addWidget(new RunSlopeWidget());
        addWidget(new WindDirectionWidget(windDirectionTextField, windDirectionCheckBox, windDirectionUnitLabel));
        addWidget(new AvgWindSpeedWidget(avgWindSpeedTextField, avgWindSpeedCheckBox, avgWindSpeedUnitLabel));
        addWidget(new GustWindSpeedWidget(gustWindSpeedTextField, gustWindSpeedCheckBox, gustWindSpeedUnitLabel));
        //addWidget(new HeadWindComponentWidget());
        //addWidget(new CrossWindComponentWidget());
        addWidget(new DensityAltitudeWidget(densityAltitudeTextField, densityAltitudeCheckBox, densityAltitudeUnitLabel));
        addWidget(new TemperatureWidget(temperatureTextField, temperatureCheckBox, temperatureUnitLabel));
        addWidget(new HumidityWidget(humidityTextField, humidityCheckBox, humidityUnitLabel));
        addWidget(new PressureWidget(altimeterTextField, altimeterCheckBox, altimeterUnitLabel));
        
        addWidget(new AirDensityWidget(airDensityTextField, airDensityCheckBox, airDensityUnitLabel));
    }

    //TextFields
    @FXML
    TextField launchWeightTextField;
    @FXML
    TextField windDirectionTextField;
    @FXML
    TextField avgWindSpeedTextField;
    @FXML
    TextField gustWindSpeedTextField;
    @FXML
    TextField densityAltitudeTextField;
    @FXML
    TextField temperatureTextField;
    
    // Used to be pressure
    @FXML
    TextField altimeterTextField;
    @FXML
    TextField humidityTextField;
    @FXML
    TextField airDensityTextField;

    //Units
    @FXML
    Label launchWeightUnitLabel;
    @FXML
    Label windDirectionUnitLabel;
    @FXML
    Label avgWindSpeedUnitLabel;
    @FXML
    Label gustWindSpeedUnitLabel;
    @FXML
    Label densityAltitudeUnitLabel;
    @FXML
    Label temperatureUnitLabel;
    
    // Used to be pressure
    @FXML
    Label altimeterUnitLabel;
    @FXML
    Label humidityUnitLabel;
    @FXML
    Label airDensityUnitLabel;

    //TextFields
    @FXML
    CheckBox windDirectionCheckBox;
    @FXML
    CheckBox avgWindSpeedCheckBox;
    @FXML
    CheckBox gustWindSpeedCheckBox;
    @FXML
    CheckBox densityAltitudeCheckBox;
    @FXML
    CheckBox temperatureCheckBox;
    
    // Used to be pressure
    @FXML
    CheckBox altimeterCheckBox;
    @FXML
    CheckBox humidityCheckBox;
    @FXML
    CheckBox airDensityCheckBox;
    
    //Text Labels
    @FXML
    Label pilotName;

    @FXML
    public void CheckBoxBinding(javafx.event.ActionEvent e) {
        CheckBox box = (CheckBox) e.getSource();
        FlowPane fp = (FlowPane) box.getParent();
        //The text field is the second child in the flow panel
        //[0] = checkbox
        //[1] = textfield
        //[2] = unit label
        TextField tf = (TextField) fp.getChildren().toArray()[1];
        tf.setEditable(box.isSelected());
        if (tf.isEditable()) {
            tf.requestFocus();
        }
    }

    public static boolean validate() {
        for (EnvironmentalWidget w : widgets) {
            if (!w.validateWidget()) {
                return false;
            }
        }
        return true;
    }
}
