/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Winch;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jtroxel
 */
public abstract class EnvironmentalWidget extends JPanel implements Observer {

    protected TextField field;
    protected CheckBox isEditable;
    protected Label unit;
    protected int unitId;
    private Timer checkTimer, verifyTimer;

    public EnvironmentalWidget(TextField field, CheckBox edit, Label unit) {
        this.field = field;
        this.isEditable = edit;
        this.unit = unit;
        if (edit != null) {
            field.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                    if (field.editableProperty().getValue()) {
                        if (newValue == true) {
                            field.setStyle("");
                            checkTimer.stop();
                            verifyTimer.stop();
                        } else {
                            try {
                                Number d = DecimalFormat.getInstance().parse(field.getText());
                                field.setStyle("-fx-border-color: green;");
                                checkTimer.restart();
                            } catch (ParseException ex) {
                                field.setStyle("-fx-border-color: red;");
                            }
                        }
                    }
                }
            });
            Winch temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch();
            //get check time if available, otherwise default is 60 seconds
            //multiply by 1000 to get milliseconds
            checkTimer = getCheckTimer();
            checkTimer.start();

            //get check time if available, otherwise default is 60 seconds
            verifyTimer = getVerifyTimer();

        }
        setupUnits();
    }

    private Timer getCheckTimer() {
        Winch temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch();
        return new Timer((temp != null ? temp.getMeteorological_check_time() : 60) * 1000, (ActionEvent e) -> {
            Platform.runLater(() -> {
                if(field.getText() == null){
                    field.setStyle("-fx-border-color: gold;");
                }
                checkTimer.stop();
                verifyTimer.restart();
            });
        });
    }

    private Timer getVerifyTimer() {
        Winch temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch();
        return new Timer((temp != null ? temp.getMeteorological_verify_time() : 60) * 1000, (ActionEvent e) -> {
            Platform.runLater(() -> {
                if(field.getText() == null){
                    field.setStyle("");
                    field.setStyle("-fx-border-color: red;");
                }
                verifyTimer.stop();
            });
        });
    }

    public String getFieldValue() {
        return field.getText();
    }

    public boolean manualEntry() {
        return isEditable.isSelected();
    }

    public boolean validateWidget() {
        //return !(field.getStyle().equals("-fx-border-color: red;"));
        return true;
    }

    public void updateTimers() {
        if (checkTimer != null) {
            Winch temp = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch();
            //get check time if available, otherwise default is 60 seconds
            //multiply by 1000 to get milliseconds
            int checkDelay = (temp != null ? temp.getMeteorological_check_time() : 60) * 1000;
            //get check time if available, otherwise default is 60 seconds
            int verifyDelay = (temp != null ? temp.getMeteorological_verify_time() : 60) * 1000;

            if (checkTimer.getDelay() != checkDelay || verifyTimer.getDelay() != verifyDelay) {
                checkTimer = getCheckTimer();
                verifyTimer = getVerifyTimer();
                checkTimer.restart();
                verifyTimer.stop();
                field.setStyle("");
            }
        }
    }

    @Override
    public abstract void update();

    @Override
    public abstract void update(String msg);

    public abstract void setupUnits();
}
