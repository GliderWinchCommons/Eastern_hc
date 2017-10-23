//Successful
package AddEditPanels;

import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.Airfield;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Optional;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;

public class AddEditAirfieldFrame extends AddEditPanel {

    @FXML
    private TextField airfieldAltitudeField;
    @FXML
    private TextField designatorField;
    @FXML
    private TextField airfieldNameField;
    @FXML
    private TextField magneticVariationField;
    @FXML
    private TextField airfieldLongitudeField;
    @FXML
    private TextField airfieldLatitudeField;
    @FXML
    private TextField utcOffsetField;

    @FXML
    private Label magneticVariationUnitsLabel;
    @FXML
    private Label longitudeUnitsLabel;
    @FXML
    private Label latitudeUnitsLabel;
    @FXML
    private Label utcOffsetUnitsLabel;

    @FXML
    private TextArea optionalInformationArea;

    private Airfield currentAirfield;
    @FXML
    private Label airfieldElevationUnitsLabel;
    private int airfieldAltitudeUnitsID;

    @Override
    public void setupUnits() {
        int airfieldAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldAltitude");
        String airfieldAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID);
        airfieldElevationUnitsLabel.setText(airfieldAltitudeUnitsString);
        //String magneticVariationUnitsString = UnitLabelUtilities.degreesUnitIndexToString()
    }

    public AddEditAirfieldFrame(SubScene airfieldPanel) {
        super(airfieldPanel);
    }

    public void edit(Airfield editAirfield) {
        setupUnits();

        isEditEntry = editAirfield != null;
        currentAirfield = editAirfield;

        if (isEditEntry) {
            airfieldNameField.setText(currentAirfield.getName());
            designatorField.setText(currentAirfield.getDesignator());
            airfieldAltitudeField.setText("" + currentAirfield.getElevation()
                    * UnitConversionRate.convertDistanceUnitIndexToFactor(airfieldAltitudeUnitsID));
            magneticVariationField.setText("" + currentAirfield.getMagneticVariation());
            airfieldLongitudeField.setText("" + currentAirfield.getLongitude());
            airfieldLatitudeField.setText("" + currentAirfield.getLatitude());
            utcOffsetField.setText("" + currentAirfield.getUtcOffset());
            optionalInformationArea.setText(currentAirfield.getOptionalInfo());
        }
    }

    @Override
    public void deleteCommand() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + currentAirfield.getName() + "?"
                + "\n This will also delete all runways on this airfield "
                + "and glider and winch positions associated with those runways.",
                ButtonType.YES, ButtonType.NO);
        a.setTitle("Delete Confirmation");
        Optional<ButtonType> choice = a.showAndWait();
        if (choice.get() == ButtonType.YES) {
            if (DatabaseEntryDelete.DeleteEntry(currentAirfield)) {
                currentData.clearAirfield();
                new Alert(Alert.AlertType.INFORMATION, "Airfield removed").showAndWait();
            }
        }
    }

    @Override
    public boolean submitData() {
        if (isComplete()) {
            String airfieldName = airfieldNameField.getText();
            String designator = designatorField.getText();
            float airfieldAltitude = Float.parseFloat(airfieldAltitudeField.getText())
                    / UnitConversionRate.convertDistanceUnitIndexToFactor(airfieldAltitudeUnitsID);
            float magneticVariation = Float.parseFloat(magneticVariationField.getText());
            float airfieldLatitude = Float.parseFloat(airfieldLatitudeField.getText());
            float airfieldLongitude = Float.parseFloat(airfieldLongitudeField.getText());
            int utcOffset = Integer.parseInt(utcOffsetField.getText());

            Airfield newAirfield;

            if (isEditEntry) {
                newAirfield = currentData.getCurrentAirfield();
                newAirfield.setName(airfieldName);
                newAirfield.setDesignator(designator);
                newAirfield.setAltitude(airfieldAltitude);
                newAirfield.setMagneticVariation(magneticVariation);
                newAirfield.setLatitude(airfieldLatitude);
                newAirfield.setLongitude(airfieldLongitude);
                newAirfield.setUtcOffset(utcOffset);
                newAirfield.setOptionalInfo(optionalInformationArea.getText());
            } else {
                newAirfield = new Airfield(0, airfieldName, designator, airfieldAltitude,
                        magneticVariation, airfieldLatitude, airfieldLongitude, utcOffset, optionalInformationArea.getText());
            }

            try {
                if (isEditEntry) {
                    if (!DatabaseEntryEdit.UpdateEntry(newAirfield)) {
                        return false;
                    }
                } else {
                    Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION,
                            "Would you like to save this to the database?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (choice.get() == ButtonType.YES) {
                        Random randomId = new Random();
                        newAirfield.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newAirfield)) {
                            newAirfield.setId(randomId.nextInt(100000000));
                        }
                        if (!DatabaseUtilities.DatabaseEntryInsert.addAirfieldToDB(newAirfield)) {
                            return false;
                        }
                    }
                }
                currentData.setCurrentAirfield(newAirfield);
                return true;
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "An error occured in the database\n\r"
                        + "Check Error Log").showAndWait();
            }
        }
        return false;
    }

    public boolean isComplete() {
        boolean valid = true;

        valid = valid && !airfieldNameField.getText().isEmpty();
        valid = valid && !designatorField.getText().isEmpty();
        valid = valid && parseFloat(airfieldAltitudeField);
        valid = valid && parseFloat(magneticVariationField);
        valid = valid && parseFloat(airfieldLatitudeField);
        valid = valid && parseFloat(airfieldLongitudeField);
        valid = valid && parseInteger(utcOffsetField);

        return valid;
    }

    @Override
    public void clearData() {
        airfieldNameField.setText("");
        designatorField.setText("");
        airfieldAltitudeField.setText("");
        magneticVariationField.setText("");
        airfieldLatitudeField.setText("");
        airfieldLongitudeField.setText("");
        utcOffsetField.setText("");
        optionalInformationArea.setText("");

        airfieldNameField.setStyle(whiteBackground);
        designatorField.setStyle(whiteBackground);
        airfieldAltitudeField.setStyle(whiteBackground);
        magneticVariationField.setStyle(whiteBackground);
        airfieldLatitudeField.setStyle(whiteBackground);
        airfieldLongitudeField.setStyle(whiteBackground);
        utcOffsetField.setStyle(whiteBackground);
    }

    private boolean parseFloat(TextField field) {
        try {
            field.setStyle(whiteBackground);
            DecimalFormat.getInstance().parse(field.getText()).floatValue();
            return true;
        } catch (ParseException ex) {
            field.setStyle(redBackground);
            return false;
        }
    }

    private boolean parseInteger(TextField field) {
        try {
            field.setStyle(whiteBackground);
            Integer.parseInt(field.getText());
            return true;
        } catch (NumberFormatException ex) {
            field.setStyle(redBackground);
            return false;
        }
    }
}
