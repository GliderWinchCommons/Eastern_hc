package AddEditPanels;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Runway;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

public class AddEditRunwayFrame extends AddEditPanel {

    @FXML
    private TextField magneticHeadingField;
    @FXML
    private TextField nameField;
    private CurrentDataObjectSet objectSet;
    private Runway currentRunway;
    private boolean isEditEntry;
    private Observer parent;
    @FXML
    private Label magneticHeadingUnitsLabel;

    public void attach(Observer o) {
        parent = o;
    }

    public AddEditRunwayFrame(SubScene runwayPanel) {
        super(runwayPanel);
    }

    public void edit(Runway editRunway) {
        objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();

        isEditEntry = editRunway != null;
        currentRunway = editRunway;

        if (isEditEntry) {
            nameField.setText(currentRunway.getName());
            magneticHeadingField.setText("" + currentRunway.getMagneticHeading());
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
            if (!DatabaseUtilities.DatabaseEntryDelete.DeleteEntry(currentRunway)) {
                objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                objectSet.clearRunway();
                new Alert(Alert.AlertType.INFORMATION, "Airfield removed").showAndWait();
                parent.update("2");
            }
        }
    }

    @Override
    protected boolean submitData() {
        if (isComplete()) {
            objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
            String name = nameField.getText();
            float magneticHeading = Float.parseFloat(magneticHeadingField.getText());

            int parentId;
            try {
                parentId = objectSet.getCurrentAirfield().getId();
            } catch (NullPointerException e) {
                new Alert(Alert.AlertType.ERROR, "Could not find a Airfield to link to").showAndWait();
                return false;
            }

            Runway newRunway = new Runway(name, magneticHeading, "");
            newRunway.setParentId(parentId);

            try {
                if (isEditEntry) {
                    newRunway.setId(objectSet.getCurrentRunway().getId());
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
                objectSet.setCurrentRunway(newRunway);
                //TODO
                //parent.update("2");
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

        nameField.setStyle(whiteBackground);
        magneticHeadingField.setStyle(whiteBackground);
    }

}
