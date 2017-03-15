/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Winch;

import DataObjects.Winch;
import java.text.DecimalFormat;
import java.text.ParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author micha
 */
public class WinchPanel {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ownerTextField;
    @FXML
    private TextField meteorologicalCheckTimeTextField;
    @FXML
    private TextField meteorologicalVerifyTimeTextField;
    @FXML
    private Label wcVersionLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button editButton;

    @FXML
    private TextField wp1TextField;
    @FXML
    private TextField wp2TextField;
    @FXML
    private TextField wp3TextField;
    @FXML
    private TextField wp4TextField;
    @FXML
    private TextField wp5TextField;
    @FXML
    private TextField wp6TextField;
    @FXML
    private TextField wp7TextField;
    @FXML
    private TextField wp8TextField;
    @FXML
    private TextField wp9TextField;
    @FXML
    private TextField wp10TextField;
    @FXML
    private TextField wp11TextField;
    @FXML
    private TextField wp12TextField;
    @FXML
    private TextField wp13TextField;
    @FXML
    private TextField wp14TextField;
    @FXML
    private TextField wp15TextField;
    @FXML
    private TextField wp16TextField;

    @FXML
    public void initialize() {
        wp1TextField.setText("0");
        wp2TextField.setText("0");
        wp3TextField.setText("0");
        wp4TextField.setText("0");
        wp5TextField.setText("0");
        wp6TextField.setText("0");
        wp7TextField.setText("0");
        wp8TextField.setText("0");
        wp9TextField.setText("0");
        wp10TextField.setText("0");
        wp11TextField.setText("0");
        wp12TextField.setText("0");
        wp13TextField.setText("0");
        wp14TextField.setText("0");
        wp15TextField.setText("0");
        wp16TextField.setText("0");
    }

    @FXML
    public void EditButton_Click(ActionEvent e) {
        saveButton.toFront();
        cancelButton.toFront();
        setTextFieldsEnabled(true);
    }

    @FXML
    public void SaveButton_Click(ActionEvent e) {
        Winch w = getWinch();
        /*
        if (w != null) {
            editButton.toFront();
            setTextFieldsEnabled(false);
            if (CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch() == null) {
                DatabaseEntryInsert.addWinchToDB(w);
            } else {
                DatabaseEntryEdit.UpdateEntry(w);
            }
            CurrentDataObjectSet.getCurrentDataObjectSet().setCurrentWinch(w);
        }*/
    }

    @FXML
    public void CancelButton_Click(ActionEvent e) {
        editButton.toFront();
        setTextFieldsEnabled(false);
    }

    private void setTextFieldsEnabled(boolean value) {
        nameTextField.editableProperty().set(value);
        ownerTextField.editableProperty().set(value);

        wp1TextField.editableProperty().set(value);
        wp2TextField.editableProperty().set(value);
        wp3TextField.editableProperty().set(value);
        wp4TextField.editableProperty().set(value);
        wp5TextField.editableProperty().set(value);
        wp6TextField.editableProperty().set(value);
        wp7TextField.editableProperty().set(value);
        wp8TextField.editableProperty().set(value);
        wp9TextField.editableProperty().set(value);
        wp10TextField.editableProperty().set(value);
        wp11TextField.editableProperty().set(value);
        wp12TextField.editableProperty().set(value);
        wp13TextField.editableProperty().set(value);
        wp14TextField.editableProperty().set(value);
        wp15TextField.editableProperty().set(value);
        wp16TextField.editableProperty().set(value);

        meteorologicalCheckTimeTextField.editableProperty().set(value);
        meteorologicalVerifyTimeTextField.editableProperty().set(value);
    }

    private Winch getWinch() {
        String name = nameTextField.getText();
        String owner = ownerTextField.getText();

        boolean valid = true;

        float wp1 = parseFloat(wp1TextField, valid);
        float wp2 = parseFloat(wp2TextField, valid);
        float wp3 = parseFloat(wp3TextField, valid);
        float wp4 = parseFloat(wp4TextField, valid);
        float wp5 = parseFloat(wp5TextField, valid);
        float wp6 = parseFloat(wp6TextField, valid);
        float wp7 = parseFloat(wp7TextField, valid);
        float wp8 = parseFloat(wp8TextField, valid);
        float wp9 = parseFloat(wp9TextField, valid);
        float wp10 = parseFloat(wp10TextField, valid);
        float wp11 = parseFloat(wp11TextField, valid);
        float wp12 = parseFloat(wp12TextField, valid);
        float wp13 = parseFloat(wp13TextField, valid);
        float wp14 = parseFloat(wp14TextField, valid);
        float wp15 = parseFloat(wp15TextField, valid);
        float wp16 = parseFloat(wp16TextField, valid);

        int meteorological_check_time = parseInteger(meteorologicalCheckTimeTextField, valid);
        int meteorological_verify_time = parseInteger(meteorologicalVerifyTimeTextField, valid);

        if (valid) {
            return new Winch(0, name, owner, "", wp1, wp2, wp3, wp4, wp5, wp6, wp7, wp8, wp9, wp10, wp11, wp12, wp13, wp14, wp15, wp16, meteorological_check_time, meteorological_verify_time, 0f, "");
        }
        return null;
    }

    private float parseFloat(TextField field, boolean returnVal) {
        try {
            field.setStyle("");
            return DecimalFormat.getInstance().parse(field.getText()).floatValue();
        } catch (ParseException ex) {
            field.setStyle("-fx-border-color: red;");
            returnVal = false;
            return -1;
        }
    }

    private int parseInteger(TextField field, boolean returnVal) {
        try {
            field.setStyle("");
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException ex) {
            field.setStyle("-fx-border-color: red;");
            returnVal = false;
            return -1;
        }
    }
}
