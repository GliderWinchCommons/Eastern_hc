/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

/**
 * This class stores info on the position of the winch
 *
 * @author dbennett3, Noah Fujioka
 */
public class WinchPosition {

    private int id;                 //random id
    private int runwayParentId;     //parent runway random id
    private String name;            //runway's name
    private float elevation;         //distance from sea level
    private float latitude;         //global y coordinate
    private float longitude;        //global x coordinate
    private String optionalInfo;

    //constructers
    public WinchPosition(String name, float altitude, float latitude, float longitude, String optional) {
        this.name = name;
        this.elevation = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
        this.optionalInfo = optional;
    }

    public WinchPosition(int id, int pid, String name, float altitude,
            float latitude, float longitude, String optional) {
        this.id = id;
        this.runwayParentId = pid;
        this.name = name;
        this.elevation = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
        this.optionalInfo = optional;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int newId) {
        id = newId;
    }

    public String getName() {
        return name;
    }

    public int getRunwayParentId() {
        return runwayParentId;
    }

    public void setRunwayParentId(int newRunwayParentId) {
        runwayParentId = newRunwayParentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setOptionalInfo(String optionalInfo) {
        this.optionalInfo = optionalInfo;
    }

    public float getElevation() {
        return elevation;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getOptionalInfo() {
        return optionalInfo;
    }

    @Override
    public String toString() {
        return name;
    }

    //check to see if object is valid
    public boolean validate() {
        return !name.equals("");
    }
}
