/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParameterSelection;

import DataObjects.CurrentDataObjectSet;
import EnvironmentalWidgets.EnvironmentalWidget;
import EnvironmentalWidgets.LaunchWeightWidget;
import Communications.MessagePipeline;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import Communications.Observer;
import DataObjects.CurrentLaunchInformation;
import EnvironmentalWidgets.AvgWindSpeedWidget;
import EnvironmentalWidgets.CrossWindComponentWidget;
import EnvironmentalWidgets.CurrentWidgetDataSet;
import EnvironmentalWidgets.DensityAltitudeWidget;
import EnvironmentalWidgets.GustWindSpeedWidget;
import EnvironmentalWidgets.HeadWindComponentWidget;
import EnvironmentalWidgets.HumidityWidget;
import EnvironmentalWidgets.PressureWidget;
import EnvironmentalWidgets.RunDirectionWidget;
import EnvironmentalWidgets.RunLengthWidget;
import EnvironmentalWidgets.RunSlopeWidget;
import EnvironmentalWidgets.TemperatureWidget;
import EnvironmentalWidgets.WindDirectionWidget;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Jacob Troxel
 */
public class EnvironmentalWindow implements Observer {

    private MessagePipeline pipe;
    private ArrayList<EnvironmentalWidget> widgets;
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
    
    private void addWidget(EnvironmentalWidget ew)
    {
        widgets.add(ew);
    }


    @Override
    public void update()
    {
        for(EnvironmentalWidget ew : widgets)
        {
            ew.update();
            ew.setupUnits();
        }
    }

    @Override
    public void update(String msg) {
    }

    //=================================================================================================================================================================================

    @FXML
    protected void initialize(){
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
        addWidget(new PressureWidget(pressureTextField, pressureCheckBox, pressureUnitLabel));
    }

    //TextFields
    @FXML TextField launchWeightTextField;
    @FXML TextField windDirectionTextField;
    @FXML TextField avgWindSpeedTextField;
    @FXML TextField gustWindSpeedTextField;
    @FXML TextField densityAltitudeTextField;
    @FXML TextField temperatureTextField;
    @FXML TextField pressureTextField;
    @FXML TextField humidityTextField;

    //Units
    @FXML Label launchWeightUnitLabel;
    @FXML Label windDirectionUnitLabel;
    @FXML Label avgWindSpeedUnitLabel;
    @FXML Label gustWindSpeedUnitLabel;
    @FXML Label densityAltitudeUnitLabel;
    @FXML Label temperatureUnitLabel;
    @FXML Label pressureUnitLabel;
    @FXML Label humidityUnitLabel;

    //TextFields
    @FXML CheckBox windDirectionCheckBox;
    @FXML CheckBox avgWindSpeedCheckBox;
    @FXML CheckBox gustWindSpeedCheckBox;
    @FXML CheckBox densityAltitudeCheckBox;
    @FXML CheckBox temperatureCheckBox;
    @FXML CheckBox pressureCheckBox;
    @FXML CheckBox humidityCheckBox;

    @FXML
    public void CheckBoxBinding(javafx.event.ActionEvent e)
    {
        CheckBox box = (CheckBox) e.getSource();
        FlowPane fp = (FlowPane) box.getParent();
        //The text field is the second child in the flow panel
        //[0] = checkbox
        //[1] = textfield
        //[2] = unit label
        TextField tf = (TextField) fp.getChildren().toArray()[1];
        tf.setEditable(box.isSelected());
        if(tf.isEditable())
            tf.requestFocus();
    }
}
