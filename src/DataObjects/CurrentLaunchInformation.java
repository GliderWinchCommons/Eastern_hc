package DataObjects;

import Communications.Observer;
import Configuration.UnitConversionRate;
import EnvironmentalWidgets.CurrentWidgetDataSet;
import ParameterSelection.Capability;
import ParameterSelection.SailplanePanel;
import java.util.ArrayList;

/**
 *
 * @author Noah
 */
public class CurrentLaunchInformation implements Observer{
    public static final float RADIUS_OF_EARTH = 6378100; // in meters
    private static CurrentLaunchInformation instance = null;
    private CurrentDataObjectSet currentDataObjectSet;
    private CurrentWidgetDataSet environmentalData;
    private SailplanePanel gliderPanel;
    private boolean completeObjects; //If the current data object set is complete
    private boolean completeDerived; //If the dervied units are complete
    private boolean complete; //Decides if the given information is enough to satisfy launch parameters
    //All numbers are stored in SI units, degrees Celcius, and Magnetic Headings
    //Pilot information
    private float pilotWeight;
    private int pilotCapacity;
    private float pilotPreference;
    //Glider information
    private float gliderMaxGrossWeight;
    private float gliderEmptyWeight;
    private float gliderIndicatedStallSpeed;
    private float gliderMaxWinchingSpeed;
    private float gliderMaxWeakLinkStrength;
    private float gliderMaxTension;
    private float gliderBallast;
    private float gliderBaggage;
    private float passengerWeight;
    //Airfield information
    private float airfieldAltitude;
    private float airfieldMagneticVariation;
    private float airfieldLatitude;
    private float airfieldLongitude;
    //Runway information
    private float runwayHeading;
    private float runwayAltitude;
    //Glider position information
    private float gliderPositionAltitude;
    private float gliderPositionLatitude;
    private float gliderPositionLongitude;
    //Winch position information
    private float winchPositionAltitude;
    private float winchPositionLatitude;
    private float winchPositionLongitude;
    //Winch information
    private float brakePressure;
    //Drive information
    private float reductionRatio;
    //Drum Information
    private float drumCoreDiameter;
    private float drumKFactor;
    private float drumCableLength;
    private float drumEndOffset;
    private float drumQuadratureSensor;
    //Parachute Information
    private float parachuteLift;
    private float parachuteDrag;
    private float parachuteWeight; 
    //Environmental and Derived Information
    private float averageWindSpeed;
    private float gustWindSpeed;
    private float windDirection;
    private float windDegreeOffset;
    private float headwindComponent;
    private float crosswindComponent;
    private float temperature;
    private float humidity;
    private float pressure;
    private float calculatedDensityAltitude;
    private float densityAltitude;
    private float runLength;
    private float runSlope;
    private float runHeading;
    private float gliderLaunchMass;
    private ArrayList<Observer> observers;
    
    public static void main(String args[]){
        //For logical testing
        float gliderLat = 47.665808f;
        float gliderLon = -117.413895f;
        float gliderAlt = 1;
        float winchLat = 47.681991f;
        float winchLon = -117.410633f;
        float winchAlt = 3;
        float magVariation = 0;
        float runDir = CurrentLaunchInformation.calculateHeading(gliderLat, gliderLon, winchLat, winchLon, magVariation);
        float windDir = 25;
        float relativeDir = CurrentLaunchInformation.calculateRelativeDirection(runDir, windDir);
        float averageWindSpeed = 5;
        float temp = 34;
        float pressure = 10;
        
        System.out.println("RelativeDir:" + String.valueOf(relativeDir));
        System.out.println("HeadwindComp:" + String.valueOf(
                CurrentLaunchInformation.calculateHeadwind(relativeDir, averageWindSpeed)));
        System.out.println("CrosswindComp:" + String.valueOf(
                CurrentLaunchInformation.calculateCrosswind(relativeDir, averageWindSpeed)));
        System.out.println("DensityAltitude:" + String.valueOf(
                CurrentLaunchInformation.calculateDensityAltitude(temp, pressure)));
        System.out.println("Runlen:" + String.valueOf(
                CurrentLaunchInformation.calculateRunLength(
                gliderAlt, gliderLat, gliderLon, winchAlt, winchLat, winchLon)));
        System.out.println("RunSlope:" + String.valueOf(
                CurrentLaunchInformation.calculateRunSlope(
                gliderAlt, gliderLat, gliderLon, winchAlt, winchLat, winchLon)));
        System.out.println("RunHeading:" + String.valueOf(runDir));
        System.out.println("TotalWeight:" + String.valueOf(
                CurrentLaunchInformation.calculateGliderLaunchMass(1, 2, 3, 4, 5)));
    
    }
    private float winchWindDirection;
    private float WinchWindSpeed;
    private float winchWindGust;
    private float gliderWindDirection;
    private float gliderWindGust;
    private float gliderWindSpeed;
    private float altimeter;
    
    private CurrentLaunchInformation(){
        currentDataObjectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
        environmentalData = CurrentWidgetDataSet.getInstance();
        currentDataObjectSet.attach(getObserver());
        
    }
    
    public static CurrentLaunchInformation getCurrentLaunchInformation()
    {
        if(instance == null)
        {
            instance = new CurrentLaunchInformation();
            instance.completeObjects = false;
            instance.completeDerived = false;
            instance.complete = false;
            instance.update();
            instance.observers = new ArrayList<Observer>();
        }
        return instance;
    }
    
    private Observer getObserver() {
        return this;
    }
    
    @Override
    public void update()
    {
        try{
            currentDataObjectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
            instance.pilotWeight = currentDataObjectSet.getCurrentPilot().getWeight();
            int capability = Capability.convertCapabilityStringToNum(
                    currentDataObjectSet.getCurrentPilot().getCapability());
            instance.pilotCapacity = capability;
            instance.pilotPreference = currentDataObjectSet.getCurrentPilot().getPreference();;

            instance.gliderMaxGrossWeight = currentDataObjectSet.getCurrentSailplane().getMaxGrossWeight();
            instance.gliderEmptyWeight = currentDataObjectSet.getCurrentSailplane().getEmptyWeight();
            instance.gliderIndicatedStallSpeed = currentDataObjectSet.getCurrentSailplane().getIndicatedStallSpeed();
            instance.gliderMaxWinchingSpeed = currentDataObjectSet.getCurrentSailplane().getMaxWinchingSpeed();
            instance.gliderMaxWeakLinkStrength = currentDataObjectSet.getCurrentSailplane().getMaxWeakLinkStrength();
            instance.gliderMaxTension = currentDataObjectSet.getCurrentSailplane().getMaxTension();
            instance.airfieldAltitude = currentDataObjectSet.getCurrentAirfield().getElevation();
            instance.airfieldMagneticVariation = currentDataObjectSet.getCurrentAirfield().getMagneticVariation();
            instance.airfieldLatitude = currentDataObjectSet.getCurrentAirfield().getLatitude();
            instance.airfieldLongitude = currentDataObjectSet.getCurrentAirfield().getLongitude();
            instance.runwayHeading = currentDataObjectSet.getCurrentRunway().getMagneticHeading() 
                    + currentDataObjectSet.getCurrentAirfield().getMagneticVariation();
            instance.gliderPositionAltitude = currentDataObjectSet.getCurrentGliderPosition().getElevation();
            instance.gliderPositionLatitude = currentDataObjectSet.getCurrentGliderPosition().getLatitude();
            instance.gliderPositionLongitude = currentDataObjectSet.getCurrentGliderPosition().getLongitude();
            instance.winchPositionAltitude = currentDataObjectSet.getCurrentWinchPosition().getElevation();
            instance.winchPositionLatitude = currentDataObjectSet.getCurrentWinchPosition().getLatitude();
            instance.winchPositionLongitude = currentDataObjectSet.getCurrentWinchPosition().getLongitude();

            //Based on current Drum class
            instance.drumCoreDiameter = currentDataObjectSet.getCurrentDrum().getCoreDiameter();
            instance.drumKFactor = currentDataObjectSet.getCurrentDrum().getKFactor();
            instance.drumCableLength = currentDataObjectSet.getCurrentDrum().getCableLength();
            instance.drumEndOffset = 0;
            instance.drumQuadratureSensor = 0;
            instance.parachuteLift = currentDataObjectSet.getCurrentDrum().getParachute().getLift();
            instance.parachuteDrag = currentDataObjectSet.getCurrentDrum().getParachute().getDrag();
            instance.parachuteWeight = currentDataObjectSet.getCurrentDrum().getParachute().getWeight(); 
            
            instance.completeObjects = true;
            update("Manual Entry");
            if (instance.completeObjects && instance.completeDerived){
                instance.complete = true;
            }else{
                instance.complete = false;
            }
            }catch (NumberFormatException e){
                System.out.println("Incomplete current launch information");
                instance.complete = false;
            }catch (Exception e){
                instance.complete = false;
            }
    }
    
    @Override
    public void update(String msg) {
        if (msg.equals("Manual Entry")){
            try{
                try{
                    if (gliderPanel != null){
                        if (currentDataObjectSet.getCurrentSailplane().getCarryBallast()){
                            instance.gliderBallast = Float.parseFloat(gliderPanel.getballastField()) / 
                                    UnitConversionRate.convertWeightUnitIndexToFactor(
                                            currentDataObjectSet.getCurrentProfile().getUnitSetting("ballastWeight"));
                        }
                        else{
                            instance.gliderBallast = 0;
                        }
                        if (currentDataObjectSet.getCurrentSailplane().getMultipleSeats()){
                            instance.passengerWeight = Float.parseFloat(gliderPanel.getpassengerWeightField()) /
                                    UnitConversionRate.convertWeightUnitIndexToFactor(
                                            currentDataObjectSet.getCurrentProfile().getUnitSetting("passengerWeight"));
                        }
                        else{
                            instance.passengerWeight = 0;
                        }
                        if (gliderPanel.getbaggageCheckBox()){
                            instance.gliderBaggage = Float.parseFloat(gliderPanel.getbaggageField()) /
                                    UnitConversionRate.convertWeightUnitIndexToFactor(
                                            currentDataObjectSet.getCurrentProfile().getUnitSetting("baggageWeight"));
                        }
                        else {
                            instance.gliderBaggage = 0;
                        }
                    }
                }catch(NullPointerException e){
                    instance.gliderBallast = 0;
                    instance.passengerWeight = 0;
                    instance.gliderBaggage = 0;
                }catch(Exception e){
                    throw e;
                }
                
                String temperatureStr = environmentalData.getValue("temperature");
                String pressureStr = environmentalData.getValue("pressure");
                String densityAltitudeStr = environmentalData.getValue("densityaltitude");
                
                if (!temperatureStr.equals("")){
                    instance.temperature = Float.parseFloat(temperatureStr);
                } else{
                    instance.temperature = 0;
                }
                if (!pressureStr.equals("")){
                    instance.pressure = Float.parseFloat(pressureStr);
                } else{
                    instance.pressure = 0;
                }
                if (!temperatureStr.equals("") && !pressureStr.equals("")){
                    instance.calculatedDensityAltitude = 
                            calculateDensityAltitude(instance.temperature, instance.pressure);
                } else{
                    instance.calculatedDensityAltitude = 0;
                }
                if (!densityAltitudeStr.equals("")){
                    instance.densityAltitude  = Float.parseFloat(densityAltitudeStr);
                } else{
                    instance.densityAltitude = calculatedDensityAltitude;
                }
                
                if(environmentalData.getValue("avgwindspeed").equals(""))
                {
                    instance.averageWindSpeed = 0;
                }
                else
                {
                    instance.averageWindSpeed = Float.parseFloat(environmentalData.getValue("avgwindspeed"));
                }
                if(environmentalData.getValue("gustwindspeed").equals(""))
                {
                    instance.gustWindSpeed = 0;
                }
                else
                {
                    instance.gustWindSpeed = Float.parseFloat(environmentalData.getValue("gustwindspeed"));
                }
                if(!environmentalData.getValue("winddirection").equals(""))
                { 
                    instance.windDirection = Float.parseFloat(environmentalData.getValue("winddirection"));
                }
                else
                {
                    instance.windDirection = 0;
                }
                instance.runLength = calculateRunLength(instance.gliderPositionAltitude, 
                        instance.gliderPositionLatitude, instance.gliderPositionLongitude,
                                               instance.winchPositionAltitude, instance.winchPositionLatitude, 
                                               instance.winchPositionLongitude);
                instance.runSlope = calculateRunSlope(instance.gliderPositionAltitude, 
                        instance.gliderPositionLatitude, instance.gliderPositionLongitude,
                                               instance.winchPositionAltitude, 
                                               instance.winchPositionLatitude, instance.winchPositionLongitude);
                instance.runHeading = calculateHeading(instance.gliderPositionLatitude, 
                                               instance.gliderPositionLongitude,
                                               instance.winchPositionLatitude, 
                                               instance.winchPositionLongitude, instance.airfieldMagneticVariation);
                instance.gliderLaunchMass = calculateGliderLaunchMass(instance.pilotWeight, instance.gliderEmptyWeight,
                                                instance.gliderBallast, 
                                                instance.gliderBaggage, instance.passengerWeight);
                instance.windDegreeOffset = calculateRelativeDirection(instance.runHeading, instance.windDirection);
                instance.headwindComponent = calculateHeadwind(instance.windDegreeOffset, instance.averageWindSpeed);
                instance.crosswindComponent = calculateCrosswind(instance.windDegreeOffset, instance.averageWindSpeed);
                
                if (!densityAltitudeStr.equals("") || (!temperatureStr.equals("") && !pressureStr.equals(""))){
                    instance.completeDerived = true;
                }
                if (instance.completeObjects && instance.completeDerived){
                    instance.complete = true;
                }else{
                    instance.complete = false;
                }
                notifyObservers();
            }catch (NumberFormatException e){
                System.out.println("Incomplete derived values");
                instance.complete = false;
            }catch (Exception e){
                instance.complete = false;
            }
        }
        else{
            //throw new UnsupportedOperationException("Not supported yet.");
        }
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
    
    public static boolean IsReady()
    {
        return((instance != null)  && instance.complete);
    }
    
    //Register glider panel with the singleton
    public void setSailplanePanel(SailplanePanel gliderPanel){
        this.gliderPanel = gliderPanel;
    }
    
    public void setSailplanePanelBallast(float weight){
        gliderPanel.setballastField(weight);
    }
    public void setSailplanePanelPassenger(float weight){
        gliderPanel.setpassengerWeightField(weight);
    }
    public void setSailplanePanelBaggage(float weight){
        gliderPanel.setbaggageField(weight);
    }
    
    //functions to determine derived values
    public static float calculateRunLength(float gliderAltitude, float gliderLatitude, float gliderLongitude,
                                           float winchAltitude, float winchLatitude, float winchLongitude){
        double xRun = RADIUS_OF_EARTH * Math.sin(Math.toRadians(winchLongitude - gliderLongitude))
                * Math.sin(Math.toRadians((winchLatitude + gliderLatitude)/2));
        double yRise = RADIUS_OF_EARTH * Math.sin(Math.toRadians(winchLatitude - gliderLatitude));
        double altitudeChange = winchAltitude - gliderAltitude;
        float length = (float)Math.sqrt((xRun * xRun) + (yRise * yRise) + (altitudeChange * altitudeChange));
        return length;
    }
    
    public static float calculateRunSlope(float gliderAltitude, float gliderLatitude, float gliderLongitude,
                                           float winchAltitude, float winchLatitude, float winchLongitude){
        double xRun = RADIUS_OF_EARTH * Math.sin(Math.toRadians(winchLongitude - gliderLongitude))
                * Math.sin(Math.toRadians((winchLatitude + gliderLatitude)/2));
        double yRise = RADIUS_OF_EARTH * Math.sin(Math.toRadians(winchLatitude - gliderLatitude));
        double altitudeChange = winchAltitude - gliderAltitude;
        float slope = (float)(Math.asin(altitudeChange / Math.sqrt((xRun * xRun) + (yRise * yRise))));
        return slope;
    }
    
    public static float calculateHeading(float gliderLatitude, float gliderLongitude,
                                        float winchLatitude, float winchLongitude, float magneticVariation){
        double xRun = RADIUS_OF_EARTH * Math.sin(Math.toRadians(winchLongitude - gliderLongitude))
                * Math.sin(Math.toRadians((winchLatitude + gliderLatitude)/2));
        double yRise = RADIUS_OF_EARTH * Math.sin(Math.toRadians(winchLatitude - gliderLatitude));
        float angle = ((float)(Math.toDegrees(Math.atan2(xRun, yRise))) + (magneticVariation)) % 360f;
        return angle;
    }
    
    public static float calculateRelativeDirection(float runDirection, float windDirection){
        //Negative degree indicates wind comes from the gilder's left
        return (windDirection - runDirection);
    }
    
    public static float calculateHeadwind(float degreeChange, float averageWindSpeed){
        return (float) (averageWindSpeed * Math.sin(Math.toRadians(degreeChange)));
    }
    
    public static float calculateCrosswind(float degreeChange, float averageWindSpeed){
        return (float) (averageWindSpeed * Math.cos(Math.toRadians(degreeChange)));
    }
    
    public static float calculateWindDirection(float runDirection, float headaverageWindSpeed, 
            float crossaverageWindSpeed){
        return runDirection + (float) Math.toDegrees(Math.atan2(crossaverageWindSpeed, headaverageWindSpeed));
    }
    
    public static float calculateWindSpeed(float headaverageWindSpeed, float crossaverageWindSpeed){
        return (float) Math.sqrt((headaverageWindSpeed * headaverageWindSpeed) 
                + (crossaverageWindSpeed * crossaverageWindSpeed));
    }
    
    public static float calculateDensityAltitude(float temperature, float pressure){
        //Using the dry air approximation equation from the National Weather Service
        return (float) (145442.16 * (1 - Math.pow(((17.326 * pressure)/(459.67 
                + ((temperature - 32.0) * (5.0/9.0)))), 0.235)) / 3.2808);
    }
    
    public static float calculateGliderLaunchMass(float pilotWeight, float gliderEmptyWeight,
                                            float gliderBallast, float gliderBaggage, float passengerWeight){
        /*
        System.out.println("Adding: " + pilotWeight + ", " + gliderEmptyWeight + ", " 
        + gliderBallast + ", " + gliderBaggage + ", " + passengerWeight);
        System.out.println("= " + pilotWeight + gliderEmptyWeight + gliderBallast 
        + gliderBaggage + passengerWeight);
        */
        return (pilotWeight + gliderEmptyWeight + gliderBallast + gliderBaggage + passengerWeight);
    }
    
//Clear Functions
    public void clearPilotWeight()
    {
        if(instance != null) instance.pilotWeight = -1;
        instance.notifyObservers();
    }
    public void clearPilotCapacity()
    {
        if(instance != null) instance.pilotCapacity = -1;
        instance.notifyObservers();
    }
    public void clearPilotPreference()
    {
        if(instance != null) instance.pilotPreference = -1;
        instance.notifyObservers();
    }
    public void clearGliderMaxGrossWeight()
    {
        if(instance != null) instance.gliderMaxGrossWeight = -1;
        instance.notifyObservers();
    }
    public void clearGliderEmptyWeight()
    {
        if(instance != null) instance.gliderEmptyWeight = -1;
        instance.notifyObservers();
    }
    public void clearGliderIndicatedStallSpeed()
    {
        if(instance != null) instance.gliderIndicatedStallSpeed = -1;
        instance.notifyObservers();
    }
    public void clearGliderMaxWinchingSpeed()
    {
        if(instance != null) instance.gliderMaxWinchingSpeed = -1;
        instance.notifyObservers();
    }
    public void clearGliderMaxWeakLinkStrength()
    {
        if(instance != null) instance.gliderMaxWeakLinkStrength = -1;
        instance.notifyObservers();
    }
    public void clearGliderMaxTension()
    {
        if(instance != null) instance.gliderMaxTension = -1;
        instance.notifyObservers();
    }
    public void clearGliderBallast()
    {
        if(instance != null) instance.gliderBallast = -1;
        instance.notifyObservers();
    }
    public void clearGliderBaggage()
    {
        if(instance != null) instance.gliderBaggage = -1;
        instance.notifyObservers();
    }
    public void clearPassengerWeight()
    {
        if(instance != null) instance.passengerWeight = -1;
        instance.notifyObservers();
    }
    public void clearAirfieldAltitude()
    {
        if(instance != null) instance.airfieldAltitude = -1;
        instance.notifyObservers();
    }
    public void clearAirfieldMagneticVariation()
    {
        if(instance != null) instance.airfieldMagneticVariation = -1;
        instance.notifyObservers();
    }
    public void clearAirfieldLatitude()
    {
        if(instance != null) instance.airfieldLatitude = -1;
        instance.notifyObservers();
    }
    public void clearAirfieldLongitude()
    {
        if(instance != null) instance.airfieldLongitude = -1;
        instance.notifyObservers();
    }
    public void clearRunwayHeading()
    {
        if(instance != null) instance.runwayHeading = -1;
        instance.notifyObservers();
    }
    public void clearRunwayAltitude()
    {
        if(instance != null) instance.runwayAltitude = -1;
        instance.notifyObservers();
    }
    public void clearGliderPositionAltitude()
    {
        if(instance != null) instance.gliderPositionAltitude = -1;
        instance.notifyObservers();
    }
    public void clearGliderPositionLatitude()
    {
        if(instance != null) instance.gliderPositionLatitude = -1;
        instance.notifyObservers();
    }
    public void clearGliderPositionLongitude()
    {
        if(instance != null) instance.gliderPositionLongitude = -1;
        instance.notifyObservers();
    }
    public void clearWinchPositionAltitude()
    {
        if(instance != null) instance.winchPositionAltitude = -1;
        instance.notifyObservers();
    }
    public void clearWinchPositionLatitude()
    {
        if(instance != null) instance.winchPositionLatitude = -1;
        instance.notifyObservers();
    }
    public void clearWinchPositionLongitude()
    {
        if(instance != null) instance.winchPositionLongitude = -1;
        instance.notifyObservers();
    }
    public void clearBrakePressure()
    {
        if(instance != null) instance.brakePressure = -1;
        instance.notifyObservers();
    }
    public void clearReductionRatio()
    {
        if(instance != null) instance.reductionRatio = -1;
        instance.notifyObservers();
    }
    public void clearDrumCoreDiameter()
    {
        if(instance != null) instance.drumCoreDiameter = -1;
        instance.notifyObservers();
    }
    public void clearDrumKFactor()
    {
        if(instance != null) instance.drumKFactor = -1;
        instance.notifyObservers();
    }
    public void clearDrumCableLength()
    {
        if(instance != null) instance.drumCableLength = -1;
        instance.notifyObservers();
    }
    public void clearDrumEndOffset()
    {
        if(instance != null) instance.drumEndOffset = -1;
        instance.notifyObservers();
    }
    public void clearDrumQuadratureSensor()
    {
        if(instance != null) instance.drumQuadratureSensor = -1;
        instance.notifyObservers();
    }
    public void clearParachuteLift()
    {
        if(instance != null) instance.parachuteLift = -1;
        instance.notifyObservers();
    }
    public void clearParachuteDrag()
    {
        if(instance != null) instance.parachuteDrag = -1;
        instance.notifyObservers();
    }
    public void clearParachuteWeight()
    {
        if(instance != null) instance.parachuteWeight = -1;
        instance.notifyObservers();
    }
    public void clearAverageWindSpeed()
    {
        if(instance != null) instance.averageWindSpeed = -1;
        instance.notifyObservers();
    }
    public void clearGustWindSpeed()
    {
        if(instance != null) instance.gustWindSpeed = -1;
        instance.notifyObservers();
    }
    public void clearWindDirection()
    {
        if(instance != null) instance.windDirection = -1;
        instance.notifyObservers();
    }
    public void clearWindDegreeOffset()
    {
        if(instance != null) instance.windDegreeOffset = -1;
        instance.notifyObservers();
    }
    public void clearHeadwindComponent()
    {
        if(instance != null) instance.headwindComponent = -1;
        instance.notifyObservers();
    }
    public void clearCrosswindComponent()
    {
        if(instance != null) instance.crosswindComponent = -1;
        instance.notifyObservers();
    }
    public void clearTemperature()
    {
        if(instance != null) instance.temperature = -1;
        instance.notifyObservers();
    }
    public void clearHumidity()
    {
        if(instance != null) instance.humidity = -1;
        instance.notifyObservers();
    }
    public void clearPressure()
    {
        if(instance != null) instance.pressure = -1;
        instance.notifyObservers();
    }
    public void clearCalculatedDensityAltitude()
    {
        if(instance != null) instance.calculatedDensityAltitude = -1;
        instance.notifyObservers();
    }
    public void clearDensityAltitude()
    {
        if(instance != null) instance.densityAltitude = -1;
        instance.notifyObservers();
    }
    public void clearRunLength()
    {
        if(instance != null) instance.runLength = -1;
        instance.notifyObservers();
    }
    public void clearRunSlope()
    {
        if(instance != null) instance.runSlope = -1;
        instance.notifyObservers();
    }
    public void clearRunHeading()
    {
        if(instance != null) instance.runHeading = -1;
        instance.notifyObservers();
    }
    public void clearGliderLaunchMass()
    {
        if(instance != null) instance.gliderLaunchMass = -1;
        instance.notifyObservers();
    }
    //Set Functions
    public void setPilotWeight(float newPilotWeight)
    {
        if(instance != null)
        {
            instance.pilotWeight = newPilotWeight;
        }
    instance.notifyObservers();
    }
    public void setPilotCapacity(int newPilotCapacity)
    {
        if(instance != null)
        {
            instance.pilotCapacity = newPilotCapacity;
        }
    instance.notifyObservers();
    }
    public void setPilotPreference(int newPilotPreference)
    {
        if(instance != null)
        {
            instance.pilotPreference = newPilotPreference;
        }
    instance.notifyObservers();
    }
    public void setGliderMaxGrossWeight(float newGliderMaxGrossWeight)
    {
        if(instance != null)
        {
            instance.gliderMaxGrossWeight = newGliderMaxGrossWeight;
        }
    instance.notifyObservers();
    }
    public void setGliderEmptyWeight(float newGliderEmptyWeight)
    {
        if(instance != null)
        {
            instance.gliderEmptyWeight = newGliderEmptyWeight;
        }
    instance.notifyObservers();
    }
    public void setGliderIndicatedStallSpeed(float newGliderIndicatedStallSpeed)
    {
        if(instance != null)
        {
            instance.gliderIndicatedStallSpeed = newGliderIndicatedStallSpeed;
        }
    instance.notifyObservers();
    }
    public void setGliderMaxWinchingSpeed(float newGliderMaxWinchingSpeed)
    {
        if(instance != null)
        {
            instance.gliderMaxWinchingSpeed = newGliderMaxWinchingSpeed;
        }
    instance.notifyObservers();
    }
    public void setGliderMaxWeakLinkStrength(float newGliderMaxWeakLinkStrength)
    {
        if(instance != null)
        {
            instance.gliderMaxWeakLinkStrength = newGliderMaxWeakLinkStrength;
        }
    instance.notifyObservers();
    }
    public void setGliderMaxTension(float newGliderMaxTension)
    {
        if(instance != null)
        {
            instance.gliderMaxTension = newGliderMaxTension;
        }
    instance.notifyObservers();
    }
    public void setGliderBallast(float newGliderBallast)
    {
        if(instance != null)
        {
            instance.gliderBallast = newGliderBallast;
        }
    instance.notifyObservers();
    }
    public void setGliderBaggage(float newGliderBaggage)
    {
        if(instance != null)
        {
            instance.gliderBaggage = newGliderBaggage;
        }
    instance.notifyObservers();
    }
    public void setPassengerWeight(float newPassengerWeight)
    {
        if(instance != null)
        {
            instance.passengerWeight = newPassengerWeight;
        }
    instance.notifyObservers();
    }
    public void setAirfieldAltitude(float newAirfieldAltitude)
    {
        if(instance != null)
        {
            instance.airfieldAltitude = newAirfieldAltitude;
        }
    instance.notifyObservers();
    }
    public void setAirfieldMagneticVariation(float newAirfieldMagneticVariation)
    {
        if(instance != null)
        {
            instance.airfieldMagneticVariation = newAirfieldMagneticVariation;
        }
    instance.notifyObservers();
    }
    public void setAirfieldLatitude(float newAirfieldLatitude)
    {
        if(instance != null)
        {
            instance.airfieldLatitude = newAirfieldLatitude;
        }
    instance.notifyObservers();
    }
    public void setAirfieldLongitude(float newAirfieldLongitude)
    {
        if(instance != null)
        {
            instance.airfieldLongitude = newAirfieldLongitude;
        }
    instance.notifyObservers();
    }
    public void setRunwayHeading(float newRunwayHeading)
    {
        if(instance != null)
        {
            instance.runwayHeading = newRunwayHeading;
        }
    instance.notifyObservers();
    }
    public void setRunwayAltitude(float newRunwayAltitude)
    {
        if(instance != null)
        {
            instance.runwayAltitude = newRunwayAltitude;
        }
    instance.notifyObservers();
    }
    public void setGliderPositionAltitude(float newGliderPositionAltitude)
    {
        if(instance != null)
        {
            instance.gliderPositionAltitude = newGliderPositionAltitude;
        }
    instance.notifyObservers();
    }
    public void setGliderPositionLatitude(float newGliderPositionLatitude)
    {
        if(instance != null)
        {
            instance.gliderPositionLatitude = newGliderPositionLatitude;
        }
    instance.notifyObservers();
    }
    public void setGliderPositionLongitude(float newGliderPositionLongitude)
    {
        if(instance != null)
        {
            instance.gliderPositionLongitude = newGliderPositionLongitude;
        }
    instance.notifyObservers();
    }
    public void setWinchPositionAltitude(float newWinchPositionAltitude)
    {
        if(instance != null)
        {
            instance.winchPositionAltitude = newWinchPositionAltitude;
        }
    instance.notifyObservers();
    }
    public void setWinchPositionLatitude(float newWinchPositionLatitude)
    {
        if(instance != null)
        {
            instance.winchPositionLatitude = newWinchPositionLatitude;
        }
    instance.notifyObservers();
    }
    public void setWinchPositionLongitude(float newWinchPositionLongitude)
    {
        if(instance != null)
        {
            instance.winchPositionLongitude = newWinchPositionLongitude;
        }
    instance.notifyObservers();
    }
    public void setBrakePressure(float newBrakePressure)
    {
        if(instance != null)
        {
            instance.brakePressure = newBrakePressure;
        }
    instance.notifyObservers();
    }
    public void setReductionRatio(float newReductionRatio)
    {
        if(instance != null)
        {
            instance.reductionRatio = newReductionRatio;
        }
    instance.notifyObservers();
    }
    public void setDrumCoreDiameter(float newDrumCoreDiameter)
    {
        if(instance != null)
        {
            instance.drumCoreDiameter = newDrumCoreDiameter;
        }
    instance.notifyObservers();
    }
    public void setDrumKFactor(float newDrumKFactor)
    {
        if(instance != null)
        {
            instance.drumKFactor = newDrumKFactor;
        }
    instance.notifyObservers();
    }
    public void setDrumCableLength(float newDrumCableLength)
    {
        if(instance != null)
        {
            instance.drumCableLength = newDrumCableLength;
        }
    instance.notifyObservers();
    }
    public void setDrumEndOffset(float newDrumEndOffset)
    {
        if(instance != null)
        {
            instance.drumEndOffset = newDrumEndOffset;
        }
    instance.notifyObservers();
    }
    public void setDrumQuadratureSensor(float newDrumQuadratureSensor)
    {
        if(instance != null)
        {
            instance.drumQuadratureSensor = newDrumQuadratureSensor;
        }
    instance.notifyObservers();
    }
    public void setParachuteLift(float newParachuteLift)
    {
        if(instance != null)
        {
            instance.parachuteLift = newParachuteLift;
        }
    instance.notifyObservers();
    }
    public void setParachuteDrag(float newParachuteDrag)
    {
        if(instance != null)
        {
            instance.parachuteDrag = newParachuteDrag;
        }
    instance.notifyObservers();
    }
    public void setParachuteWeight(float newParachuteWeight)
    {
        if(instance != null)
        {
            instance.parachuteWeight = newParachuteWeight;
        }
    instance.notifyObservers();
    }
    public void setAverageWindSpeed(float newAverageWindSpeed)
    {
        if(instance != null)
        {
            instance.averageWindSpeed = newAverageWindSpeed;
        }
    instance.notifyObservers();
    }
    public void setGustWindSpeed(float newGustWindSpeed)
    {
        if(instance != null)
        {
            instance.gustWindSpeed = newGustWindSpeed;
        }
    instance.notifyObservers();
    }
    public void setWindDirection(float newWindDirection)
    {
        if(instance != null)
        {
            instance.windDirection = newWindDirection;
        }
    instance.notifyObservers();
    }
    public void setWindDegreeOffset(float newWindDegreeOffset)
    {
        if(instance != null)
        {
            instance.windDegreeOffset = newWindDegreeOffset;
        }
    instance.notifyObservers();
    }
    public void setHeadwindComponent(float newHeadwindComponent)
    {
        if(instance != null)
        {
            instance.headwindComponent = newHeadwindComponent;
        }
    instance.notifyObservers();
    }
    public void setCrosswindComponent(float newCrosswindComponent)
    {
        if(instance != null)
        {
            instance.crosswindComponent = newCrosswindComponent;
        }
    instance.notifyObservers();
    }
    public void setTemperature(float newTemperature)
    {
        if(instance != null)
        {
            instance.temperature = newTemperature;
        }
    instance.notifyObservers();
    }
    public void setHumidity(float newHumidity)
    {
        if(instance != null)
        {
            instance.humidity = newHumidity;
        }
    instance.notifyObservers();
    }
    public void setPressure(float newPressure)
    {
        if(instance != null)
        {
            instance.pressure = newPressure;
        }
    instance.notifyObservers();
    }
    public void setCalculatedDensityAltitude(float newCalculatedDensityAltitude)
    {
        if(instance != null)
        {
            instance.calculatedDensityAltitude = newCalculatedDensityAltitude;
        }
    instance.notifyObservers();
    }
    public void setDensityAltitude(float newDensityAltitude)
    {
        if(instance != null)
        {
            instance.densityAltitude = newDensityAltitude;
        }
    instance.notifyObservers();
    }
    public void setRunLength(float newRunLength)
    {
        if(instance != null)
        {
            instance.runLength = newRunLength;
        }
    instance.notifyObservers();
    }
    public void setRunSlope(float newRunSlope)
    {
        if(instance != null)
        {
            instance.runSlope = newRunSlope;
        }
    instance.notifyObservers();
    }
    public void setRunHeading(float newRunHeading)
    {
        if(instance != null)
        {
            instance.runHeading = newRunHeading;
        }
    instance.notifyObservers();
    }
    public void setGliderLaunchMass(float newGliderLaunchMass)
    {
        if(instance != null)
        {
            instance.gliderLaunchMass = newGliderLaunchMass;
        }
    instance.notifyObservers();
    }
    //Get Functions
    public float getPilotWeight()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.pilotWeight;
        }
    }
    public int getPilotCapacity()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.pilotCapacity;
        }
    }
    public float getPilotPreference()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.pilotPreference;
        }
    }
    public float getGliderMaxGrossWeight()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderMaxGrossWeight;
        }
    }
    public float getGliderEmptyWeight()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderEmptyWeight;
        }
    }
    public float getGliderIndicatedStallSpeed()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderIndicatedStallSpeed;
        }
    }
    public float getGliderMaxWinchingSpeed()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderMaxWinchingSpeed;
        }
    }
    public float getGliderMaxWeakLinkStrength()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderMaxWeakLinkStrength;
        }
    }
    public float getGliderMaxTension()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderMaxTension;
        }
    }
    public float getGliderBallast()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderBallast;
        }
    }
    public float getGliderBaggage()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderBaggage;
        }
    }
    public float getPassengerWeight()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.passengerWeight;
        }
    }
    public float getAirfieldAltitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.airfieldAltitude;
        }
    }
    public float getAirfieldMagneticVariation()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.airfieldMagneticVariation;
        }
    }
    public float getAirfieldLatitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.airfieldLatitude;
        }
    }
    public float getAirfieldLongitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.airfieldLongitude;
        }
    }
    public float getRunwayHeading()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.runwayHeading;
        }
    }
    public float getRunwayAltitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.runwayAltitude;
        }
    }
    public float getGliderPositionAltitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderPositionAltitude;
        }
    }
    public float getGliderPositionLatitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderPositionLatitude;
        }
    }
    public float getGliderPositionLongitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderPositionLongitude;
        }
    }
    public float getWinchPositionAltitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.winchPositionAltitude;
        }
    }
    public float getWinchPositionLatitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.winchPositionLatitude;
        }
    }
    public float getWinchPositionLongitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.winchPositionLongitude;
        }
    }
    public float getBrakePressure()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.brakePressure;
        }
    }
    public float getReductionRatio()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.reductionRatio;
        }
    }
    public float getDrumCoreDiameter()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.drumCoreDiameter;
        }
    }
    public float getDrumKFactor()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.drumKFactor;
        }
    }
    public float getDrumCableLength()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.drumCableLength;
        }
    }
    public float getDrumEndOffset()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.drumEndOffset;
        }
    }
    public float getDrumQuadratureSensor()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.drumQuadratureSensor;
        }
    }
    public float getParachuteLift()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.parachuteLift;
        }
    }
    public float getParachuteDrag()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.parachuteDrag;
        }
    }
    public float getParachuteWeight()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.parachuteWeight;
        }
    }
    public float getAverageWindSpeed()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.averageWindSpeed;
        }
    }
    public float getGustWindSpeed()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gustWindSpeed;
        }
    }
    public float getWindDirection()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.windDirection;
        }
    }
    public float getWindDegreeOffset()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.windDegreeOffset;
        }
    }
    public float getHeadwindComponent()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.headwindComponent;
        }
    }
    public float getCrosswindComponent()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.crosswindComponent;
        }
    }
    public float getTemperature()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.temperature;
        }
    }
    public float getHumidity()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.humidity;
        }
    }
    public float getPressure()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.pressure;
        }
    }
    public float getCalculatedDensityAltitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.calculatedDensityAltitude;
        }
    }
    public float getDensityAltitude()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.densityAltitude;
        }
    }
    public float getRunLength()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.runLength;
        }
    }
    public float getRunSlope()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.runSlope;
        }
    }
    public float getRunHeading()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.runHeading;
        }
    }
    public float getGliderLaunchMass()
    {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderLaunchMass;
        }
    }

    public float getWindDirectionWinch() {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.winchWindDirection;
        }    
    }

    public float getWindSpeedWinch() {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.WinchWindSpeed;
        }    
    }

    public float getWindGustWinch() {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.winchWindGust;
        }    
    }

    public float getWindDirectionGlider() {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderWindDirection;
        }    
    }

    public float getWindGustGlider() {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderWindGust;
        }    
    }

    public float getWindSpeedGlider() {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.gliderWindSpeed;
        }    
    }

    public float getAltimeter() {
        if(instance == null)
        {
            return -1;
        }
        else
        {
            return instance.altimeter;
        }
    }
}
