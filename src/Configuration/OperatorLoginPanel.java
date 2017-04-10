/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configuration;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import DatabaseUtilities.DatabaseEntryDelete;
import static DatabaseUtilities.DatabaseEntryIdCheck.matchPassword;
import DatabaseUtilities.DatabaseEntrySelect;
import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import mainhost.MainWindow;

/**
 *
 * @author micha
 */
public class OperatorLoginPanel implements Observer {

    private MainWindow mw;
    private SubScene loginSubScene;
    private NewOperatorPanel newOperatorPanel;
    private SubScene profileManagementFrame;
    @FXML
    private TableView operatorTable;
    @FXML
    private Label nameLabel;
    @FXML
    private Label adminLabel;
    @FXML
    private TextArea infoBox;
    @FXML
    private Button logoutButton;
    @FXML
    private Button newOperatorButton;
    @FXML
    private Button editOperatorButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button newAdminButton;
    @FXML
    private Button editSettingsButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button showPasswordButton;

    private String password;
    private static boolean loggedIn;
    private Operator currentOperator;

    private CurrentDataObjectSet currentData;
    private Observer parent;

    public OperatorLoginPanel(MainWindow mw, SubScene loginSubScene, NewOperatorPanel newOperatorPanel, SubScene profileManagementFrame) {

        this.mw = mw;
        this.loginSubScene = loginSubScene;
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        this.newOperatorPanel = newOperatorPanel;
        this.profileManagementFrame = profileManagementFrame;
        password = "";
        loggedIn = false;
    }

    public void attach(Observer o) {
        this.parent = o;
    }

    @FXML
    protected void initialize() {
        newOperatorButton.setDisable(true);
        newAdminButton.setDisable(true);
        editOperatorButton.setDisable(true);
        logoutButton.setDisable(true);
        editSettingsButton.setDisable(true);
        deleteButton.setDisable(true);
        showPasswordButton.setDisable(true);

        TableColumn airfieldCol = (TableColumn) operatorTable.getColumns().get(0);
        airfieldCol.setCellValueFactory(new PropertyValueFactory<>("first"));

        TableColumn designatorCol = (TableColumn) operatorTable.getColumns().get(1);
        designatorCol.setCellValueFactory(new PropertyValueFactory<>("last"));

        ObservableList list = FXCollections.observableList(DatabaseEntrySelect.getOperators());
        if (list.isEmpty()) {
            loginButton.disableProperty().set(true);
            newAdminButton.setDisable(false);
        }
        operatorTable.setItems(list);
        currentOperator = null;
    }

    private void loadData() {
        if (loggedIn) {
            nameLabel.setText(currentData.getCurrentProfile().getFirst() + " "
                    + currentData.getCurrentProfile().getMiddle() + " "
                    + currentData.getCurrentProfile().getLast());
            adminLabel.visibleProperty().set(currentData.getCurrentProfile().getAdmin());
            infoBox.setText(currentData.getCurrentProfile().getInfo());
            newAdminButton.disableProperty().set(!currentData.getCurrentProfile().getAdmin());
            deleteButton.disableProperty().set(newAdminButton.disableProperty().getValue());
            //showPasswordButton.disableProperty().set(newAdminButton.disableProperty().getValue());
        } else {
            nameLabel.setText("");
            adminLabel.visibleProperty().set(false);
            infoBox.setText("");
            newAdminButton.disableProperty().set(true);
            deleteButton.disableProperty().set(true);
            showPasswordButton.disableProperty().set(true);
        }
    }

    @FXML
    private void NewOperatorButton_Click(ActionEvent e) {
        newOperatorPanel.addOperator(false, null, "");
    }

    @FXML
    private void NewAdminButton_Click(ActionEvent e) {
        if (!operatorTable.getItems().isEmpty()) {
            Dialog<String> dialogBox = createPasswordDialogBox(currentData.getCurrentProfile());
            Optional<String> passwordResult = dialogBox.showAndWait();

            passwordResult.ifPresent(password -> {
                newOperatorPanel.addOperator(true, null, "");
            });
        } else {
            newOperatorPanel.addOperator(true, null, "");
        }
    }

    @FXML
    private void EditOperatorButton_Click(ActionEvent e) {
        newOperatorPanel.addOperator(false, currentData.getCurrentProfile(), password);
    }

    @FXML
    private void EditSettingsButton_Click(ActionEvent e) {
        profileManagementFrame.toFront();
    }

    @FXML
    private void LogoutButton_Click(ActionEvent e) {
        mw.enableTabs(false);
        newOperatorButton.setDisable(true);
        newAdminButton.setDisable(true);
        editOperatorButton.setDisable(true);
        logoutButton.setDisable(true);
        loginButton.setDisable(false);
        editSettingsButton.setDisable(true);
        loggedIn = false;
        currentOperator = null;
    }

    @FXML
    private void LoginButton_Click(ActionEvent e) {
        Operator newValue = (Operator) operatorTable.getSelectionModel().getSelectedItem();
        if (newValue != null) {
            //create a new dialog box
            Dialog<String> dialogBox = createPasswordDialogBox(newValue);
            Optional<String> passwordResult = dialogBox.showAndWait();

            passwordResult.ifPresent(pass -> {
                //cancelButton.disableProperty().set(false);
                mw.enableTabs(true);
                password = pass;
                newOperatorButton.setDisable(false);
                editOperatorButton.setDisable(false);
                logoutButton.setDisable(false);
                editSettingsButton.setDisable(false);
                loginButton.setDisable(true);
                loggedIn = true;
                currentData.setCurrentProfile((Operator) newValue);
                currentOperator = (Operator) newValue;
                loadData();
            });
        }
    }

    @FXML
    public void DeleteButton_Click(ActionEvent e) {
        Operator selected = (Operator) operatorTable.getSelectionModel().getSelectedItem();
        if (selected != null && operatorTable.getItems().size() > 1) {
            if (!selected.getAdmin() || selected != currentOperator) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete " + selected.getFirst() + " " + selected.getMiddle() + " " + selected.getLast() + "?",
                        ButtonType.YES, ButtonType.NO);
                a.setTitle("Delete Confirmation");
                Optional<ButtonType> choice = a.showAndWait();
                if (choice.get() == ButtonType.YES) {
                    if (DatabaseEntryDelete.DeleteEntry(selected)) {
                        new Alert(Alert.AlertType.INFORMATION, "Operator removed").showAndWait();
                        operatorTable.getItems().remove(selected);
                    }
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Can't delete yourself").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please make sure an operator is slected or there is more than one operator in the table.").showAndWait();
        }
    }

    @FXML
    public void ShowPasswordButton_Click(ActionEvent e) {
        Operator selected = (Operator) operatorTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            //TODO: Get password from database and then display in an alert panel or something
        }
    }

    private Dialog createPasswordDialogBox(Operator operator) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Login");
        //create a flow pane to contain all custom items
        FlowPane pane = new FlowPane();
        pane.setPrefSize(300, 120);
        pane.setOrientation(Orientation.VERTICAL);
        pane.setAlignment(Pos.CENTER);
        pane.setColumnHalignment(HPos.CENTER);

        Label name = new Label(operator.getFirst() + " "
                + operator.getMiddle() + " "
                + operator.getLast());
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
                if (!matchPassword(operator, password.getText())) {
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
        } else {
            operatorTable.getSelectionModel().selectFirst();
        }
        loginSubScene.toFront();
        if (currentOperator == null && currOperator != null) {
            currentOperator = currOperator;
            currentData.setCurrentProfile(currentOperator);
            loginButton.setDisable(false);
            newAdminButton.setDisable(true);
        } else if (currentOperator != null) {
            currentData.setCurrentProfile(currentOperator);
        }
    }

    @Override
    public void update(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }
}
