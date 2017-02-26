//Should be successful if entries in DB are set in the CurrentDataObjectSet
package AddEditPanels;

import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.WinchPosition;
import DatabaseUtilities.DatabaseEntryIdCheck;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.SubScene;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

public class AddEditWinchPosFrame extends AddEditPanel {

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
    private WinchPosition currentWinchPos;
    private boolean isEditEntry;
    private Observer parent;
    @FXML
    private Label winchPosAltitudeUnitsLabel;
    private int winchPosAltitudeUnitsID;

    public void setupUnits() {
        winchPosAltitudeUnitsID = objectSet.getCurrentProfile().getUnitSetting("winchPosAltitude");
        String winchPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(winchPosAltitudeUnitsID);
        winchPosAltitudeUnitsLabel.setText(winchPosAltitudeUnitsString);
    }

    public void attach(Observer o) {
        parent = o;
    }

    public AddEditWinchPosFrame(SubScene winchPosPanel) {
        super(winchPosPanel);
    }

    public void edit(WinchPosition editWinchPos) {
        objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();

        isEditEntry = editWinchPos != null;
        currentWinchPos = editWinchPos;

        if (isEditEntry) {
            latitudeField.setText("" + currentWinchPos.getLatitude());
            longitudeField.setText("" + currentWinchPos.getLongitude());
            altitudeField.setText("" + currentWinchPos.getElevation()
                    * UnitConversionRate.convertDistanceUnitIndexToFactor(winchPosAltitudeUnitsID));
        }
    }

    @Override
    public void deleteCommand() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + currentWinchPos.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        a.setTitle("Delete Confirmation");
        Optional<ButtonType> choice = a.showAndWait();
        if (choice.get() == ButtonType.YES) {
            if (!DatabaseUtilities.DatabaseEntryDelete.DeleteEntry(currentWinchPos)) {
                objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                objectSet.cleafGliderPosition();
                new Alert(Alert.AlertType.INFORMATION, "Glider position removed").showAndWait();
                parent.update("4");
            }
        }
    }

    @Override
    protected boolean submitData() {
        if (isComplete()) {
            objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
            String winchPosId = nameField.getText();
            float altitude = Float.parseFloat(altitudeField.getText())
                    / UnitConversionRate.convertDistanceUnitIndexToFactor(winchPosAltitudeUnitsID);
            float longitude = Float.parseFloat(longitudeField.getText());
            float latitude = Float.parseFloat(latitudeField.getText());

            int runwayParentId;

            try {
                runwayParentId = objectSet.getCurrentRunway().getId();
            } catch (NullPointerException e) {
                new Alert(Alert.AlertType.ERROR, "Could not find a Runway to link to").showAndWait();
                return false;
            }

            WinchPosition newWinchPos = new WinchPosition(winchPosId, altitude,
                    latitude, longitude, "");
            newWinchPos.setRunwayParentId(runwayParentId);

            try {
                if (isEditEntry) {
                    newWinchPos.setId(currentWinchPos.getId());
                    if (!DatabaseUtilities.DatabaseEntryEdit.UpdateEntry(newWinchPos)) {
                        return false;
                    }
                } else {
                    Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION,
                            "Would you like to save this to the database?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (choice.get() == ButtonType.YES) {
                        Random randomId = new Random();
                        newWinchPos.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newWinchPos)) {
                            newWinchPos.setId(randomId.nextInt(100000000));
                        }
                        if (!DatabaseUtilities.DatabaseEntryInsert.addWinchPositionToDB(newWinchPos)) {
                            return false;
                        }
                    }
                }
                objectSet.setCurrentWinchPosition(newWinchPos);
                //TODO
                //parent.update("4");
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
            String altitude = altitudeField.getText();
            String longitude = longitudeField.getText();
            String latitude = latitudeField.getText();
            nameField.setStyle(whiteBackground);
            altitudeField.setStyle(whiteBackground);
            longitudeField.setStyle(whiteBackground);
            latitudeField.setStyle(whiteBackground);

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
