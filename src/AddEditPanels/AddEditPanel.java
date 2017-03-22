/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEditPanels;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import javafx.fxml.FXML;
import javafx.scene.SubScene;

/**
 *
 * @author micha
 */
public abstract class AddEditPanel implements Observer {

    SubScene displayPanel;
    protected static final String redBackground = "-fx-control-inner-background: pink;";
    protected static final String whiteBackground = "";
    protected CurrentDataObjectSet currentData;
    protected Observer parent;
    protected boolean isEditEntry;

    public AddEditPanel(SubScene displayPanel) {
        this.displayPanel = displayPanel;
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.parent = null;
    }

    public void attach(Observer o) {
        this.parent = o;
    }

    protected abstract void clearData();

    protected abstract boolean submitData();

    protected abstract void deleteCommand();

    protected abstract void setupUnits();

    @FXML
    public void CancelButton_Click(javafx.event.ActionEvent e) {
        clearData();
        displayPanel.toFront();
    }

    @FXML
    public void SubmitButton_Click(javafx.event.ActionEvent e) {
        if (submitData()) {
            clearData();
            displayPanel.toFront();
        }
    }

    @FXML
    public void ClearButton_Click(javafx.event.ActionEvent e) {
        clearData();
    }

    @FXML
    public void DeleteButton_Click(javafx.event.ActionEvent e) {
        if (isEditEntry) {
            deleteCommand();
            clearData();
            displayPanel.toFront();
        }
    }

    @Override
    public void update() {
        if (currentData.getCurrentProfile() != null && this.parent != null) {
            setupUnits();
        }
    }

    @Override
    public void update(String msg) {
        //Not doing anything with this message
    }
}
