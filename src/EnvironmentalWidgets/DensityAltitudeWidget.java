/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.CurrentLaunchInformation;
import DataObjects.Operator;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.Color;

/**
 *
 * @author jtroxel
 */
public class DensityAltitudeWidget extends EnvironmentalWidget {

    public DensityAltitudeWidget(TextField field, CheckBox edit, Label unit) {
        super(field, edit, unit);
    }

    @Override
    public void update() {
        //field.setBackground(Color.WHITE);
        if (manualEntry())
        {
            try{
                float alt = Float.parseFloat(field.getText()) / UnitConversionRate.convertDistanceUnitIndexToFactor(unitId);
                CurrentWidgetDataSet.getInstance().setValue("densityaltitude", String.valueOf(alt));
            }catch (NumberFormatException e){
                //field.setBackground(Color.PINK);
            }
        }
        else
        {
            float alt = CurrentLaunchInformation.getCurrentLaunchInformation().getCalculatedDensityAltitude() * UnitConversionRate.convertDistanceUnitIndexToFactor(unitId);
            field.setText(String.format("%.2f", alt));
        }
    }

    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        Operator temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile();
        unitId = temp.getUnitSetting("densityaltitude");
        unit.setText(" " + UnitLabelUtilities.lenghtUnitIndexToString(unitId));
    }
    
}
