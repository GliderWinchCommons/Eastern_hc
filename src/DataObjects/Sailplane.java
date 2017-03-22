/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

/**
 * This Class stores the data about a Sailplane
 *
 * @author garreola-gutierrez, dbennett3
 */
public class Sailplane {

    private int id;                                 //randomly generated id
    private String regNumber;                       //reg_number of the plane
    private String name;                            //glider name
    private String owner;                           //name of the owner
    private String type;                            //type of the plane
    private float maximumGrossWeight;               //max weight plane can carry
    private float emptyWeight;                      //empty weight of the plane
    private float indicatedStallSpeed;              //stall speed of the plane
    private float maximumWinchingSpeed;             //max winching speed of the plane
    private float maximumAllowableWeakLinkStrength; //max weak link of the plane
    private float maximumTension;                   //max tension of the plane
    private float cableReleaseAngle;                //cable release angle of the plane
    private boolean carryBallast;                   //whether or not the plane can carry ballast
    private boolean multipleSeats;                  //whether or not the plane can carry passengers
    private String optionalInfo;                    //optional info on the plane

    float ballast;

    /**
     * Default Constructor
     */
    public Sailplane() {
    }

    public Sailplane(String regNumber, String name, String owner, String Type,
            float maximumGrossWeight, float emptyWeight, float indicatedStallSpeed,
            float maximumWinchingSpeed, float maximumAllowableWeakLinkStrength, float maxTension,
            float cableReleaseAngle, boolean carryBallast, boolean multipleSeats, String optional) {
        this.regNumber = regNumber;
        this.name = name;
        this.owner = owner;
        this.type = Type;
        this.maximumGrossWeight = maximumGrossWeight;
        this.emptyWeight = emptyWeight;
        this.indicatedStallSpeed = indicatedStallSpeed;
        this.maximumWinchingSpeed = maximumWinchingSpeed;
        this.maximumAllowableWeakLinkStrength = maximumAllowableWeakLinkStrength;
        this.maximumTension = maxTension;
        this.cableReleaseAngle = cableReleaseAngle;
        this.carryBallast = carryBallast;
        this.multipleSeats = multipleSeats;
        this.optionalInfo = optional;

        ballast = 0;
    }

    public Sailplane(int id, String regNumber, String name, String owner, String Type,
            float maximumGrossWeight, float emptyWeight, float indicatedStallSpeed,
            float maximumWinchingSpeed, float maximumAllowableWeakLinkStrength, float maxTension,
            float cableReleaseAngle, boolean carryBallast, boolean multipleSeats, String optional) {
        this.id = id;
        this.regNumber = regNumber;
        this.name = name;
        this.owner = owner;
        this.type = Type;
        this.maximumGrossWeight = maximumGrossWeight;
        this.emptyWeight = emptyWeight;
        this.indicatedStallSpeed = indicatedStallSpeed;
        this.maximumWinchingSpeed = maximumWinchingSpeed;
        this.maximumAllowableWeakLinkStrength = maximumAllowableWeakLinkStrength;
        this.maximumTension = maxTension;
        this.cableReleaseAngle = cableReleaseAngle;
        this.carryBallast = carryBallast;
        this.multipleSeats = multipleSeats;
        this.optionalInfo = optional;

        ballast = 0;
    }

    //getters and setters
    public void setId(int id) {
        this.id = id;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaximumGrossWeight(float maximumGrossWeight) {
        this.maximumGrossWeight = maximumGrossWeight;
    }

    public void setEmptyWeight(float emptyWeight) {
        this.emptyWeight = emptyWeight;
    }

    public void setIndicatedStallSpeed(float indicatedStallSpeed) {
        this.indicatedStallSpeed = indicatedStallSpeed;
    }

    public void setMaximumWinchingSpeed(float maximumWinchingSpeed) {
        this.maximumWinchingSpeed = maximumWinchingSpeed;
    }

    public void setMaximumAllowableWeakLinkStrength(float maximumAllowableWeakLinkStrength) {
        this.maximumAllowableWeakLinkStrength = maximumAllowableWeakLinkStrength;
    }

    public void setMaximumTension(float maximumTension) {
        this.maximumTension = maximumTension;
    }

    public void setCableReleaseAngle(float cableReleaseAngle) {
        this.cableReleaseAngle = cableReleaseAngle;
    }

    public void setCarryBallast(boolean carryBallast) {
        this.carryBallast = carryBallast;
    }

    public void setMultipleSeats(boolean multipleSeats) {
        this.multipleSeats = multipleSeats;
    }

    public void setOptionalInfo(String optionalInfo) {
        this.optionalInfo = optionalInfo;
    }

    public void setBallast(float ballast) {
        this.ballast = ballast;
    }

    public int getId() {
        return id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getType() {
        return type;
    }

    public float getMaxGrossWeight() {
        return maximumGrossWeight;
    }

    public float getEmptyWeight() {
        return emptyWeight;
    }

    public float getIndicatedStallSpeed() {
        return indicatedStallSpeed;
    }

    public float getMaxWinchingSpeed() {
        return maximumWinchingSpeed;
    }

    public float getMaxWeakLinkStrength() {
        return maximumAllowableWeakLinkStrength;
    }

    public float getMaxTension() {
        return maximumTension;
    }

    public float getCableReleaseAngle() {
        return cableReleaseAngle;
    }

    public float getMaximumGrossWeight() {
        return maximumGrossWeight;
    }

    public boolean getCarryBallast() {
        return carryBallast;
    }

    public boolean getMultipleSeats() {
        return multipleSeats;
    }

    public String getOptionalInfo() {
        return optionalInfo;
    }

    public float getBallastWeight() {
        return ballast;
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

    public boolean isCarryBallast() {
        return carryBallast;
    }

    public boolean isMultipleSeats() {
        return multipleSeats;
    }

    public float getBallast() {
        return ballast;
    }

    public void addBallast(float ballast) {
        this.ballast += ballast;
    }

    @Override
    public String toString() {
        return name + " " + regNumber + " " + owner;
    }

    //check for valid glider
    public boolean validate() {
        return !name.equals("") && !regNumber.equals("");
    }
}
