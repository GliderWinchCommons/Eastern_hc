/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Winch;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Winch;
import DatabaseUtilities.DatabaseEntrySelect;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class WinchPanel implements Observer {

    @FXML
    private TableView winchTable;
    @FXML
    private Label winchNameLabel;
    @FXML
    private Button newWinchButton;
    @FXML
    private Button editWinchButton;

    private CurrentDataObjectSet currentData;
    SubScene editWinchPanel;
    AddEditWinchPanel editFrame;

    public WinchPanel(SubScene editWinchPanel, AddEditWinchPanel editFrame) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.editWinchPanel = editWinchPanel;
        this.editFrame = editFrame;
    }

    @FXML
    public void initialize() {
        TableColumn nameCol = (TableColumn) winchTable.getColumns().get(0);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        winchTable.setItems(FXCollections.observableList(DatabaseEntrySelect.getWinch()));
        winchTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentWinch((Winch) newValue);
                loadData();
            }
        });
        winchTable.getSelectionModel().selectFirst();
        loadData();
    }

    private void loadData() {
        if (currentData.getCurrentWinch() != null) {
            winchNameLabel.setText(currentData.getCurrentWinch().getName());
        } else {
            winchNameLabel.setText("");
        }

        if (currentData.getCurrentProfile().getAdmin()) {
            newWinchButton.setDisable(false);
            editWinchButton.setDisable(false);
        } else {
            newWinchButton.setDisable(true);
            editWinchButton.setDisable(true);
        }
    }

    @FXML
    public void NewWinchButton_Click(ActionEvent e) {
        editFrame.edit(null);
        editWinchPanel.toFront();
    }

    @FXML
    public void EditWinchButton_Click(ActionEvent e) {
        editFrame.edit(currentData.getCurrentWinch());
        editWinchPanel.toFront();
    }

    @Override
    public void update() {
        loadData();
        Winch selected = (Winch) winchTable.getSelectionModel().getSelectedItem();
        Winch currWinch = currentData.getCurrentWinch();
        if (currWinch == null && selected != null) {
            winchTable.getItems().remove(selected);
        } else {
            if (!winchTable.getItems().contains(currWinch)) {
                winchTable.getItems().add(currWinch);
            }
            winchTable.getSelectionModel().select(currWinch);
        }
        winchTable.refresh();
    }

    @Override
    public void update(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
