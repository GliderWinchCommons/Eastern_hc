/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ParameterSelection;

/**
 *
 * @author Alex
 */
public class Preference {
    public static final String MILD = "Mild";
    public static final String NOMINAL = "Nominal";
    public static final String PERFORMANCE = "Performance";
    
    public static String convertPreferenceNumToString(int num) {
        if(num == 0)
            return MILD;
        else if(num == 1)
            return NOMINAL;
        else 
            return PERFORMANCE;
    }
    
    public static int convertPreferenceStringToNum(String preferenceString) {
        if(preferenceString.equals(MILD))
            return 0;
        else if(preferenceString.equals(NOMINAL))
            return 1;
        else 
            return 2;
    }
}
