/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;


import DataObjects.CurrentDataObjectSet;
import DataObjects.CurrentLaunchInformation;

/**
 *
 * @author jtroxel
 */
public class RunSlopeWidget extends EnvironmentalWidget {

    public RunSlopeWidget() {
        super(null, null, null);
    }

    @Override
    public void update() {
        float slope = CurrentLaunchInformation.getCurrentLaunchInformation().getRunSlope();
        field.setText(String.format("%.2f", slope));
    }


    @Override
    public void update(String msg) {
    }

    @Override
    public void setupUnits() {
        unitId = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile().getUnitSetting("runslope");
        unit.setText(" degrees");
    }
    
}
