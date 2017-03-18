package ParameterSelection;

import AddEditPanels.AddEditDrumPanel;
import Communications.Observer;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Drum;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DrumPanel implements Observer {

    private CurrentDataObjectSet currentData;
    private int cableLengthUnitsID;
    private int coreDiameterUnitsID;

    SubScene parachuteScene;
    AddEditDrumPanel editFrame;
    SubScene editDrumPanel;

    @FXML
    TableView drumTable;

    @FXML
    Label drumNumberLabel;
    @FXML
    Label coreDiameterLabel;
    @FXML
    Label kFactorLabel;
    @FXML
    Label cableLengthLabel;
    @FXML
    Label cableDensityLabel;
    @FXML
    Label systemEquivalentMassLabel;
    @FXML
    Label numLaunchesLabel;
    @FXML
    Label maxTensionLabel;

    @FXML
    Label coreDiameterUnitLabel;
    @FXML
    Label cableLengthUnitLabel;
    @FXML
    Label cableDensityUnitLabel;
    @FXML
    Label systemEquivalentMassUnitLabel;
    @FXML
    Label maxTensionUnitLabel;

    public DrumPanel(SubScene editDrumPanel, SubScene parachuteScene, AddEditDrumPanel editFrame) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        currentData.attach(this);
        this.parachuteScene = parachuteScene;
        this.editDrumPanel = editDrumPanel;
        this.editFrame = editFrame;
    }

    @FXML
    protected void initialize() {

        TableColumn drumCol = (TableColumn) drumTable.getColumns().get(0);
        drumCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        drumTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getDrum()));
        drumTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentDrum((Drum) newValue);
            }
        });
        drumTable.getSelectionModel().selectFirst();
        loadData();
        setupUnits();
    }

    public void loadData() {
        if (currentData.getCurrentDrum() != null) {
            drumNumberLabel.setText("" + currentData.getCurrentDrum().getDrumNumber());
            coreDiameterLabel.setText("" + currentData.getCurrentDrum().getCoreDiameter());
            kFactorLabel.setText("" + currentData.getCurrentDrum().getKFactor());
            cableLengthLabel.setText("" + currentData.getCurrentDrum().getCableLength());
            cableDensityLabel.setText("" + currentData.getCurrentDrum().getCableDensity());
            systemEquivalentMassLabel.setText("" + currentData.getCurrentDrum().getSystemEquivalentMass());
            numLaunchesLabel.setText("" + currentData.getCurrentDrum().getNumLaunches());
            maxTensionLabel.setText("" + currentData.getCurrentDrum().getMaxTension());
        } else {
            drumNumberLabel.setText("");
            coreDiameterLabel.setText("");
            kFactorLabel.setText("");
            cableLengthLabel.setText("");
            cableDensityLabel.setText("");
            systemEquivalentMassLabel.setText("");
            numLaunchesLabel.setText("");
            maxTensionLabel.setText("");
        }
    }

    public void setupUnits() {        
        cableLengthUnitsID = currentData.getCurrentProfile().getUnitSetting("cableLength");
        String cableLengthUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(cableLengthUnitsID);
        cableLengthUnitLabel.setText(cableLengthUnitsString);

        coreDiameterUnitsID = currentData.getCurrentProfile().getUnitSetting("coreDiameter");
        String coreDiameterUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(coreDiameterUnitsID);
        coreDiameterUnitLabel.setText(coreDiameterUnitsString);        
    }

    @Override
    public void update() {
        if(drumTable != null) {
            loadData();
            setupUnits();
            Drum selected = (Drum) drumTable.getSelectionModel().getSelectedItem();
            Drum currDrum = currentData.getCurrentDrum();
            if (currDrum == null && selected != null) {
                drumTable.getItems().remove(selected);
            } else {
                if (!drumTable.getItems().contains(currDrum)) {
                    drumTable.getItems().add(currDrum);
                }
                drumTable.getSelectionModel().select(currDrum);
            }
            drumTable.refresh();
        }
    }

    private Observer getObserver() {
        return this;
    }

    public void clear() {

    }

    @FXML
    public void ParachuteButton_Click(javafx.event.ActionEvent e) {
        parachuteScene.toFront();
    }

    @FXML
    public void NewDrumButton_Click(ActionEvent e) {
        editFrame.edit(null);
        editDrumPanel.toFront();
    }

    @FXML
    public void EditDrumButton_Click(ActionEvent e) {
        Drum selected = (Drum) drumTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            editFrame.edit(selected);
            editDrumPanel.toFront();
        }
    }

    @Override
    public void update(String msg) {

    }
}
