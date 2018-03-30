package ParameterSelection;

import AddEditPanels.AddEditGlider;
import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.CurrentLaunchInformation;
import DataObjects.Sailplane;
import DatabaseUtilities.DatabaseEntrySelect;
import java.awt.Color;
import java.text.DecimalFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javax.swing.JTextField;

public class SailplanePanel implements Observer {

    private CurrentDataObjectSet currentData;
    private AddEditGlider editFrame;

    @FXML
    TableView gliderTable;

    //Field Values
    @FXML
    Label registrationNumberLabel;
    @FXML
    Label ownerLabel;
    @FXML
    Label emptyWeightLabel;
    @FXML
    Label maxGrossWeightLabel;
    @FXML
    Label indicatedStallSpeedLabel;
    @FXML
    Label maxWinchingSpeedLabel;
    @FXML
    Label maxWeakLinkStrengthLabel;
    @FXML
    Label maxTensionLabel;
    @FXML
    Label cableReleaseAngleLabel;

    //Unit Values
    @FXML
    Label emptyWeightUnitLabel;
    @FXML
    Label maxGrossWeightUnitLabel;
    @FXML
    Label indicatedStallSpeedUnitLabel;
    @FXML
    Label maxWinchingSpeedUnitLabel;
    @FXML
    Label maxWeakLinkStrengthUnitLabel;
    @FXML
    Label maxTensionUnitLabel;
    @FXML
    Label cableReleaseAngleUnitLabel;
    @FXML
    Label ballastUnitLabel;
    @FXML
    Label baggageUnitLabel;
    @FXML
    Label totalPassengerWeightUnitLabel;

    //Unit IDs
    private int emptyWeightUnitsID;
    private int maxGrossWeightUnitsID;
    private int stallSpeedUnitsID;
    private int tensionUnitsID;
    private int weakLinkStrengthUnitsID;
    private int winchingSpeedUnitsID;
    private int ballastWeightUnitsID;
    private int baggageWeightUnitsID;
    private int passengerWeightUnitsID;
    
    //Buttons
    @FXML
    Button editBtn;
    @FXML
    Button addBtn;

    @FXML
    TextField baggageField;
    @FXML
    TextField ballastField;
    @FXML
    TextField passengerWeightField;

    private CurrentLaunchInformation launchInfo;

    SubScene editSailplanePanel;
    GridPane scenarioHomePane;

    /**
     * Creates new form sailplanePanel
     */
    public SailplanePanel(SubScene editSailplanePanel, GridPane scenarioHomePane, AddEditGlider editFrame) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        launchInfo = CurrentLaunchInformation.getCurrentLaunchInformation();
        launchInfo.setSailplanePanel(this);
        this.editSailplanePanel = editSailplanePanel;
        this.scenarioHomePane = scenarioHomePane;
        this.editFrame = editFrame;
    }

    @FXML
    protected void initialize() {
        TableColumn registrationCol = (TableColumn) gliderTable.getColumns().get(0);
        registrationCol.setCellValueFactory(new PropertyValueFactory<>("regNumber"));

        gliderTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getSailplanes()));
        gliderTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentGlider((Sailplane) newValue);
            }
        });
        
        ballastField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                    ballastField.setText(oldValue);
                }
            }
        });
        
        baggageField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                    baggageField.setText(oldValue);
                }
            }
        });
        
        passengerWeightField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                    passengerWeightField.setText(oldValue);
                }
            }
        });
        
        //gliderTable.getSelectionModel().selectFirst();
        loadData();
        //setupUnits();
    }

    public void loadData() {
        if (currentData.getCurrentSailplane() != null) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            
            registrationNumberLabel.setText("" + currentData.getCurrentSailplane().getRegNumber());
            ownerLabel.setText("" + currentData.getCurrentSailplane().getOwner());
            emptyWeightLabel.setText("" + df.format(currentData.getCurrentSailplane().getEmptyWeight() * UnitConversionRate.convertWeightUnitIndexToFactor(emptyWeightUnitsID)));
            maxGrossWeightLabel.setText("" + df.format(currentData.getCurrentSailplane().getMaxGrossWeight() * UnitConversionRate.convertWeightUnitIndexToFactor(maxGrossWeightUnitsID)));
            indicatedStallSpeedLabel.setText("" + df.format(currentData.getCurrentSailplane().getIndicatedStallSpeed() * UnitConversionRate.convertWeightUnitIndexToFactor(stallSpeedUnitsID)));
            maxWinchingSpeedLabel.setText("" + df.format(currentData.getCurrentSailplane().getMaxWinchingSpeed() * UnitConversionRate.convertWeightUnitIndexToFactor(winchingSpeedUnitsID)));
            maxWeakLinkStrengthLabel.setText("" + df.format(currentData.getCurrentSailplane().getMaxWeakLinkStrength() * UnitConversionRate.convertWeightUnitIndexToFactor(weakLinkStrengthUnitsID)));
            maxTensionLabel.setText("" + df.format(currentData.getCurrentSailplane().getMaxTension() * UnitConversionRate.convertWeightUnitIndexToFactor(tensionUnitsID)));
            cableReleaseAngleLabel.setText("" + df.format(currentData.getCurrentSailplane().getCableReleaseAngle()));
            
            editBtn.setDisable(false);
            setupUnits();
        } 
        else {
            //Name Labels
            registrationNumberLabel.setText("");
            ownerLabel.setText("");
            emptyWeightLabel.setText("");
            maxGrossWeightLabel.setText("");
            indicatedStallSpeedLabel.setText("");
            maxWinchingSpeedLabel.setText("");
            maxWeakLinkStrengthLabel.setText("");
            maxTensionLabel.setText("");
            cableReleaseAngleLabel.setText("");
            
            //Unit Labels
            emptyWeightUnitLabel.setText("");
            maxGrossWeightUnitLabel.setText("");
            indicatedStallSpeedUnitLabel.setText("");
            ballastUnitLabel.setText("");
            baggageUnitLabel.setText("");
            totalPassengerWeightUnitLabel.setText("");
            maxTensionUnitLabel.setText("");
            maxWeakLinkStrengthUnitLabel.setText("");
            maxWinchingSpeedUnitLabel.setText("");
            
            editBtn.setDisable(true);
        }
    }

    @Override
    public void update() {
        loadData();
        //setupUnits();
        Sailplane selected = (Sailplane) gliderTable.getSelectionModel().getSelectedItem();
        Sailplane currSailplane = currentData.getCurrentSailplane();
        if (currSailplane == null && selected != null) {
            gliderTable.getItems().remove(selected);
        } else {
            if (!gliderTable.getItems().contains(currSailplane)) {
                gliderTable.getItems().add(currSailplane);
            }
            gliderTable.getSelectionModel().select(currSailplane);
        }
        gliderTable.refresh();
    }

    private void updateLaunchInfo(JTextField textField) {
        try {
            launchInfo.update("Manual Entry");
            Float.parseFloat(textField.getText());
            textField.setBackground(Color.GREEN);
        } catch (NumberFormatException e) {
            textField.setBackground(Color.PINK);
        }
    }

    @FXML
    public void GliderFinishButton_Click(ActionEvent e) {
        scenarioHomePane.toFront();
    }

    @FXML
    public void NewSailplaneButton_Click(ActionEvent e) {
        editFrame.edit(null, false);
        editSailplanePanel.toFront();
    }

    @FXML
    public void EditSailplaneButton_Click(ActionEvent e) {
        editFrame.edit(currentData.getCurrentSailplane(), true);
        editSailplanePanel.toFront();
    }

    public String getbaggageField() {
        return (baggageField.getText());
    }

    public String getballastField() {
        return (ballastField.getText());
    }

    public String getpassengerWeightField() {
        return (passengerWeightField.getText());
    }

    public Boolean getbaggageCheckBox() {
        //return(baggageCheckBox.isSelected());
        return true;
    }

    public void setbaggageField(float setValue) {
        baggageField.setText(String.format("%.2f", (setValue
                * UnitConversionRate.convertWeightUnitIndexToFactor(
                        currentData.getCurrentProfile().getUnitSetting("baggageWeight")))));
    }

    public void setballastField(float setValue) {
        ballastField.setText(String.format("%.2f", (setValue
                * UnitConversionRate.convertWeightUnitIndexToFactor(
                        currentData.getCurrentProfile().getUnitSetting("ballastWeight")))));
    }

    public void setpassengerWeightField(float setValue) {
        passengerWeightField.setText(String.format("%.2f", (setValue
                * UnitConversionRate.convertWeightUnitIndexToFactor(
                        currentData.getCurrentProfile().getUnitSetting("passengerWeight")))));
    }

    public void setupUnits() {
        emptyWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("emptyWeight");
        String emptyWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(emptyWeightUnitsID);
        emptyWeightUnitLabel.setText(emptyWeightUnitsString);

        maxGrossWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("maxGrossWeight");
        String maxGrossWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(maxGrossWeightUnitsID);
        maxGrossWeightUnitLabel.setText(maxGrossWeightUnitsString);

        stallSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("stallSpeed");
        String stallSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(stallSpeedUnitsID);
        indicatedStallSpeedUnitLabel.setText(stallSpeedUnitsString);

        ballastWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("ballastWeight");
        String ballastWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(ballastWeightUnitsID);
        ballastUnitLabel.setText(ballastWeightUnitsString);

        baggageWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("baggageWeight");
        String baggageWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(baggageWeightUnitsID);
        baggageUnitLabel.setText(baggageWeightUnitsString);

        passengerWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("passengerWeight");
        String passengerWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(passengerWeightUnitsID);
        totalPassengerWeightUnitLabel.setText(passengerWeightUnitsString);

        tensionUnitsID = currentData.getCurrentProfile().getUnitSetting("maxTension");
        String tensionUnitsString = UnitLabelUtilities.tensionUnitIndexToString(tensionUnitsID);
        maxTensionUnitLabel.setText(tensionUnitsString);

        weakLinkStrengthUnitsID = currentData.getCurrentProfile().getUnitSetting("weakLinkStrength");
        String weakLinkStrengthUnitsString = UnitLabelUtilities.tensionUnitIndexToString(weakLinkStrengthUnitsID);
        maxWeakLinkStrengthUnitLabel.setText(weakLinkStrengthUnitsString);

        winchingSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("winchingSpeed");
        String winchingSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(winchingSpeedUnitsID);
        maxWinchingSpeedUnitLabel.setText(winchingSpeedUnitsString);
    }

    private Observer getObserver() {
        return this;
    }

    public void clear() {

    }

    @Override
    public void update(String msg) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
