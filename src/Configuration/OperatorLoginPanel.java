/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configuration;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import static DatabaseUtilities.DatabaseEntryIdCheck.matchPassword;
import DatabaseUtilities.DatabaseEntrySelect;
import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author micha
 */
public class OperatorLoginPanel implements Observer {

    //TODO: Use this to observe the newOperator panel
    private TabPane currentScenarioTabPane;
    private SubScene loginSubScene;
    @FXML
    private TableView operatorTable;
    @FXML
    private Label nameLabel;
    @FXML
    private Label adminLabel;
    @FXML
    private TextArea infoBox;
    @FXML
    private Button cancelButton;
    @FXML
    private Button newOperatorButton;
    @FXML
    private Button editButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button newAdminButton;

    private NewOperatorPanel newOperatorPanel;

    private CurrentDataObjectSet currentData;
    private Observer parent;

    public OperatorLoginPanel(TabPane currentScenarioTabPane, SubScene loginSubScene, NewOperatorPanel newOperatorPanel) {
        this.currentScenarioTabPane = currentScenarioTabPane;
        this.loginSubScene = loginSubScene;
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.newOperatorPanel = newOperatorPanel;
    }

    public void attach(Observer o) {
        this.parent = o;
    }

    @FXML
    protected void initialize() {
        cancelButton.disableProperty().set(true);

        TableColumn airfieldCol = (TableColumn) operatorTable.getColumns().get(0);
        airfieldCol.setCellValueFactory(new PropertyValueFactory<>("first"));

        TableColumn designatorCol = (TableColumn) operatorTable.getColumns().get(1);
        designatorCol.setCellValueFactory(new PropertyValueFactory<>("last"));

        ObservableList list = FXCollections.observableList(DatabaseEntrySelect.getOperators());
        if (list.isEmpty()) {
            newOperatorButton.disableProperty().set(true);
            editButton.disableProperty().set(true);
            loginButton.disableProperty().set(true);
        }
        operatorTable.setItems(list);
        operatorTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (newValue != null) {
                currentData.setCurrentProfile((Operator) newValue);
                loadData();
            }
        });
        operatorTable.getSelectionModel().selectFirst();
    }

    private void loadData() {
        nameLabel.setText(currentData.getCurrentProfile().getFirst() + " "
                + currentData.getCurrentProfile().getMiddle() + " "
                + currentData.getCurrentProfile().getLast());
        adminLabel.visibleProperty().set(currentData.getCurrentProfile().getAdmin());
        infoBox.setText(currentData.getCurrentProfile().getInfo());
        newAdminButton.disableProperty().set(!currentData.getCurrentProfile().getAdmin());
    }

    @FXML
    private void NewOperatorButton_Click(ActionEvent e) {
        newOperatorPanel.addOperator(false, null, "");
    }

    @FXML
    private void NewAdminButton_Click(ActionEvent e) {
        if (!operatorTable.getItems().isEmpty()) {
            Dialog<String> dialogBox = createPasswordDialogBox();
            Optional<String> passwordResult = dialogBox.showAndWait();

            passwordResult.ifPresent(password -> {
                newOperatorPanel.addOperator(true, null, "");
            });
        } else {
            newOperatorPanel.addOperator(true, null, "");
        }
    }

    @FXML
    private void EditButton_Click(ActionEvent e) {
        Dialog<String> dialogBox = createPasswordDialogBox();
        Optional<String> passwordResult = dialogBox.showAndWait();

        passwordResult.ifPresent(password -> {
            newOperatorPanel.addOperator(false, currentData.getCurrentProfile(), password);
        });
    }

    @FXML
    private void CancelButton_Click(ActionEvent e) {
        currentScenarioTabPane.toFront();
    }

    @FXML
    private void LoginButton_Click(ActionEvent e) {
        //create a new dialog box
        Dialog<String> dialogBox = createPasswordDialogBox();
        Optional<String> passwordResult = dialogBox.showAndWait();

        passwordResult.ifPresent(password -> {
            cancelButton.disableProperty().set(false);
            currentScenarioTabPane.toFront();
            //parent.update();
        });
    }

    private Dialog createPasswordDialogBox() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Login");
        //create a flow pane to contain all custom items
        FlowPane pane = new FlowPane();
        pane.setPrefSize(300, 120);
        pane.setOrientation(Orientation.VERTICAL);
        pane.setAlignment(Pos.CENTER);
        pane.setColumnHalignment(HPos.CENTER);

        Label name = new Label(currentData.getCurrentProfile().getFirst() + " "
                + currentData.getCurrentProfile().getMiddle() + " "
                + currentData.getCurrentProfile().getLast());
        name.setStyle("-fx-font-size:1.33em;");
        name.setPadding(new Insets(0, 0, 10, 0));
        pane.getChildren().add(name);

        pane.getChildren().add(new Label("Password"));
        PasswordField password = new PasswordField();
        password.alignmentProperty().set(Pos.CENTER);
        //password.textProperty().
        pane.getChildren().add(password);

        Label errorLabel = new Label("Incorrect Password!");
        errorLabel.setStyle("-fx-text-fill:red;");
        errorLabel.setVisible(false);
        pane.getChildren().add(errorLabel);

        //create custom login button
        ButtonType login = new ButtonType("Login", ButtonData.OK_DONE);
        //add login button to pane
        dialog.getDialogPane().getButtonTypes().add(login);
        //add cancel button to pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Button loginButton = (Button) dialog.getDialogPane().lookupButton(login);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);

        loginButton.translateXProperty().bind(dialog.getDialogPane().prefWidthProperty().subtract(loginButton.prefHeightProperty().add(cancelButton.prefWidthProperty().subtract(16))));
        cancelButton.translateXProperty().bind(dialog.getDialogPane().prefWidthProperty().subtract(loginButton.prefHeightProperty().add(cancelButton.prefWidthProperty()).subtract(16)));

        //TODO: Add password validation
        loginButton.addEventFilter(ActionEvent.ACTION, event -> {
            //consume if the password does not match
            //display error message or red box or something
            try {
                if (!matchPassword(currentData.getCurrentProfile(), password.getText())) {
                    errorLabel.setVisible(true);
                    event.consume();
                }
            } catch (SQLException | ClassNotFoundException e) {
                errorLabel.setText("Error with database");
                errorLabel.setVisible(true);
                event.consume();
            }
        });

        dialog.getDialogPane().setContent(pane);
        Platform.runLater(() -> password.requestFocus());

        //return the results if it is login button, otherwise return nothing
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == login) {
                return password.getText();
            }
            return null;
        });

        return dialog;
    }

    @Override
    public void update() {
        Operator currOperator = currentData.getCurrentProfile();
        if (currOperator != null) {
            if (!operatorTable.getItems().contains(currOperator)) {
                operatorTable.getItems().add(currOperator);
            }
            operatorTable.getSelectionModel().select(currOperator);
            newOperatorButton.disableProperty().set(false);
            loginButton.disableProperty().set(false);
            editButton.disableProperty().set(false);
        } else {
            operatorTable.getSelectionModel().selectFirst();
        }
        loginSubScene.toFront();
    }

    @Override
    public void update(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
