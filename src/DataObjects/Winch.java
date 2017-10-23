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

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setW1(float w1) {
        this.w1 = w1;
    }

    public void setW2(float w2) {
        this.w2 = w2;
    }

    public void setW3(float w3) {
        this.w3 = w3;
    }

    public void setW4(float w4) {
        this.w4 = w4;
    }

    public void setW5(float w5) {
        this.w5 = w5;
    }

    public void setW6(float w6) {
        this.w6 = w6;
    }

    public void setW7(float w7) {
        this.w7 = w7;
    }

    public void setW8(float w8) {
        this.w8 = w8;
    }

    public void setW9(float w9) {
        this.w9 = w9;
    }

    public void setW10(float w10) {
        this.w10 = w10;
    }

    public void setW11(float w11) {
        this.w11 = w11;
    }

    public void setW12(float w12) {
        this.w12 = w12;
    }

    public void setW13(float w13) {
        this.w13 = w13;
    }

    public void setW14(float w14) {
        this.w14 = w14;
    }

    public void setW15(float w15) {
        this.w15 = w15;
    }

    public void setW16(float w16) {
        this.w16 = w16;
    }

    public int getMeteorological_check_time() {
        return meteorological_check_time;
    }

    public void setMeteorological_check_time(int meteorological_check_time) {
        this.meteorological_check_time = meteorological_check_time;
    }

    public int getMeteorological_verify_time() {
        return meteorological_verify_time;
    }

    public void setMeteorological_verify_time(int meteorological_verify_time) {
        this.meteorological_verify_time = meteorological_verify_time;
    }

    public float getRun_orientation_tolerance() {
        return run_orientation_tolerance;
    }

    public void setRun_orientation_tolerance(float run_orientation_tolerance) {
        this.run_orientation_tolerance = run_orientation_tolerance;
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
    public boolean validate() {
        return !name.equals("") && !owner.equals("");
    }
}
