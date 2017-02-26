/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import Configuration.UnitConversionToIndexUtilities;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.CurrentLaunchInformation;

/**
 *
 * @author jtroxel
 */
public class RunDirectionWidget extends EnvironmentalWidget {

    public RunDirectionWidget() {
        super(null, null, null);
    }

    @Override
    public void update() {
        float direction = (CurrentLaunchInformation.getCurrentLaunchInformation().getRunHeading());
        if (unitId == UnitConversionToIndexUtilities.degreesUnitStringToIndex("true")){
            direction -= CurrentLaunchInformation.getCurrentLaunchInformation().getAirfieldMagneticVariation();
            direction = direction % 360f;
        }
        field.setText(String.format("%.2f", direction));
    }

    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        unitId = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile().getUnitSetting("rundirection");
        unit.setText(" degrees " + UnitLabelUtilities.degreesUnitIndexToString(unitId));
    }
    
}
