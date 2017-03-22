package Configuration;

import DataObjects.CurrentDataObjectSet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ProfileGliderPanel {

    private CurrentDataObjectSet currentData;
    @FXML
    protected ChoiceBox emptyWeightComboBox;
    @FXML
    protected ChoiceBox maxGrossWeightComboBox;
    @FXML
    protected ChoiceBox stallSpeedComboBox;
    @FXML
    protected ChoiceBox weakLinkStrengthComboBox;
    @FXML
    protected ChoiceBox maxWinchingSpeedComboBox;
    @FXML
    protected ChoiceBox maxTensionComboBox;
    @FXML
    protected ChoiceBox ballastWeightComboBox;
    @FXML
    protected ChoiceBox baggageWeightComboBox;
    @FXML
    protected ChoiceBox passengerWeightComboBox;

    @FXML
    protected void initialize() {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        stallSpeedComboBox.setItems(FXCollections.observableArrayList("kph", "mph", "m/s", "kn", "kts"));
        maxWinchingSpeedComboBox.setItems(FXCollections.observableArrayList("kph", "mph", "m/s", "kn", "kts"));
        weakLinkStrengthComboBox.setItems(FXCollections.observableArrayList("N", "lbf", "kgf", "daN"));
        maxTensionComboBox.setItems(FXCollections.observableArrayList("N", "lbf", "kgf", "daN"));
        emptyWeightComboBox.setItems(FXCollections.observableArrayList("kg", "lbs"));
        maxGrossWeightComboBox.setItems(FXCollections.observableArrayList("kg", "lbs"));
        ballastWeightComboBox.setItems(FXCollections.observableArrayList("kg", "lbs"));
        baggageWeightComboBox.setItems(FXCollections.observableArrayList("kg", "lbs"));
        passengerWeightComboBox.setItems(FXCollections.observableArrayList("kg", "lbs"));
    }
}
