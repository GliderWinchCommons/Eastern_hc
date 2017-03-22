/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DashboardInterface;

import Communications.Observer;
import DataObjects.CurrentDataObjectSet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.color.ColorSpace;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * This UI class implements the graph of relevant data about a launch using the 
 * JFreechart library
 * 
 * @author Alex
 */
public class LaunchGraph extends JPanel implements Observer {
    public HashMap datasetMap = new HashMap();
    
    private final long serialVersionUID = 1L;
    XYSeriesCollection heightDataset = new XYSeriesCollection();
    XYSeriesCollection speedDataset = new XYSeriesCollection();
    XYSeriesCollection tensionDataset = new XYSeriesCollection();
    XYSeries tensionTimeSeries = new XYSeries("Tension");
    XYSeries speedTimeSeries = new XYSeries("Speed");
    XYSeries heightTimeSeries = new XYSeries("Height");
    
    private XYPlot plot;
    
    private long previousTime = 0L;
    private float maxTensionMarker;
    private double currentAngle = 0f;
    private double startTime;
    private double curTime;
    
    private boolean running = false;
    
     public LaunchGraph(String title) {
        if(CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentSailplane() == null)
        {
            maxTensionMarker = 1000;
        }
        else
        {
            // this is not being converted -- must happen before added back in...
            maxTensionMarker = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentSailplane().getMaxTension();
        }
        setBackground(Color.WHITE);
        ChartPanel chartPanel = (ChartPanel) createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 270));
        add(chartPanel);
        //Thread t = new Thread(this);
        //t.start();
    }
     
     /**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     *
     * @return A chart.
     */
    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Speed, Height and Tension v Time:",  // title
            "Time",             // x-axis label
            "Speed",   // y-axis label
            null,            // data
            PlotOrientation.VERTICAL,
            true,               // create legend?
            true,               // generate tooltips?
            false               // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);

        plot = chart.getXYPlot();        
        XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
        XYSplineRenderer splinerenderer3 = new XYSplineRenderer();
        

        XYDataset dataset1 = createDataset(0L, 130000);
        plot.setDataset(0,dataset1);
        plot.setRenderer(0,splinerenderer1);
        plot.getRenderer().setSeriesPaint(0, Color.MAGENTA);
        NumberAxis domainAxis = new NumberAxis("Time (Seconds from Start of Launch)");
        plot.setDomainAxis(domainAxis);
        //domainAxis.setRange(0, 30);
        NumberAxis heightYAxis = new NumberAxis("Height");
        heightYAxis.setRange(0, 1050);
        plot.setRangeAxis(heightYAxis);
        datasetMap.put("HEIGHT", dataset1);
    
        XYDataset dataset2 = createDataset2();
        plot.setDataset(1, dataset2);
        plot.setRenderer(1, splinerenderer2);
        plot.getRenderer().setSeriesPaint(1, Color.BLUE);
        NumberAxis speedYAxis = new NumberAxis("Speed");
        speedYAxis.setRange(0, 50);
        plot.setRangeAxis(1, speedYAxis);
        datasetMap.put("SPEED", dataset2);
    
        XYDataset dataset3 = createDataset3();
        plot.setDataset(2, dataset3);
        plot.setRenderer(2, splinerenderer3);
        NumberAxis tensionYAxis = new NumberAxis("Tension");
        plot.getRenderer().setSeriesPaint(2, Color.RED);
        tensionYAxis.setRange(0, 8000);
        plot.setRangeAxis(2, tensionYAxis);
        datasetMap.put("TENSION", dataset3);

        plot.mapDatasetToRangeAxis(0, 0);//1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); //2nd dataset to 2nd y-axis
        plot.mapDatasetToRangeAxis(2, 2);
    
        //float[] dashArray = {1,1,1,0};
        //plot.addRangeMarker(new ValueMarker(maxTensionMarker, Color.YELLOW, new BasicStroke(1, 0, 0, 1, dashArray, 0)));
        //XYTextAnnotation text = new XYTextAnnotation("Max Tension", 10, maxTensionMarker);
        //text.setFont(new Font("SansSerif", Font.PLAIN, 9));
        //plot.addAnnotation(text);
        
                
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(false);
        renderer.setBaseShapesFilled(false);
        
        XYLineAndShapeRenderer renderer2 = (XYLineAndShapeRenderer) plot.getRendererForDataset(speedDataset);
        renderer2.setBaseShapesVisible(false);
        renderer2.setBaseShapesFilled(false);
        
        XYLineAndShapeRenderer renderer3 = (XYLineAndShapeRenderer) plot.getRendererForDataset(tensionDataset);
        renderer3.setBaseShapesVisible(false);
        renderer3.setBaseShapesFilled(false);
        
        return chart;
    }
    
    /**
     * Adds a new vertical blue marker to the graph at the change of states
     * 
     * @param state the new state the launch has transitioned into
     */
    public void addStateMarker(int state, double time) {
        plot.addDomainMarker(new ValueMarker(time, Color.BLUE, new BasicStroke((float) 2.5)));
        switch(state) {
            case 2:
                XYTextAnnotation text = new XYTextAnnotation("Armed", time, 500);
                text.setFont(new Font("SansSerif", Font.PLAIN, 9));
                plot.addAnnotation(text);
                break;
            case 3:
                XYTextAnnotation text2 = new XYTextAnnotation("Profile", time, 510);
                text2.setFont(new Font("SansSerif", Font.PLAIN, 9));
                plot.addAnnotation(text2);
                break;
            case 4:
                XYTextAnnotation text3 = new XYTextAnnotation("Ramp", time, 520);
                text3.setFont(new Font("SansSerif", Font.PLAIN, 9));
                plot.addAnnotation(text3);
                break;
            case 5:
                XYTextAnnotation text4 = new XYTextAnnotation("Constant", time, 530);
                text4.setFont(new Font("SansSerif", Font.PLAIN, 9));
                plot.addAnnotation(text4);
                break;
            case 6:
                XYTextAnnotation text5 = new XYTextAnnotation("Recovery", time, 540);
                text5.setFont(new Font("SansSerif", Font.PLAIN, 9));
                plot.addAnnotation(text5);
                break;
            case 7:
                XYTextAnnotation text6 = new XYTextAnnotation("Retrieve", time, 550);
                text6.setFont(new Font("SansSerif", Font.PLAIN, 9));
                plot.addAnnotation(text6);
                break;
        }
    }
    
    /**
     * Creates a dataset for height values 
     *
     * @return The dataset.
     */
    private XYDataset createDataset(long startTimeMili, int maxOffset) {
        heightDataset.addSeries(heightTimeSeries);
        return heightDataset;
    }
    
    /**
     * Creates a dataset for speed values 
     *
     * @return The dataset.
     */
    private XYDataset createDataset2() {
        speedDataset.addSeries(speedTimeSeries);
        return speedDataset;

    }
    
    /**
     * Creates a dataset for tension values 
     *
     * @return The dataset.
     */
    private XYDataset createDataset3 () {
        tensionDataset.addSeries(tensionTimeSeries);
        return tensionDataset;
    }
    
    private XYDataset createDataset4(int offset) {
        long dateMili = 0L;

        TimeSeries s1 = new TimeSeries("");
        s1.add(new Second(new Date(dateMili)), 110);
        s1.add(new Second(new Date(dateMili + offset)), 110);        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);

        return dataset;

    }
    
    /**
     * Adds a new height value to the height dataset
     * 
     * @param time the time associated with the value on the graph
     * @param value the height value to be graphed
     */
    public void addHeightValue(double time, float value) {
        heightTimeSeries.addOrUpdate(time , value);
    }
    
    /**
     * Adds a new tension value to the tension dataset
     * 
     * @param time the time associated with the value on the graph
     * @param value the tension value to be graphed
     */
    public void addTensionValue(double time, float value) {
        tensionTimeSeries.addOrUpdate(time , value);
    }
    
    /**
     * Adds a new speed value to the height dataset
     * 
     * @param time the time associated with the value on the graph
     * @param value the speed value to be graphed
     */
    public void addSpeedValue(double time, float value) {
        try{
            previousTime += time;
            speedTimeSeries.addOrUpdate(time , value);
        } catch(SeriesException e) {
            
        }
    }
    
    public void addDataset(String title, int index, int range) {
        XYSplineRenderer render = new XYSplineRenderer();
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries(title);
        dataset.addSeries(series);
        XYDataset xyDataset = dataset;
        
        plot.setDataset(index, xyDataset);
        plot.setRenderer(index, render);
        
        NumberAxis axis = new NumberAxis(title);
        axis.setRange(0, range);
        
        plot.setRangeAxis(index, axis);
        plot.mapDatasetToRangeAxis(index, index);
        
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRendererForDataset(xyDataset);
        renderer.setBaseShapesVisible(false);
        renderer.setBaseShapesFilled(false);
    }
    
    public void addDataPoint(long time, float value, TimeSeries series) {
        series.addOrUpdate(new Millisecond(new Date(previousTime)), value);
    }
    
    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(null);
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(false);
        panel.setMouseWheelEnabled(false);
        panel.setAutoscrolls(false);
        panel.setDomainZoomable(false);
        panel.setFocusable(false);
        return panel;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void update(String msg) {
        String[] dataPoint = msg.split(";");
        switch(dataPoint[0]) {
            case "STATE":
                //addStateMarker(Integer.parseInt(dataPoint[1]), curTime);
                if(dataPoint[1].equals("3"))
                {
                    startTime = Double.parseDouble(dataPoint[2]);
                    curTime = startTime;
                    running = true;
                }
                if(dataPoint[1].equals("1") && running)
                {
                    running = false;
                }  
                break;
            case "TENSION":
                curTime += Double.parseDouble(dataPoint[2]);
                if(running) addTensionValue(curTime - startTime, Float.parseFloat(dataPoint[1]));
                break;
            case "SPEED":
                if(running) addSpeedValue(curTime - startTime, Float.parseFloat(dataPoint[1]));
                break;
            case "OUT":
                float outLength = Float.parseFloat(dataPoint[1]);
                float height = (float)(outLength * Math.sin(Math.toRadians(currentAngle)));
                if(running) addHeightValue(curTime - startTime, height);
                break;
            case "ANGLE":
                currentAngle = Double.parseDouble(dataPoint[1]);
                break;
        }
        
        
    }
}
