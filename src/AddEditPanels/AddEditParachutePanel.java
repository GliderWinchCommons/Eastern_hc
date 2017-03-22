/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddEditPanels;

import DataObjects.Parachute;
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
public class AddEditParachutePanel extends AddEditPanel {

    @FXML
    private TextField parachuteNameField;
    @FXML
    private TextField liftField;
    @FXML
    private TextField dragField;
    @FXML
    private TextField weightField;

    @FXML
    private TextArea optionalInformationArea;

    private Parachute currentParachute;

    public AddEditParachutePanel(SubScene displayPanel) {
        super(displayPanel);
    }

    public void edit(Parachute editParachute) {
        currentParachute = editParachute;
    }

    @Override
    protected void clearData() {
        parachuteNameField.setText("");
        liftField.setText("");
        dragField.setText("");
        weightField.setText("");
        optionalInformationArea.setText("");
    }

    private boolean isComplete() {
        return true;
    }

    @Override
    protected boolean submitData() {
        if (isComplete()) {
            String name = parachuteNameField.getText();
            Float lift = Float.parseFloat(liftField.getText());
            Float drag = Float.parseFloat(dragField.getText());
            Float weight = Float.parseFloat(weightField.getText());

            Parachute newParachute;
            if (isEditEntry) {
                newParachute = currentParachute;
                newParachute.setName(name);
                newParachute.setLift(lift);
                newParachute.setDrag(drag);
                newParachute.setWeight(weight);
                newParachute.setInfo(optionalInformationArea.getText());
            } else {
                newParachute = new Parachute(0, name, lift, drag, weight, optionalInformationArea.getText());
            }
            try {
                if (isEditEntry) {
                    if (!DatabaseEntryEdit.UpdateEntry(newParachute)) {
                        return false;
                    }
                } else {
                    Optional<ButtonType> choice = new Alert(Alert.AlertType.CONFIRMATION,
                            "Would you like to save this to the database?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (choice.get() == ButtonType.YES) {
                        Random randomId = new Random();
                        newParachute.setParachuteId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newParachute)) {
                            newParachute.setParachuteId(randomId.nextInt(100000000));
                        }
                        if (!DatabaseUtilities.DatabaseEntryInsert.addParachuteToDB(newParachute)) {
                            return false;
                        }
                    }
                }
                currentData.getCurrentDrum().setParachute(newParachute);
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
                "Are you sure you want to delete " + currentParachute.getName() + "?",
                ButtonType.YES, ButtonType.NO);
        a.setTitle("Delete Confirmation");
        Optional<ButtonType> choice = a.showAndWait();
        if (choice.get() == ButtonType.YES) {
            if (DatabaseEntryDelete.DeleteEntry(currentParachute)) {
                currentData.clearDrum();
                new Alert(Alert.AlertType.INFORMATION, "Parachute removed").showAndWait();
            }
        }
    }

    @Override
    protected void setupUnits() {
        //TODO
    }

}
