package DataObjects;

/*
Yea, I don't know what this is for.
If your reading this you can probably find a way to get rid of it
*/

import java.util.ArrayList;
import java.util.List;

public class Drive {

    private float reductionRatio;
    private List<Drum> drumList;
    private String name;


    public Drive()
    {
        drumList = new ArrayList();
    }
    
    public Drive(String nameIn, float reductionRatio) {
        drumList = new ArrayList();        
        name = nameIn;
    }
    
    public void addDrum(Drum drum) {
        drumList.add(drum);
    }

    public String getName()
    {
        return name;
    }
    
    public void setName(String s)
    {
        name = s;
    }

    public List<Drum> getDrumList()
    {
        return drumList;
    }
    
    public float getReductionRatio(){
        return reductionRatio;
    }
    
    public void setReductionRatio(float reductionRatio){
        this.reductionRatio = reductionRatio;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}