package ParameterSelection;

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
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

/**
 * Created by micha on 10/18/2016.
 */
public class RunwayPanel extends JPanel implements Observer {

    private int runwayMagneticHeadingUnitsID;
    private CurrentDataObjectSet currentData;

    SubScene editRunwayPanel;
    SubScene gliderPosPane;

    @FXML
    TableView runwayTable;

    @FXML
    Label magneticHeadingLabel;
    @FXML
    Label magneticHeadingUnitLabel;

    public RunwayPanel(SubScene editRunwayPanel, SubScene gliderPosPane) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.editRunwayPanel = editRunwayPanel;
        this.gliderPosPane = gliderPosPane;
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
            magneticHeadingLabel.setText("" + currentData.getCurrentRunway().getMagneticHeading());
            setupUnits();
        }
    }

    public void setupUnits() {
        runwayMagneticHeadingUnitsID = currentData.getCurrentProfile().getUnitSetting("runwayMagneticHeading");
        String runwayMagneticHeadingUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runwayMagneticHeadingUnitsID);
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
    public void ChooseGliderPosButton_Click(ActionEvent e) {
        gliderPosPane.toFront();
    }

    @FXML
    public void NewRunwayButton_Click(ActionEvent e) {
        editRunwayPanel.toFront();
    }

}
