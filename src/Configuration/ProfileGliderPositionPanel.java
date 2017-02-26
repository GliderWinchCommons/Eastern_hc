package Configuration;

import DataObjects.CurrentDataObjectSet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

/**
 * Created by micha on 1/30/2017.
 */
public class ProfileGliderPositionPanel
{
    private   CurrentDataObjectSet currentData;
    @FXML protected ChoiceBox gliderPosAltitudeComboBox;

    @FXML
    protected void initialize()
    {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        gliderPosAltitudeComboBox.setItems(FXCollections.observableArrayList("ft", "m", "km", "mi"));
    }
}
