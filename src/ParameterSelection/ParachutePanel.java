package ParameterSelection;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Parachute;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 * Created by micha on 2/2/2017.
 */
public class ParachutePanel implements Observer {

    GridPane scenarioHome;
    CurrentDataObjectSet currentData;

    @FXML
    TableView parachuteTable;

    //Values
    @FXML
    Label liftLabel;
    @FXML
    Label dragLabel;
    @FXML
    Label weightLabel;

    //Unit Labels
    @FXML
    Label liftUnitLabel;
    @FXML
    Label dragUnitLabel;
    @FXML
    Label weightUnitLabel;

    public ParachutePanel(GridPane scenarioHome) {
        this.scenarioHome = scenarioHome;
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
    }

    @FXML
    protected void initialize() {
        TableColumn parachuteCol = (TableColumn) parachuteTable.getColumns().get(0);
        parachuteCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        parachuteTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getParachutes()));
        parachuteTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                if (currentData.getCurrentDrum() != null) {
                    currentData.getCurrentDrum().setParachute((Parachute) newValue);
                }
            }
        });
        parachuteTable.getSelectionModel().selectFirst();

        loadData();
        setupUnits();
    }

    public void loadData() {
        if (currentData.getCurrentDrum() != null) {
            liftLabel.setText("" + currentData.getCurrentDrum().getParachute().getLift());
            dragLabel.setText("" + currentData.getCurrentDrum().getParachute().getDrag());
            weightLabel.setText("" + currentData.getCurrentDrum().getParachute().getWeight());
        } else {
            liftLabel.setText("");
            dragLabel.setText("");
            weightLabel.setText("");
        }
    }

    public void setupUnits() {

    }

    @FXML
    public void FinishButton_Click(javafx.event.ActionEvent e) {
        scenarioHome.toFront();
    }

    @Override
    public void update() {
        loadData();
        setupUnits();
        Parachute selected = (Parachute) parachuteTable.getSelectionModel().getSelectedItem();
        Parachute currParachute = null;//currentData.getCurrentParachute();
        if (currParachute == null && selected != null) {
            parachuteTable.getItems().remove(selected);
        } else {
            if (!parachuteTable.getItems().contains(currParachute)) {
                parachuteTable.getItems().add(currParachute);
            }
            parachuteTable.getSelectionModel().select(currParachute);
        }
        parachuteTable.refresh();
    }

    @Override
    public void update(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
