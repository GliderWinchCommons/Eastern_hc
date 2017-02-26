package EnvironmentalWidgets;

import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.CurrentLaunchInformation;
import DataObjects.Operator;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author jtroxel
 */
public class LaunchWeightWidget extends EnvironmentalWidget {

    public LaunchWeightWidget(TextField field, Label unit)
    {
        super(field, null, unit);
    }

    @Override
    public void update()
    {
        float weight = (CurrentLaunchInformation.getCurrentLaunchInformation().getGliderLaunchMass()) * UnitConversionRate.convertWeightUnitIndexToFactor(unitId);
        field.setText(String.format("%.2f", weight));
    }

    @Override
    public void update(String msg) {}

    @Override
    public void setupUnits() {
        Operator temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile();
        unitId = temp.getUnitSetting("launchweight");
        unit.setText(" " + UnitLabelUtilities.weightUnitIndexToString(unitId));
    }
    
}
