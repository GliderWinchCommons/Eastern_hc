package ParameterSelection;

import DataObjects.CurrentDataObjectSet;
import DataObjects.Sailplane;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javafx.event.ActionEvent;

import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentLaunchInformation;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class SailplanePanel implements Observer {

    private CurrentDataObjectSet currentData;

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
    public SailplanePanel(SubScene editSailplanePanel, GridPane scenarioHomePane) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        launchInfo = CurrentLaunchInformation.getCurrentLaunchInformation();
        launchInfo.setSailplanePanel(this);
        this.editSailplanePanel = editSailplanePanel;
        this.scenarioHomePane = scenarioHomePane;
    }

    @FXML
    protected void initialize() {
        TableColumn registrationCol = (TableColumn) gliderTable.getColumns().get(0);
        registrationCol.setCellValueFactory(new PropertyValueFactory<>("Registration"));

        gliderTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getSailplanes()));
        gliderTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentGlider((Sailplane) newValue);
                loadData();
            }
        });
        gliderTable.getSelectionModel().selectFirst();
        loadData();
    }

    public void loadData() {
        if (currentData.getCurrentSailplane() != null) {
            registrationNumberLabel.setText("" + currentData.getCurrentSailplane().getRegistration());
            ownerLabel.setText("" + currentData.getCurrentSailplane().getOwner());
            emptyWeightLabel.setText("" + currentData.getCurrentSailplane().getEmptyWeight());
            maxGrossWeightLabel.setText("" + currentData.getCurrentSailplane().getMaxGrossWeight());
            indicatedStallSpeedLabel.setText("" + currentData.getCurrentSailplane().getIndicatedStallSpeed());
            maxWinchingSpeedLabel.setText("" + currentData.getCurrentSailplane().getMaxWinchingSpeed());
            maxWeakLinkStrengthLabel.setText("" + currentData.getCurrentSailplane().getMaxWeakLinkStrength());
            maxTensionLabel.setText("" + currentData.getCurrentSailplane().getMaxTension());
            cableReleaseAngleLabel.setText("" + currentData.getCurrentSailplane().getCableReleaseAngle());
            setupUnits();
        }
    }

    @Override
    public void update() {
        setupUnits();
        loadData();
        DefaultListModel sailplaneModel = new DefaultListModel();
        sailplaneModel.clear();

        Sailplane currentSailplane = currentData.getCurrentSailplane();

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
