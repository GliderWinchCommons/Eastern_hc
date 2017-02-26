package Configuration;

import DataObjects.CurrentDataObjectSet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import javax.swing.JComboBox;


public class ProfileAirfieldPanel{
    private   CurrentDataObjectSet currentData;
    @FXML protected ChoiceBox            airfieldAltitudeComboBox;

    @FXML
    protected void initialize(){
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        airfieldAltitudeComboBox.setItems(FXCollections.observableArrayList("ft", "m", "km", "mi"));
    }
}
