package ParameterSelection;

import Communications.Observer;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.GliderPosition;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

/**
 * Created by micha on 10/18/2016.
 */
public class GliderPositionPanel extends JPanel implements Observer {

    private int gliderPosAltitudeUnitsID;

    private CurrentDataObjectSet currentData;

    SubScene editGliderPositionPanel;
    SubScene winchPosPane;

    @FXML
    TableView gliderPositionTable;

    @FXML
    Label positionNameLabel;
    @FXML
    Label altitudeLabel;
    @FXML
    Label longitudeLabel;
    @FXML
    Label latitudeLabel;

    @FXML
    Label altitudeUnitLabel;
    @FXML
    Label longitudeUnitLabel;
    @FXML
    Label latitudeUnitLabel;

    public GliderPositionPanel(SubScene editGliderPositionPanel, SubScene winchPosPane) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.editGliderPositionPanel = editGliderPositionPanel;
        this.winchPosPane = winchPosPane;
    }

    @FXML
    protected void initialize() {

        TableColumn gliderPosCol = (TableColumn) gliderPositionTable.getColumns().get(0);
        gliderPosCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        gliderPositionTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getGliderPositions()));
        gliderPositionTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentGliderPosition((GliderPosition) newValue);
                loadData();
            }
        });
        gliderPositionTable.getSelectionModel().selectFirst();

        loadData();
    }

    public void loadData() {
        if (currentData.getCurrentGliderPosition() != null) {
            positionNameLabel.setText("" + currentData.getCurrentGliderPosition().getName());
            altitudeLabel.setText("" + currentData.getCurrentGliderPosition().getElevation());
            longitudeLabel.setText("" + currentData.getCurrentGliderPosition().getLongitude());
            latitudeLabel.setText("" + currentData.getCurrentGliderPosition().getLatitude());
            setupUnits();
        }
    }

    public void setupUnits() {
        gliderPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("gliderPosAltitude");
        String gliderPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(gliderPosAltitudeUnitsID);
    }

    @Override
    public void update() {

    }

    @Override
    public void update(String s) {

    }

    public void clear() {

    }

    private Observer getObserver() {
        return this;
    }

    @FXML
    public void ChooseWinchPosButton_Click(javafx.event.ActionEvent e) {
        winchPosPane.toFront();
    }

    @FXML
    public void NewGliderPositionButton_Click(ActionEvent e) {
        editGliderPositionPanel.toFront();
    }
}
