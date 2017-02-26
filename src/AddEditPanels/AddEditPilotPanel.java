package AddEditPanels;

import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Pilot;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import javax.swing.*;
import java.util.Optional;
import java.util.Random;

public class AddEditPilotPanel extends AddEditPanel {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField flightWeightField;
    private ButtonGroup pilotCapability;
    private ButtonGroup pilotLaunchPref;
    @FXML
    private TextField emergencyContactNameField;
    @FXML
    private TextField emergencyContactPhoneField;
    @FXML
    private TextArea optionalInfoField;
    private Pilot currentPilot;
    private boolean isEditEntry;
    private Observer parent;
    private CurrentDataObjectSet currentData;
    private int flightWeightUnitsID;
    @FXML
    private Label flightWeightUnitsLabel;

    public void setupUnits() {
        flightWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
        String flightWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID);
        flightWeightUnitsLabel.setText(flightWeightUnitsString);
    }

    public void attach(Observer o) {
        parent = o;
    }

    public AddEditPilotPanel(SubScene pilotPanel) {
        super(pilotPanel);
    }

    public void edit(Pilot editPilot) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();

        isEditEntry = editPilot != null;
        currentPilot = editPilot;

        if (isEditEntry) {
            firstNameField.setText(currentPilot.getFirstName());
            lastNameField.setText(currentPilot.getLastName());
            middleNameField.setText(currentPilot.getMiddleName());
            flightWeightField.setText("" + currentPilot.getWeight()
                    * UnitConversionRate.convertWeightUnitIndexToFactor(flightWeightUnitsID));
            emergencyContactNameField.setText(currentPilot.getEmergencyName());
            emergencyContactPhoneField.setText(currentPilot.getEmergencyPhone());

        }
    }

    @Override
    public void deleteCommand() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + currentPilot.getFirstName() + "?",
                ButtonType.YES, ButtonType.NO);
        a.setTitle("Delete Confirmation");
        Optional<ButtonType> choice = a.showAndWait();
        if (choice.get() == ButtonType.YES) {
            if (!DatabaseEntryDelete.DeleteEntry(currentPilot)) {
                CurrentDataObjectSet objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                objectSet.clearPilot();
                new Alert(Alert.AlertType.INFORMATION, "Airfield removed").showAndWait();
                parent.update();
            }
        }
    }

    @Override
    protected boolean submitData() {
        if (isComplete()) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String middleName = middleNameField.getText();
            String emergencyContact = emergencyContactNameField.getText();
            String emergencyPhone = emergencyContactPhoneField.getText();
            String optionalInformation = optionalInfoField.getText();

            float weight = Float.parseFloat(flightWeightField.getText())
                    / UnitConversionRate.convertWeightUnitIndexToFactor(flightWeightUnitsID);
            //TODO
            String capability = "Student"; //pilotCapability.getSelection().getActionCommand();
            float preference = 0;

            Pilot newPilot = new Pilot(0, firstName, lastName, middleName,
                    weight, capability, preference, emergencyContact,
                    emergencyPhone, optionalInformation);

            try {
                currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
                if (isEditEntry) {
                    newPilot.setId(currentData.getCurrentAirfield().getId());
                    if (!DatabaseEntryEdit.updateEntry(newPilot)) {
                        return false;
                    }
                } else {
                    Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION,
                            "Would you like to save this to the database?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (choice.get() == ButtonType.YES) {
                        Random randomId = new Random();
                        newPilot.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newPilot)) {
                            newPilot.setId(randomId.nextInt(100000000));
                        }
                        if (!DatabaseUtilities.DatabaseEntryInsert.addPilotToDB(newPilot)) {
                            return false;
                        }
                    }
                }
                currentData.setCurrentPilot(newPilot);
                //TODO
                //parent.update();
                return true;
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "An error occured in the database\n\r"
                        + "Check Error Log").showAndWait();
            }
        }
        return false;
    }

    public boolean isComplete() {
        try {
            boolean emptyFields = false;
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String weightStr = flightWeightField.getText();
            firstNameField.setStyle(whiteBackground);
            lastNameField.setStyle(whiteBackground);
            flightWeightField.setStyle(whiteBackground);

            if (firstName.isEmpty()) {
                firstNameField.setStyle(redBackground);
                emptyFields = true;
            }
            if (lastName.isEmpty()) {
                lastNameField.setStyle(redBackground);
                emptyFields = true;
            }
            if (weightStr.isEmpty()) {
                flightWeightField.setStyle(redBackground);
                emptyFields = true;
            }

            /* TODO
            if (pilotCapability.getSelection() == null) {
                //TODO some kind of alert
                emptyFields = true;
            }

            if (pilotLaunchPref.getSelection() == null) {
                //TODO some kind of alert
                emptyFields = true;
            }
             */
            if (emptyFields) {
                throw new IllegalArgumentException();
            }

            Float.parseFloat(weightStr);
            return true;
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Please input correct numerical values").showAndWait();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, "Please complete all required fields").showAndWait();
        }
        return false;
    }

    @Override
    public void clearData() {
        firstNameField.setText("");
        lastNameField.setText("");
        middleNameField.setText("");
        flightWeightField.setText("");
        //TODO
        //pilotCapability.clearSelection();
        //pilotLaunchPref.clearSelection();
        emergencyContactNameField.setText("");
        emergencyContactPhoneField.setText("");
        optionalInfoField.setText("");

        firstNameField.setStyle(whiteBackground);
        lastNameField.setStyle(whiteBackground);
        flightWeightField.setStyle(whiteBackground);
    }

}
