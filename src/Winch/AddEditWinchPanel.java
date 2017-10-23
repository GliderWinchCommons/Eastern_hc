/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Winch;

import DataObjects.Winch;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddEditWinchPanel extends AddEditPanels.AddEditPanel {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ownerTextField;
    @FXML
    private TextField meteorologicalCheckTimeTextField;
    @FXML
    private TextField meteorologicalVerifyTimeTextField;
    @FXML
    private TextField run_orientation_toleranceTextField;
    @FXML
    private Label wcVersionLabel;

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

    public AddEditWinchPanel(SubScene displayPanel) {
        super(displayPanel);
    }

    public void edit(Winch editWinch) {
        isEditEntry = editWinch != null;
        if (isEditEntry) {
            wp1TextField.setText("" + editWinch.getW1());
            wp2TextField.setText("" + editWinch.getW2());
            wp3TextField.setText("" + editWinch.getW3());
            wp4TextField.setText("" + editWinch.getW4());
            wp5TextField.setText("" + editWinch.getW5());
            wp6TextField.setText("" + editWinch.getW6());
            wp7TextField.setText("" + editWinch.getW7());
            wp8TextField.setText("" + editWinch.getW8());
            wp9TextField.setText("" + editWinch.getW9());
            wp10TextField.setText("" + editWinch.getW10());
            wp11TextField.setText("" + editWinch.getW11());
            wp12TextField.setText("" + editWinch.getW12());
            wp13TextField.setText("" + editWinch.getW13());
            wp14TextField.setText("" + editWinch.getW14());
            wp15TextField.setText("" + editWinch.getW15());
            wp16TextField.setText("" + editWinch.getW16());

            nameTextField.setText(editWinch.getName());
            ownerTextField.setText(editWinch.getOwner());

            meteorologicalCheckTimeTextField.setText("" + editWinch.getMeteorological_check_time());
            meteorologicalVerifyTimeTextField.setText("" + editWinch.getMeteorological_verify_time());
            run_orientation_toleranceTextField.setText("" + editWinch.getRun_orientation_tolerance());
        } else {
            clearData();
        }
    }

    @Override
    protected void clearData() {
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

        nameTextField.setText("");
        ownerTextField.setText("");

        meteorologicalCheckTimeTextField.setText("");
        meteorologicalVerifyTimeTextField.setText("");
        run_orientation_toleranceTextField.setText("");
    }

    @Override
    protected boolean submitData() {
        Winch valid = getWinch(), newWinch;
        if (valid != null) {
            if (isEditEntry) {
                newWinch = currentData.getCurrentWinch();
                newWinch.setName(valid.getName());
                newWinch.setOwner(valid.getOwner());
                newWinch.setW1(valid.getW1());
                newWinch.setW2(valid.getW2());
                newWinch.setW3(valid.getW3());
                newWinch.setW4(valid.getW4());
                newWinch.setW5(valid.getW5());
                newWinch.setW6(valid.getW6());
                newWinch.setW7(valid.getW7());
                newWinch.setW8(valid.getW8());
                newWinch.setW9(valid.getW9());
                newWinch.setW10(valid.getW10());
                newWinch.setW11(valid.getW11());
                newWinch.setW12(valid.getW12());
                newWinch.setW13(valid.getW13());
                newWinch.setW14(valid.getW14());
                newWinch.setW15(valid.getW15());
                newWinch.setW16(valid.getW16());
                newWinch.setMeteorological_check_time(valid.getMeteorological_check_time());
                newWinch.setMeteorological_verify_time(valid.getMeteorological_verify_time());
                newWinch.setRun_orientation_tolerance(valid.getRun_orientation_tolerance());
            } else {
                newWinch = valid;
            }

            try {
                if (!isEditEntry) {
                    Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION,
                            "Would you like to save this to the database?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (choice.get() == ButtonType.YES) {
                        Random randomId = new Random();
                        newWinch.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newWinch)) {
                            newWinch.setId(randomId.nextInt(100000000));
                        }
                        if (!DatabaseUtilities.DatabaseEntryInsert.addWinchToDB(newWinch)) {
                            return false;
                        }
                    }
                } else {
                    if (!DatabaseEntryEdit.UpdateEntry(newWinch)) {
                        return false;
                    }
                }
                currentData.setCurrentWinch(newWinch);
                return true;
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occured in the database\n\r"
                        + "Check Error Log").showAndWait();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AddEditWinchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return false;
    }

    @Override
    protected void deleteCommand() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + currentData.getCurrentWinch().getName() + "?",
                ButtonType.YES, ButtonType.NO);
        a.setTitle("Delete Confirmation");
        Optional<ButtonType> choice = a.showAndWait();
        if (choice.get() == ButtonType.YES) {
            if (DatabaseEntryDelete.DeleteEntry(currentData.getCurrentWinch())) {
                currentData.clearWinch();
                new Alert(Alert.AlertType.INFORMATION, "Winch removed").showAndWait();
            }
        }
    }

    @Override
    protected void setupUnits() {
        //No units at the moment
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
        float rot = parseFloat(run_orientation_toleranceTextField, valid);

        int meteorological_check_time = parseInteger(meteorologicalCheckTimeTextField, valid);
        int meteorological_verify_time = parseInteger(meteorologicalVerifyTimeTextField, valid);

        if (valid) {
            return new Winch(0, name, owner, "", wp1, wp2, wp3, wp4, wp5, wp6, wp7, wp8, wp9, wp10, wp11, wp12, wp13, wp14, wp15, wp16, meteorological_check_time, meteorological_verify_time, rot, "");
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
