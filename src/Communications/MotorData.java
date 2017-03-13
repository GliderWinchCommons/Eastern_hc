package Communications;

import java.util.ArrayList;

/**
 *
 * @author Matt
 */
public class MotorData {
    private ArrayList<BatteryData> batteries;    
    int motorNum;
    
    public MotorData(int motorNum)
    {
        this.batteries = null;
        this.motorNum = motorNum;
    }
    
    public int getMotorNum()
    {
        return motorNum;
    }
    
    /**
     * Adds a new battery to the Motor's list of batteries, sorted by battery number.
     * If MotorData object already contains a battery with the given battery number,
     * the entry is rejected.
     * 
     * @param batteryNum the number of the battery to be added
     * @param voltage the voltage of the battery to be added
     */
    public void addBattery(int batteryNum, float voltage)
    {
        if (batteries == null)
        {
            batteries.add(new BatteryData(batteryNum, voltage));
        }
        else
        {
            int i = 0;
            for(BatteryData bd: batteries)
            {
               if(bd.getBatteryNum() < batteryNum){ 
                   continue; 
               }else if(bd.getBatteryNum() == batteryNum){
                   break;
               }else{
                   batteries.add(i, new BatteryData(batteryNum, voltage));
               }
               i++;
            }
        }
        
    }
    
    public ArrayList<BatteryData> getBatteries()
    {
        return batteries;
    }
    
}
