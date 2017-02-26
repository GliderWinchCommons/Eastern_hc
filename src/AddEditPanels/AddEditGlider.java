package AddEditPanels;

import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Sailplane;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

public class AddEditGlider extends AddEditPanel {

    @FXML
    private TextField nNumberField;
    @FXML
    private TextField ownerField;
    @FXML
    private TextField emptyWeightField;
    @FXML
    private TextField grossWeightField;
    @FXML
    private TextField stallSpeedField;
    @FXML
    private TextField weakLinkField;
    @FXML
    private TextField tensionField;
    @FXML
    private TextField releaseAngleField;
    @FXML
    private TextField winchingSpeedField;
    @FXML
    private TextArea optionalInformationArea;
    private CheckBox ballastCheckBox;
    private CheckBox multipleSeatsCheckBox;
    private Sailplane currentGlider;
    private boolean isEditEntry;
    private Observer parent;
    private CurrentDataObjectSet currentData;
    @FXML
    private Label emptyWeightUnitsLabel;
    @FXML
    private Label maxGrossWeightUnitsLabel;
    @FXML
    private Label stallSpeedUnitsLabel;
    @FXML
    private Label tensionUnitsLabel;
    @FXML
    private Label weakLinkStrengthUnitsLabel;
    @FXML
    private Label winchingSpeedUnitsLabel;
    private int emptyWeightUnitsID;
    private int maxGrossWeightUnitsID;
    private int stallSpeedUnitsID;
    private int tensionUnitsID;
    private int weakLinkStrengthUnitsID;
    private int winchingSpeedUnitsID;

    public void setupUnits() {
        emptyWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("emptyWeight");
        String emptyWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(emptyWeightUnitsID);
        emptyWeightUnitsLabel.setText(emptyWeightUnitsString);

        maxGrossWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("maxGrossWeight");
        String maxGrossWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(maxGrossWeightUnitsID);
        maxGrossWeightUnitsLabel.setText(maxGrossWeightUnitsString);

        stallSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("stallSpeed");
        String stallSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(stallSpeedUnitsID);
        stallSpeedUnitsLabel.setText(stallSpeedUnitsString);

        tensionUnitsID = currentData.getCurrentProfile().getUnitSetting("maxTension");
        String tensionUnitsString = UnitLabelUtilities.tensionUnitIndexToString(tensionUnitsID);
        tensionUnitsLabel.setText(tensionUnitsString);

        weakLinkStrengthUnitsID = currentData.getCurrentProfile().getUnitSetting("weakLinkStrength");
        String weakLinkStrengthUnitsString = UnitLabelUtilities.tensionUnitIndexToString(weakLinkStrengthUnitsID);
        weakLinkStrengthUnitsLabel.setText(weakLinkStrengthUnitsString);

        winchingSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("winchingSpeed");
        String winchingSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(winchingSpeedUnitsID);
        winchingSpeedUnitsLabel.setText(winchingSpeedUnitsString);
    }

    public void attach(Observer o) {
        parent = o;
    }

    public AddEditGlider(SubScene gliderPane) {
        super(gliderPane);
    }

    public void edit(Sailplane sailplaneEdited) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();

        isEditEntry = sailplaneEdited != null;
        currentGlider = sailplaneEdited;

        if (isEditEntry) {
            stallSpeedField.setText("" + currentGlider.getIndicatedStallSpeed()
                    * UnitConversionRate.convertSpeedUnitIndexToFactor(stallSpeedUnitsID));
            grossWeightField.setText("" + currentGlider.getMaximumGrossWeight()
                    * UnitConversionRate.convertWeightUnitIndexToFactor(maxGrossWeightUnitsID));
            emptyWeightField.setText("" + currentGlider.getEmptyWeight()
                    * UnitConversionRate.convertWeightUnitIndexToFactor(emptyWeightUnitsID));
            weakLinkField.setText("" + currentGlider.getMaxWeakLinkStrength()
                    * UnitConversionRate.convertTensionUnitIndexToFactor(weakLinkStrengthUnitsID));
            tensionField.setText("" + currentGlider.getMaxTension()
                    * UnitConversionRate.convertTensionUnitIndexToFactor(tensionUnitsID));
            releaseAngleField.setText("" + currentGlider.getCableReleaseAngle());
            winchingSpeedField.setText("" + currentGlider.getMaxWinchingSpeed()
                    * UnitConversionRate.convertSpeedUnitIndexToFactor(winchingSpeedUnitsID));
        }
    }

    @Override
    public void deleteCommand() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + currentGlider.getId() + "?",
                ButtonType.YES, ButtonType.NO);
        a.setTitle("Delete Confirmation");
        Optional<ButtonType> choice = a.showAndWait();
        if (choice.get() == ButtonType.YES) {
            if (!DatabaseEntryDelete.DeleteEntry(currentGlider)) {
                CurrentDataObjectSet objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                objectSet.clearGlider();
                new Alert(Alert.AlertType.INFORMATION, "Glider removed").showAndWait();
                parent.update();
            }
        }
    }

    @Override
    public boolean submitData() {
        if (isComplete()) {
            String nNumber = nNumberField.getText();
            String name = "Planet Express";
            String owner = "Hubert Farnsworth";
            float emptyWeight = (Float.parseFloat(emptyWeightField.getText())
                    / UnitConversionRate.convertWeightUnitIndexToFactor(emptyWeightUnitsID));
            float grossWeight = Float.parseFloat(grossWeightField.getText())
                    / UnitConversionRate.convertWeightUnitIndexToFactor(maxGrossWeightUnitsID);
            float stallSpeed = Float.parseFloat(stallSpeedField.getText())
                    / UnitConversionRate.convertSpeedUnitIndexToFactor(stallSpeedUnitsID);
            float weakLink = Float.parseFloat(weakLinkField.getText())
                    / UnitConversionRate.convertTensionUnitIndexToFactor(weakLinkStrengthUnitsID);
            float tension = Float.parseFloat(tensionField.getText())
                    / UnitConversionRate.convertTensionUnitIndexToFactor(tensionUnitsID);
            float releaseAngle = Float.parseFloat(releaseAngleField.getText());
            float winchingSpeed = Float.parseFloat(winchingSpeedField.getText())
                    / UnitConversionRate.convertSpeedUnitIndexToFactor(winchingSpeedUnitsID);
            //TODO
            boolean carryBallast = false;//ballastCheckBox.isSelected();
            boolean multipleSeats = false;//multipleSeatsCheckBox.isSelected();

            Sailplane newGlider = new Sailplane(nNumber, name, owner, "", grossWeight,
                    emptyWeight, stallSpeed, winchingSpeed, weakLink, tension,
                    releaseAngle, carryBallast, multipleSeats, "");

            try {
                currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
                if (isEditEntry) {
                    newGlider.setId(currentData.getCurrentSailplane().getId());
                    if (!DatabaseEntryEdit.UpdateEntry(newGlider)) {
                        return false;
                    }
                } else {
                    Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION,
                            "Would you like to save this to the database?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (choice.get() == ButtonType.YES) {
                        Random randomId = new Random();
                        newGlider.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newGlider)) {
                            newGlider.setId(randomId.nextInt(100000000));
                        }
                        if (!DatabaseUtilities.DatabaseEntryInsert.addSailplaneToDB(newGlider)) {
                            return false;
                        }
                    }
                }
                currentData.setCurrentGlider(newGlider);
                //TODO
                //parent.update();
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
            String nNumber = nNumberField.getText();
            String emptyWeight = emptyWeightField.getText();
            String grossWeight = grossWeightField.getText();
            String stallSpeed = stallSpeedField.getText();
            String weakLink = weakLinkField.getText();
            String tension = tensionField.getText();
            String releaseAngle = releaseAngleField.getText();
            String winchingSpeed = winchingSpeedField.getText();

            nNumberField.setStyle(whiteBackground);
            emptyWeightField.setStyle(whiteBackground);
            grossWeightField.setStyle(whiteBackground);
            stallSpeedField.setStyle(whiteBackground);
            weakLinkField.setStyle(whiteBackground);
            tensionField.setStyle(whiteBackground);
            releaseAngleField.setStyle(whiteBackground);
            winchingSpeedField.setStyle(whiteBackground);

            if (nNumber.isEmpty()) {
                nNumberField.setStyle(redBackground);
                emptyFields = true;
            }
            if (emptyWeight.isEmpty()) {
                emptyWeightField.setStyle(redBackground);
                emptyFields = true;
            }
            if (grossWeight.isEmpty()) {
                grossWeightField.setStyle(redBackground);
                emptyFields = true;
            }
            if (stallSpeed.isEmpty()) {
                stallSpeedField.setStyle(redBackground);
                emptyFields = true;
            }
            if (weakLink.isEmpty()) {
                weakLinkField.setStyle(redBackground);
                emptyFields = true;
            }
            if (tension.isEmpty()) {
                tensionField.setStyle(redBackground);
                emptyFields = true;
            }
            if (releaseAngle.isEmpty()) {
                releaseAngleField.setStyle(redBackground);
                emptyFields = true;
            }
            if (winchingSpeed.isEmpty()) {
                winchingSpeedField.setStyle(redBackground);
                emptyFields = true;
            }
            if (emptyFields) {
                throw new IllegalArgumentException();
            }

            Float.parseFloat(emptyWeight);
            Float.parseFloat(grossWeight);
            Float.parseFloat(stallSpeed);
            Float.parseFloat(weakLink);
            Float.parseFloat(tension);
            Float.parseFloat(releaseAngle);
            Float.parseFloat(winchingSpeed);
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
        nNumberField.setText("");
        emptyWeightField.setText("");
        grossWeightField.setText("");
        stallSpeedField.setText("");
        winchingSpeedField.setText("");
        weakLinkField.setText("");
        tensionField.setText("");
        releaseAngleField.setText("");
        //TODO
        //ballastCheckBox.setSelected(false);
        //multipleSeatsCheckBox.setSelected(false);

        nNumberField.setStyle(whiteBackground);
        emptyWeightField.setStyle(whiteBackground);
        grossWeightField.setStyle(whiteBackground);
        stallSpeedField.setStyle(whiteBackground);
        winchingSpeedField.setStyle(whiteBackground);
        weakLinkField.setStyle(whiteBackground);
        tensionField.setStyle(whiteBackground);
        releaseAngleField.setStyle(whiteBackground);

    }

}
