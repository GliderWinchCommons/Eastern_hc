package ParameterSelection;

import DataObjects.CurrentDataObjectSet;
import DataObjects.Drive;
import DataObjects.Drum;
import DataObjects.Parachute;
import DataObjects.Winch;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author jtroxel
 */
public class WinchEditPanel extends JPanel {
    private JPanel WinchPanel;
    private JPanel DrumPanel;
    private JPanel ParachutePanel;
    private JList winchList;
    private JList drumList;
    private JList paraList;
    
    private JLabel winchBrakePressureField;
    private JLabel winchNameField;
    private JLabel winchOptDataField;
    
    private JLabel drumCoreDiameterField;
    private JLabel drumKFactorField;
    private JLabel drumCableLengthField;
    private JLabel drumNumLaunchesField;
    private JLabel drumNameField;

    private JLabel paraNameField;
    private JLabel paraIDField;
    private JLabel paraLiftField;
    private JLabel paraDragField;
    private JLabel paraWeightField;

    private CurrentDataObjectSet data;
    
    private ArrayList<Winch> currentWinches;
    private List<Drum> currentDrums;
    private List<Parachute> currentParas;
    
    DefaultListModel winchModel = new DefaultListModel();
    DefaultListModel drumModel = new DefaultListModel();
    DefaultListModel paraModel = new DefaultListModel();

    
    public WinchEditPanel()
    {
        data = CurrentDataObjectSet.getCurrentDataObjectSet();
        currentWinches = getWinchList();
        initComponents();
    }
    
    private void updateDrumList() {
        /*
        currentDrums = data.getCurrentWinch().getDrumsForDrive("1");
        for(Drum drum : currentDrums) {
            drumModel.addElement(drum);
        }
        */
    }
    
    private void updateParachuteList() {
        /*
        currentParas = data.getCurrentWinch().getParachuteList();
        for(Parachute para : currentParas) {
            paraModel.addElement(para);
        }
        */
    }
    
    private void initComponents()
    {
        this.setLayout(new GridLayout(3,1));
        WinchPanel = new JPanel();
        WinchPanel.setLayout(new GridLayout(1,2));
        WinchPanel.setBackground(Color.WHITE);
        DrumPanel = new JPanel();
        DrumPanel.setLayout(new GridLayout(1,2));
        DrumPanel.setBackground(Color.WHITE);
        ParachutePanel = new JPanel();
        ParachutePanel.setLayout(new GridLayout(1,2));
        ParachutePanel.setBackground(Color.WHITE);
        
        winchNameField = new JLabel("Name");
        winchBrakePressureField = new JLabel("Brake");
        winchOptDataField = new JLabel("Optional");
        
        drumNameField = new JLabel("Name");
        drumCoreDiameterField = new JLabel("Core Diameter");
        drumCableLengthField = new JLabel("Cable Length");
        drumKFactorField = new JLabel("K Factor");
        drumNumLaunchesField = new JLabel("Launches");
        
        paraNameField = new JLabel("Name");
        paraIDField = new JLabel("ID");
        paraLiftField = new JLabel("Lift");
        paraDragField = new JLabel("Drag");
        paraWeightField = new JLabel("Weight");
        
        winchList = new javax.swing.JList();
        JScrollPane winchScroller = new JScrollPane();
        winchScroller.setBackground(Color.WHITE);
        WinchPanel.add(winchScroller);
        JPanel winchInfoPanel = new JPanel();
        winchInfoPanel.setBackground(Color.WHITE);
        winchInfoPanel.setLayout(new BoxLayout(winchInfoPanel, BoxLayout.PAGE_AXIS));
        winchInfoPanel.add(winchNameField);        
        winchInfoPanel.add(winchBrakePressureField);
        winchInfoPanel.add(winchOptDataField);
        winchInfoPanel.add(new JButton("Add Winch"));
        winchInfoPanel.add(new JButton("Edit Winch"));
        WinchPanel.add(winchInfoPanel);
        for(Winch winch : currentWinches) {
            winchModel.addElement(winch);
        }
        winchList.setModel(winchModel);
        winchList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(winchList.getSelectedIndex() >= 0){
                    Winch curWinch = (Winch)winchList.getSelectedValue();
                    data.setCurrentWinch(curWinch);
                    winchNameField.setText("Name: " + curWinch.getName());
                    /*
                    winchBrakePressureField.setText("Brake Pressure: " + String.valueOf(curWinch.getBrakePressure()));
                    winchOptDataField.setText("Optional Data: " + curWinch.getOptionalData());
                    */
                    updateDrumList();
                    updateParachuteList();
                }
            }
        });
        winchScroller.setViewportView(winchList);
        
        drumList = new javax.swing.JList();
        JScrollPane drumScroller = new JScrollPane();
        DrumPanel.add(drumScroller);
        JPanel drumInfoPanel = new JPanel();
        drumInfoPanel.setLayout(new BoxLayout(drumInfoPanel, BoxLayout.PAGE_AXIS));
        drumInfoPanel.add(drumNameField);
        drumInfoPanel.add(drumCoreDiameterField);
        drumInfoPanel.add(drumCableLengthField);
        drumInfoPanel.add(drumKFactorField);
        drumInfoPanel.add(drumNumLaunchesField);
        drumInfoPanel.add(new JButton("Add Drum"));
        drumInfoPanel.add(new JButton("Edit Drum"));
        DrumPanel.add(drumInfoPanel);
        drumList.setModel(drumModel);
        drumList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(drumList.getSelectedIndex() >= 0){
                    Drum curDrum = (Drum)drumList.getSelectedValue();
                    drumNameField.setText("Name: " + curDrum.getName());
                    drumCableLengthField.setText("Cable Length: " + String.valueOf(curDrum.getCableLength()));
                    drumCoreDiameterField.setText("Core Diameter: " + String.valueOf(curDrum.getCoreDiameter()));
                    drumKFactorField.setText("K Factor: " + String.valueOf(curDrum.getKFactor()));
                    drumNumLaunchesField.setText("Number Launches: " + String.valueOf(curDrum.getNumLaunches()));
                }
            }
        });
        drumScroller.setViewportView(drumList);

        paraList = new javax.swing.JList();
        JScrollPane paraScroller = new JScrollPane();
        ParachutePanel.add(paraScroller);
        JPanel paraInfoPanel = new JPanel();
        paraInfoPanel.setLayout(new BoxLayout(paraInfoPanel, BoxLayout.PAGE_AXIS));
        paraInfoPanel.add(paraNameField);
        paraInfoPanel.add(paraIDField);
        paraInfoPanel.add(paraDragField);
        paraInfoPanel.add(paraLiftField);
        paraInfoPanel.add(paraWeightField);
        paraInfoPanel.add(new JButton("Add Parachute"));
        paraInfoPanel.add(new JButton("Edit Parachute"));
        ParachutePanel.add(paraInfoPanel);
        paraList.setModel(paraModel);
        paraList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(paraList.getSelectedIndex() >= 0){
                    Parachute curPara = (Parachute)paraList.getSelectedValue();
                    paraNameField.setText("Name: " + curPara.getName());
                    paraIDField.setText("ID: " + curPara.getParachuteId());
                    paraLiftField.setText("Lift: " + curPara.getLift());
                    paraDragField.setText("Drag: " + curPara.getDrag());
                    paraWeightField.setText("Weight: " + curPara.getWeight());
                }
            }
        });
        paraScroller.setViewportView(paraList);
        
        add(WinchPanel);
        add(DrumPanel);
        add(ParachutePanel);
    }
    
    private ArrayList<Winch> getWinchList() {
        ArrayList<Winch> result = new ArrayList();
        Winch testWinch = new Winch();
        result.add(testWinch);
        return result;
    }
}
