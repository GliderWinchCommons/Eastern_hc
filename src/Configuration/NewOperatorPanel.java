/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configuration;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import DatabaseUtilities.DatabaseEntryEdit;
import static DatabaseUtilities.DatabaseEntryIdCheck.matchPassword;
import DatabaseUtilities.DatabaseEntryInsert;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewOperatorPanel {

    private static SubScene newOperatorPanel;

    @FXML
    private Label adminLabel;
    @FXML
    private Label operatorLabel;
    
    private static boolean isAdmin;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextArea infoBox;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField retypedPassword;

    private Observer parent;
    private Operator theOperator;
    private CurrentDataObjectSet currentData;

    public NewOperatorPanel(SubScene operatorPanel) {
        NewOperatorPanel.isAdmin = false;
        NewOperatorPanel.newOperatorPanel = operatorPanel;
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
    }

    public void addOperator(boolean admin, Operator operator, String pass) {
        if(admin == true) {
            adminLabel.setVisible(true);
            operatorLabel.setVisible(false);
        }
        else {
            adminLabel.setVisible(false);
            operatorLabel.setVisible(true);
        }
        
        isAdmin = admin;
        theOperator = operator;
        loadData(pass);
        newOperatorPanel.toFront();
    }

    public void editOperator(boolean admin) {
        if(admin == true) {
            adminLabel.setVisible(true);
            operatorLabel.setVisible(false);
        }
        else {
            adminLabel.setVisible(false);
            operatorLabel.setVisible(true);
        }
        
        
    }
    
    public void attach(Observer o) {
        this.parent = o;
    }

    private void loadData(String pass) {
        if (theOperator != null) {
            firstNameTextField.setText(theOperator.getFirst());
            middleNameTextField.setText(theOperator.getMiddle());
            lastNameTextField.setText(theOperator.getLast());
            password.setText(pass);
            retypedPassword.setText(pass);
            infoBox.setText(theOperator.getInfo());
        } else {
            firstNameTextField.setText("");
            middleNameTextField.setText("");
            lastNameTextField.setText("");
            password.setText("");
            retypedPassword.setText("");
            infoBox.setText("");
        }
        password.setStyle("");
        retypedPassword.setStyle("");
        firstNameTextField.setStyle("");
        middleNameTextField.setStyle("");
        lastNameTextField.setStyle("");
        infoBox.setStyle("");
        passwordErrorLabel.visibleProperty().set(false);
    }

    @FXML
    public void CreateButton_Click(ActionEvent e) {
        if (validate()) {
            if (theOperator == null) {
                Random randomId = new Random();
                theOperator = new Operator(randomId.nextInt(100000000),
                        firstNameTextField.getText(),
                        middleNameTextField.getText(),
                        lastNameTextField.getText(),
                        isAdmin, infoBox.getText(),
                        "{}");
                DatabaseEntryInsert.addOperatorToDB(theOperator, password.getText());
            } else {
                try {
                    theOperator = new Operator(theOperator.getID(), firstNameTextField.getText(), middleNameTextField.getText(), lastNameTextField.getText(), theOperator.getAdmin(), infoBox.getText(), theOperator.getUnitSettingsForStorage());
                    if (!matchPassword(theOperator, password.getText())) {
                        DatabaseEntryEdit.ChangePassword(theOperator, password.getText());
                    }
                    DatabaseEntryEdit.UpdateEntry(theOperator);
                } catch (SQLException ex) {
                    Logger.getLogger(NewOperatorPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(NewOperatorPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //currentData.setCurrentProfile(theOperator);
            parent.update();
        }
    }

    @FXML
    public void CancelButton_Click(ActionEvent e) {
        clear();
        parent.update();
    }

    private boolean validate() {
        boolean r = true;
        if (!password.getText().equals(retypedPassword.getText()) || password.getText().isEmpty()) {
            r = false;
            passwordErrorLabel.visibleProperty().set(true);
            retypedPassword.setStyle("-fx-border-color:red;");
        } else {
            passwordErrorLabel.visibleProperty().set(false);
            retypedPassword.setStyle("");
        }

        if (firstNameTextField.getText().isEmpty()) {
            firstNameTextField.setStyle("-fx-border-color:red;");
            r = false;
        } else {
            firstNameTextField.setStyle("-fx-border-color:red;");
        }

        if (lastNameTextField.getText().isEmpty()) {
            lastNameTextField.setStyle("-fx-border-color:red;");
            r = false;
        } else {
            lastNameTextField.setStyle("-fx-border-color:red;");
        }
        return r;
    }

    private void clear() {
        firstNameTextField.clear();
        middleNameTextField.clear();
        lastNameTextField.clear();
        infoBox.clear();
        password.clear();
        retypedPassword.clear();
        passwordErrorLabel.visibleProperty().set(false);
    }
}
