/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configuration;

import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import DatabaseUtilities.DatabaseEntrySelect;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
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

/**
 *
 * @author micha
 */
public class OperatorLoginPanel {
    
    private SubScene thisScene;
    @FXML private TableView operatorTable;
    @FXML private Label nameLabel;
    @FXML private Label adminLabel;
    @FXML private TextArea infoBox;       
    @FXML private Button cancelButton;
    @FXML private Button newOperatorButton;
    private CurrentDataObjectSet currentData;
    
    
    public OperatorLoginPanel(SubScene thisScene)
    {
        this.thisScene = thisScene;          
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
    }
    
    @FXML protected void initialize()
    {
        cancelButton.disableProperty().set(true);
        
        TableColumn airfieldCol = (TableColumn) operatorTable.getColumns().get(0);
        airfieldCol.setCellValueFactory(new PropertyValueFactory<>("first"));

        TableColumn designatorCol = (TableColumn) operatorTable.getColumns().get(1);
        designatorCol.setCellValueFactory(new PropertyValueFactory<>("last"));

        ObservableList list = FXCollections.observableList(DatabaseEntrySelect.getOperators());
        if(list.size() == 0)
        {
            list.add(currentData.getCurrentProfile());
            newOperatorButton.disableProperty().set(true);
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
    
    private void loadData()
    {
        nameLabel.setText(  currentData.getCurrentProfile().getFirst() + " " + 
                            currentData.getCurrentProfile().getMiddle() + " " +
                            currentData.getCurrentProfile().getLast());
        adminLabel.visibleProperty().set(currentData.getCurrentProfile().getAdmin());
        infoBox.setText(currentData.getCurrentProfile().getInfo());
    }
    
    @FXML private void NewOperatorButton_Click(ActionEvent e)
    {
        
    }
    
    @FXML private void NewAdminButton_Click(ActionEvent e)
    {
        
    }
    
    @FXML private void EditButton_Click(ActionEvent e)
    {
        
    }
    
    @FXML private void CancelButton_Click(ActionEvent e)
    {
        thisScene.toBack();
    }   
    
    @FXML private void LoginButton_Click(ActionEvent e)
    {
        //create a new dialog box
        Dialog<String> dialogBox = createDialogBox();
        Optional<String> passwordResult = dialogBox.showAndWait();
        
        passwordResult.ifPresent(password -> {
            cancelButton.disableProperty().set(false);
            newOperatorButton.disableProperty().set(false);
            thisScene.toBack();
        });                
    }
    
    private Dialog createDialogBox()
    {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Login");        
        //create a flow pane to contain all custom items
        FlowPane pane = new FlowPane();
        pane.setPrefSize(300, 100);
        pane.setOrientation(Orientation.VERTICAL);
        pane.setAlignment(Pos.CENTER);
        pane.setColumnHalignment(HPos.CENTER);
        
        Label name = new Label( currentData.getCurrentProfile().getFirst() + " " + 
                                currentData.getCurrentProfile().getMiddle() + " " +
                                currentData.getCurrentProfile().getLast());
        name.setStyle("-fx-font-size:1.33em;");
        name.setPadding(new Insets(0,0,10,0));
        pane.getChildren().add(name);               
        
        pane.getChildren().add(new Label("Password"));
        PasswordField password = new PasswordField();        
        pane.getChildren().add(password);                
        
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
            if(false){
                event.consume();
            }
        });
        
        
        
        dialog.getDialogPane().setContent(pane);
        Platform.runLater(() -> password.requestFocus());
        
        //return the results if it is login button, otherwise return nothing
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == login)
            {
                return password.getText();
            }
           return null; 
        });
        
        return dialog;
    }                 
}
