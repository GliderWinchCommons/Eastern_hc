package Configuration;

import DataObjects.CurrentDataObjectSet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ProfileOtherPanel {

    private CurrentDataObjectSet currentData;
    @FXML
    protected ChoiceBox launchWeightComboBox;
    @FXML
    protected ChoiceBox runLengthComboBox;
    @FXML
    protected ChoiceBox runDirectionComboBox;
    @FXML
    protected ChoiceBox crosswindComboBox;
    @FXML
    protected ChoiceBox headwindComboBox;
    @FXML
    protected ChoiceBox densityAltitudeComboBox;
    @FXML
    protected ChoiceBox gustWindSpeedComboBox;
    @FXML
    protected ChoiceBox pressureComboBox;
    @FXML
    protected ChoiceBox temperatureComboBox;
    @FXML
    protected ChoiceBox avgWindSpeedComboBox;
    @FXML
    protected ChoiceBox windDirectionComboBox;

    @FXML
    protected void initialize() {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();

        launchWeightComboBox.setItems(FXCollections.observableArrayList("kg", "lbs"));
        runLengthComboBox.setItems(FXCollections.observableArrayList("m", "ft", "mm", "cm", "km", "mi", "in"));
        densityAltitudeComboBox.setItems(FXCollections.observableArrayList("m", "ft", "mm", "cm", "km", "mi", "in"));
        runDirectionComboBox.setItems(FXCollections.observableArrayList("true", "magnetic", "relative"));
        windDirectionComboBox.setItems(FXCollections.observableArrayList("true", "magnetic", "relative"));
        crosswindComboBox.setItems(FXCollections.observableArrayList("kph", "mph", "m/s", "kn", "kts"));
        headwindComboBox.setItems(FXCollections.observableArrayList("kph", "mph", "m/s", "kn", "kts"));
        avgWindSpeedComboBox.setItems(FXCollections.observableArrayList("kph", "mph", "m/s", "kn", "kts"));
        gustWindSpeedComboBox.setItems(FXCollections.observableArrayList("kph", "mph", "m/s", "kn", "kts"));
        temperatureComboBox.setItems(FXCollections.observableArrayList("C", "F"));
        pressureComboBox.setItems(FXCollections.observableArrayList("hPa", "kPa", "psi", "bar", "millibar", "atm"));
    }
}
