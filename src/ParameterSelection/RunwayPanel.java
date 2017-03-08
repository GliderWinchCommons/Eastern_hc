package ParameterSelection;

import AddEditPanels.AddEditRunwayFrame;
import Communications.Observer;
import Configuration.UnitLabelUtilities;
import DataObjects.*;
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
public class RunwayPanel extends JPanel implements Observer {

    private int runwayMagneticHeadingUnitsID;
    private CurrentDataObjectSet currentData;
    private AddEditRunwayFrame editFrame;

    SubScene editRunwayPanel;
    SubScene runwayPane;

    @FXML
    TableView runwayTable;

    @FXML
    Label runwayNameLabel;
    @FXML
    Label magneticHeadingLabel;
    @FXML
    Label magneticHeadingUnitLabel;

    public RunwayPanel(SubScene editRunwayPanel, SubScene runwaySubScene, AddEditRunwayFrame editFrame) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.editRunwayPanel = editRunwayPanel;
        this.runwayPane = runwaySubScene;
        this.editFrame = editFrame;
    }

    @FXML
    protected void initialize() {

        TableColumn runwayCol = (TableColumn) runwayTable.getColumns().get(0);
        runwayCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        runwayTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getRunways()));
        runwayTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentRunway((Runway) newValue);
                loadData();
            }
        });
        runwayTable.getSelectionModel().selectFirst();
        loadData();
    }

    public void loadData() {

        if (currentData.getCurrentRunway() != null) {
            runwayNameLabel.setText(currentData.getCurrentRunway().getName());
            magneticHeadingLabel.setText(Float.toString(currentData.getCurrentRunway().getMagneticHeading()));
        } else {
            runwayNameLabel.setText("");
            magneticHeadingLabel.setText("");
        }
        setupUnits();
    }

    public void setupUnits() {
        runwayMagneticHeadingUnitsID = currentData.getCurrentProfile().getUnitSetting("runwayMagneticHeading");
        String runwayMagneticHeadingUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runwayMagneticHeadingUnitsID);
        magneticHeadingUnitLabel.setText(runwayMagneticHeadingUnitsString);
    }

    @Override
    public void update() {
        Runway selected = (Runway) runwayTable.getSelectionModel().getSelectedItem();
        Runway currRunway = currentData.getCurrentRunway();
        if (currRunway == null && selected != null) {
            runwayTable.getItems().remove(selected);
        } else if (currRunway != selected) {
            if (!runwayTable.getItems().contains(currRunway)) {
                runwayTable.getItems().add(currRunway);
            } else {
                runwayTable.getSelectionModel().select(currRunway);
            }
        }
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
    public void ChooseGliderPosButton_Click(ActionEvent e) {
        runwayPane.toFront();
    }

    @FXML
    public void NewRunwayButton_Click(ActionEvent e) {
        editFrame.edit(null);
        editRunwayPanel.toFront();
    }

    @FXML
    public void EditRunwayButton_Click(ActionEvent e) {
        editFrame.edit(currentData.getCurrentRunway());
        editRunwayPanel.toFront();
    }

}
