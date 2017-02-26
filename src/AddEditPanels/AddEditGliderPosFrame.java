//Should be successful if entries in DB are set in the CurrentDataObjectSet
package AddEditPanels;

import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.GliderPosition;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.SubScene;

import javafx.scene.control.*;

import java.util.Optional;
import java.util.Random;

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
    private CurrentDataObjectSet objectSet;
    private GliderPosition currentGliderPos;
    private boolean isEditEntry;
    private Observer parent;
    @FXML
    private Label gliderPosAltitudeUnitsLabel;
    private int gliderPosAltitudeUnitsID;

    public void setupUnits() {
        gliderPosAltitudeUnitsID = objectSet.getCurrentProfile().getUnitSetting("gliderPosAltitude");
        String GliderPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(gliderPosAltitudeUnitsID);
        gliderPosAltitudeUnitsLabel.setText(GliderPosAltitudeUnitsString);
    }

    public void attach(Observer o) {
        parent = o;
    }

    public AddEditGliderPosFrame(SubScene gliderPosPanel) {
        super(gliderPosPanel);
    }

    public void edit(GliderPosition editGliderPos) {
        objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();

        isEditEntry = editGliderPos != null;
        currentGliderPos = editGliderPos;

        if (isEditEntry) {
            latitudeField.setText("" + currentGliderPos.getLatitude());
            longitudeField.setText("" + currentGliderPos.getLongitude());
            altitudeField.setText("" + currentGliderPos.getElevation()
                    * UnitConversionRate.convertDistanceUnitIndexToFactor(gliderPosAltitudeUnitsID));
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
            if (!DatabaseUtilities.DatabaseEntryDelete.DeleteEntry(currentGliderPos)) {
                objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                objectSet.cleafGliderPosition();
                new Alert(Alert.AlertType.INFORMATION, "Glider position removed").showAndWait();
                parent.update("3");
            }
        }
    }

    @Override
    protected boolean submitData() {
        if (isComplete()) {
            objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
            String gliderPosName = nameField.getText();
            float altitude = Float.parseFloat(altitudeField.getText())
                    / UnitConversionRate.convertDistanceUnitIndexToFactor(gliderPosAltitudeUnitsID);
            float longitude = Float.parseFloat(longitudeField.getText());
            float latitude = Float.parseFloat(latitudeField.getText());

            int runwayParentId;

            try {
                runwayParentId = objectSet.getCurrentRunway().getId();
            } catch (NullPointerException e) {
                new Alert(Alert.AlertType.ERROR, "Could not find a Runway to link to").showAndWait();
                return false;
            }

            GliderPosition newGliderPos = new GliderPosition(gliderPosName, altitude,
                    latitude, longitude, "");
            newGliderPos.setRunwayParentId(runwayParentId);

            try {
                if (isEditEntry) {
                    newGliderPos.setId(currentGliderPos.getId());
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
                objectSet.setCurrentGliderPosition(newGliderPos);
                //TODO
                //parent.update("3");
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
        nameField.setStyle(whiteBackground);
        altitudeField.setStyle(whiteBackground);
        longitudeField.setStyle(whiteBackground);
        latitudeField.setStyle(whiteBackground);
    }
}
