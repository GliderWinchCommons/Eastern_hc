package EnvironmentalWidgets;

import java.util.HashMap;

/**
 *
 * @author jtroxel
 */
public class CurrentWidgetDataSet {
    private static CurrentWidgetDataSet instance = null;
    HashMap<String, String> values;
    
    public static CurrentWidgetDataSet getInstance()
    {
        if(instance == null)
        {
            instance = new CurrentWidgetDataSet();
            instance.values = new HashMap<>();
        }
        return instance;
    }
    
    public String getValue(String key)
    {
        if(values.containsKey(key.toUpperCase()))
        {
            return values.get(key.toUpperCase());
        }
        else
        {
            return "";
        }
    }
    
    public void setValue(String key, String value)
    {
        values.put(key.toUpperCase(), value);
    }
}
