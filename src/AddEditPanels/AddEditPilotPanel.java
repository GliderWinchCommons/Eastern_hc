package AddEditPanels;

import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Pilot;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javax.swing.*;

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
    private int flightWeightUnitsID;
    @FXML
    private Label flightWeightUnitsLabel;
    @FXML
    Slider preferenceSlider;

    @Override
    public void setupUnits() {
        flightWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
        String flightWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID);
        flightWeightUnitsLabel.setText(flightWeightUnitsString);
    }

    public AddEditPilotPanel(SubScene pilotPanel) {
        super(pilotPanel);
    }

    @FXML
    public void initialize() {
        preferenceSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double d) {
                if (d == 0) {
                    return "Mild";
                }
                if (d == .5) {
                    return "Nominal";
                }
                if (d == 1) {
                    return "Aggressive";
                }
                return d.toString();
            }

            @Override
            public Double fromString(String string) {
                if (string.equalsIgnoreCase("Student")) {
                    return 0.0;
                }
                if (string.equalsIgnoreCase("Proficient")) {
                    return 0.5;
                }
                if (string.equalsIgnoreCase("Advanced")) {
                    return 1.0;
                }
                return Double.parseDouble(string);
            }
        });
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
            optionalInfoField.setText(currentPilot.getOptionalInfo());
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
            if (DatabaseEntryDelete.DeleteEntry(currentPilot)) {
                currentData.clearPilot();
                new Alert(Alert.AlertType.INFORMATION, "Pilot removed").showAndWait();
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

            Pilot newPilot;

            if (isEditEntry) {
                newPilot = currentData.getCurrentPilot();
                newPilot.setFirstName(firstName);
                newPilot.setLastName(lastName);
                newPilot.setMiddleName(middleName);
                newPilot.setFlightWeight(weight);
                newPilot.setCapability(capability);
                newPilot.setPreference(preference);
                newPilot.setEmergencyContact(emergencyContact);
                newPilot.setEmergencyPhone(emergencyPhone);
                newPilot.setOptional_info(optionalInformation);
            } else {
                newPilot = new Pilot(0, firstName, lastName, middleName,
                        weight, capability, preference, emergencyContact,
                        emergencyPhone, optionalInformation);
            }

            try {
                currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
                if (isEditEntry) {
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
