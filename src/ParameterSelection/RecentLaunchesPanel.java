package ParameterSelection;

/**
 *
 * @author Noah Fujioka
 */

import DataObjects.FlightSummary;
import DataObjects.CurrentDataObjectSet;
import Communications.Observer;
import DatabaseUtilities.DatabaseEntrySelect;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RecentLaunchesPanel extends javax.swing.JPanel implements Observer {

    private List<FlightSummary> recentFlights;
    JScrollPane RecentLaunchScrollPane;
    private javax.swing.JList RecentLaunchJList;
    private JLabel listTitle;
    private ParameterSelectionPanel parameterSelectionPanel;
    
    public RecentLaunchesPanel(ParameterSelectionPanel parameterSelectionPanel) {
        this.parameterSelectionPanel = parameterSelectionPanel;
        recentFlights = new ArrayList<FlightSummary>();
        RecentLaunchScrollPane = new JScrollPane();
        RecentLaunchJList = new javax.swing.JList();
        listTitle = new JLabel("Replay List");
        listTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
        
        this.setLayout(new BorderLayout(0, 0));
        this.setPreferredSize(new Dimension(200,400));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.WHITE);
        this.add(listTitle, BorderLayout.NORTH);
        this.add(RecentLaunchScrollPane, BorderLayout.CENTER);

        recentFlights = DatabaseEntrySelect.getFlights();
        
        DefaultListModel recentFlightsModel = new DefaultListModel();
        for(Object str: recentFlights){
                recentFlightsModel.addElement(str);
        }
        RecentLaunchJList.setModel(recentFlightsModel);
        RecentLaunchJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                recentLaunchJListSelectionChanged(listSelectionEvent);
            }
        });
        RecentLaunchJList.setSelectedIndex(-1);
        RecentLaunchScrollPane.setViewportView(RecentLaunchJList);
        RecentLaunchScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        RecentLaunchScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
    }                      

    public void update() {
        DefaultListModel recentFlightsModel = new DefaultListModel();
        recentFlightsModel.clear();
        for(Object str: recentFlights){
                recentFlightsModel.addElement(str);
        }
        RecentLaunchJList.setModel(recentFlightsModel);
        RecentLaunchScrollPane.setViewportView(RecentLaunchJList);
    }
    
    public void update(String s){}
    
    private void recentLaunchJListSelectionChanged(ListSelectionEvent listSelectionEvent){
        if(RecentLaunchJList.getSelectedIndex() >= 0){
            try{
                FlightSummary theFlight = (FlightSummary) RecentLaunchJList.getSelectedValue();
                theFlight.setCurrentDataObjectSet();
                parameterSelectionPanel.update();
            }catch(Exception e){
            }
        }
    }
}
