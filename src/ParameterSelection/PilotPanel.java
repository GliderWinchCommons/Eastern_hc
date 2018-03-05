package ParameterSelection;

import AddEditPanels.AddEditPilotPanel;
import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Pilot;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

public class PilotPanel implements Observer {

    private Boolean shown;
    private CurrentDataObjectSet currentData;
    private AddEditPilotPanel editFrame;

    GridPane scenarioHomePane;
    SubScene editPilotPanel;

    @FXML
    TableView pilotTable;
    @FXML
    Slider preferenceSlider;

    @FXML
    Label pilotNameLabel;
    @FXML
    Label flightWeightLabel;
    @FXML
    Label capabilityLabel;

    @FXML
    Label flightWeightUnitLabel;

    private int flightWeightUnitsID;

    public PilotPanel(SubScene editPilotPanel, GridPane scenarioHomePane, AddEditPilotPanel editFrame) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.editPilotPanel = editPilotPanel;
        this.scenarioHomePane = scenarioHomePane;
        this.editFrame = editFrame;
    }

    @FXML
    protected void initialize() {
        TableColumn firstCol = (TableColumn) pilotTable.getColumns().get(0);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn lastCol = (TableColumn) pilotTable.getColumns().get(1);
        lastCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        ObservableList list = FXCollections.observableList(DatabaseEntrySelect.getPilots());
        pilotTable.setItems(list);
        
        pilotTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentPilot((Pilot) newValue);
                loadData();
            }
        });
        
        //pilotTable.getSelectionModel().selectFirst();
        setupUnits();
        loadData();

        preferenceSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double d) {
                if (d == 0) {
                    return "Mild";
                }
                if (d == .5) {
                    return "Nominal";
                }
                if (d == 1) {
                    return "Aggressive";
                }
                return d.toString();
            }

            @Override
            public Double fromString(String string) {
                if (string.equalsIgnoreCase("Student")) {
                    return 0.0;
                }
                if (string.equalsIgnoreCase("Proficient")) {
                    return 0.5;
                }
                if (string.equalsIgnoreCase("Advanced")) {
                    return 1.0;
                }
                return Double.parseDouble(string);
            }
        });
    }

    public void loadData() {
        if (currentData.getCurrentPilot() != null) {
            pilotNameLabel.setText(currentData.getCurrentPilot().getFirstName() + " " + currentData.getCurrentPilot().getMiddleName() + " " + currentData.getCurrentPilot().getLastName());
            flightWeightLabel.setText(currentData.getCurrentPilot().getWeight() * UnitConversionRate.convertWeightUnitIndexToFactor(flightWeightUnitsID) + " " + UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID));
            capabilityLabel.setText("" + currentData.getCurrentPilot().getCapability());
            preferenceSlider.adjustValue(currentData.getCurrentPilot().getPreference());
        } else {
            pilotNameLabel.setText("Pilot Name");
            flightWeightLabel.setText("N/A");
            capabilityLabel.setText("N/A");
            preferenceSlider.adjustValue(0.5);
        }
    }

    public void setupUnits() {
        flightWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
        //flightWeightUnitLabel.setText(UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID));
    }

    @Override
    public void update() {
        setupUnits();
        loadData();
        Pilot selected = (Pilot) pilotTable.getSelectionModel().getSelectedItem();
        Pilot currPilot = currentData.getCurrentPilot();
        if (currPilot == null && selected != null) {
            pilotTable.getItems().remove(selected);
        } else {
            if(currPilot != null) {
                if (!pilotTable.getItems().contains(currPilot)) {
                    pilotTable.getItems().add(currPilot);
                }
                pilotTable.getSelectionModel().select(currPilot);
            }
        }
        pilotTable.refresh();
    }

    private Observer getObserver() {
        return this;
    }

    public void clear() {

    }

    @Override
    public void update(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    public void PilotFinishButton_Click(ActionEvent e) {
        currentData.setCurrScenarioFlightPref((float) preferenceSlider.getValue());
        scenarioHomePane.toFront();
    }

    @FXML
    public void NewPilotButton_Click(ActionEvent e) {
        editFrame.edit(null, false);
        editPilotPanel.toFront();
    }

    @FXML
    public void EditPilotButton_Click(ActionEvent e) {
        editFrame.edit(currentData.getCurrentPilot(), true);
        editPilotPanel.toFront();
    }
}
