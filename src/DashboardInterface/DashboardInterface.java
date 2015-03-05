package DashboardInterface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import Communications.InternalMessage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

/**
 *
 * @author garreola-gutierrez
 */
public class DashboardInterface extends javax.swing.JFrame {
    private int launchState = 0;

    /**
     * Creates new form ParameterSelection
     */
    public DashboardInterface() {
        initComponents();
       // StateMachinePanel machinePanel = new StateMachinePanel();
       // jPanel1.add(machinePanel);
        int newSpeed = 0;
        int newTension = 0;
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        systemsStatus1 = new SystemsStatus();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tensionGraphPanel1 = new TensionGraphPanel();
        stateMachinePanel12 = new StateMachinePanel();
        dial = new TensionSpeedDial();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        systemsStatus1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                systemsStatus1MouseClicked(evt);
            }
        });

        /*jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "tension", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });*/
        dial.setVisible(true);
        
        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("environmental variables");

        jLabel2.setText("Current Tension:");

        
        getContentPane().setBackground(Color.WHITE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(systemsStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(stateMachinePanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(dial, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tensionGraphPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(systemsStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2)
                        .addGap(2, 2, 2)
                        .addComponent(dial, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(stateMachinePanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(tensionGraphPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(16, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void systemsStatus1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_systemsStatus1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_systemsStatus1MouseClicked

   /* private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        DialTest dial = new DialTest("Tension");
        dial.setSize(new Dimension(300,300));
        dial.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dial.setLocation(880,50);
        dial.setVisible(true);
        
    }//GEN-LAST:event_jList1ValueChanged
    */
    
    public void updateDisplay(InternalMessage internalMsg) {
        dial.dialUpdate(internalMsg.getTension(), internalMsg.getCableSpeed());
        float height = (float)(internalMsg.getCableOut() * Math.sin(internalMsg.getCableAngle()));
        // long time = (long) internalMsg.getTimestamp() * 1000;
        long time = (long) (internalMsg.getElaspedTime() * -1000);
        //System.out.println(internalMsg.getElaspedTime());
        tensionGraphPanel1.addSpeedValue(time, internalMsg.getCableSpeed());
        tensionGraphPanel1.addHeightValue(time, height);
        tensionGraphPanel1.addTensionValue(time, internalMsg.getTension());
        
        if(launchState != internalMsg.getState()) {
            launchState = internalMsg.getState();
            stateMachinePanel12.updateState(launchState);
            tensionGraphPanel1.addStateMarker(launchState);
        }
    }

    public void markEndLaunch() {
        tensionGraphPanel1.addStateMarker(7);
        stateMachinePanel12.updateState(7);
    }
    
    public void resetState() {
        launchState = 0;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private StateMachinePanel stateMachinePanel12;
    private SystemsStatus systemsStatus1;
    private TensionGraphPanel tensionGraphPanel1;
    private TensionSpeedDial dial;
    // End of variables declaration//GEN-END:variables
}
