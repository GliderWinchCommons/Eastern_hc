package ParameterSelection;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import Configuration.UnitLabelUtilities;
import DataObjects.Drum;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class DrumPanel implements Observer {

    private CurrentDataObjectSet currentData;
    private int cableLengthUnitsID;
    private int coreDiameterUnitsID;

    @FXML
    SubScene parachuteScene;

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

    public DrumPanel(SubScene parachuteScene) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        currentData.attach(this);
        this.parachuteScene = parachuteScene;
    }

    @FXML
    protected void initialize() {

        TableColumn drumCol = (TableColumn) drumTable.getColumns().get(0);
        drumCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        drumTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getDrum()));
        drumTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentDrum((Drum) newValue);
                loadData();
            }
        });
        drumTable.getSelectionModel().selectFirst();
        loadData();
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
            setupUnits();
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
        setupUnits();
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

    @Override
    public void update(String msg) {

    }
}
