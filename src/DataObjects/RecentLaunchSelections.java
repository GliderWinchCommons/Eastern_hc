/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

import Communications.Observer;
import java.util.ArrayList;
import DatabaseUtilities.DatabaseEntryInsert;
import DatabaseUtilities.DatabaseEntrySelect;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Noah
 */
public class RecentLaunchSelections {
    private static RecentLaunchSelections instance = null;
    private CurrentDataObjectSet currentDataObjectSet;
    private List<Integer> recentPilot;
    private List<Integer> recentSailplane;
    private List<Integer> recentProfile;
    private List<Integer> recentRunway;
    private List<Integer> recentWinchPosition;
    private List<Integer> recentGliderPosition;
    private List<Integer> recentWinch;
    private List<Integer> recentAirfield;
    private List<String> recentDrum;
    private List<Observer> observers;
    
    public static RecentLaunchSelections getRecentLaunchSelections()
    {
        if(instance == null)
        {
            instance = new RecentLaunchSelections();
            instance.recentPilot = new ArrayList();
            instance.recentSailplane = new ArrayList();
            instance.recentProfile = new ArrayList();
            instance.recentRunway = new ArrayList();
            instance.recentWinchPosition = new ArrayList();
            instance.recentGliderPosition = new ArrayList();
            instance.recentWinch = new ArrayList();
            instance.recentAirfield = new ArrayList();
            instance.recentDrum = new ArrayList();
            instance.observers = new ArrayList();
        }
        return instance;
    }
    
    public void addCurrentToRecentLaunchSelections(){
        currentDataObjectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
        instance.addRecentPilot(currentDataObjectSet.getCurrentPilot());
        instance.addRecentSailplane(currentDataObjectSet.getCurrentSailplane());
        instance.addRecentProfile(currentDataObjectSet.getCurrentProfile());
        instance.addRecentRunway(currentDataObjectSet.getCurrentRunway());
        instance.addRecentWinchPosition(currentDataObjectSet.getCurrentWinchPosition());
        instance.addRecentGliderPosition(currentDataObjectSet.getCurrentGliderPosition());
        instance.addRecentWinch(currentDataObjectSet.getCurrentWinch());
        instance.addRecentAirfield(currentDataObjectSet.getCurrentAirfield());
        instance.addRecentDrum(currentDataObjectSet.getCurrentDrum());
        
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
    //this will clear the recent loaded objects.
    public static void Clear()
    {
        instance = null;
    }
    
    public static boolean IsInitialized()
    {
        return(instance != null);
    }
        
    //clear each list
    public void clearRecentPilot()
    {
        if(instance != null) instance.recentPilot = null;
        instance.notifyObservers();
    }
    public void clearRecentSailplane()
    {
        if(instance != null) instance.recentSailplane = null;
        instance.notifyObservers();
    }
    public void clearRecentProfile()
    {
        if(instance != null) instance.recentProfile = null;
        instance.notifyObservers();
    }
    public void clearRecentRunway()
    {
        if(instance != null) instance.recentRunway = null;
        instance.notifyObservers();
    }
    public void clearRecentWinchPosition()
    {
        if(instance != null) instance.recentWinchPosition = null;
        instance.notifyObservers();
    }
    public void clearRecentGliderPosition()
    {
        if(instance != null) instance.recentGliderPosition = null;
        instance.notifyObservers();
    }
    public void clearRecentWinch()
    {
        if(instance != null) instance.recentWinch = null;
        instance.notifyObservers();
    }
    public void clearRecentAirfield()
    {
        if(instance != null) instance.recentAirfield = null;
        instance.notifyObservers();
    }
    public void clearRecentDrum()
    {
        if(instance != null) instance.recentDrum = null;
        instance.notifyObservers();
    }
    //Add a new object to the list
    public void addRecentPilot(Pilot newPilot)
    {
        if(instance != null)
        {
            instance.recentPilot.add(newPilot.getPilotId());
        }
    instance.notifyObservers();
    }
    public void addRecentSailplane(Sailplane newSailplane)
    {
        if(instance != null)
        {
            instance.recentSailplane.add(newSailplane.getId());
        }
    instance.notifyObservers();
    }
    public void addRecentProfile(Operator newProfile)
    {
        if(instance != null)
        {
            instance.recentProfile.add(newProfile.getID());
        }
    instance.notifyObservers();
    }
    public void addRecentRunway(Runway newRunway)
    {
        if(instance != null)
        {
            instance.recentRunway.add(newRunway.getId());
        }
    instance.notifyObservers();
    }
    public void addRecentWinchPosition(WinchPosition newWinchPosition)
    {
        if(instance != null)
        {
            instance.recentWinchPosition.add(newWinchPosition.getId());
        }
    instance.notifyObservers();
    }
    public void addRecentGliderPosition(GliderPosition newGliderPosition)
    {
        if(instance != null)
        {
            instance.recentGliderPosition.add(newGliderPosition.getId());
        }
    instance.notifyObservers();
    }
    public void addRecentWinch(Winch newWinch)
    {
        if(instance != null)
        {
            instance.recentWinch.add(newWinch.getId());
        }
    instance.notifyObservers();
    }
    public void addRecentAirfield(Airfield newAirfield)
    {
        if(instance != null)
        {
            instance.recentAirfield.add(newAirfield.getId());
        }
    instance.notifyObservers();
    }
    public void addRecentDrum(Drum newDrum)
    {
        if(instance != null)
        {
            //instance.recentDrum.add(newDrum.getId());
        }
    instance.notifyObservers();
    }
    //Get the list of object
    public List<Pilot> getRecentPilot()
    {
            List<Pilot> pilots = DatabaseEntrySelect.getPilots();
            List<Pilot> recentPilotList = new ArrayList<>();
            if(instance == null)
            {
                return null;
            }
            else
            {
                for (int index = 0; (index < instance.recentPilot.size()) && (recentPilotList.size() < 5); index++){
                    for (int index2 = 0; (index2 < pilots.size()); index2++){
                        if (pilots.get(index2).getPilotId() == instance.recentPilot.get(index)){
                            recentPilotList.add(pilots.get(index2));
                        }
                    }
                }
                
                return recentPilotList;
            }
    }
    public List<Sailplane> getRecentSailplane()
    {
            List<Sailplane> sailplanes = DatabaseEntrySelect.getSailplanes();
            List<Sailplane> recentSailplaneList = new ArrayList<Sailplane>();
            if(instance == null)
            {
                return null;
            }
            else
            {
                for (int index = 0; (index < instance.recentSailplane.size()) && (recentSailplaneList.size() < 5); 
                        index++){
                    for (int index2 = 0; (index2 < sailplanes.size()); index2++){
                        if (sailplanes.get(index2).getId() == instance.recentSailplane.get(index)){
                            recentSailplaneList.add(sailplanes.get(index2));
                        }
                    }
                }
                return recentSailplaneList;
            }
    }
    /*public List<Profile> getRecentProfile()
    {
        try{
            List<Profile> profiles = DatabaseDataObjectUtilities.getProfiles();
            List<Profile> recentProfileList = new ArrayList<Profile>();
            if(instance == null)
            {
                return null;
            }
            else
            {
                for (int index = 0; (index < instance.recentProfile.size()) && (recentProfileList.size() < 5); index++){
                    for (int index2 = 0; (index2 < profiles.size()); index2++){
                        if (profiles.get(index2).getId().equals(instance.recentProfile.get(index))){
                            recentProfileList.add(profiles.get(index2));
                        }
                    }
                }
                return recentProfileList;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecentLaunchSelections.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/
    public List<Runway> getRecentRunway()
    {
            List<Runway> runways = DatabaseEntrySelect.getRunways();
            List<Runway> recentRunwayList = new ArrayList<Runway>();
            if(instance == null)
            {
                return null;
            }
            else
            {
                for (int index = 0; (index < instance.recentRunway.size()) && (recentRunwayList.size() < 5); index++){
                    for (int index2 = 0; (index2 < runways.size()); index2++){
                        if (runways.get(index2).getId() == instance.recentRunway.get(index)){
                            recentRunwayList.add(runways.get(index2));
                        }
                    }
                }
                return recentRunwayList;
            }
    }
    
    public List<WinchPosition> getRecentWinchPosition()
    {
            List<WinchPosition> winchPositions = DatabaseEntrySelect.getWinchPositions();
            List<WinchPosition> recentWinchPositionList = new ArrayList<WinchPosition>();
            if(instance == null)
            {
                return null;
            }
            else
            {
                for (int index = 0; (index < instance.recentWinchPosition.size()) && (recentWinchPositionList.size() < 5);
                        index++){
                    for (int index2 = 0; (index2 < winchPositions.size()); index2++){
                        if (winchPositions.get(index2).getId() == instance.recentWinchPosition.get(index)){
                            recentWinchPositionList.add(winchPositions.get(index2));
                        }
                    }
                }
                return recentWinchPositionList;
            }
    }
    public List<GliderPosition> getRecentGliderPosition()
    {
            List<GliderPosition> gliderPositions = DatabaseEntrySelect.getGliderPositions();
            List<GliderPosition> recentGliderPositionList = new ArrayList<GliderPosition>();
            if(instance == null)
            {
                return null;
            }
            else
            {
                for (int index = 0; (index < instance.recentGliderPosition.size()) && (recentGliderPositionList.size() < 5); 
                        index++){
                    for (int index2 = 0; (index2 < gliderPositions.size()); index2++){
                        if (gliderPositions.get(index2).getId() == instance.recentGliderPosition.get(index)){
                            recentGliderPositionList.add(gliderPositions.get(index2));
                        }
                    }
                }
                return recentGliderPositionList;
            }
    }
        
    /*public List<Winch> getRecentWinch()
    {
        try{
            List<Winch> winchs = DatabaseDataObjectUtilities.getWinchs();
            List<Winch> recentWinchList = new ArrayList<Winch>();
            if(instance == null)
            {
                return null;
            }
            else
            {
                for (int index = 0; (index < instance.recentWinch.size()) && (recentWinchList.size() < 5); index++){
                    for (int index2 = 0; (index2 < winchs.size()); index2++){
                        if (winchs.get(index2).getId().equals(instance.recentWinch.get(index))){
                            recentWinchList.add(winchs.get(index2));
                        }
                    }
                }
                return recentWinchList;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecentLaunchSelections.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/
    public List<Airfield> getRecentAirfield()
    {
            List<Airfield> airfields = DatabaseEntrySelect.getAirfields();
            List<Airfield> recentAirfieldList = new ArrayList<Airfield>();
            if(instance == null)
            {
                return null;
            }
            else
            {
                for (int index = 0; (index < instance.recentAirfield.size()) && (recentAirfieldList.size() < 5); index++){
                    for (int index2 = 0; (index2 < airfields.size()); index2++){
                        if (airfields.get(index2).getId() == instance.recentAirfield.get(index)){
                            recentAirfieldList.add(airfields.get(index2));
                        }
                    }
                }
                return recentAirfieldList;
            }

    }
    /*public List<Drum> getRecentDrum()
    {
        try{
            List<Drum> drums = DatabaseDataObjectUtilities.getDrums();
            List<Drum> recentDrumList = new ArrayList<Drum>();
            if(instance == null)
            {
                return null;
            }
            else
            {
                for (int index = 0; (index < instance.recentDrum.size()) && (recentDrumList.size() < 5); index++){
                    for (int index2 = 0; (index2 < drums.size()); index2++){
                        if (drums.get(index2).getId().equals(instance.recentDrum.get(index)){
                            recentDrumList.add(drums.get(index2));
                        }
                    }
                }
                return recentDrumList;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecentLaunchSelections.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/
}