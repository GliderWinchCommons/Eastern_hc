/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

/**
 * This class stores information about a launch
 *
 * @author Noah Fujioka
 */
public class LaunchMetaData {
    Operator currentOperatorProfile;
    Pilot currentPilot;
    Sailplane currentSailplane;
    Airfield currentAirfield;
    Runway currentRunway;
    GliderPosition currentPosition;
    Parachute currentParachute;
    Drum currentDrum;
    Winch currentWinch;
    Environmental currentEnvironmental; //At the start of the flight
    long startTimestamp;
    long endTimestamp;
    
    public LaunchMetaData(){
    }
    
    public LaunchMetaData(Operator newProfile, Pilot newPilot, Sailplane newSailplane,
            Airfield newAirfield, Runway newRunway, GliderPosition newPosition,
            Parachute newParachute, Drum newDrum, Winch newWinch, 
            Environmental newEnvironmental, long newStartTimestamp,
            long newEndTimestamp){
        currentOperatorProfile = newProfile;
        currentPilot = newPilot;
        currentSailplane = newSailplane;
        currentAirfield = newAirfield;
        currentRunway = newRunway;
        currentPosition = newPosition;
        currentParachute = newParachute;
        currentDrum = newDrum;
        currentWinch = newWinch;
        currentEnvironmental = newEnvironmental;
        startTimestamp = newStartTimestamp;
        endTimestamp = newEndTimestamp;
    }
    
    @Override
    public String toString() { 
        return (currentPilot.toString() + ", " + currentSailplane.toString() +
                ", " + startTimestamp);
    }

    public Operator getProfile(){
        return currentOperatorProfile;
    }
    
    public Pilot getPilot(){
        return currentPilot;
    }
    
    public Sailplane getSailplane(){
        return currentSailplane;
    }
    
    public Airfield getAirfield(){
        return currentAirfield;
    }
    
    public Runway getRunway(){
        return currentRunway;
    }
    
    public GliderPosition getPosition(){
        return currentPosition;
    }
    
    public Parachute getParachtue(){
        return currentParachute;
    }
    
    public Drum getDrum(){
        return currentDrum;
    }
    
    public Winch getWinch(){
        return currentWinch;
    }
    
    public Environmental getEnvironmental(){
        return currentEnvironmental;
    }
    
    public long getStartTimestamp(){
        return startTimestamp;
    }
    
    public long getEndTimestamp(){
        return endTimestamp;
    }
}
