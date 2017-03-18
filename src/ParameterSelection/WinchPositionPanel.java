package ParameterSelection;

import AddEditPanels.AddEditWinchPosFrame;
import Communications.Observer;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.WinchPosition;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javax.swing.*;

/**
 * Created by micha on 10/18/2016.
 */
public class WinchPositionPanel extends JPanel implements Observer {

    private CurrentDataObjectSet currentData;
    private int winchPosAltitudeUnitsID;
    private AddEditWinchPosFrame editFrame;

    SubScene editWinchPositionPanel;
    GridPane scenarioHomePane;

    @FXML
    TableView winchPositionTable;

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

    public WinchPositionPanel(SubScene editWinchPositionPanel, GridPane scenarioHomePane, AddEditWinchPosFrame editFrame) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.editWinchPositionPanel = editWinchPositionPanel;
        this.scenarioHomePane = scenarioHomePane;
        this.editFrame = editFrame;
    }

    @FXML
    protected void initialize() {
        TableColumn winchPosCol = (TableColumn) winchPositionTable.getColumns().get(0);
        winchPosCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        winchPositionTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getWinchPositions()));
        winchPositionTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null && currentData.getCurrentWinchPosition() != (WinchPosition) newValue) {
                currentData.setCurrentWinchPosition((WinchPosition) newValue);
            }
        });
        winchPositionTable.getSelectionModel().selectFirst();
        loadData();
        setupUnits();
    }

    public void loadData() {
        if (currentData.getCurrentWinchPosition() != null) {
            positionNameLabel.setText("" + currentData.getCurrentWinchPosition().getName());
            elevationLabel.setText("" + currentData.getCurrentWinchPosition().getElevation());
            longitudeLabel.setText("" + currentData.getCurrentWinchPosition().getLongitude());
            latitudeLabel.setText("" + currentData.getCurrentWinchPosition().getLatitude());
        } else {
            positionNameLabel.setText("");
            elevationLabel.setText("");
            longitudeLabel.setText("");
            latitudeLabel.setText("");
        }
    }

    public void setupUnits() {
        winchPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("winchPosAltitude");
        String winchPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(winchPosAltitudeUnitsID);
        elevationUnitLabel.setText(winchPosAltitudeUnitsString);
    }

    @Override
    public void update() {
        loadData();
        setupUnits();
        WinchPosition selected = (WinchPosition) winchPositionTable.getSelectionModel().getSelectedItem();
        WinchPosition currWinchPosition = currentData.getCurrentWinchPosition();
        if (currWinchPosition == null && selected != null) {
            winchPositionTable.getItems().remove(selected);
        } else {
            if (!winchPositionTable.getItems().contains(currWinchPosition)) {
                winchPositionTable.getItems().add(currWinchPosition);
            }
            winchPositionTable.getSelectionModel().select(currWinchPosition);
        }
        winchPositionTable.refresh();
    }

    @Override
    public void update(String s) {

    }

    private Observer getObserver() {
        return this;
    }

    public void clear() {

    }

    @FXML
    public void AirfieldFinishButton_Click(javafx.event.ActionEvent e) {
        scenarioHomePane.toFront();
    }

    @FXML
    public void NewWinchPositionButton_Click(ActionEvent e) {
        editFrame.edit(null);
        editWinchPositionPanel.toFront();
    }

    @FXML
    public void EditWinchPositionButton_Click(ActionEvent e) {
        editFrame.edit(currentData.getCurrentWinchPosition());
        editWinchPositionPanel.toFront();
    }
}
