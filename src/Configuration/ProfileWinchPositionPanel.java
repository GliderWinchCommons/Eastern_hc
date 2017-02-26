package Configuration;

import DataObjects.CurrentDataObjectSet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

/**
 * Created by micha on 1/30/2017.
 */
public class ProfileWinchPositionPanel
{
    private   CurrentDataObjectSet currentData;
    @FXML protected ChoiceBox            winchPosAltitudeComboBox;

    @FXML
    protected void initialize()
    {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        winchPosAltitudeComboBox.setItems(FXCollections.observableArrayList("ft", "m", "km", "mi"));
    }
}
