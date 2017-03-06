package ParameterSelection;

import AddEditPanels.AddEditPilotPanel;
import Communications.Observer;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Pilot;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javax.swing.DefaultListModel;

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
    Label flightWeightLabel;
    @FXML
    Label capabiltiyLabel;

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

        pilotTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getPilots()));
        pilotTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentPilot((Pilot) newValue);
                loadData();
            }
        });
        pilotTable.getSelectionModel().selectFirst();
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
            flightWeightLabel.setText("" + currentData.getCurrentPilot().getWeight());
            //TODO
            //capabiltiyLabel.setText("" + currentData.getCurrentPilot().getCapability());
            preferenceSlider.adjustValue(currentData.getCurrentPilot().getPreference());
            setupUnits();
        }
    }

    public void setupUnits() {
        flightWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
        flightWeightUnitLabel.setText(UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID));
        System.out.println();
    }

    @Override
    public void update() {
        setupUnits();
        DefaultListModel pilotModel = new DefaultListModel();
        pilotModel.clear();
        pilotTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getPilots()));
        if (currentData.getCurrentPilot() != null) {
            pilotTable.getSelectionModel().select(currentData.getCurrentPilot());
        }
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
        scenarioHomePane.toFront();
    }

    @FXML
    public void NewPilotButton_Click(ActionEvent e) {
        editFrame.edit(null);
        editPilotPanel.toFront();
    }

    @FXML
    public void EditPilotButton_Click(ActionEvent e) {
        editFrame.edit(currentData.getCurrentPilot());
        editPilotPanel.toFront();
    }
}
