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

/**
 *
 * @author jtroxel
 */
public class RunLengthWidget extends EnvironmentalWidget {

    public RunLengthWidget() 
    {
        super(null, null, null);
    }

    @Override
    public void update()
    {
        float length = (CurrentLaunchInformation.getCurrentLaunchInformation().getRunLength()) * UnitConversionRate.convertDistanceUnitIndexToFactor(unitId);
        field.setText(String.format("%.2f", length));
    }

    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        Operator temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile();
        unitId = temp.getUnitSetting("runlength");
        unit.setText(" " + UnitLabelUtilities.lenghtUnitIndexToString(unitId));
    }
    
}
