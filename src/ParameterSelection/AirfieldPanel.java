package ParameterSelection;

import AddEditPanels.AddEditAirfieldFrame;
import Communications.Observer;
import Configuration.UnitLabelUtilities;
import DataObjects.Airfield;
import DataObjects.CurrentDataObjectSet;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.JPanel;

public class AirfieldPanel extends JPanel implements Observer {

    private CurrentDataObjectSet currentData;
    private int airfieldAltitudeUnitsID;
    private int magneticVariationUnitsID;
    private int longitudeUnitsID;
    private int latitudeUnitsID;
    private int UTCUnitsID;

    SubScene editAirfieldPanel;
    SubScene runwayPanel;
    AddEditAirfieldFrame editFrame;

    @FXML
    TableView airfieldTable;

    @FXML
    Label designatorLabel;
    @FXML
    Label altitudeLabel;
    @FXML
    Label magneticVariationLabel;
    @FXML
    Label longitudeLabel;
    @FXML
    Label latitudeLabel;
    @FXML
    Label UTCOffsetLabel;

    @FXML
    Label altitudeUnitLabel;
    @FXML
    Label magneticVariationUnitLabel;
    @FXML
    Label longitudeUnitLabel;
    @FXML
    Label latitudeUnitLabel;
    @FXML
    Label UTCOffsetUnitLabel;

    public AirfieldPanel(SubScene editAirfieldPanel, SubScene runwayPanel, AddEditAirfieldFrame editFrame) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.editAirfieldPanel = editAirfieldPanel;
        this.runwayPanel = runwayPanel;
        this.editFrame = editFrame;
    }

    @FXML
    protected void initialize() {
        TableColumn airfieldCol = (TableColumn) airfieldTable.getColumns().get(0);
        airfieldCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn designatorCol = (TableColumn) airfieldTable.getColumns().get(1);
        designatorCol.setCellValueFactory(new PropertyValueFactory<>("designator"));

        airfieldTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getAirfields()));
        airfieldTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentAirfield((Airfield) newValue);
                loadData();
            }
        });
        airfieldTable.getSelectionModel().selectFirst();
        loadData();
    }

    public void loadData() {
        if (currentData.getCurrentAirfield() != null) {
            designatorLabel.setText("" + currentData.getCurrentAirfield().getDesignator());
            altitudeLabel.setText("" + currentData.getCurrentAirfield().getElevation());
            magneticVariationLabel.setText("" + currentData.getCurrentAirfield().getMagneticVariation());
            longitudeLabel.setText("" + currentData.getCurrentAirfield().getLongitude());
            latitudeLabel.setText("" + currentData.getCurrentAirfield().getLatitude());
            UTCOffsetLabel.setText("" + currentData.getCurrentAirfield().getUTC());
            setupUnits();
        }
    }

    public void setupUnits() {
        airfieldAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldAltitude");
        altitudeUnitLabel.setText(UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID));

        magneticVariationUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldMagneticVariation");
        magneticVariationUnitLabel.setText(UnitLabelUtilities.lenghtUnitIndexToString(magneticVariationUnitsID));

        longitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldLongitude");
        longitudeUnitLabel.setText(UnitLabelUtilities.lenghtUnitIndexToString(longitudeUnitsID));

        latitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldLatitude");
        latitudeUnitLabel.setText(UnitLabelUtilities.lenghtUnitIndexToString(latitudeUnitsID));

        UTCUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldUTC");
        UTCOffsetUnitLabel.setText(UnitLabelUtilities.lenghtUnitIndexToString(UTCUnitsID));
    }

    @Override
    public void update(String s) {
        setupUnits();
        if (s.equals("1")) {
            airfieldTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getAirfields()));
        }
    }

    private Observer getObserver() {
        return this;
    }

    public void clear() {

    }

    @FXML
    public void ChooseRunwayButton_Click(ActionEvent e) {
        runwayPanel.toFront();
    }

    @FXML
    public void NewAirfieldButton_Click(ActionEvent e) {
        editFrame.edit(null);
        editAirfieldPanel.toFront();
    }

    @FXML
    public void EditAirfieldButton_Click(ActionEvent e) {
        Airfield selected = (Airfield) airfieldTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            editFrame.edit(selected);
            editAirfieldPanel.toFront();
        }
    }

    @Override
    public void update() {

    }
}
