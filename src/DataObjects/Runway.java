/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataObjects;

/**
 * This Class stores the data about a Runway
 * 
 * @author garreola-gutierrez, matt dargen, dbennett3, Noah Fujioka
 */
public class Runway {
    private int id;                 //randomly genterated id
    private int parentId;           //airfield's random id
    private String runwayName;      //runway's name
    private float magneticHeading;  //degrees from magnetic north
    private String optionalInfo;
    
    //constructers
    
    public Runway(String runwayName, float magneticHeading, String optional){
       this.runwayName = runwayName;
       this.magneticHeading = magneticHeading;
       this.optionalInfo = optional;
    }
    
    public Runway(int id, int pid, String runwayName, float magneticHeading, String optional){
       this.id = id;
       this.parentId = pid;
       this.runwayName = runwayName;
       this.magneticHeading = magneticHeading;
       this.optionalInfo = optional;
    }
    
    
    //getters and setters
    public int getId(){
        return id;
    }
    
    public void setId(int newId){
        id = newId;
    }
    
    public String getName(){
       return runwayName; 
    }
    
    public float getMagneticHeading(){
       return magneticHeading; 
    }
    
    public int getParentId(){
        return parentId;
    }
    
    public void setParentId(int newParentId){
        parentId = newParentId;
    }    
    
    public String getOptionalInfo() {
        return optionalInfo;
    }
    
    @Override
    public String toString() {
      return runwayName;
    } 
    
    //check to see if the object is valid
    public boolean validate() {
        return !runwayName.equals("");
    }
}
