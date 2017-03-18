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
public class UnitLabelUtilities {
    public static String lenghtUnitIndexToString(int index) {
        switch (index) {
            case 0:
                return "m";
            case 1:
                return "ft";
            case 2: 
                return "mm";
            case 3:
                return "cm";
            case 4:
                return "km";
            case 5:
                return "mi";
            case 6:
                return "in";
            default:
                return "m";
        }
    }
    
    public static String tensionUnitIndexToString(int index) {
       switch (index) {
            case 0:
                return "N";
            case 1:
                return "lbf";
            case 2: 
                return "kgf";
            case 3:
                return "daN";
            default:
                return "N";
        } 
    }
    
    public static String weightUnitIndexToString(int index) {
       switch (index) {
            case 0:
                return "kg";
            case 1:
                return "lbs";
            default:
                return "kg";
        } 
    }
    
    public static String velocityUnitIndexToString(int index) {
       switch (index) {
            case 0:
                return "kph";
            case 1:
                return "mph";
            case 2: 
                return "m/s";
            case 3:
                return "kn";
            case 4:
                return "kts";
            default:
                return "kph";
        } 
    }
    
    public static String degreesUnitIndexToString(int index) {
       switch (index) {
            case 0:
                return "true";
            case 1:
                return "magnetic";
            case 2:
                return "relative";
            default:
                return "true";
        } 
    }
    
    public static String tempUnitIndexToString(int index) {
       switch (index) {
            case 0:
                return "C";
            case 1:
                return "F";
            default:
                return "C";
        } 
    }
    
    public static String pressureUnitIndexToString(int index) {
       switch (index) {
            case 0: 
                return "hPa";
            case 1:
                return "kPa";
            case 2:
                return "psi";
            case 3:
                return "bar";  
            case 4:
                return "millibar";  
            case 5:
                return "atm"; 
            default:
                return "hPa";
        } 
    }
}
