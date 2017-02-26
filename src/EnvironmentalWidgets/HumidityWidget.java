/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.Color;

/**
 *
 * @author jtroxel
 */
public class HumidityWidget extends EnvironmentalWidget {

    public HumidityWidget(TextField field, CheckBox edit, Label unit) {
        super(field, edit, unit);
    }

    @Override
    public void update() {
        //field.setBackground(Color.WHITE);
        if (manualEntry())
        {
            try{
                float humid = Float.parseFloat(field.getText());
                CurrentWidgetDataSet.getInstance().setValue("humidity", String.valueOf(humid));
            }catch (NumberFormatException e){
                //field.setBackground(Color.PINK);
            }
        }
        else
        {
            if (CurrentWidgetDataSet.getInstance().getValue("humidity").equals(""))
            {
                field.setText("");
            }
            else
            {
                float speed = Float.parseFloat(CurrentWidgetDataSet.getInstance().getValue("humidity"));
                field.setText(String.format("%.2f", speed));
            }
        }
    }

    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        //unitId = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile().getUnitSetting("humidity");
        unit.setText(" %");
    }
    
}
