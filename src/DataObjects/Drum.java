package DataObjects;

public class Drum {

    private int drumId;                 //randomly generated id
    private int winchId;                //the winches random id
    private String name;                //Drum's name
    private int drumNum;                //Drum's unique number
    private float coreDiameter;         //Drum's core diameter
    private float kFactor;              //Drum's K-Factor
    private float springConst;          //Drum's Spring Constant
    private float cableLength;          //Length of the cable
    private float cableDensity;         //Density of the cable
    private float systemEquivalentMass; //Drum System Equivalent Mass
    private int numLaunches;            //Number of lauches this drum has done
    private float maxTension;           //Maximum tension the cable can happen
    private String info;

    private Parachute para;             //Currently attached parachute
    private Drive drive;                //Yea I got no clue

    //constructors
    public Drum() {

    }

    public Drum(int id, int wid, String n, int dnum, float cDi, float kF, float sC, float cL,
            float cDe, float dsem, int num, float mt, String info) {
        drumId = id;
        winchId = wid;
        name = n;
        drumNum = dnum;
        coreDiameter = cDi;
        kFactor = kF;
        springConst = sC;
        cableLength = cL;
        cableDensity = cDe;
        systemEquivalentMass = dsem;
        numLaunches = num;
        maxTension = mt;
        this.info = info;
    }

    public Drum(String n, float cD, float kF, float cL, float mt, String info) {
        name = n;
        coreDiameter = cD;
        kFactor = kF;
        cableLength = cL;
        numLaunches = 0;
        maxTension = mt;
        this.info = info;
    }

    //getters and setters
    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive d) {
        drive = d;
    }

    public void setId(int i) {
        drumId = i;
    }

    public void setName(String s) {
        name = s;
    }

    public void setCoreDiameter(float cd) {
        if (cd > 0) {
            coreDiameter = cd;
        }
    }

    public void setKFactor(float k) {
        kFactor = k;
    }

    public void setCableLength(float cl) {
        if (cl > 0) {
            cableLength = cl;
        }
    }

    public void setDrumId(int drumId) {
        this.drumId = drumId;
    }

    public void setWinchId(int winchId) {
        this.winchId = winchId;
    }

    public void setDrumNum(int drumNum) {
        this.drumNum = drumNum;
    }

    public void setkFactor(float kFactor) {
        this.kFactor = kFactor;
    }

    public void setSpringConst(float springConst) {
        this.springConst = springConst;
    }

    public void setCableDensity(float cableDensity) {
        this.cableDensity = cableDensity;
    }

    public void setSystemEquivalentMass(float systemEquivalentMass) {
        this.systemEquivalentMass = systemEquivalentMass;
    }

    public void setNumLaunches(int numLaunches) {
        this.numLaunches = numLaunches;
    }

    public void setMaxTension(float maxTension) {
        this.maxTension = maxTension;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setPara(Parachute para) {
        this.para = para;
    }

    public int getId() {
        return drumId;
    }

    public String getName() {
        return name;
    }

    public float getKFactor() {
        return kFactor;
    }

    public float getSpringConstant() {
        return springConst;
    }

    public float getCableLength() {
        return cableLength;
    }

    public float getCoreDiameter() {
        return coreDiameter;
    }

    public int getNumLaunches() {
        return numLaunches;
    }

    public int getWinchId() {
        return winchId;
    }

    public int getDrumNumber() {
        return drumNum;
    }

    public float getMaxTension() {
        return maxTension;
    }

    public String getOptionalInfo() {
        return info;
    }

    public Parachute getParachute() {
        return para;
    }

    public void setParachute(Parachute parachute) {
        para = parachute;
        CurrentDataObjectSet.getCurrentDataObjectSet().forceUpdate();
    }

    public void clearParachute() {
        para = null;
        CurrentDataObjectSet.getCurrentDataObjectSet().forceUpdate();
    }

    public float getSystemEquivalentMass() {
        return systemEquivalentMass;
    }

    public float getCableDensity() {
        return cableDensity;
    }

    public int getDrumId() {
        return drumId;
    }

    public int getDrumNum() {
        return drumNum;
    }

    public float getkFactor() {
        return kFactor;
    }

    public float getSpringConst() {
        return springConst;
    }

    public String getInfo() {
        return info;
    }

    public Parachute getPara() {
        return para;
    }

    @Override
    public String toString() {
        if (para == null) {
            return drive.getName() + " - " + name + " (NO PARACHUTE)";
        } else {
            return drive.getName() + " - " + name + " (" + para.getName() + ")";
        }
    }

    public boolean validate() {
        return !name.equals("") && para != null;
    }
}
