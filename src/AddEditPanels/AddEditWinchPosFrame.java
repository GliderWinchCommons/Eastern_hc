//Should be successful if entries in DB are set in the CurrentDataObjectSet
package AddEditPanels;

import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.WinchPosition;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;

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
    private WinchPosition currentWinchPos;
    @FXML
    private Label winchPosAltitudeUnitsLabel;
    private int winchPosAltitudeUnitsID;

    @Override
    public void setupUnits() {
        winchPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("winchPosAltitude");
        String winchPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(winchPosAltitudeUnitsID);
        winchPosAltitudeUnitsLabel.setText(winchPosAltitudeUnitsString);
    }

    public AddEditWinchPosFrame(SubScene winchPosPanel) {
        super(winchPosPanel);
    }

    public void edit(WinchPosition editWinchPos) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();

        isEditEntry = editWinchPos != null;
        currentWinchPos = editWinchPos;

        if (isEditEntry) {
            nameField.setText("" + currentWinchPos.getName());
            latitudeField.setText("" + currentWinchPos.getLatitude());
            longitudeField.setText("" + currentWinchPos.getLongitude());
            altitudeField.setText("" + currentWinchPos.getElevation()
                    * UnitConversionRate.convertDistanceUnitIndexToFactor(winchPosAltitudeUnitsID));
            optionalInformationArea.setText(currentWinchPos.getOptionalInfo());
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
            if (DatabaseUtilities.DatabaseEntryDelete.DeleteEntry(currentWinchPos)) {
                currentData.clearWinchPosition();
                new Alert(Alert.AlertType.INFORMATION, "Winch position removed").showAndWait();
            }
        }
    }

    @Override
    protected boolean submitData() {
        if (isComplete()) {
            currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
            String winchPosId = nameField.getText();
            float altitude = Float.parseFloat(altitudeField.getText())
                    / UnitConversionRate.convertDistanceUnitIndexToFactor(winchPosAltitudeUnitsID);
            float longitude = Float.parseFloat(longitudeField.getText());
            float latitude = Float.parseFloat(latitudeField.getText());

            int runwayParentId;

            try {
                runwayParentId = currentData.getCurrentRunway().getId();
            } catch (NullPointerException e) {
                new Alert(Alert.AlertType.ERROR, "Could not find a Runway to link to").showAndWait();
                return false;
            }

            WinchPosition newWinchPos;
            if (isEditEntry) {
                newWinchPos = currentData.getCurrentWinchPosition();
                newWinchPos.setElevation(altitude);
                newWinchPos.setLatitude(latitude);
                newWinchPos.setLongitude(longitude);
                newWinchPos.setOptionalInfo(optionalInformationArea.getText());
            } else {
                newWinchPos = new WinchPosition(winchPosId, altitude,
                        latitude, longitude, optionalInformationArea.getText());
                newWinchPos.setRunwayParentId(runwayParentId);
            }

            try {
                if (isEditEntry) {
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
                currentData.setCurrentWinchPosition(newWinchPos);
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
        optionalInformationArea.setText("");
        nameField.setStyle(whiteBackground);
        altitudeField.setStyle(whiteBackground);
        longitudeField.setStyle(whiteBackground);
        latitudeField.setStyle(whiteBackground);
    }
}
