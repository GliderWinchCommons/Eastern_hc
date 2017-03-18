package DashboardInterface;

//TODO: remove import *s
import Communications.MessagePipeline;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import Communications.Observer;

/**
 *
 * @author Matt Dargen
 * @modifications: Johnny White
 * @modifications: Jacob Troxel
 */
public class StateMachineDiagram extends javax.swing.JPanel implements Observer {

    JTextField last = new JTextField();
    private FlightDashboard parent;
    private final int width = 400;
    
    public StateMachineDiagram() {
        statePics = new HashMap<>();
        loadPictures();
        initComponents();
        MessagePipeline.getDataRelay();
    }
    
    public void setParent(FlightDashboard f)
    {   
        parent = f;
    }
    
    private ImageIcon getScaledImage(ImageIcon img, int w)
    {
        float ratio = (float)(w) / img.getIconWidth();
        int h = (int) (img.getIconHeight() * ratio);
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img.getImage(), 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }
    
    public void updateState(int state)
    {
        if(state == 0 || state == 14 || state == 6 || state == 7)
        {
            //length shown
            parent.ToggleDial(1);
        }
        else
        {
            parent.ToggleDial(0);
            //tension
        }
        stateLabel.setIcon(statePics.get(state));
    }
    
    private void loadPictures()
    {
        statePics.put(0, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/safe.png")),width));
        statePics.put(1, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/prep.png")),width));
        statePics.put(2, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/armed.png")),width));
        statePics.put(3, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/profile.png")),width));
        statePics.put(4, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/ramp.png")),width));
        statePics.put(5, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/constant.png")),width));
        statePics.put(6, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/recovery.png")),width));
        statePics.put(7, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/retrieve.png")),width));
        statePics.put(14, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/stop.png")),width));
        statePics.put(15, getScaledImage(new ImageIcon(getClass().getResource("/DashboardInterface/images/abort.png")),width));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        stateLabel = new javax.swing.JLabel();
        stateLabel.setIcon(statePics.get(0)); // NOI18N
        add(stateLabel, BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    
    }//GEN-LAST:event_formKeyPressed

    private void jToggleButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseClicked
        //updateState(getRandomState());
    }//GEN-LAST:event_jToggleButton1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel stateLabel;
    private HashMap<Integer,ImageIcon> statePics;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update() {
    }

    @Override
    public void update(String msg) {
        if(!msg.equals(""))
        {
            //System.out.println(msg);
            String mParts[] = msg.split(";");
            switch (mParts[0])
            {
                case "STATE":
                    updateState(Integer.parseInt(mParts[1]));
                    break;
            }
        }
    }
}
