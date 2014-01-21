/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ParameterSelection;

import DataObjects.Airfield;
import java.awt.Color;
import javax.swing.DefaultListModel;

/**
 *
 * @author garreola-gutierrez
 */
public class AirfieldSelection extends javax.swing.JPanel {

    /**
     * Creates new form AirfieldSelection
     */
    // Mdl that is first displayed
    DefaultListModel<Airfield> mdl = new DefaultListModel(); 
   
    // Array list of Airfield objects
    Airfield [] airfieldNewList = new Airfield[3];         
    public AirfieldSelection() {
        initComponents();
        
            // For loop to create airfield objects
            for(int i = 0; i < airfieldNewList.length; i++){
            airfieldNewList[i] = new Airfield();
            airfieldNewList[i].setName("name"+ i);
            airfieldNewList[i].setDesignator("designator" +i);
            airfieldNewList[i].setLocation("location" + i);
            airfieldNewList[i].setAltitude("altitude" + i);
            airfieldNewList[i].setMagneticVariation("magnetic Variation"+i);
            airfieldNewList[i].setRunway("runway" + i);
            airfieldNewList[i].setMagneticHeading("Magnetic Heading" + i);
            airfieldNewList[i].setPosition("position" +i);
            airfieldNewList[i].setPositionMaximumLength("position Maximum Length"+i);
            airfieldNewList[i].setPositionSlope("position Slope"+i);
            airfieldNewList[i].setPositionCenterlineOffset("Position CenterlineOffest" +i);
            
            // Adds new created objects to defaultListModel
            mdl.addElement(airfieldNewList[i]);
        }
        // Sets defaultListModel to be the model for jlist 
        airfieldJList.setModel(mdl);
        
        nameJTextField.setEditable(false);
        designatorJTextField.setEditable(false);
        locationJTextField.setEditable(false);
        altitudeJTextField.setEditable(false);
        magneticVariationJTextField.setEditable(false);
        runwayJTextField.setEditable(false);
        magneticHeadingJTextField.setEditable(false);
        positionJTextField.setEditable(false);
        maximumLengthJTextField.setEditable(false);
        slopeJTextField.setEditable(false);
        centerlineOffsetJTextField.setEditable(false);
        
                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        airfieldInputLabel = new javax.swing.JLabel();
        airfieldInputJTextFiedl = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        airfieldJList = new javax.swing.JList();
        centerlineOffsetLabel = new javax.swing.JLabel();
        airfieldNameLabel = new javax.swing.JLabel();
        desgnatorlabel = new javax.swing.JLabel();
        locationlabel = new javax.swing.JLabel();
        Altitudelabel = new javax.swing.JLabel();
        magneticVariationLabel = new javax.swing.JLabel();
        runwayLabel = new javax.swing.JLabel();
        magneticHeading = new javax.swing.JLabel();
        positionLabel = new javax.swing.JLabel();
        slopeLabel = new javax.swing.JLabel();
        maximumLengthLabel = new javax.swing.JLabel();
        nameJTextField = new javax.swing.JTextField();
        designatorJTextField = new javax.swing.JTextField();
        locationJTextField = new javax.swing.JTextField();
        altitudeJTextField = new javax.swing.JTextField();
        magneticVariationJTextField = new javax.swing.JTextField();
        runwayJTextField = new javax.swing.JTextField();
        magneticHeadingJTextField = new javax.swing.JTextField();
        positionJTextField = new javax.swing.JTextField();
        maximumLengthJTextField = new javax.swing.JTextField();
        slopeJTextField = new javax.swing.JTextField();
        centerlineOffsetJTextField = new javax.swing.JTextField();

        airfieldInputLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        airfieldInputLabel.setText("Airfield Name :");
        airfieldInputLabel.setToolTipText("");

        airfieldInputJTextFiedl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                airfieldInputJTextFiedlKeyReleased(evt);
            }
        });

        airfieldJList.setModel(mdl);
        airfieldJList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                airfieldJListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(airfieldJList);

        centerlineOffsetLabel.setText("<html>Centerline <br/> Offset <html/>");

        airfieldNameLabel.setText("Name");

        desgnatorlabel.setText("Designator");

        locationlabel.setText("Location");

        Altitudelabel.setText("Altitude");

        magneticVariationLabel.setText("<html> Magnetic <br/> Variation </html>");
        magneticVariationLabel.setToolTipText("");
        magneticVariationLabel.setAutoscrolls(true);

        runwayLabel.setText("Runway");

        magneticHeading.setText("<html> Magnetic <br/> Heading </html>");

        positionLabel.setText("Position");

        slopeLabel.setText("Slope");

        maximumLengthLabel.setText("<html>Maximum <br/> Length </html>");

        nameJTextField.setBackground(new java.awt.Color(255, 0, 0));

        designatorJTextField.setBackground(new java.awt.Color(255, 0, 0));

        locationJTextField.setBackground(new java.awt.Color(255, 0, 0));

        altitudeJTextField.setBackground(new java.awt.Color(255, 0, 0));

        magneticVariationJTextField.setBackground(new java.awt.Color(255, 0, 0));
        magneticVariationJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magneticVariationJTextFieldActionPerformed(evt);
            }
        });

        runwayJTextField.setBackground(new java.awt.Color(255, 0, 0));

        magneticHeadingJTextField.setBackground(new java.awt.Color(255, 0, 0));

        positionJTextField.setBackground(new java.awt.Color(255, 0, 0));

        maximumLengthJTextField.setBackground(new java.awt.Color(255, 0, 0));

        slopeJTextField.setBackground(new java.awt.Color(255, 0, 0));

        centerlineOffsetJTextField.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(airfieldInputLabel)
                .addGap(10, 10, 10)
                .addComponent(airfieldInputJTextFiedl, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(airfieldNameLabel)
                .addGap(43, 43, 43)
                .addComponent(nameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(runwayLabel)
                .addGap(31, 31, 31)
                .addComponent(runwayJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(desgnatorlabel)
                .addGap(18, 18, 18)
                .addComponent(designatorJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(magneticHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(magneticHeadingJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(locationlabel)
                .addGap(30, 30, 30)
                .addComponent(locationJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(positionLabel)
                .addGap(33, 33, 33)
                .addComponent(positionJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(Altitudelabel)
                .addGap(33, 33, 33)
                .addComponent(altitudeJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(maximumLengthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(maximumLengthJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(magneticVariationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(magneticVariationJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(slopeLabel)
                .addGap(44, 44, 44)
                .addComponent(slopeJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(centerlineOffsetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(centerlineOffsetJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(airfieldInputLabel)
                    .addComponent(airfieldInputJTextFiedl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(airfieldNameLabel)
                    .addComponent(nameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(runwayLabel)
                    .addComponent(runwayJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(desgnatorlabel)
                    .addComponent(designatorJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(magneticHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(magneticHeadingJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(locationlabel)
                    .addComponent(locationJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(positionLabel)
                    .addComponent(positionJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Altitudelabel)
                    .addComponent(altitudeJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maximumLengthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maximumLengthJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(magneticVariationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(magneticVariationJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slopeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slopeJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(centerlineOffsetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(centerlineOffsetJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void airfieldInputJTextFiedlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_airfieldInputJTextFiedlKeyReleased
        // TODO add your handling code here:
        // Creates a string from a input
        String matchstring = airfieldInputJTextFiedl.getText();
       // makes new updated defaultListModel
        DefaultListModel airfieldeModel = new DefaultListModel();
        
        // TODO Change from String specific to type Sailplane
        for(Airfield myObj : airfieldNewList){
            if(myObj.toString().toUpperCase().startsWith(matchstring.toUpperCase()))
                airfieldeModel.addElement(myObj);
        }
        // New updated defaultListModel is set the jlist
        airfieldJList.setModel(airfieldeModel);
    }//GEN-LAST:event_airfieldInputJTextFiedlKeyReleased

    private void magneticVariationJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_magneticVariationJTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_magneticVariationJTextFieldActionPerformed

    private void airfieldJListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_airfieldJListMouseClicked
        // TODO add your handling code here:
        try {
            // do stuff
            // Gets the object is that selected when the button is pushed
            // fills the parts of the object to the right jtextfields
            if(mdl.getSize() > 0){
                Airfield value = (Airfield) airfieldJList.getSelectedValue();
                nameJTextField.setText(value.getName());
                designatorJTextField.setText(value.getDesignator());
                locationJTextField.setText(value.getLocation());
                altitudeJTextField.setText(value.getAltitude());
                magneticVariationJTextField.setText(value.getMagneticVariation());
                runwayJTextField.setText(value.getRunway());
                magneticHeadingJTextField.setText(value.getMagneticHeading());
                positionJTextField.setText(value.getPosition());
                maximumLengthJTextField.setText(value.getPositionMaximumLength());
                slopeJTextField.setText(value.getPositionSlope());
                centerlineOffsetJTextField.setText(value.getPositionCenterlineOffset());
            }
        
            // Checks if jTextfields are empty and changes background 
            // of jtextfield if filled
            if(!(nameJTextField.getText().equals(""))){
                nameJTextField.setBackground(Color.GREEN);       
            }
            if(!(designatorJTextField.getText().equals(""))){
                designatorJTextField.setBackground(Color.GREEN); 
            }
            if(!(locationJTextField.getText().equals(""))){
                locationJTextField.setBackground(Color.GREEN);  
            }
            if(!(altitudeJTextField.getText().equals(""))){
                altitudeJTextField.setBackground(Color.GREEN);
            }
            if(!(magneticVariationJTextField.getText().equals(""))){
                magneticVariationJTextField.setBackground(Color.GREEN);
            }
            if(!(runwayJTextField.getText().equals(""))){
                runwayJTextField.setBackground(Color.GREEN);       
            }
            if(!(magneticHeadingJTextField.getText().equals(""))){
                magneticHeadingJTextField.setBackground(Color.GREEN); 
            }
            if(!(positionJTextField.getText().equals(""))){
                positionJTextField.setBackground(Color.GREEN);
            }
            if(!(maximumLengthJTextField.getText().equals(""))){
                maximumLengthJTextField.setBackground(Color.GREEN);
            }
            if(!(slopeJTextField.getText().equals(""))){
                slopeJTextField.setBackground(Color.GREEN);
            }
            if(!(centerlineOffsetJTextField.getText().equals(""))){
                centerlineOffsetJTextField.setBackground(Color.GREEN);
            }
        }
        catch (Exception e) {
        //TODO decide how to handle exception
        }
    }//GEN-LAST:event_airfieldJListMouseClicked

public Airfield getSeleAirfiield() {
    if(airfieldJList.getSelectedIndex() >= 0)
        return airfieldNewList[airfieldJList.getSelectedIndex()];
    return (null);
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Altitudelabel;
    private javax.swing.JTextField airfieldInputJTextFiedl;
    private javax.swing.JLabel airfieldInputLabel;
    private javax.swing.JList airfieldJList;
    private javax.swing.JLabel airfieldNameLabel;
    private javax.swing.JTextField altitudeJTextField;
    private javax.swing.JTextField centerlineOffsetJTextField;
    private javax.swing.JLabel centerlineOffsetLabel;
    private javax.swing.JLabel desgnatorlabel;
    private javax.swing.JTextField designatorJTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField locationJTextField;
    private javax.swing.JLabel locationlabel;
    private javax.swing.JLabel magneticHeading;
    private javax.swing.JTextField magneticHeadingJTextField;
    private javax.swing.JTextField magneticVariationJTextField;
    private javax.swing.JLabel magneticVariationLabel;
    private javax.swing.JTextField maximumLengthJTextField;
    private javax.swing.JLabel maximumLengthLabel;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JTextField positionJTextField;
    private javax.swing.JLabel positionLabel;
    private javax.swing.JTextField runwayJTextField;
    private javax.swing.JLabel runwayLabel;
    private javax.swing.JTextField slopeJTextField;
    private javax.swing.JLabel slopeLabel;
    // End of variables declaration//GEN-END:variables
}
