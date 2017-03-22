package Communications;

import java.util.ArrayList;
import EnvironmentalWidgets.CurrentWidgetDataSet;
import java.sql.SQLException;

public class DataRelay
{
    private ArrayList<Observer> TensionListeners;
    private ArrayList<Observer> TorqueListeners;
    private ArrayList<Observer> CableSpeedListeners;
    private ArrayList<Observer> CableAngleListeners;
    private ArrayList<Observer> CableOutListeners;    
    private ArrayList<Observer> StateListeners;
    private boolean newLaunch;
    private boolean readyForLaunch;
    private MessageListener parent;
            
    public DataRelay()
    {
        newLaunch = true;
        TensionListeners = new ArrayList<>();
        TorqueListeners = new ArrayList<>();
        CableSpeedListeners = new ArrayList<>();
        CableAngleListeners = new ArrayList<>();
        CableOutListeners = new ArrayList<>();
        StateListeners = new ArrayList<>();
    }
    
    public void setParent(MessageListener ml)
    {
        parent = ml;        
    }
    
    public void sendLaunchParameterRequest()
    {
        if(newLaunch)
        {
            newLaunch = false;
            readyForLaunch = parent.calculateLaunchParameters();
            if(!readyForLaunch) newLaunch = true;
        }
        //write that data out
        parent.sendLaunchParameters(readyForLaunch);
    }
    
    public void setNewLaunch(boolean nl)
    {
        newLaunch = nl;
    }
    
    public void attach(String type, Observer o)
    {
        switch(type)
        {
            case "TENSION":
                TensionListeners.add(o);
                break;
            case "SPEED":
                CableSpeedListeners.add(o);
                break;
            case "TORQUE":
                TorqueListeners.add(o);
                break;
            case "ANGLE":
                CableAngleListeners.add(o);
                break;
            case "OUT":
                CableOutListeners.add(o);
                break;
            case "STATE":
                StateListeners.add(o);
                break;
        }
    }

    public void sendTimeMessage(){}

    public void sendState(int currentState, int activeDrum)
    {
        //System.out.println("STATE:" + currentState);
        long milliTime = (long)(parent.getCurrentUnixTime());
        for(Observer o : StateListeners)
        {
            o.update("STATE;"+String.valueOf(currentState)+";"+String.valueOf(milliTime));
        }  
        if(currentState == 3)
        {
            newLaunch = true;
            DatabaseUtilities.DatabaseEntryInsert.addLaunchToDB(parent.getCurrentUnixTime(), 
                    parent.getCurrentUnixTime(), false);

        }
    }

    public void sendUnknownMessage(){}

    public void sendFilteredData(float[] data, float groupDelay)
    {
        //CurrentDataObjectSet.getCurrentDataObjectSet().forceUpdate();
        //0-torque, 1-tension, 2-cable_speed, 3-cable_angle, 4-cable_out
        //System.out.println("GSGS-" + String.valueOf(groupDelay));
        //System.out.println("RTRT-" + String.valueOf(parent.intUnixTime));
        
        //long milliTime = (long)(parent.currentUnixTime);
        //System.out.println("MTIME: " + milliTime);
        //System.out.println("UTIME: " + parent.currentUnixTime);
        //System.out.println("GDELAY: " + groupDelay);
        
        
        for(Observer o : TensionListeners)
        {
            o.update("TENSION;"+String.valueOf(data[1])+";"+String.valueOf(groupDelay));
        }
        for(Observer o : TorqueListeners)
        {
            o.update("TORQUE;"+String.valueOf(data[0])+";"+String.valueOf(groupDelay));
        }
        for(Observer o : CableSpeedListeners)
        {
            o.update("SPEED;"+String.valueOf(data[2])+";"+String.valueOf(groupDelay));
        }
        for(Observer o : CableAngleListeners)
        {
            o.update("ANGLE;"+String.valueOf(data[3])+";"+String.valueOf(groupDelay));
        }
        for(Observer o : CableOutListeners)
        {
            o.update("OUT;"+String.valueOf(data[4])+";"+String.valueOf(groupDelay));
        }
    }
    
    public void sendDensityAltitudeStatus(float status, float pressure, float temperature, float humidity)
    {
        CurrentWidgetDataSet data = CurrentWidgetDataSet.getInstance();
        data.setValue("pressure", pressure+"");
        data.setValue("temperature", temperature+"");
        data.setValue("humidity", humidity+"");
    }
    
    public void sendWindStatus(float status, float speed, float direction, float gust)
    {
        CurrentWidgetDataSet data = CurrentWidgetDataSet.getInstance();
        data.setValue("windspeed", speed+"");
        data.setValue("winddirection", direction+"");
        data.setValue("gustspeed", gust+"");
    }
    
    public void forwardMessage(String msg)
    {
        String[] parts = msg.split(";");
        switch(parts[0])
        {
            case "TENSION":
                for(Observer o : TensionListeners)
                {
                    o.update(msg);
                }
                break;
            case "TORQUE":
                for(Observer o : TorqueListeners)
                {
                    o.update(msg);
                }
                break;
            case "SPEED":
                for(Observer o : CableSpeedListeners)
                {
                    o.update(msg);
                }
                break;
            case "ANGLE":
                for(Observer o : CableAngleListeners)
                {
                    o.update(msg);
                }
                break;
            case "OUT":
                for(Observer o : CableOutListeners)
                {
                    o.update(msg);
                }  
                break;
        }
    }
}
