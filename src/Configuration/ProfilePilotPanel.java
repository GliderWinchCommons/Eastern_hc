package Configuration;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;


public class ProfilePilotPanel {
    @FXML     ChoiceBox flightWeightComboBox;
    //protected JComboBox flightWeightComboBox;

    @FXML
    protected void initialize(){
        flightWeightComboBox.setItems(FXCollections.observableArrayList("lbs", "kg"));
    }
}
