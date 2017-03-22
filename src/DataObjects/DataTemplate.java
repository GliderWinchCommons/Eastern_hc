/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

/**
 *
 * @author gene
 */
public class DataTemplate {

    private int   capability;                       //Pilot's capability
    private float preference;                       //Pilot's launch preference
    
    private float maximumGrossWeight;               //max weight plane can carry
    private float indicatedStallSpeed;              //stall speed of the plane
    private float maximumWinchingSpeed;             //max winching speed of the plane
    private float maximumAllowableWeakLinkStrength; //max weak link of the plane
    private float maximumTension;                   //max tension of the plane
    private float cableReleaseAngle;                //cable release angle of the plane
    
    private float gliderElevation;                  //glider distance from sea level
    private float gliderLatitude;                   //glider global y coordinate
    private float gliderLongitude;                  //glider global x coordinate
    
    private float winchElevation;                   //winch distance from sea level
    private float winchLatitude;                    //winch global y coordinate
    private float winchLongitude;                   //winch global x coordinate
    
    private float coreDiameter;                     //Drum's core diameter
    private float kFactor;                          //Drum's K-Factor
    private float cableLength;                      //Length of the cable
    private float cableDensity;                     //Density of the cable
    private float systemEquivalentMass;             //Drum System Equivalent Mass
    private float maxTension;                       //Maximum tension the cable can happen
    
    private float lift;                             //how much lift he parachute has
    private float drag;                             //how much drag the parachute creates
    private float parachuteWeight;                  //how much the parachute weighs
    
    private float gliderWind;                       //average wind speed at glider
    private float gliderGust;                       //gust speeds at glider
    private float gliderWindDirection;              //direction of wind at glider
    private float winchWind;                        //average wind speed at winch
    private float winchGust;                        //gust speeds at winch
    private float winchWindDirection;               //direction of wind at winch
    
    private float densityAltitude;                  //air density as a hight above sea level
    private float temp;                             //current tempeture
    private float altimeterSettings;                //local air pressure

    private float flightWeight;                     //Pilot's weight
    private float emptyWeight;                      //empty weight of the plane
    private float baggage;                          //weight of the planes baggage
    private float ballast;                          //planes ballest
    private float passenger;                        //planes passenger weight

    //16 parameters set by an administrator
    private float w1, w2, w3, w4, w5, w6, w7, w8,
            w9, w10, w11, w12, w13, w14, w15, w16;

    public DataTemplate(int capability, float preference, 
            float maximumGrossWeight, float indicatedStallSpeed, 
            float maximumWinchingSpeed, float maximumAllowableWeakLinkStrength, 
            float maximumTension, float cableReleaseAngle, 
            float gliderElevation, float gliderLatitude, 
            float gliderLongitude, float winchElevation, 
            float winchLatitude, float winchLongitude, float coreDiameter, 
            float kFactor, float cableLength, float cableDensity, 
            float systemEquivalentMass, float maxTension, 
            float lift, float drag, float parachuteWeight, 
            float gliderWind, float gliderGust, float gliderWindDirection, 
            float winchWind, float winchGust, float winchWindDirection, 
            float densityAltitude, float temp, float altimeterSettings, 
            float flightWeight, float emptyWeight, float baggage, 
            float ballast, float passenger, float w1, float w2, 
            float w3, float w4, float w5, float w6, float w7, float w8, 
            float w9, float w10, float w11, float w12, float w13, 
            float w14, float w15, float w16) {
        this.capability = capability;
        this.preference = preference;
        this.maximumGrossWeight = maximumGrossWeight;
        this.indicatedStallSpeed = indicatedStallSpeed;
        this.maximumWinchingSpeed = maximumWinchingSpeed;
        this.maximumAllowableWeakLinkStrength = maximumAllowableWeakLinkStrength;
        this.maximumTension = maximumTension;
        this.cableReleaseAngle = cableReleaseAngle;
        this.gliderElevation = gliderElevation;
        this.gliderLatitude = gliderLatitude;
        this.gliderLongitude = gliderLongitude;
        this.winchElevation = winchElevation;
        this.winchLatitude = winchLatitude;
        this.winchLongitude = winchLongitude;
        this.coreDiameter = coreDiameter;
        this.kFactor = kFactor;
        this.cableLength = cableLength;
        this.cableDensity = cableDensity;
        this.systemEquivalentMass = systemEquivalentMass;
        this.maxTension = maxTension;
        this.lift = lift;
        this.drag = drag;
        this.parachuteWeight = parachuteWeight;
        this.gliderWind = gliderWind;
        this.gliderGust = gliderGust;
        this.gliderWindDirection = gliderWindDirection;
        this.winchWind = winchWind;
        this.winchGust = winchGust;
        this.winchWindDirection = winchWindDirection;
        this.densityAltitude = densityAltitude;
        this.temp = temp;
        this.altimeterSettings = altimeterSettings;
        this.flightWeight = flightWeight;
        this.emptyWeight = emptyWeight;
        this.baggage = baggage;
        this.ballast = ballast;
        this.passenger = passenger;
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
    
    public float geWeight() {
        return emptyWeight + flightWeight + ballast + passenger + parachuteWeight + baggage;
    }

    public int getCapability() {
        return capability;
    }

    public float getPreference() {
        return preference;
    }

    public float getMaximumGrossWeight() {
        return maximumGrossWeight;
    }

    public float getIndicatedStallSpeed() {
        return indicatedStallSpeed;
    }

    public float getMaximumWinchingSpeed() {
        return maximumWinchingSpeed;
    }

    public float getMaximumAllowableWeakLinkStrength() {
        return maximumAllowableWeakLinkStrength;
    }

    public float getMaximumTension() {
        return maximumTension;
    }

    public float getCableReleaseAngle() {
        return cableReleaseAngle;
    }

    public float getGliderElevation() {
        return gliderElevation;
    }

    public float getGliderLatitude() {
        return gliderLatitude;
    }

    public float getGliderLongitude() {
        return gliderLongitude;
    }

    public float getWinchElevation() {
        return winchElevation;
    }

    public float getWinchLatitude() {
        return winchLatitude;
    }

    public float getWinchLongitude() {
        return winchLongitude;
    }

    public float getCoreDiameter() {
        return coreDiameter;
    }

    public float getkFactor() {
        return kFactor;
    }

    public float getCableLength() {
        return cableLength;
    }

    public float getCableDensity() {
        return cableDensity;
    }

    public float getSystemEquivalentMass() {
        return systemEquivalentMass;
    }

    public float getMaxTension() {
        return maxTension;
    }

    public float getLift() {
        return lift;
    }

    public float getDrag() {
        return drag;
    }

    public float getGliderWind() {
        return gliderWind;
    }

    public float getGliderGust() {
        return gliderGust;
    }

    public float getGliderWindDirection() {
        return gliderWindDirection;
    }

    public float getWinchWind() {
        return winchWind;
    }

    public float getWinchGust() {
        return winchGust;
    }

    public float getWinchWindDirection() {
        return winchWindDirection;
    }

    public float getDensityAltitude() {
        return densityAltitude;
    }

    public float getTemp() {
        return temp;
    }

    public float getAltimeterSettings() {
        return altimeterSettings;
    }

    public float getW1() {
        return w1;
    }

    public float getW2() {
        return w2;
    }

    public float getW3() {
        return w3;
    }

    public float getW4() {
        return w4;
    }

    public float getW5() {
        return w5;
    }

    public float getW6() {
        return w6;
    }

    public float getW7() {
        return w7;
    }

    public float getW8() {
        return w8;
    }

    public float getW9() {
        return w9;
    }

    public float getW10() {
        return w10;
    }

    public float getW11() {
        return w11;
    }

    public float getW12() {
        return w12;
    }

    public float getW13() {
        return w13;
    }

    public float getW14() {
        return w14;
    }

    public float getW15() {
        return w15;
    }

    public float getW16() {
        return w16;
    }

    

}
