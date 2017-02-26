/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEditPanels;

import javafx.fxml.FXML;
import javafx.scene.SubScene;

/**
 *
 * @author micha
 */
public abstract class AddEditPanel {
    
    SubScene displayPanel;
    protected static final String redBackground = "-fx-control-inner-background: pink;";
    protected static final String whiteBackground = "";
    
    public AddEditPanel(SubScene displayPanel)
    {
        this.displayPanel = displayPanel;
    }
    
    protected abstract void clearData();
    protected abstract boolean submitData();
    protected abstract void deleteCommand();
    
    @FXML public void CancelButton_Click(javafx.event.ActionEvent e) { 
        clearData();
        displayPanel.toFront(); 
    }
    
    @FXML public void SubmitButton_Click(javafx.event.ActionEvent e) {
        if(submitData()) 
        {
            clearData();
            displayPanel.toFront();
        }
    }
    
    @FXML public void ClearButton_Click(javafx.event.ActionEvent e) { 
        clearData(); 
    }
    
    @FXML public void DeleteButton_Click(javafx.event.ActionEvent e) { 
        deleteCommand();
        clearData();
        displayPanel.toFront();
    }    
}
