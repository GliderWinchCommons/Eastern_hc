/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configuration;

import DatabaseUtilities.DatabaseImporter;
import ParameterSelection.ParameterSelectionPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author dbennett3
 */
public class DatabaseImportFrame extends javax.swing.JFrame {

    private javax.swing.JPanel contentPane;
    private javax.swing.JButton ImportSubmitButton;
    private javax.swing.JCheckBox SelectAllCheck;
    private javax.swing.JScrollPane TableListPanel;
    private javax.swing.JList TableList;
    private List<String> names = new ArrayList<>();
    private List<String> fileNames = new ArrayList<>();
    private File file;
    private ParameterSelectionPanel selectionPanel;
    
    /**
     * Creates new form DatabaseExportFrame
     */
    public DatabaseImportFrame(File zipName, ParameterSelectionPanel psp) throws IOException {
        this.file = zipName;
        this.selectionPanel = psp;
        initTableList();
        initComponents();
    }
    
    private void initTableList() throws IOException
    {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(file));
        for(ZipEntry e; (e = zin.getNextEntry()) != null;) {
            String fileName = e.toString();
            if(fileName.contains("_")) {
                fileNames.add(fileName);
                names.add(GetNameFromFile(fileName));
            }
        }
    }
    
    private String GetNameFromFile(String s)
    {
        String[] split = s.split("_");
        return split[1];
    }
    
    private JFrame getFrame()
    {
        return this;
    }
    
    
    private void initComponents() {
        setTitle("Import tables");
        TableListPanel = new javax.swing.JScrollPane();
        TableList = new javax.swing.JList();
        ImportSubmitButton = new javax.swing.JButton();
        SelectAllCheck = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(100, 100, 600, 400));
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        
        TableList.setPreferredSize(new Dimension(500,300));
        DefaultListModel tableModel = new DefaultListModel();
        for(Object str : names) {
            tableModel.addElement(str);
        }
        TableList.setModel(tableModel);
        TableListPanel.setViewportView(TableList);
        
        contentPane.add(TableList, BorderLayout.NORTH);
        
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        JButton submitButton = new JButton("Import");
        submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        
                        List<String> selectedTables = new ArrayList<String>();
                        List<String> orderedTables = new ArrayList<String>();
                        
                        int[] selectedIndices;
                        selectedIndices = TableList.getSelectedIndices();
                                               
                        for(int i : selectedIndices) {
                            selectedTables.add(fileNames.get(i));
                        }
                        
                        orderedTables = orderList(selectedTables);
                        //System.out.println(orderedTables.toString());
                        
                        try{
                            DatabaseImporter.importDatabase(file, selectedTables);
                            //Done twice for possible foreign key contraints. Ugly solution
                            //DatabaseImporter.importDatabase(file, orderedTables);
                            selectionPanel.update();
                            getFrame().dispose();
                        }catch(Exception e)
                        {
                            logError(e);
                            JOptionPane.showMessageDialog(rootPane, "Couldn't import", "Error", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }  

            private void logError(Exception e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
                                         
                });
        panel.add(submitButton);
        
        JCheckBox selectAll = new JCheckBox();
        selectAll.setLabel("Select all");
        selectAll.addItemListener(new ItemListener() 
            {
                @Override
                public void itemStateChanged(ItemEvent e)
                {
                    if(e.getStateChange() == ItemEvent.SELECTED)
                    {
                        int[] indices = new int[names.size()];
                        for(int iter = 0; iter < names.size(); iter++)
                        {
                            indices[iter] = iter;
                        }
                        TableList.setSelectedIndices(indices);
                        TableList.setEnabled(false);
                    } 
                    else
                    {
                        TableList.clearSelection();
                        TableList.setEnabled(true);
                    }
                }
            });
        panel.add(selectAll);
    }

    public List<String> orderList(List<String> listStr) {
        List<String> orderedList = new ArrayList<>();
        
        for(String s : listStr) {
            if(s.contains("AIRFIELD")) {
                orderedList.add(s);
            }
        }
        
        for(String s : listStr) {
            if(s.contains("RUNWAY")) {
                orderedList.add(s);
            }
        }
        
        for(String s : listStr) { 
            if( !(s.contains("AIRFIELD") || s.contains("RUNWAY")) ) {
                orderedList.add(s);
            }
        }
        
        System.out.println(orderedList.toString());
        
        return orderedList;
    }

}
