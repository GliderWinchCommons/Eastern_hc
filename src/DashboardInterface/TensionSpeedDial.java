/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DashboardInterface;

import Communications.MessagePipeline;
import Communications.Observer;
import java.awt.Dimension;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

/**
 * This UI class implements the dial to display a live readout of tension and speed
 * 
 * @author Alex Williams
 */

public class TensionSpeedDial extends JPanel implements Observer {
    DefaultValueDataset dataset1;
    DefaultValueDataset dataset2;
    JSlider slider1;
    JSlider slider2;
    private MessagePipeline pipe;
    JPanel parent;

    private void updateTension(double tension)
    {
        tension = tension * 0.224808943;
        dataset2.setValue(tension);
    }

    private void updateSpeed(double speed)
    {
        speed = speed * 1.94384449;
        dataset1.setValue(speed);
    }

    public TensionSpeedDial(JPanel parentIn)
    {
        super(new BorderLayout());
        parent = parentIn;
        dataset1 = new DefaultValueDataset(0D);
        dataset2 = new DefaultValueDataset(0D);
        pipe = MessagePipeline.getInstance();
        pipe.attach(this);
        DialPlot dialplot = new DialPlot();
        dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
        dialplot.setDataset(0, dataset1);
        dialplot.setDataset(1, dataset2);
        setBackground(Color.WHITE);        
        StandardDialFrame standarddialframe = new StandardDialFrame();
        standarddialframe.setBackgroundPaint(Color.lightGray);
        standarddialframe.setForegroundPaint(Color.darkGray);

        dialplot.setDialFrame(standarddialframe);

        DialBackground dialbackground = new DialBackground(Color.LIGHT_GRAY);

        dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        dialplot.setBackground(dialbackground);

        DialTextAnnotation dialtextannotation = new DialTextAnnotation("Tension (lbf)");
        dialtextannotation.setFont(new Font("Dialog", 1, 12));
        dialtextannotation.setPaint(Color.RED);
        dialtextannotation.setRadius(0.47999999999999996D);
        dialplot.addLayer(dialtextannotation);

        DialTextAnnotation dialtextannotation2 = new DialTextAnnotation("Speed (kts)");
        dialtextannotation2.setFont(new Font("Dialog", 1, 12));
        dialtextannotation2.setPaint(Color.BLUE);
        dialtextannotation2.setRadius(0.78999999999999996D);
        dialplot.addLayer(dialtextannotation2);

        /*DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
        dialvalueindicator.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator.setOutlinePaint(Color.BLACK);
        dialvalueindicator.setRadius(0.84999999999999998D);
        dialvalueindicator.setAngle(-90D);
        dialplot.addLayer(dialvalueindicator);

        DialValueIndicator dialvalueindicator1 = new DialValueIndicator(1);
        dialvalueindicator1.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator1.setOutlinePaint(Color.BLACK);
        dialvalueindicator1.setRadius(0.60999999999999998D);
        dialvalueindicator1.setAngle(-90D);
        dialplot.addLayer(dialvalueindicator1);*/

        StandardDialScale standarddialscale = new StandardDialScale(0D, 90D, -120D, -300D, 10D, 4);
        standarddialscale.setTickRadius(0.88D);
        standarddialscale.setTickLabelOffset(0.14999999999999999D);
        standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
        standarddialscale.setTickLabelPaint(Color.BLUE);
        standarddialscale.setMajorTickPaint(Color.BLUE);
        standarddialscale.setMinorTickPaint(Color.BLUE);
        dialplot.addScale(0, standarddialscale);

        StandardDialScale standarddialscale1 = new StandardDialScale(0.0D, 2500D, -120D, -300D, 500D, 4);
        standarddialscale1.setTickRadius(0.5D);
        standarddialscale1.setTickLabelOffset(0.14999999999999999D);
        standarddialscale1.setTickLabelFont(new Font("Dialog", 0, 10));
        standarddialscale1.setTickLabelPaint(Color.RED);
        standarddialscale1.setMajorTickPaint(Color.RED);
        standarddialscale1.setMinorTickPaint(Color.RED);
        dialplot.addScale(1, standarddialscale1);

        dialplot.mapDatasetToScale(1, 1);

        org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
        pointer.setFillPaint(Color.BLUE);
        dialplot.addPointer(pointer);

        
        org.jfree.chart.plot.dial.DialPointer.Pointer pin = new org.jfree.chart.plot.dial.DialPointer.Pointer(1);
        pin.setRadius(0.55000000000000004D);
        pin.setFillPaint(Color.RED);
        dialplot.addPointer(pin);

        DialCap dialcap = new DialCap();
        dialcap.setRadius(0.10000000000000001D);
        dialplot.setCap(dialcap);

        Dimension size = parent.getBounds().getSize();
        int width = parent.getWidth();
        int height = parent.getHeight();

        width = 200;

        JFreeChart jfreechart = new JFreeChart(dialplot);
        jfreechart.setBackgroundPaint(Color.WHITE);
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartpanel.setPreferredSize(new Dimension(width, width));

        add(chartpanel);
    }
        
    @Override
    public void update() {}
    
    @Override
    public void update(String msg) {
        if(!msg.equals(""))
        {
            String mParts[] = msg.split(";");
            switch (mParts[0])
            {
                case "TENSION":
                    updateTension(Double.parseDouble(mParts[1]));
                    break;
                case "SPEED":
                    updateSpeed(Double.parseDouble(mParts[1]));
                    break;
            }
        }
    }
    
}
