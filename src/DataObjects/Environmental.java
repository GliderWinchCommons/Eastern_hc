/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataObjects;

/**
 *
 * @author garreola-gutierrez
 */
public class Environmental {
    float windSpeed;
    String direction;
    float temperature;
    float pressure;
    float densityAlitude;
    public Environmental(){
        
    }
    public Environmental(float w, String d, float a){
       windSpeed = w;
       direction = d;
       densityAlitude = a;

    }
    
    public void setWindSpeed(float wind){
        windSpeed = wind;    
    }
    
    public float getWindSpeed(){
    return windSpeed;
    }
    
    public void setDirection(String d){
        direction = d;
    }
       
    public String getDirection(){
        return direction;
    }
    
    public void setTemperature(float t){
        temperature = t;
    }
    
    public float getTemperature(){
        return temperature;
    }
    public void setPressure(float p){
        pressure = p;
    }
    public float getPressure(){
        return pressure;
    }
    public void setAltitudeDensity(float a){
        densityAlitude = a;
        
    }
    public float getAltitudeDensity(){
        return densityAlitude;
    }
    public void calculateAltitudeDensity(){
        densityAlitude = pressure + (120 * temperature);
    }
    
}
