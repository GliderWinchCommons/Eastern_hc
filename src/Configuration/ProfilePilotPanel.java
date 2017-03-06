package Configuration;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ProfilePilotPanel {

    @FXML
    ChoiceBox flightWeightComboBox;
    //protected JComboBox flightWeightComboBox;

    @FXML
    protected void initialize() {
        flightWeightComboBox.setItems(FXCollections.observableArrayList("lbs", "kg"));

    }
}
