/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import Communications.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Timer resetTimer;

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
                            resetTimer.stop();
                        } else {
                            resetTimer.start();
                        }
                    }
                }
            });

            ActionListener action = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    field.setStyle("-fx-border-color: red;");
                }
            };
            resetTimer = new Timer(5000, action);
            resetTimer.start();
        }
        setupUnits();
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

    @Override
    public abstract void update();

    @Override
    public abstract void update(String msg);

    public abstract void setupUnits();
}
