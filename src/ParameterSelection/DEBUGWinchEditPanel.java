package ParameterSelection;

import Communications.Observer;
import DataObjects.Drive;
import DataObjects.Drum;
import DataObjects.Parachute;
import DataObjects.Winch;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import DataObjects.CurrentDataObjectSet;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

/**
 *
 * @author jtroxel
 */
public class DEBUGWinchEditPanel extends JPanel implements Observer {
    
    private Winch DEBUGWinch;
    private JPanel DEBUGWinchPanel;
    private JPanel DEBUGMainPanel;
    private int selected;
    private CurrentDataObjectSet data;
    private JPanel buttonPanel;
    
    public DEBUGWinchEditPanel(ParameterSelectionPanel p) {
        //CurrentDataObjectSet.getCurrentDataObjectSet().attach(this);
        setBackground(Color.WHITE);
        selected = 0;
        //initDEBUGWinch();
        initComponents();
        JScrollPane scrollPane = new JScrollPane(DEBUGMainPanel);
        scrollPane.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
                
        data = CurrentDataObjectSet.getCurrentDataObjectSet();
        data.setCurrentWinch(DEBUGWinch);
    }
    
    public void setParamPanel(ParameterSelectionPanel p)
    {
    }
    
    private void buildWinchPanel(Winch winch, boolean isSelected) {
        if(winch == null) return;
        DEBUGWinchPanel = new JPanel();
        DEBUGWinchPanel.setBackground(Color.WHITE);

        JPanel WinchPanel = new JPanel();
        WinchPanel.setLayout(new BoxLayout(WinchPanel, BoxLayout.Y_AXIS));
        JLabel winchName = new JLabel("WINCH: " + winch.getName());
        winchName.setAlignmentX(Component.CENTER_ALIGNMENT);
        WinchPanel.add(winchName);
        /*
        for(Drum d : winch.getDrumsForDrive("Drive 1")) {
            JPanel drum = new JPanel();
            JLabel drumName = new JLabel("DRUM: " + d.toString());
            drum.add(drumName);
            WinchPanel.add(drum);
        }
        
        for(Parachute p : winch.getParachuteList()) {
            JPanel para = new JPanel();
            JLabel paraName = new JLabel("PARACHUTE: " + p.getName());
            para.add(paraName);
            WinchPanel.add(para);
        }
        */
        
        DEBUGWinchPanel.add(WinchPanel);
        if(isSelected) DEBUGWinchPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        else DEBUGWinchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        DEBUGMainPanel.add(DEBUGWinchPanel);
    }
    
    private void initComponents() {
        DEBUGMainPanel = new JPanel();
        DEBUGMainPanel.setLayout(new BoxLayout(DEBUGMainPanel, BoxLayout.Y_AXIS));
        DEBUGMainPanel.setBackground(Color.WHITE);
        buttonPanel = new JPanel();
        JButton loadNewWinchButton = new JButton("Load Winch Config File");
        loadNewWinchButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Winch Config");
                chooser.setApproveButtonText("Select");
                String filePath;
                String fileName;
                int option = chooser.showOpenDialog(DEBUGWinchEditPanel.this);
                if(option == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    File chosen = chooser.getCurrentDirectory();
                    filePath = chosen.getPath();
                    fileName = file.getName();
                    loadWinch(filePath + "\\" + fileName);
                }
            }
        });
        buttonPanel.setBackground(Color.WHITE);
        loadNewWinchButton.setBackground(new Color(200,200,200));
        buttonPanel.add(loadNewWinchButton);
        buttonPanel.setMaximumSize(new Dimension(200, 40));
        DEBUGMainPanel.add(buttonPanel);
        
        buildWinchPanel(DEBUGWinch, true);
        /*for(int i = 0; i < 7; ++i) {
            if(i == selected) buildWinchPanel(DEBUGWinch, true);
            else buildWinchPanel(DEBUGWinch, false);
        }*/
    }
    
    private void loadWinch(String filepath)
    {
        DEBUGWinch = loadWinchFile(filepath);
        CurrentDataObjectSet.getCurrentDataObjectSet().setCurrentWinch(DEBUGWinch);
        CurrentDataObjectSet.getCurrentDataObjectSet().forceUpdate();
        DEBUGMainPanel.removeAll();
        DEBUGMainPanel.add(buttonPanel);
        buildWinchPanel(DEBUGWinch, true);
    }
    
    private Winch loadWinchFile(String file)
    {
        ArrayList<String> lines = new ArrayList<>();

        Winch loadedWinch = new Winch();
        Drive curDrive = new Drive();
        Drum curDrum = new Drum();
        Parachute curPara = new Parachute();
        
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            try {
                while ((line = br.readLine()) != null)
                {
                    lines.add(line);
                }
            } catch (IOException ex) {
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
        
        for(String str : lines)
        {
            String parts[] = str.split(":");
            String name = parts[0].trim();
            
            switch(name)
            {
                case "winch-name":
                    loadedWinch.setName(parts[1]);
                    break;
                case "winch-id":
                    //loadedWinch.setId(parts[1]);
                    break;
                case "winch-optional-data":
                    //loadedWinch.setOptionalData(parts[1]);
                    break;
                case "winch-brake-pressure":
                    //loadedWinch.setBrakePressure(Float.parseFloat(parts[1]));
                    break;
                case "drive":
                    curDrive = new Drive();
                    break;
                case "drive-name":
                    curDrive.setName(parts[1]);
                    break;
                case "drive-reduction-ratio":
                    curDrive.setReductionRatio(Float.parseFloat(parts[1]));
                    break;
                case "end-drive":
                    //loadedWinch.addDrive(curDrive);
                    break;
                case "drum":
                    curDrum = new Drum();
                    curDrum.setDrive(curDrive);
                    break;
                case "drum-name":
                    curDrum.setName(parts[1]);
                    break;
                case "drum-core-diameter":
                    curDrum.setCoreDiameter(Float.parseFloat(parts[1]));
                    break;
                case "drum-k-factor":
                    curDrum.setKFactor(Float.parseFloat(parts[1]));
                    break;
                case "drum-cable-length":
                    curDrum.setCableLength(Float.parseFloat(parts[1]));
                    break;
                case "end-drum":
                    curDrive.addDrum(curDrum);
                    break;
                case "parachute":
                    curPara = new Parachute();
                    break;
                case "parachute-name":
                    curPara.setName(parts[1]);
                    break;
                case "parachute-number":
                    curPara.setParachuteId(Integer.parseInt(parts[1]));
                    break;
                case "parachute-lift":
                    curPara.setLift(Float.parseFloat(parts[1]));
                    break;
                case "parachute-drag":
                    curPara.setDrag(Float.parseFloat(parts[1]));
                    break;
                case "parachute-weight":
                    curPara.setWeight(Float.parseFloat(parts[1]));
                    break;
                case "end-parachute":
                    //loadedWinch.addParachute(curPara);
                    break;
            }
        }
        return loadedWinch;
    }

    @Override
    public void update() {
    }

    @Override
    public void update(String msg) {
    }
}
