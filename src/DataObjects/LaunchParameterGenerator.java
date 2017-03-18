/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

import ParameterSelection.EnvironmentalWindow;

/**
 *
 * @author HGJ
 */
public class LaunchParameterGenerator {

    private LaunchParameterList lpGen;

    public LaunchParameterGenerator() {

        // creates a blank templates populated with 0's
        this.lpGen = new LaunchParameterList();

        // creates an object comprised of the current scenario set
        CurrentDataObjectSet currentSet = CurrentDataObjectSet.getCurrentDataObjectSet();

        // creates a temporary pilot object  from the current scenario set
        Pilot pilot = currentSet.getCurrentPilot();
        CurrentLaunchInformation info = CurrentLaunchInformation.getCurrentLaunchInformation();
        /*if a valid pilot has not been selected then a null value is passed back
        *meaning a template acan not be generated
         */
        if (pilot != null) {
            this.lpGen.setCapability(info.getPilotCapacity());
            this.lpGen.setFlightWeight(pilot.getWeight());
            this.lpGen.setPreference(pilot.getPreference());

        }

        // creates a temporary pilot object  from the current scenario set
        Sailplane glider = currentSet.getCurrentSailplane();

        /*if a valid glider has not been selected then a null value is passed back
        *meaning a template acan not be generated
         */
        if (glider != null) {

            this.lpGen.setMaxGrossWeight(glider.getMaxGrossWeight());
            this.lpGen.setPassengerWeight(info.getPassengerWeight());
            this.lpGen.setEmptyWeight(glider.getEmptyWeight());
            this.lpGen.setMaximumWinchingSpeed(glider.getMaxWinchingSpeed());
            this.lpGen.setIndicatedStallSpeed(glider.getIndicatedStallSpeed());
            this.lpGen.setMaximumAllowableWeakLinkStrength(glider.getMaxWeakLinkStrength());
            this.lpGen.setMaximumTension(glider.getMaxTension());
            this.lpGen.setCableReleaseAngle(glider.getCableReleaseAngle());
            this.lpGen.setBaggage(info.getGliderBaggage());
            this.lpGen.setBallast(glider.getBallastWeight());

        }

        // creates a temporary gliderPostion object  from the current scenario set
        GliderPosition gliderPosition = currentSet.getCurrentGliderPosition();
        /*if a valid gliderPosition has not been selected then a null value is passed back
        *meaning a template acan not be generated
         */
        if (gliderPosition != null) {
            this.lpGen.setGliderElevation(gliderPosition.getElevation());
            this.lpGen.setGliderLat(gliderPosition.getLatitude());
            this.lpGen.setGliderLng(gliderPosition.getLongitude());
        }

        // creates a temporary winchPostion object  from the current scenario set
        WinchPosition winchPosition = currentSet.getCurrentWinchPosition();
        /*if a valid winchPosition has not been selected then a null value is passed back
        *meaning a template acan not be generated
         */
        if (winchPosition != null) {
            this.lpGen.setWinchElevation(winchPosition.getElevation());
            this.lpGen.setWinchLat(winchPosition.getLatitude());
            this.lpGen.setWinchLng(winchPosition.getLongitude());
        }

        // creates a temporary drum object  from the current scenario set
        Drum drum = currentSet.getCurrentDrum();
        //if a valid drumn has not been selected then a null value is passed back
        //  *meaning a template acan not be generated

        if (drum != null) {
            this.lpGen.setCoreDiameter(drum.getCoreDiameter());
            this.lpGen.setkFactor(drum.getKFactor());
            this.lpGen.setCableLength(drum.getCableLength());
            this.lpGen.setCableDensity(drum.getCableDensity());
            this.lpGen.setSystemEquivalentMass(drum.getSystemEquivalentMass());
            this.lpGen.setMaxTension(drum.getMaxTension());
            this.lpGen.setLift(drum.getParachute().getLift());
            this.lpGen.setDrag(drum.getParachute().getDrag());
            this.lpGen.setParachuteWeight(drum.getParachute().getWeight());
        }

        //get current winch meteorlogical data
        this.lpGen.setWinchAverageWindSpeed(info.getWindSpeedWinch());
        this.lpGen.setWinchGustSpeed(info.getWindGustWinch());
        this.lpGen.setWinchWindDirection(info.getWindDirectionWinch());

        //get currnet glider meteorlogical data
        this.lpGen.setGliderAverageWindSpeed(info.getWindSpeedGlider());
        this.lpGen.setGliderGustSpeed(info.getWindGustGlider());
        this.lpGen.setGliderWindDirection(info.getWindDirectionGlider());

        //get Meteorological data
        this.lpGen.setDensityAltitude(info.getDensityAltitude());
        this.lpGen.setTemp(info.getTemperature());
        this.lpGen.setAltimeterSetting(info.getAltimeter());

    }

    // Uses template data fields to gerenate launch parameters written by george
    public float[] GenerateParameters() {
        //temporary float arrray used for testing with paramters

        float[] launchparameter_flt = new float[150];
        for (int x = 0; x < launchparameter_flt.length; x++) {
            launchparameter_flt[x] = (float) 0.1 + x;
        }

        return launchparameter_flt;
    }

    public boolean CanGenerate() {
        CurrentDataObjectSet currentSet = CurrentDataObjectSet.getCurrentDataObjectSet();
        return currentSet.check() && EnvironmentalWindow.validate();
    }
}
