/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

/**
 * This class stores data about the parachute
 *
 * @author Alec Jacuzzi, dbennett3
 */
public class Parachute {

    private int parachuteId;    //random id
    private String name;        //parachute name
    private float lift;         //how much lift he parachute has
    private float drag;         //how much drag the parachute creates
    private float weight;       //how much the parachute weighs
    private String info;

    //constructors
    public Parachute() {
        parachuteId = 0;
        lift = 0;
        drag = 0;
        weight = 0;
    }

    public Parachute(int id, String n, float initialLift, float initialDrag, float initialWeight, String info) {
        this.parachuteId = id;
        lift = initialLift;
        drag = initialDrag;
        weight = initialWeight;
        name = n;
        this.info = info;
    }

    public Parachute(float initialLift, float initialDrag, float initialWeight, String info) {
        lift = initialLift;
        drag = initialDrag;
        weight = initialWeight;
        this.info = info;
    }

    /**
     * This method can change the parachute ID number of a Parachute object.
     * This method always works, only if Parachute already exists.
     *
     * @param newParachuteNumber used to identify of the Parachute object
     */
    public void setParachuteId(int newParachuteNumber) {
        parachuteId = newParachuteNumber;
    }

    //setters and getters
    public void setLift(float newLift) {
        lift = newLift;
    }

    public void setWeight(float w) {
        weight = w;
    }

    public void setDrag(float newDrag) {
        drag = newDrag;
    }

    public void setName(String s) {
        name = s;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getLift() {
        return lift;
    }

    public float getDrag() {
        return drag;
    }

    public float getWeight() {
        return weight;
    }

    public int getParachuteId() {
        return parachuteId;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return name;
    }

    //check if parachute is valid
    public boolean validate() {
        return !name.equals("");
    }
}
