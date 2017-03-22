package ParameterSelection;

import AddEditPanels.AddEditGliderPosFrame;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.*;

/**
 * Created by micha on 10/18/2016.
 */
public class GliderPositionPanel extends JPanel implements Observer {

    private int gliderPosAltitudeUnitsID;

    private CurrentDataObjectSet currentData;
    private AddEditGliderPosFrame editFrame;

    SubScene editGliderPositionPanel;
    SubScene winchPosPane;

    @FXML
    TableView gliderPositionTable;

    @FXML
    Label positionNameLabel;
    @FXML
    Label elevationLabel;
    @FXML
    Label longitudeLabel;
    @FXML
    Label latitudeLabel;

    @FXML
    Label elevationUnitLabel;
    @FXML
    Label longitudeUnitLabel;
    @FXML
    Label latitudeUnitLabel;

    public GliderPositionPanel(SubScene editGliderPositionPanel, SubScene winchPosPane, AddEditGliderPosFrame editFrame) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.editGliderPositionPanel = editGliderPositionPanel;
        this.winchPosPane = winchPosPane;
        this.editFrame = editFrame;
    }

    @FXML
    protected void initialize() {

        TableColumn gliderPosCol = (TableColumn) gliderPositionTable.getColumns().get(0);
        gliderPosCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        gliderPositionTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getGliderPositions()));
        gliderPositionTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null && currentData.getCurrentGliderPosition() != (GliderPosition) newValue) {
                currentData.setCurrentGliderPosition((GliderPosition) newValue);
            }
        });
        gliderPositionTable.getSelectionModel().selectFirst();

        loadData();
        setupUnits();
    }

    public void loadData() {
        if (currentData.getCurrentGliderPosition() != null) {
            positionNameLabel.setText("" + currentData.getCurrentGliderPosition().getName());
            elevationLabel.setText("" + currentData.getCurrentGliderPosition().getElevation());
            longitudeLabel.setText("" + currentData.getCurrentGliderPosition().getLongitude());
            latitudeLabel.setText("" + currentData.getCurrentGliderPosition().getLatitude());
        } else {
            positionNameLabel.setText("");
            elevationLabel.setText("");
            longitudeLabel.setText("");
            latitudeLabel.setText("");
        }
    }

    public void setupUnits() {
        gliderPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("gliderPosAltitude");
        String gliderPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(gliderPosAltitudeUnitsID);
        elevationUnitLabel.setText(gliderPosAltitudeUnitsString);
    }

    @Override
    public void update() {
        loadData();
        setupUnits();
        GliderPosition selected = (GliderPosition) gliderPositionTable.getSelectionModel().getSelectedItem();
        GliderPosition currGliderPosition = currentData.getCurrentGliderPosition();
        if (currGliderPosition == null && selected != null) {
            gliderPositionTable.getItems().remove(selected);
        } else {
            if (!gliderPositionTable.getItems().contains(currGliderPosition)) {
                gliderPositionTable.getItems().add(currGliderPosition);
            }
            gliderPositionTable.getSelectionModel().select(currGliderPosition);
        }
        gliderPositionTable.refresh();
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
        editFrame.edit(null);
        editGliderPositionPanel.toFront();
    }

    @FXML
    public void EditGliderPositionButton_Click(ActionEvent e) {
        editFrame.edit(currentData.getCurrentGliderPosition());
        editGliderPositionPanel.toFront();
    }
}
