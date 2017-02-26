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
public class TemperatureWidget extends EnvironmentalWidget {

    public TemperatureWidget(TextField field, CheckBox edit, Label unit) {
        super(field, edit, unit);
    }

    @Override
    public void update() {
        //field.setBackground(Color.WHITE);
        if (manualEntry())
        {
            try{
                float temp = Float.parseFloat(field.getText());
                if (unitId == 1)
                {
                    temp = ((temp - 32f) * (5f/9f));
                }
                CurrentWidgetDataSet.getInstance().setValue("temperature", String.valueOf(temp));
            }catch (NumberFormatException e){
                //field.setBackground(Color.PINK);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else
        {
            if(unitId == 0)
            {
                String value = CurrentWidgetDataSet.getInstance().getValue("temperature");
                if (value.equals("")){
                    this.field.setText("");
                }
                else
                {
                    this.field.setText(String.format("%.2f", value));
                }
            }
            else if(unitId == 1)
            {
                String value = CurrentWidgetDataSet.getInstance().getValue("temperature");
                if (value.equals("")){
                    this.field.setText("0.00");
                }
                else
                {
                    this.field.setText(String.format("%.2f", (Float.parseFloat(CurrentWidgetDataSet.getInstance().getValue("temperature")) * (9f/5f) + 32f)));
                }
            }
        }
    }

    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        unitId = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile().getUnitSetting("temperature");
        unit.setText(" " + UnitLabelUtilities.tempUnitIndexToString(unitId));
    }
    
}
