package Configuration;

import DataObjects.CurrentDataObjectSet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;


public class ProfileOtherPanel {
    private   CurrentDataObjectSet currentData;
    @FXML protected ChoiceBox            launchWeightComboBox;
    @FXML protected ChoiceBox            runLengthComboBox;
    @FXML protected ChoiceBox            runDirectionComboBox;
    @FXML protected ChoiceBox            crosswindComboBox;
    @FXML protected ChoiceBox            headwindComboBox;
    @FXML protected ChoiceBox            densityAltitudeComboBox;
    @FXML protected ChoiceBox            gustWindSpeedComboBox;
    @FXML protected ChoiceBox            pressureComboBox;
    @FXML protected ChoiceBox            temperatureComboBox;
    @FXML protected ChoiceBox            avgWindSpeedComboBox;
    @FXML protected ChoiceBox            windDirectionComboBox;

    @FXML
    protected void initialize(){
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();

        launchWeightComboBox.setItems(FXCollections.observableArrayList("lbs", "kg"));
        runLengthComboBox.setItems(FXCollections.observableArrayList("ft", "m", "km", "mi"));
        densityAltitudeComboBox.setItems(FXCollections.observableArrayList("ft", "m", "km", "mi"));
        runDirectionComboBox.setItems(FXCollections.observableArrayList("magnetic", "true"));
        windDirectionComboBox.setItems(FXCollections.observableArrayList("magnetic", "true"));
        crosswindComboBox.setItems(FXCollections.observableArrayList("kts", "mph", "kph", "m/s", "kn"));
        headwindComboBox.setItems(FXCollections.observableArrayList("kts", "mph", "kph", "m/s", "kn"));
        avgWindSpeedComboBox.setItems(FXCollections.observableArrayList("kts", "mph", "kph", "m/s", "kn"));
        gustWindSpeedComboBox.setItems(FXCollections.observableArrayList("kts", "mph", "kph", "m/s", "kn"));
        temperatureComboBox.setItems(FXCollections.observableArrayList("F", "C"));
        pressureComboBox.setItems(FXCollections.observableArrayList("millibar", "psi", "hPa", "kpa", "bar", "atm"));
    }   
}

