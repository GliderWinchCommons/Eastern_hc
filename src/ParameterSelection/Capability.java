/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ParameterSelection;

/**
 *
 * @author awilliams5
 */
public class Capability {
    public static final String STUDENT = "Student";
    public static final String PROFICIENT = "Proficient";
    public static final String ADVANCED = "Advanced";
    
    public static String convertCapabilityNumToString(int num) {
        if(num == 2)
            return ADVANCED;
        else if(num == 1)
            return PROFICIENT;
        else 
            return STUDENT;
    }
    
    public static int convertCapabilityStringToNum(String preferenceString) {
        if(preferenceString.equals(STUDENT))
            return 0;
        else if(preferenceString.equals(PROFICIENT))
            return 1;
        else 
            return 2;
    }
}
