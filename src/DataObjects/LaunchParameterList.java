/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

/**
 *
 * @author Homero/ Gene
 */
 
public class LaunchParameterList {
    
    //Pilot 
    private int   capability;                       //Pilot's capability
    private float preference;                       //Pilot's launch preference
    private float flightWeight;                     //Pilot's weight
    
    //Glider 
    private float maxGrossWeight;                   //max weight of the plane
    private float passengerWeight;                  //the weight of the passenger
    private float emptyWeight;                      //empty weight of the plane
    private float maximumWinchingSpeed;             //max winching speed of the plane
    private float indicatedStallSpeed;              //stall speed of the plane
    private float maximumAllowableWeakLinkStrength; //max weak link of the plane
    private float maximumTension;                   //max tension of the plane
    private float cableReleaseAngle;                //cable release angle of the plane
    private float baggage;                          //weight of the planes baggage
    private float ballast;                          //planes ballest
    
    //GliderPosition
    private float gliderElevation;                  //elevation at glider position
    private float gliderLat;                        //latitude at glider position
    private float gliderLng;                        //longitude at glider position
    
    //Winch Postion
    private float winchElevation;                   //elevation at winch position
    private float winchLat;                         //latitude at winch position
    private float winchLng;                         //longitude at winch position
    
    //Drum
    private float coreDiameter;                     //Drum's core diameter
    private float kFactor;                          //Drum's K-Factor
    private float cableLength;                      //Length of the cable
    private float cableDensity;                     //Density of the cable
    private float systemEquivalentMass;             //Drum System Equivalent Mass
    private float maxTension;                       //Maximum tension the cable can happen
    
    //Parachute 
    private float lift;                             //how much lift he parachute has
    private float drag;                             //how much drag the parachute creates
    private float parachuteWeight;                  //how much the parachute weighs

    //Meteorological winch
    private float winchAverageWindSpeed;             //Average wind speed at winch position
    private float winchGustSpeed;                    //gust speed at winch position
    private float winchWindDirection;                //wind direction at winch position
    
    //Meteorological glider
    private float gliderAverageWindSpeed;            //Average wind speed at glider position
    private float gliderGustSpeed;                   //gust speed at glider position
    private float gliderWindDirection;               //wind direction at glider position
    
    //Meteorological
    private float densityAltitude;
    private float temp;                             //temperature
    private float altimeterSetting;

    //16 parameters set by an administrator
    private float w1, w2, w3, w4, w5, w6, w7, w8,
            w9, w10, w11, w12, w13, w14, w15, w16;

    public LaunchParameterList()
    {
        this.capability = 0;
        this.preference = 0;
        this.indicatedStallSpeed = 0;
        this.maximumWinchingSpeed = 0;
        this.maximumAllowableWeakLinkStrength = 0;
        this.maximumTension = 0;
        this.cableReleaseAngle = 0;
        this.coreDiameter = 0;
        this.kFactor = 0;
        this.cableLength = 0;
        this.cableDensity = 0;
        this.systemEquivalentMass = 0;
        this.maxTension = 0;
        this.lift = 0;
        this.drag = 0;
        this.parachuteWeight = 0;
        this.flightWeight = 0;
        this.emptyWeight = 0;
        this.baggage = 0;
        this.ballast = 0;
        this.maxGrossWeight = 0;                          
        this.passengerWeight = 0;
        this.gliderElevation = 0;                          
        this.gliderLat = 0;                                  
        this.gliderLng = 0;
        this.winchElevation = 0;                          
        this.winchLat = 0;                                  
        this.winchLng = 0;
        this.gliderAverageWindSpeed = 0;
        this.gliderGustSpeed = 0;
        this.gliderWindDirection = 0;
        this.winchAverageWindSpeed = 0;
        this.winchGustSpeed = 0;
        this.winchWindDirection = 0;
        this.densityAltitude = 0;
        this.temp =0;
        this.altimeterSetting = 0;
        this.w1 = 0;
        this.w2 = 0;
        this.w3 = 0;
        this.w4 = 0;
        this.w5 = 0;
        this.w6 = 0;
        this.w7 = 0;
        this.w8 = 0;
        this.w9 = 0;
        this.w10 = 0;
        this.w11 = 0;
        this.w12 = 0;
        this.w13 = 0;
        this.w14 = 0;
        this.w15 = 0;
        this.w16 = 0;
    }
    
    
    
    public LaunchParameterList(int capability, float preference,float maxGrossWeight,float passengerWeight,float densityAltitude,float temp, float altimeterSetting, float winchAverageWindSpeed, float winchGustSpeed, float winchWindDirection,float gliderAverageWindSpeed, float gliderGustSpeed, float gliderWindDirection,float winchElevation, float winchLat, float winchLng, float gliderElevation, float gliderLat, float gliderLng,float indicatedStallSpeed, float maximumWinchingSpeed, float maximumAllowableWeakLinkStrength, float maximumTension, float cableReleaseAngle, float coreDiameter, float kFactor, float cableLength, float cableDensity, float systemEquivalentMass, float maxTension, float lift, float drag, float parachuteWeight, float flightWeight, float emptyWeight, float baggage, float ballast, float w1, float w2, float w3, float w4, float w5, float w6, float w7, float w8, float w9, float w10, float w11, float w12, float w13, float w14, float w15, float w16) {
        this.capability = capability;
        this.preference = preference;
        this.indicatedStallSpeed = indicatedStallSpeed;
        this.maximumWinchingSpeed = maximumWinchingSpeed;
        this.maximumAllowableWeakLinkStrength = maximumAllowableWeakLinkStrength;
        this.maximumTension = maximumTension;
        this.cableReleaseAngle = cableReleaseAngle;
        this.coreDiameter = coreDiameter;
        this.kFactor = kFactor;
        this.cableLength = cableLength;
        this.cableDensity = cableDensity;
        this.systemEquivalentMass = systemEquivalentMass;
        this.maxTension = maxTension;
        this.lift = lift;
        this.drag = drag;
        this.parachuteWeight = parachuteWeight;
        this.flightWeight = flightWeight;
        this.emptyWeight = emptyWeight;
        this.baggage = baggage;
        this.ballast = ballast;
        this.maxGrossWeight = maxGrossWeight;                           
        this.passengerWeight = passengerWeight;
        this.gliderElevation = gliderElevation;                          
        this.gliderLat = gliderLat;                                  
        this.gliderLng = gliderLng;
        this.winchElevation = winchElevation;                          
        this.winchLat = winchLat;                                  
        this.winchLng = winchLng;
        this.gliderAverageWindSpeed = gliderAverageWindSpeed;
        this.gliderGustSpeed = gliderGustSpeed;
        this.gliderWindDirection = gliderWindDirection;
        this.winchAverageWindSpeed = winchAverageWindSpeed;
        this.winchGustSpeed = winchGustSpeed;
        this.winchWindDirection = winchWindDirection;
        this.densityAltitude = densityAltitude;
        this.temp =temp;
        this.altimeterSetting = altimeterSetting;
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
        this.w5 = w5;
        this.w6 = w6;
        this.w7 = w7;
        this.w8 = w8;
        this.w9 = w9;
        this.w10 = w10;
        this.w11 = w11;
        this.w12 = w12;
        this.w13 = w13;
        this.w14 = w14;
        this.w15 = w15;
        this.w16 = w16;
    }
    
    
        
    
    /**
     * @return density altitude
     */
    public float getDensityAltitude() {
        return densityAltitude;
    }    
    
    /**
     * @return temperature
     */
    public float getTemp() {
        return temp;
    }
    
    /**
     * @return altimeter Setting
     */
    public float getAltimeterSetting() {
        return altimeterSetting;
    }
    
    /**
     * @return the winch average wind speed;
     */
    public float getWinchAverageWindSpeed() {
        return winchAverageWindSpeed;
    }
    
    /**
     * @return the winch gust speed;
     */
    public float getWinchGustSpeed() {
        return winchGustSpeed;
    }
    
    /**
     * @return the winchElevation;
     */
    public float getwinchWindDirection() {
        return winchWindDirection;
    }
    
    /**
     * @return the glider average wind speed;
     */
    public float getGliderAverageWindSpeed() {
        return gliderAverageWindSpeed;
    }
    
    /**
     * @return the glider gust speed;
     */
    public float getGliderGustSpeed() {
        return gliderGustSpeed;
    }
    
    /**
     * @return the glider wind direction;
     */
    public float getGliderWindDirection() {
        return gliderWindDirection;
    }
    
    /**
     * @return the gliderElevation;
     */
    public float getGliderElevation() {
        return gliderElevation;
    }
    
    /**
     * @return the glider latitude;
     */
    public float getGliderLat() {
        return gliderLat;
    }
    
    /**
     * @return the glider longitude;
     */
    public float getGliderLng() {
        return gliderLng;
    }
    
    /**
     * @return the winch Elevation;
     */
    public float getWinchElevation() {
        return winchElevation;
    }
    
    /**
     * @return the winch latitude;
     */
    public float getWinchLat() {
        return winchLat;
    }
    
    /**
     * @return the winch longitude;
     */
    public float getWinchLng() {
        return winchLng;
    }
    
    /**
     * @return the flightWeight
     */
    public float getFlightWeight() {
        return flightWeight;
    }
    
    /**
     * @return the emptyWeight
     */
    public float getEmptyWeight() {
        return emptyWeight;
    }
    
    /**
     * @return the baggage
     */
    public float getBaggage() {
        return baggage;
    }
    
    /**
     * @return the ballast
     */
    public float getbBallast() {
        return ballast;
    }
    
    /**
     * @return the maxGrossWeight
     */
    public float getMaxGrossWeight() {
        return maxGrossWeight;
    }
    
    /**
     * @return the passengerWeight
     */
    public float getPassengerWeight() {
        return passengerWeight;
    }
    
    /**
     * @return the capability
     */
    public int getCapability() {
        return capability;
    }

    /**
     * @return the preference
     */
    public float getPreference() {
        return preference;
    }

    /**
     * @return the indicatedStallSpeed
     */
    public float getIndicatedStallSpeed() {
        return indicatedStallSpeed;
    }

    /**
     * @return the maximumWinchingSpeed
     */
    public float getMaximumWinchingSpeed() {
        return maximumWinchingSpeed;
    }

    /**
     * @return the maximumAllowableWeakLinkStrength
     */
    public float getMaximumAllowableWeakLinkStrength() {
        return maximumAllowableWeakLinkStrength;
    }

    /**
     * @return the maximumTension
     */
    public float getMaximumTension() {
        return maximumTension;
    }

    /**
     * @return the cableReleaseAngle
     */
    public float getCableReleaseAngle() {
        return cableReleaseAngle;
    }

    /**
     * @return the coreDiameter
     */
    public float getCoreDiameter() {
        return coreDiameter;
    }

    /**
     * @return the kFactor
     */
    public float getkFactor() {
        return kFactor;
    }

    /**
     * @return the cableLength
     */
    public float getCableLength() {
        return cableLength;
    }

    /**
     * @return the cableDensity
     */
    public float getCableDensity() {
        return cableDensity;
    }

    /**
     * @return the systemEquivalentMass
     */
    public float getSystemEquivalentMass() {
        return systemEquivalentMass;
    }

    /**
     * @return the maxTension
     */
    public float getMaxTension() {
        return maxTension;
    }

    /**
     * @return the lift
     */
    public float getLift() {
        return lift;
    }

    /**
     * @return the drag
     */
    public float getDrag() {
        return drag;
    }

    /**
     * @return the weight
     */
    public float getParachuteWeight() {
        return parachuteWeight;
    }

    /**
     * @return the w1
     */
    public float getW1() {
        return w1;
    }

    /**
     * @return the w2
     */
    public float getW2() {
        return w2;
    }

    /**
     * @return the w3
     */
    public float getW3() {
        return w3;
    }

    /**
     * @return the w4
     */
    public float getW4() {
        return w4;
    }

    /**
     * @return the w5
     */
    public float getW5() {
        return w5;
    }

    /**
     * @return the w6
     */
    public float getW6() {
        return w6;
    }

    /**
     * @return the w7
     */
    public float getW7() {
        return w7;
    }

    /**
     * @return the w8
     */
    public float getW8() {
        return w8;
    }

    /**
     * @return the w9
     */
    public float getW9() {
        return w9;
    }

    /**
     * @return the w10
     */
    public float getW10() {
        return w10;
    }

    /**
     * @return the w11
     */
    public float getW11() {
        return w11;
    }

    /**
     * @return the w12
     */
    public float getW12() {
        return w12;
    }

    /**
     * @return the w13
     */
    public float getW13() {
        return w13;
    }

    /**
     * @return the w14
     */
    public float getW14() {
        return w14;
    }

    /**
     * @return the w15
     */
    public float getW15() {
        return w15;
    }

    /**
     * @return the w16
     */
    public float getW16() {
        return w16;
    }
    
    /**
     * @set density altitude
     */
    public void setDensityAltitude(float densityAltitude) {
        this.densityAltitude = densityAltitude;
    }    
    
    /**
     * @set temperature
     */
    public void setTemp(float temp) {
        this.temp = temp;
    }
    
    /**
     * @set altimeter Setting
     */
    public void setAltimeterSetting(float altimeterSetting) {
        this.altimeterSetting = altimeterSetting;
    }
    
    
    /**
     * @set glider average wind speed;
     */
    public void setGliderAverageWindSpeed(float gliderAverageWindSpeed) {
         this.gliderAverageWindSpeed = gliderAverageWindSpeed ;
    }
    
    /**
     * @set glider gust speed;
     */
    public void setGliderGustSpeed(float gliderGustSpeed) {
        this.gliderGustSpeed =  gliderGustSpeed;
    }
    
    /**
     * @set glider wind direction;
     */
    public void setGliderWindDirection(float gliderWindDirection) {
        this.gliderWindDirection = gliderWindDirection;
    }
    
    
    /**
     * @set winch average wind speed;
     */
    public void setWinchAverageWindSpeed(float winchAverageWindSpeed) {
         this.winchAverageWindSpeed = winchAverageWindSpeed ;
    }
    
    /**
     * @set winch gust speed;
     */
    public void setWinchGustSpeed(float winchGustSpeed) {
        this.winchGustSpeed =  winchGustSpeed;
    }
    
    /**
     * @set gliderElevation;
     */
    public void setWinchWindDirection(float winchWindDirection) {
        this.winchWindDirection = winchWindDirection;
    }
    
    
    /**
     * @set gliderElevation;
     */
    public void setGliderElevation(float gliderElevation) {
        this.gliderElevation = gliderElevation;
    }
    
    /**
     * @set glider latitude;
     */
    public void setGliderLat(float gliderLat) {
        this.gliderLat = gliderLat;
    }
    
    /**
     * @set glider longitude;
     */
    public void setGliderLng(float gliderLng) {
        this.gliderLng = gliderLng;
    }
    
    /**
     * @set winchElevation;
     */
    public void setWinchElevation(float winchElevation) {
        this.winchElevation = winchElevation;
    }
    
    /**
     * @set winch latitude;
     */
    public void setWinchLat(float winchLat) {
        this.winchLat = winchLat;
    }
    
    /**
     * @set glider longitude;
     */
    public void setWinchLng(float winchLng) {
        this.winchLng = winchLng;
    }
    
    /**
     * @set flightWeight
     */
    public void setFlightWeight(float flightWeight) {
        this.flightWeight = flightWeight;
    }
    
    /**
     * @set flightWeight
     */
    public void setEmptyWeight(float emptyWeight) {
        this.emptyWeight = emptyWeight;
    }
    
    /**
     * @set flightWeight
     */
    public void setBaggage(float baggage) {
        this.baggage = baggage;
    }
    
    /**
     * @set flightWeight
     */
    public void setBallast(float ballast) {
        this.ballast = ballast;
    }
    
    /**
     * @set maxGrossWeight
     */
    public void setMaxGrossWeight(float maxGrossWeight) {
        this.maxGrossWeight = maxGrossWeight;
    }
    
    /**
     * @set passengerWeight
     */
    public void setPassengerWeight(float passengerWeight) {
        this.passengerWeight = passengerWeight;
    }

    /**
     * @set capability
     */
    public void setCapability(int capability) {
        this.capability = capability;
    }

    /**
     * @set preference
     */
    public void setPreference(float preference) {
        this.preference = preference;
    }

    /**
     * set indicatedStallSpeed
     */
    public void setIndicatedStallSpeed(float indicatedStallSpeed) {
        this.indicatedStallSpeed = indicatedStallSpeed;
    }

    /**
     * @set maximumWinchingSpeed
     */
    public void setMaximumWinchingSpeed(float maximumWinchingSpeed) {
        this.maximumWinchingSpeed = maximumWinchingSpeed;
    }

    /**
     * @set maximumAllowableWeakLinkStrength
     */
    public void setMaximumAllowableWeakLinkStrength(float maximumAllowableWeakLinkStrength) {
        this.maximumAllowableWeakLinkStrength = maximumAllowableWeakLinkStrength;
    }

    /**
     * @set maximumTension
     */
    public void setMaximumTension(float maximumTension) {
        this.maximumTension = maximumTension;
    }

    /**
     * @set cableReleaseAngle
     */
    public void setCableReleaseAngle(float cableReleaseAngle) {
        this.cableReleaseAngle = cableReleaseAngle;
    }

    /**
     * @set coreDiameter
     */
    public void setCoreDiameter(float coreDiameter) {
        this.coreDiameter = coreDiameter;
    }

    /**
     * @set kFactor
     */
    public void setkFactor(float kFactor) {
        this.kFactor = kFactor;
    }

    /**
     * @set cableLength
     */
    public void setCableLength(float cableLength) {
        this.cableLength = cableLength;
    }

    /**
     * @set cableDensity
     */
    public void setCableDensity(float cableDensity) {
        this.cableDensity = cableDensity;
    }

    /**
     * @set systemEquivalentMass
     */
    public void setSystemEquivalentMass(float systemEquivalentMass) {
        this.systemEquivalentMass = systemEquivalentMass;
    }

    /**
     * @set maxTension
     */
    public void setMaxTension(float maxTension) {
        this.maxTension = maxTension;
    }

    /**
     * @set lift
     */
    public void  setLift(float lift) {
        this.lift = lift;
    }

    /**
     * @set drag
     */
    public void setDrag(float drag) {
        this.drag = drag;
    }

    /**
     * @set Parachute Weight
     */
    public void setParachuteWeight(float parachuteWeight) {
        this.parachuteWeight = parachuteWeight;
    }

    /**
     * @set w1
     */
    public void setW1(float w1) {
        this.w1 = w1;
    }

    /**
     * @set w2
     */
    public void setW2(float w2) {
        this.w2 = w2;
    }

    /**
     * @set w3
     */
    public void setW3(float w3) {
        this.w3 = w3;
    }
   
    /**
     * @set w4
     */
    public void setW4(float w4) {
        this.w4 = w4;
    }

    /**
     * @set w5
     */
    public void setW5(float w5) {
        this.w5 = w5;
    }

   /**
     * @set w6
     */
    public void setW6(float w6) {
        this.w6 = w6;
    }

    /**
     * @set w7
     */
    public void setW7(float w7) {
        this.w7 = w7;
    }
    /**
    /**
     * @set w8
     */
    public void setW8(float w8) {
        this.w8 = w8;
    }

   /**
     * @set w9
     */
    public void setW9(float w9) {
        this.w9 = w9;
    }

    /**
     * @set w10
     */
    public void setW10(float w10) {
        this.w10 = w10;
    }

    /**
     * @set w11
     */
    public void setW11(float w11) {
        this.w11 = w11;
    }

    /**
     * @set w12
     */
    public void setW12(float w12) {
        this.w12 = w12;
    }

    /**
     * @set w13
     */
    public void setW13(float w13) {
        this.w13 = w13;
    }

    /**
     * @set w14
     */
    public void setW14(float w14) {
        this.w14 = w14;
    }
    

    /**
     * @set w15
     */
    public void setW15(float w15) {
        this.w15 = w15;
    }

    /**
     * @set w16
     */
    public void setW16(float w16) {
        this.w16 = w16;
    }

    
    
    
    
    
}