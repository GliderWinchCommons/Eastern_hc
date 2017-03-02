/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configuration;

import DataObjects.Operator;
import DatabaseUtilities.DatabaseEntryInsert;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewOperatorPanel {

    private OperatorLoginPanel login;
    private static SubScene newOperatorPanel;

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

    public NewOperatorPanel(OperatorLoginPanel loginScene, SubScene operatorPanel) {
        NewOperatorPanel.isAdmin = false;
        this.login = loginScene;
        NewOperatorPanel.newOperatorPanel = operatorPanel;
    }

    public static void addOperator(boolean admin) {
        isAdmin = admin;
        newOperatorPanel.toFront();
    }

    @FXML
    public void CreateButton_Click(ActionEvent e) {
        if (validate()) {
            Random randomId = new Random();
            Operator o = new Operator(randomId.nextInt(100000000),
                    firstNameTextField.getText(),
                    middleNameTextField.getText(),
                    lastNameTextField.getText(),
                    isAdmin, infoBox.getText(),
                    "{}");
            DatabaseEntryInsert.addOperatorToDB(o, password.getText());
            login.toFront(o);
        }
    }

    @FXML
    public void CancelButton_Click(ActionEvent e) {
        clear();
        login.toFront(null);
    }

    private boolean validate() {
        boolean r = true;
        if (!password.getText().equals(retypedPassword.getText()) || password.getText().isEmpty()) {
            r = false;
            passwordErrorLabel.visibleProperty().set(true);
            retypedPassword.setStyle("-fx-border-color:red;");
        } else {
            passwordErrorLabel.visibleProperty().set(true);
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
