/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvironmentalWidgets;

import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Communications.Observer;
import DataObjects.CurrentLaunchInformation;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author jtroxel
 */
public abstract class EnvironmentalWidget extends JPanel implements Observer {
    protected TextField field;
    protected CheckBox isEditable;
    protected Label unit;
    protected int unitId;
    
    public EnvironmentalWidget(TextField field, CheckBox edit, Label unit)
    {
        this.field = field;
        this.isEditable = edit;
        this.unit = unit;
        setupUnits();
    }
    
    public String getFieldValue()
    {
        return field.getText();
    }

    public boolean manualEntry(){
        return isEditable.isSelected();
    }

    @Override
    public abstract void update();

    @Override
    public abstract void update(String msg);
    
    public abstract void setupUnits();
}
