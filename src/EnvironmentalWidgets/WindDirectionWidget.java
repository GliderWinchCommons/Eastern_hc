/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.CurrentLaunchInformation;
import DataObjects.Operator;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author jtroxel
 */
public class WindDirectionWidget extends EnvironmentalWidget {

    public WindDirectionWidget(TextField field, CheckBox edit, Label unit) {
        super(field, edit, unit);
    }

    @Override
    public void update() {
        if (manualEntry()) {
            try {
                float direction = Float.parseFloat(field.getText());
                if (unitId == 0) {
                    direction -= CurrentLaunchInformation.getCurrentLaunchInformation().getAirfieldMagneticVariation();
                } else if (unitId == 2) {
                    direction += CurrentLaunchInformation.getCurrentLaunchInformation().getRunHeading();
                }
                direction = direction % 360f;
                CurrentWidgetDataSet.getInstance().setValue("winddirection", String.valueOf(direction));
            } catch (NumberFormatException e) {
                //field.setBackground(Color.PINK);
            }
        } else {
            String value = CurrentWidgetDataSet.getInstance().getValue("winddirection");
            switch (unitId) {
                case 0:
                    if (value.equals("")) {
                        this.field.setText("");
                    } else {
                        float direction = Float.parseFloat(value);
                        direction -= CurrentLaunchInformation.getCurrentLaunchInformation().getAirfieldMagneticVariation();
                        direction = direction % 360f;
                        this.field.setText(String.format("%.2f", direction));
                    }
                    break;
                case 1:
                    if (value.equals("")) {
                        this.field.setText("");
                    } else {
                        float direction = Float.parseFloat(value);
                        direction = direction % 360f;
                        this.field.setText(String.format("%.2f", direction));
                    }
                    break;
                case 2:
                    if (value.equals("")) {
                        this.field.setText("");
                    } else {
                        this.field.setText(String.format("%.2f", CurrentLaunchInformation.getCurrentLaunchInformation().getWindDegreeOffset()));
                    }
                    break;
                default:
                    this.field.setText("");
                    break;
            }
        }
        setupUnits();
    }

    @Override

    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        Operator temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile();
        unitId = temp.getUnitSetting("winddirection");
        unit.setText(" degrees " + UnitLabelUtilities.degreesUnitIndexToString(unitId));
    }

}
