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


public class ProfileGliderPanel {
    private         CurrentDataObjectSet currentData;
    @FXML protected ChoiceBox            emptyWeightComboBox;
    @FXML protected ChoiceBox            maxGrossWeightComboBox;
    @FXML protected ChoiceBox            stallSpeedComboBox;
    @FXML protected ChoiceBox            weakLinkStrengthComboBox;
    @FXML protected ChoiceBox            maxWinchingSpeedComboBox;
    @FXML protected ChoiceBox            maxTensionComboBox;
    @FXML protected ChoiceBox            ballastWeightComboBox;
    @FXML protected ChoiceBox            baggageWeightComboBox;
    @FXML protected ChoiceBox            passengerWeightComboBox;

    @FXML
    protected void initialize(){
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        stallSpeedComboBox.setItems(FXCollections.observableArrayList("kts", "mph", "kph", "m/s", "kn"));
        maxWinchingSpeedComboBox.setItems(FXCollections.observableArrayList("kts", "mph", "kph", "m/s", "kn"));
        weakLinkStrengthComboBox.setItems(FXCollections.observableArrayList("lbf", "N", "kgf", "daN"));
        maxTensionComboBox.setItems(FXCollections.observableArrayList("lbf", "N", "kgf", "daN"));
        emptyWeightComboBox.setItems(FXCollections.observableArrayList("lbs", "kg"));
        maxGrossWeightComboBox.setItems(FXCollections.observableArrayList("lbs", "kg"));
        ballastWeightComboBox.setItems(FXCollections.observableArrayList("lbs", "kg"));
        baggageWeightComboBox.setItems(FXCollections.observableArrayList("lbs", "kg"));
        passengerWeightComboBox.setItems(FXCollections.observableArrayList("lbs", "kg"));
    }   
}
