package AddEditPanels;

import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Runway;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddEditRunwayFrame extends AddEditPanel {

    @FXML
    private TextField magneticHeadingField;
    @FXML
    private TextField nameField;
    private Runway currentRunway;
    @FXML
    private Label magneticHeadingUnitsLabel;
    @FXML
    private TextArea optionalInformationTextArea;

    private int magneticHeadingUnitsID;

    public AddEditRunwayFrame(SubScene runwayPanel) {
        super(runwayPanel);
    }

    public void edit(Runway editRunway) {
        isEditEntry = editRunway != null;
        currentRunway = editRunway;

        if (isEditEntry) {
            nameField.setText(currentRunway.getName());
            magneticHeadingField.setText("" + currentRunway.getMagneticHeading());
            optionalInformationTextArea.setText(currentRunway.getOptionalInfo());
        }
    }

    @Override
    public void deleteCommand() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + currentRunway.getId() + "?"
                + "\n This will also delete all glider and winch positions associated with this runway.",
                ButtonType.YES, ButtonType.NO);
        a.setTitle("Delete Confirmation");
        Optional<ButtonType> choice = a.showAndWait();
        if (choice.get() == ButtonType.YES) {
            if (DatabaseUtilities.DatabaseEntryDelete.DeleteEntry(currentRunway)) {
                currentData.clearRunway();
                new Alert(Alert.AlertType.INFORMATION, "Runway removed").showAndWait();
            }
        }
    }

    @Override
    protected boolean submitData() {
        if (isComplete()) {
            currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
            String name = nameField.getText();
            float magneticHeading = Float.parseFloat(magneticHeadingField.getText());

            int parentId;
            try {
                parentId = currentData.getCurrentAirfield().getId();
            } catch (NullPointerException e) {
                new Alert(Alert.AlertType.ERROR, "Could not find a Airfield to link to").showAndWait();
                return false;
            }

            Runway newRunway;
            if (isEditEntry) {
                newRunway = currentData.getCurrentRunway();
                newRunway.setRunwayName(name);
                newRunway.setMagneticHeading(magneticHeading);
                newRunway.setOptionalInfo(optionalInformationTextArea.getText());
            } else {
                newRunway = new Runway(name, magneticHeading, optionalInformationTextArea.getText());
                newRunway.setParentId(parentId);
            }
            try {
                if (isEditEntry) {
                    if (!DatabaseEntryEdit.UpdateEntry(newRunway)) {
                        return false;
                    }
                } else {
                    Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION,
                            "Would you like to save this to the database?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (choice.get() == ButtonType.YES) {
                        Random randomId = new Random();
                        newRunway.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newRunway)) {
                            newRunway.setId(randomId.nextInt(100000000));
                        }
                        if (!DatabaseUtilities.DatabaseEntryInsert.addRunwayToDB(newRunway)) {
                            return false;
                        }
                    }
                }
                currentData.setCurrentRunway(newRunway);
                return true;
            } catch (SQLException | ClassNotFoundException e1) {
                new Alert(Alert.AlertType.ERROR, "An error occured in the database\n\r"
                        + "Check Error Log").showAndWait();
            }
        }
        return false;
    }

    public boolean isComplete() {
        try {
            boolean emptyFields = false;
            String name = nameField.getText();
            String magneticHeading = magneticHeadingField.getText();
            nameField.setStyle(whiteBackground);
            magneticHeadingField.setStyle(whiteBackground);

            if (name.isEmpty()) {
                nameField.setStyle(redBackground);
                emptyFields = true;
            }
            if (magneticHeading.isEmpty()) {
                magneticHeadingField.setStyle(redBackground);
                emptyFields = true;
            }

            if (emptyFields) {
                throw new IllegalArgumentException();
            }

            Float.parseFloat(magneticHeading);
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
        nameField.setText("");
        magneticHeadingField.setText("");
        optionalInformationTextArea.setText("");

        nameField.setStyle(whiteBackground);
        magneticHeadingField.setStyle(whiteBackground);
    }

    @Override
    protected void setupUnits() {
        magneticHeadingUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
        String flightWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(magneticHeadingUnitsID);
        magneticHeadingUnitsLabel.setText(flightWeightUnitsString);
    }

}
