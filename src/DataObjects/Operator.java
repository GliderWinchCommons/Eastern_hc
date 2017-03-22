/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

import java.util.HashMap;
/**
 *  This Class stores the data about an operator
 *  Includes his desired settings.
 * 
 * @author jtroxel
 */
public class Operator {
    private int id;                                 //operators random id
    private String firstName;                       //Operators' first name
    private String middleName;                      //Operators' middle name
    private String lastName;                        //Operators' last name
    private boolean admin;                          //Operators' privlige level
    private String info;
    private HashMap<String,Integer> unitSettings;   //Unit display preferences
    
    //constructers

    private Operator() {//Default admin, should not be saved
        id = 0;
        firstName = "Amane";
        middleName = "";
        lastName = "Mochizuki";
        admin = true;
        info = "This is a temporary administrator account, it should not be saved.";
        initSettingsFromString("{}");
    }
    
    public Operator(int id, String settings) {
        unitSettings = new HashMap();
        this.id = id;
        firstName = "";
        middleName = "";
        lastName = "";
        admin = false;
        info = "";
        initSettingsFromString(settings);
    }
    public Operator(int id, String name, String settings) {
        firstName = name;
        unitSettings = new HashMap();
        this.id = id;        
        middleName = "";
        lastName = "";
        admin = false;
        info = "";
        initSettingsFromString(settings);
    }
    public Operator(int id, String f, String m, String l, boolean admin, String info, String settings) {
        firstName = f;
        lastName = l;
        middleName = m;
        this.admin = admin;
        this.info = info;
        unitSettings = new HashMap();
        this.id = id;
        initSettingsFromString(settings);
    }
     
    
    //sets the hashmap
    private void initSettingsFromString(String settings) {
        if(!settings.equals("{}")) {
            /* settings could be "{'PILOT_WEIGHT':0,'SAILPLANE_WEIGHT':1}" */
            settings = settings.replace("{","");
            settings = settings.replace("}","");
            String[] settingsArray = settings.split(","); //["'PILOT_WEIGHT':0"]
            
            for (String setting : settingsArray) {
                int hashValue = 0;
                String[] settingArray = setting.split(":");
                String hashId = settingArray[0].replace("'","");
                try {
                    hashValue = Integer.parseInt(settingArray[1]);
                } catch (NumberFormatException nfe) {
                    // for now, do nothing;
                }
                unitSettings.put(hashId.toUpperCase(), hashValue);
            }
        }
    }
    
    
    //getters and setters
    public void setID(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.firstName = name;
    }

    public void setUnitSetting(String id, int value) {
        unitSettings.put(id.toUpperCase(), value);
    }   

    public int getUnitSetting(String id) {
        if (unitSettings.containsKey(id.toUpperCase())) return unitSettings.get(id.toUpperCase());
        else return -1;
    }
    
    public int getID() {
        return id;
    }
    
    public boolean getAdmin() {
        return admin;
    }
    
    public String getFirst() {
        return firstName;
    }
    
    public String getMiddle() {
        return middleName;
    }
    
    public String getLast() {
        return lastName;
    }
    
    public String getInfo() {
        return info;
    }
    
    //converts hash map into a parseable string
    public String getUnitSettingsForStorage() {
        String result = "{";
        for (int i = 0; i < unitSettings.size(); i++) {
            String id = (String) unitSettings.keySet().toArray()[i];
            result += "'" + id + "':";
            result += Integer.toString(unitSettings.get(id));
            if (i != unitSettings.size() - 1) {
                result += ",";
            }
        }
        result += "}";
        return result;
    }
    
    //you should probably edit this method somehow
    //to stop it from creating an admin when one exits
    public Operator defaultAccount() {
        return new Operator();
    }
    
    @Override
    public String toString() { 
        return firstName + " " + middleName + " " + lastName;
    }
}
