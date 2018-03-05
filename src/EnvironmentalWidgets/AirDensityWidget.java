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

/**
 *
 * @author User
 */
public class AirDensityWidget extends EnvironmentalWidget {

    public AirDensityWidget(TextField field, CheckBox edit, Label unit) {
        super(field, edit, unit);
    }

    @Override
    public void update() {
        //field.setBackground(Color.WHITE);
        if (manualEntry()) {
            try {
                float airDen = Float.parseFloat(field.getText()) / UnitConversionRate.convertPressureUnitIndexToFactor(unitId);
                CurrentWidgetDataSet.getInstance().setValue("airDensity", String.valueOf(airDen));
            } catch (NumberFormatException e) {
                //field.setBackground(Color.PINK);
            }
        } else {
            if (CurrentWidgetDataSet.getInstance().getValue("airDensity").equals("")) {
                field.setText("");
            } else {
                float airDen = (Float.parseFloat(CurrentWidgetDataSet.getInstance().getValue("airDensity")) * UnitConversionRate.convertPressureUnitIndexToFactor(unitId));
                field.setText(String.format("%.2f", airDen));
            }
        }
        setupUnits();
    }

    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        unitId = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile().getUnitSetting("airDensity");
        unit.setText(" " + UnitLabelUtilities.pressureUnitIndexToString(unitId));
    }
}
