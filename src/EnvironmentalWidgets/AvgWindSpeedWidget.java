/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import Configuration.UnitConversionRate;
import Configuration.UnitConversionToIndexUtilities;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.CurrentLaunchInformation;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.Color;

/**
 *
 * @author jtroxel
 */
public class AvgWindSpeedWidget extends EnvironmentalWidget {

    public AvgWindSpeedWidget(TextField textField, CheckBox checkBox, Label units) {
        super(textField, checkBox, units);
    }

    @Override
    public void update() {
        //field.setBackground(Color.WHITE);
        if (manualEntry())
        {
            try{
                float speed = Float.parseFloat(field.getText()) / UnitConversionRate.convertSpeedUnitIndexToFactor(unitId);
                CurrentWidgetDataSet.getInstance().setValue("avgwindspeed", String.valueOf(speed));
            }catch (NumberFormatException e){
                //field.setBackground(Color.PINK);
            }
        }
        else
        {
            if (CurrentWidgetDataSet.getInstance().getValue("avgwindspeed").equals(""))
            {
                field.setText("");
            }
            else
            {
                float speed = (Float.parseFloat(CurrentWidgetDataSet.getInstance().getValue("avgwindspeed")) * UnitConversionRate.convertSpeedUnitIndexToFactor(unitId));
                field.setText(String.format("%.2f", speed));
            }
        }
    }

    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        unitId = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile().getUnitSetting("avgwindspeed");
        unit.setText(" " + UnitLabelUtilities.velocityUnitIndexToString(unitId));
    }
    
}
