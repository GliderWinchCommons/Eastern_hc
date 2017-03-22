/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Communications;

/**
 *
 * @author Matt
 */
public class BatteryData {
    private int batteryNum;
    private float voltage;
    
    public BatteryData(int batteryNum, float voltage)
    {
        this.batteryNum = batteryNum;
        this.voltage = voltage;
    }
    
    public void setBatteryNum(int batteryNum)
    {
        this.batteryNum = batteryNum;
    }
    
    public int getBatteryNum()
    {
        return batteryNum;
    }
    
    public void setVoltage(float voltage)
    {
        this.voltage = voltage;
    }
    
    public float getVoltage()
    {
        return voltage;
    }
}
