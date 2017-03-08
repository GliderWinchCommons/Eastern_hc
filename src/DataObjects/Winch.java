/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

/**
 * This class stores the relevant data about a drum on the winch
 *
 * @author Johnny White, Noah Fujioka
 * @date 11/19/2014
 */
public class Winch {

    private int id;                         //randomly generated id
    private String name;                    //name of the winch
    private String owner;                   //owner of the winch
    private String wc_version;              //version number of the parameters below

    //16 parameters set by an administrator
    private float w1, w2, w3, w4, w5, w6, w7, w8,
            w9, w10, w11, w12, w13, w14, w15, w16;
    private int meteorological_check_time;      //time before operator is warned about old data
    private int meteorological_verify_time;     //time before operator is forced to check old data
    private float run_orientation_tolerance;    //TODO Remeber what this did
    private String info;
    //private List<Drum> driveList;

    //constructers
    public Winch() {
        //driveList = new ArrayList<>();
        name = "";
    }

    public Winch(int id, String name, String owner, String wc, float w1, float w2, float w3,
            float w4, float w5, float w6, float w7, float w8, float w9, float w10, float w11, float w12,
            float w13, float w14, float w15, float w16, int mct, int mvt, float rot, String info) {
        //driveList = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.wc_version = wc;
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
        this.meteorological_check_time = mct;
        this.meteorological_verify_time = mvt;
        this.run_orientation_tolerance = rot;
        this.info = info;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int newId) {
        id = newId;
    }

    public void setName(String nameIn) {
        name = nameIn;
    }

    /*
    public void addDrive(Drive drive) {
        driveList.add(drive);
    }
     */
    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
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

    public int meteorologicalCheckTime() {
        return meteorological_check_time;
    }

    public int meteorologicalVerifyTime() {
        return meteorological_verify_time;
    }

    public float runOrientationTolerance() {
        return run_orientation_tolerance;
    }

    public String getOptionalInfo() {
        return info;
    }

    @Override
    public String toString() {
        return name;
    }

    //check for valid winch
    public boolean check() {
        return !name.equals("") && !owner.equals("");
    }
}
