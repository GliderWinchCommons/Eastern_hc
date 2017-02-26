package Configuration;

import DataObjects.CurrentDataObjectSet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

/**
 * Created by micha on 1/30/2017.
 */
public class ProfileRunwayPanel
{
    private   CurrentDataObjectSet currentData;
    @FXML protected ChoiceBox            runwayMagneticHeadingComboBox;

    @FXML
    protected void initialize()
    {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        runwayMagneticHeadingComboBox.setItems(FXCollections.observableArrayList("magnetic", "true"));
    }
}
