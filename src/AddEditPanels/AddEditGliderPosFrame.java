//Should be successful if entries in DB are set in the CurrentDataObjectSet
package AddEditPanels;

import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.GliderPosition;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;

public class AddEditGliderPosFrame extends AddEditPanel {

    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private TextField altitudeField;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea optionalInformationArea;
    private GliderPosition currentGliderPos;
    @FXML
    private Label gliderPosAltitudeUnitsLabel;
    private int gliderPosAltitudeUnitsID;

    @Override
    public void setupUnits() {
        gliderPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("gliderPosAltitude");
        String GliderPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(gliderPosAltitudeUnitsID);
        gliderPosAltitudeUnitsLabel.setText(GliderPosAltitudeUnitsString);
    }

    public AddEditGliderPosFrame(SubScene gliderPosPanel) {
        super(gliderPosPanel);
    }

    public void edit(GliderPosition editGliderPos) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();

        isEditEntry = editGliderPos != null;
        currentGliderPos = editGliderPos;

        if (isEditEntry) {
            nameField.setText(currentGliderPos.getName());
            latitudeField.setText("" + currentGliderPos.getLatitude());
            longitudeField.setText("" + currentGliderPos.getLongitude());
            altitudeField.setText("" + currentGliderPos.getElevation()
                    * UnitConversionRate.convertDistanceUnitIndexToFactor(gliderPosAltitudeUnitsID));
            optionalInformationArea.setText(currentGliderPos.getOptionalInfo());
        }
    }

    @Override
    public void deleteCommand() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + currentGliderPos.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        a.setTitle("Delete Confirmation");
        Optional<ButtonType> choice = a.showAndWait();
        if (choice.get() == ButtonType.YES) {
            if (DatabaseUtilities.DatabaseEntryDelete.DeleteEntry(currentGliderPos)) {
                currentData.clearGliderPosition();
                new Alert(Alert.AlertType.INFORMATION, "Glider position removed").showAndWait();
            }
        }
    }

    @Override
    protected boolean submitData() {
        if (isComplete()) {
            currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
            String gliderPosName = nameField.getText();
            float altitude = Float.parseFloat(altitudeField.getText())
                    / UnitConversionRate.convertDistanceUnitIndexToFactor(gliderPosAltitudeUnitsID);
            float longitude = Float.parseFloat(longitudeField.getText());
            float latitude = Float.parseFloat(latitudeField.getText());

            int runwayParentId;

            try {
                runwayParentId = currentData.getCurrentRunway().getId();
            } catch (NullPointerException e) {
                new Alert(Alert.AlertType.ERROR, "Could not find a Runway to link to").showAndWait();
                return false;
            }

            GliderPosition newGliderPos;
            if (isEditEntry) {
                newGliderPos = currentData.getCurrentGliderPosition();
                newGliderPos.setPositionName(gliderPosName);
                newGliderPos.setAltitude(altitude);
                newGliderPos.setLongitude(longitude);
                newGliderPos.setLatitude(latitude);
                newGliderPos.setOptionalInfo(optionalInformationArea.getText());
            } else {
                newGliderPos = new GliderPosition(gliderPosName, altitude,
                        latitude, longitude, optionalInformationArea.getText());
                newGliderPos.setRunwayParentId(runwayParentId);
            }
            try {
                if (isEditEntry) {
                    if (!DatabaseEntryEdit.UpdateEntry(newGliderPos)) {
                        return false;
                    }
                } else {
                    Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION,
                            "Would you like to save this to the database?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (choice.get() == ButtonType.YES) {
                        Random randomId = new Random();
                        newGliderPos.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newGliderPos)) {
                            newGliderPos.setId(randomId.nextInt(100000000));
                        }
                        if (!DatabaseUtilities.DatabaseEntryInsert.addGliderPositionToDB(newGliderPos)) {
                            return false;
                        }
                    }
                }
                currentData.setCurrentGliderPosition(newGliderPos);
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
            String name = nameField.getText();
            String altitude = altitudeField.getText();
            String longitude = longitudeField.getText();
            String latitude = latitudeField.getText();
            nameField.setStyle(whiteBackground);
            altitudeField.setStyle(whiteBackground);
            longitudeField.setStyle(whiteBackground);
            latitudeField.setStyle(whiteBackground);;

            if (name.isEmpty()) {
                nameField.setStyle(redBackground);
                emptyFields = true;
            }
            if (altitude.isEmpty()) {
                altitudeField.setStyle(redBackground);
                emptyFields = true;
            }
            if (longitude.isEmpty()) {
                longitudeField.setStyle(redBackground);
                emptyFields = true;
            }
            if (latitude.isEmpty()) {
                latitudeField.setStyle(redBackground);
                emptyFields = true;
            }

            if (emptyFields) {
                throw new IllegalArgumentException();
            }

            Float.parseFloat(altitude);
            Float.parseFloat(longitude);
            Float.parseFloat(latitude);

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
        altitudeField.setText("");
        longitudeField.setText("");
        latitudeField.setText("");
        optionalInformationArea.setText("");
        nameField.setStyle(whiteBackground);
        altitudeField.setStyle(whiteBackground);
        longitudeField.setStyle(whiteBackground);
        latitudeField.setStyle(whiteBackground);
    }
}
