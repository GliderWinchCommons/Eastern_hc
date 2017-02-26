//Successful
package AddEditPanels;

import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Airfield;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
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
    private TextArea optionalInformationArea;

    private Airfield currentAirfield;
    private CurrentDataObjectSet objectSet;
    private boolean isEditEntry;
    private Observer parent;
    @FXML
    private Label airfieldAltitudeUnitsLabel;
    private int airfieldAltitudeUnitsID;

    public void setupUnits() {
        airfieldAltitudeUnitsID = objectSet.getCurrentProfile().getUnitSetting("airfieldAltitude");
        String airfieldAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID);
        airfieldAltitudeUnitsLabel.setText(airfieldAltitudeUnitsString);
    }

    public void attach(Observer o) {
        parent = o;
    }

    public AddEditAirfieldFrame(SubScene airfieldPanel) {
        super(airfieldPanel);
    }

    public void edit(Airfield editAirfield) {
        objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
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
            if (!DatabaseEntryDelete.DeleteEntry(currentAirfield)) {
                objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                objectSet.clearAirfield();
                new Alert(Alert.AlertType.INFORMATION, "Airfield removed").showAndWait();
                parent.update("1");
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

            Airfield newAirfield = new Airfield(airfieldName, designator, airfieldAltitude,
                    magneticVariation, airfieldLatitude, airfieldLongitude, "");

            try {
                objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                if (isEditEntry) {
                    newAirfield.setId(objectSet.getCurrentAirfield().getId());
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
                objectSet.setCurrentAirfield(newAirfield);
                parent.update("1");
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
            String airfieldName = airfieldNameField.getText();
            String designator = designatorField.getText();
            String airfieldAltitude = airfieldAltitudeField.getText();
            String magneticVariation = magneticVariationField.getText();
            String airfieldLatitude = airfieldLatitudeField.getText();
            String airfieldLongitude = airfieldLongitudeField.getText();

            airfieldNameField.setStyle(whiteBackground);
            designatorField.setStyle(whiteBackground);
            airfieldAltitudeField.setStyle(whiteBackground);
            magneticVariationField.setStyle(whiteBackground);
            airfieldLatitudeField.setStyle(whiteBackground);
            airfieldLongitudeField.setStyle(whiteBackground);

            if (airfieldName.isEmpty()) {
                airfieldNameField.setStyle(redBackground);
                emptyFields = true;
            }
            if (designator.isEmpty()) {
                designatorField.setStyle(redBackground);
                emptyFields = true;
            }
            if (airfieldAltitude.isEmpty()) {
                airfieldAltitudeField.setStyle(redBackground);
                emptyFields = true;
            }
            if (magneticVariation.isEmpty()) {
                magneticVariationField.setStyle(redBackground);
                emptyFields = true;
            }
            if (airfieldLatitude.isEmpty()) {
                airfieldLatitudeField.setStyle(redBackground);
                emptyFields = true;
            }
            if (airfieldLongitude.isEmpty()) {
                airfieldLongitudeField.setStyle(redBackground);
                emptyFields = true;
            }
            if (emptyFields) {
                throw new IllegalArgumentException();
            }

            Float.parseFloat(airfieldAltitude);
            Float.parseFloat(magneticVariation);
            Float.parseFloat(airfieldLatitude);
            Float.parseFloat(airfieldLongitude);

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
        airfieldNameField.setText("");
        designatorField.setText("");
        airfieldAltitudeField.setText("");
        magneticVariationField.setText("");
        airfieldLatitudeField.setText("");
        airfieldLongitudeField.setText("");

        airfieldNameField.setStyle(whiteBackground);
        designatorField.setStyle(whiteBackground);
        airfieldAltitudeField.setStyle(whiteBackground);
        magneticVariationField.setStyle(whiteBackground);
        airfieldLatitudeField.setStyle(whiteBackground);
        airfieldLongitudeField.setStyle(whiteBackground);

    }

}
