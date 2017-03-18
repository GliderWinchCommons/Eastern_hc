/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEditPanels;

import DataObjects.Drum;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author micha
 */
public class AddEditDrumPanel extends AddEditPanel {

    @FXML
    private TextField drumNameField;
    @FXML
    private TextField drumNumberField;
    @FXML
    private TextField coreDiameterField;
    @FXML
    private TextField kFactorField;
    @FXML
    private TextField cableLengthField;
    @FXML
    private TextField cableDensityField;
    @FXML
    private TextField systemEquivalentMassField;
    @FXML
    private TextField numLaunchesField;
    @FXML
    private TextField maxTensionField;

    @FXML
    private TextArea optionalInformationArea;

    private Drum currentDrum;

    public AddEditDrumPanel(SubScene displayPanel) {
        super(displayPanel);
    }

    public void edit(Drum editDrum) {
        currentDrum = editDrum;
    }

    @Override
    protected void clearData() {
        drumNameField.setText("");
        drumNumberField.setText("");
        coreDiameterField.setText("");
        kFactorField.setText("");
        cableLengthField.setText("");
        cableDensityField.setText("");
        systemEquivalentMassField.setText("");
        numLaunchesField.setText("");
        maxTensionField.setText("");
    }

    private boolean isComplete() {
        return true;
    }

    @Override
    protected boolean submitData() {
        if (isComplete()) {

            String name = drumNameField.getText();
            int winchId = currentData.getCurrentWinch().getId();
            int drumNum = Integer.parseInt(drumNumberField.getText());
            float coreDiameter = Float.parseFloat(coreDiameterField.getText());
            float kFactor = Float.parseFloat(kFactorField.getText());
            float springConst = 0;
            float cableLength = Float.parseFloat(cableLengthField.getText());
            float cableDensity = Float.parseFloat(cableDensityField.getText());
            float systemEquivalentMass = Float.parseFloat(systemEquivalentMassField.getText());
            int numLaunches = Integer.parseInt(numLaunchesField.getText());
            float maxTension = Float.parseFloat(maxTensionField.getText());
            String info = optionalInformationArea.getText();

            Drum newDrum;
            if (isEditEntry) {
                newDrum = currentDrum;
                newDrum.setDrumNum(drumNum);
                newDrum.setCoreDiameter(coreDiameter);
                newDrum.setKFactor(kFactor);
                newDrum.setSpringConst(springConst);
                newDrum.setCableLength(cableLength);
                newDrum.setCableDensity(cableDensity);
            } else {
                newDrum = new Drum(0, winchId, name, drumNum, coreDiameter, kFactor,
                        springConst, cableLength, cableDensity, systemEquivalentMass,
                        numLaunches, maxTension, info);
            }
            try {
                if (isEditEntry) {
                    if (!DatabaseEntryEdit.UpdateEntry(newDrum)) {
                        return false;
                    }
                } else {
                    Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION,
                            "Would you like to save this to the database?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (choice.get() == ButtonType.YES) {
                        Random randomId = new Random();
                        newDrum.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newDrum)) {
                            newDrum.setId(randomId.nextInt(100000000));
                        }
                        if (!DatabaseUtilities.DatabaseEntryInsert.addDrumToDB(newDrum)) {
                            return false;
                        }
                    }
                }
                currentData.setCurrentDrum(newDrum);
                return true;
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "An error occured in the database\n\r"
                        + "Check Error Log").showAndWait();
            }
        }
        return false;
    }

    @Override
    protected void deleteCommand() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + currentDrum.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        a.setTitle("Delete Confirmation");
        Optional<ButtonType> choice = a.showAndWait();
        if (choice.get() == ButtonType.YES) {
            if (DatabaseEntryDelete.DeleteEntry(currentDrum)) {
                currentData.clearDrum();
                new Alert(Alert.AlertType.INFORMATION, "Drum removed").showAndWait();
            }
        }
    }

    @Override
    protected void setupUnits() {
        //TODO
    }

}
