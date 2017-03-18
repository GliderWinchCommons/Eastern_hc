package DashboardInterface;

import Communications.MessagePipeline;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author jtroxel
 */
public class FlightDashboard extends javax.swing.JPanel
{
    private CableOutSpeedDial cableOutSpeedDial;
    private TensionSpeedDial tensionSpeedDial;
    private SystemsStatus health;
    private LaunchGraph graph;
    private JPanel contentPane;
    private GroupLayout layout;
    private JPanel graphPane;
    private JPanel dialSquare1;
    private JPanel dialSquare2;
    private JPanel dialPane;
    private JPanel systemPane;
    private StateMachineDiagram diagramPane;
    
    public void ToggleDial(int dialState)
    {
        if(dialState == 0)
        {
            dialSquare1.setVisible(false);
            dialSquare2.setVisible(true);
        }
        
        else if(dialState == 1)
        {
            dialSquare1.setVisible(true);
            dialSquare2.setVisible(false);
        }
    }
    
    public FlightDashboard()
    {
        //initComponents();
    }
    
    private void initComponents()
    {       
        /*contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        graphPane = new javax.swing.JPanel();
        dialPane = new JPanel();
        this.setBackground(Color.WHITE);
        
        dialPane.setLayout(new GridBagLayout());
        
        systemPane = new JPanel();
        diagramPane = new StateMachineDiagram();
        diagramPane.setParent(this);
        diagramPane.setBackground(Color.WHITE);
        //diagramPane.setPreferredSize(new Dimension(400,100));
        
        dialSquare1 = new JPanel(new BorderLayout()) {
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w>h ? h : w);
                //System.out.println(s);
                return new Dimension(s,s);
            }
        };
        
        cableOutSpeedDial = new CableOutSpeedDial(dialSquare1);
        //we want to listen for speed and cable out
        MessagePipeline.getInstance();
        MessagePipeline.getDataRelay().attach("OUT", cableOutSpeedDial);
        MessagePipeline.getDataRelay().attach("SPEED", cableOutSpeedDial);
        MessagePipeline.getDataRelay().attach("STATE", diagramPane);

        dialSquare1.add(cableOutSpeedDial, BorderLayout.CENTER);
        
        dialSquare2 = new JPanel(new BorderLayout()) {
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w>h ? h : w);
                //System.out.println(s);
                return new Dimension(s,s);
            }
        };
        
        ToggleDial(1);
        
        tensionSpeedDial = new TensionSpeedDial(dialSquare2);
        //we want to listen for speed and tension
        MessagePipeline.getInstance();
        MessagePipeline.getDataRelay().attach("TENSION", tensionSpeedDial);
        MessagePipeline.getDataRelay().attach("SPEED", tensionSpeedDial);
        MessagePipeline.getDataRelay().attach("STATE", diagramPane);

        dialSquare2.add(tensionSpeedDial, BorderLayout.CENTER);
        
        health = new SystemsStatus();
        health.setPreferredSize(new Dimension(400, 300));
        graph = new LaunchGraph("title");
        
        MessagePipeline.getDataRelay().attach("SPEED", graph);
        MessagePipeline.getDataRelay().attach("TENSION", graph);
        MessagePipeline.getDataRelay().attach("OUT", graph);
        MessagePipeline.getDataRelay().attach("ANGLE", graph);
        MessagePipeline.getDataRelay().attach("STATE", graph);
                
        graphPane.add(graph);
        dialPane.add(dialSquare1);
        dialPane.add(dialSquare2);
        dialPane.setBackground(Color.WHITE);
        systemPane.add(health);
        systemPane.setBackground(Color.WHITE);
        graphPane.setBackground(Color.WHITE);
        layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphPane)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(systemPane)
                            .addComponent(diagramPane))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dialPane)))
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dialPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(diagramPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(systemPane)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(graphPane)
                .addContainerGap())
        );
        
        this.add(contentPane);
*/
    }
}
