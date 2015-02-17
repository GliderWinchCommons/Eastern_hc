package DataObjects;
/**
 *
 * @author Jacob Troxel
 */

import java.util.ArrayList;
import Communications.Observer;

public class CurrentDataObjectSet {
    
    private static CurrentDataObjectSet instance = null;
    private Pilot currentPilot;
    private Sailplane currentSailplane;
    private Profile currentProfile;
    private Runway currentRunway;
    private WinchPosition currentWinchPos;
    private GliderPosition currentGliderPos;
    private Winch currentWinch;
    private Airfield currentAirfield;
    private ArrayList<Observer> observers;
    
    public static CurrentDataObjectSet getCurrentDataObjectSet()
    {
        if(instance == null)
        {
            instance = new CurrentDataObjectSet();
            instance.observers = new ArrayList<Observer>();
        }
        return instance;
    }
    
    public void attach(Observer ob)
    {
        observers.add(ob);
    }
    
    private void notifyObservers()
    {
        for(Observer ob : observers)
        {
            ob.update();
        }
    }
    
    //use with caution!!!
    //this will clear the current loaded objects.
    public static void Clear()
    {
        instance = null;
    }
    
    public static boolean IsInitialized()
    {
        return(instance != null);
    }
    
    public void clearPilot()
    {
        
    }
    
    
    
    //Ah yeah, getters and setters
    public void setCurrentPilot(Pilot pilot)
    {
        if(instance != null)
        {
            instance.currentPilot = pilot;
        }
        instance.notifyObservers();
    }
    public void setCurrentGlider(Sailplane sailplane)
    {
        if(instance != null)
        {
            instance.currentSailplane = sailplane;
        }       
        instance.notifyObservers();
    }
    public void setCurrentRunway(Runway runway)
    {
        if(instance != null)
        {
            instance.currentRunway = runway;
        }    
        instance.notifyObservers();
    }
    public void setCurrentAirfield(Airfield airfield)
    {
        if(instance != null)
        {
            instance.currentAirfield = airfield;
        }        
        instance.notifyObservers();
    }
    public void setCurrentProfile(Profile profile)
    {
        if(instance != null)
        {
            instance.currentProfile = profile;
        }        
        instance.notifyObservers();
    }
    public void setCurrentGliderPosition(GliderPosition pos)
    {
        if(instance != null)
        {
            instance.currentGliderPos = pos;
        }        
        instance.notifyObservers();
    }
    public void setCurrentWinchPosition(WinchPosition pos)
    {
        if(instance != null)
        {
            instance.currentWinchPos = pos;
        }        
        instance.notifyObservers();
    }
    public void setCurrentWinch(Winch winch)
    {
        if(instance != null)
        {
            instance.currentWinch = winch;
        }        
        instance.notifyObservers();
    }
    public Pilot getCurrentPilot()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentPilot;
        }
    }
    public Sailplane getCurrentSailplane()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentSailplane;
        }        
    }
    public Airfield getCurrentAirfield()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentAirfield;
        }        
    }
    public Runway getCurrentRunway()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentRunway;
        }        
    }
    public GliderPosition getCurrentGliderPosition()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentGliderPos;
        }        
    }
    public WinchPosition getCurrentWinchPosition()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentWinchPos;
        }        
    }
    public Winch getCurrentWinch()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentWinch;
        }        
    }
    public Profile getCurrentProfile()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentProfile;
        }        
    }
}
