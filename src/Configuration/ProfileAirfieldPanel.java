package Configuration;

import DataObjects.CurrentDataObjectSet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ProfileAirfieldPanel {

    private CurrentDataObjectSet currentData;
    @FXML
    protected ChoiceBox airfieldAltitudeComboBox;

    @FXML
    protected void initialize() {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        airfieldAltitudeComboBox.setItems(FXCollections.observableArrayList("m", "ft", "mm", "cm", "km", "mi", "in"));
    }
}
