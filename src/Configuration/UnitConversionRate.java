/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Configuration;

/**
 *
 * @author awilliams5
 */
public class UnitConversionRate {
    //Conversion factors from the standard SI unit to the given unit
    
    //Conversion rates for distances from the standard of a Meter
    public static final float METERS = 1;
    public static final float FEET = (float) 3.2804;
    public static final float MILLIMETERS = 1000;
    public static final float CENTIMETERS = 100;
    public static final float KILOMETERS = (float) 0.001;
    public static final float MILES = (float) 0.000621371;
    public static final float INCHES = (float) 39.3701;
    
    //Conversion rates for tension from the standard of a Newton
    public static final float NEWTON = 1;
    public static final float DECANEWTON = (float) 0.1;
    public static final float POUND_FORCE = (float) 0.224808943;
    public static final float KILOGRAM_FORCE = (float) 0.101971621;
    
    //Conversion rates for weight from the standard of a Pound
    public static final float POUND = (float) 2.2046244;
    public static final float KILOGRAM =  1;
    
    //Conversion rates for speed from the standard of km/h
    public static final float KPH = 1;
    public static final float MPH = (float) 0.621371;
    public static final float MPS = (float) 0.2777778;
    public static final float KN = (float) 0.539957;
        
    //Conversion rates for pressure from the standard of hPa
    public static final float HPA = 1;
    public static final float KPA = (float) 0.1;
    public static final float PSI = (float) 0.0145038;
    public static final float BAR = (float) 0.001;
    public static final float MILLIBAR = (float) 1;
    public static final float ATM = (float) 0.000986923;
    
    
    public static float convertTensionUnitIndexToFactor(int index) {
        switch (index) {
            case 0:
                return NEWTON;
            case 1:
                return POUND_FORCE;
            case 2:
                return KILOGRAM_FORCE;
            case 3:
                return DECANEWTON;   
            default:
                return NEWTON;
        }
    }  
    
    
    public static float convertDistanceUnitIndexToFactor(int index) {
        switch (index) {
            case 0:
                return METERS;
            case 1:
                return FEET;
            case 2:
                return MILLIMETERS;
            case 3:
                return CENTIMETERS;
            case 4:
                return KILOMETERS;
            case 5:
                return MILES;
            case 6:
                return INCHES;
            default:
                return METERS;
        }
    }    

    public static float convertWeightUnitIndexToFactor(int index) {
        switch (index) {
            case 0:
                return KILOGRAM;
            case 1:
                return POUND;
            default:
                return KILOGRAM;
        }
    }    
    
    public static float convertSpeedUnitIndexToFactor(int index) {
        switch (index) {
            case 0:
                return KPH;
            case 1:
                return MPH;
            case 2:
                return MPS; 
            case 3:
                return KN;
            case 4:
                return KN;
            default:
                return KPH;
        }
    }
    
    public static float convertPressureUnitIndexToFactor(int index) {
       switch (index) {
            case 0: 
                return HPA;
            case 1:
                return KPA;
            case 2:
                return PSI;
            case 3:
                return BAR;
            case 4:
                return MILLIBAR; 
            case 5:
                return ATM; 
            default:
                return HPA;
        } 
    }
}
