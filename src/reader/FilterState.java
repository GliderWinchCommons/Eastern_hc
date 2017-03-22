package reader;

import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;

public class FilterState implements Serializable
{
    //TODO When the save filters button is clicked, check for selected boxes, then create a
    //TODO new FilterState object which will contain an ArrayList of Strings that are equal to
    //TODO the text of the checkbox that has been checked.

    private ArrayList<String> filterStrings;

    public FilterState()
    {
        filterStrings = new ArrayList<>();
    }

    public FilterState(ArrayList<CheckBox> filters)
    {
        filterStrings = new ArrayList<>();
        for(CheckBox filter : filters)
        {
            if(filter.isSelected())
            {
                filterStrings.add(filter.getText());
            }
        }
    }

    public void saveFilterState()
    {
        FileChooser saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Save Filter State");
        File saveFile = saveFileChooser.showSaveDialog(null);
        if (saveFile != null)
        {
            try
            {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile));
                out.writeObject(this);
            }
            catch(FileNotFoundException ex)
            {
                //Write error to log file
            }
            catch(IOException ioex)
            {
                //write error to log file
            }
        }
    }

    public ArrayList<String> loadFilter()
    {
        FileChooser openFileChooser = new FileChooser();
        openFileChooser.setTitle("Load Filter State");
        File filterStateFile = openFileChooser.showOpenDialog(null);
        if (filterStateFile != null)
        {
            try
            {
                ObjectInputStream oInput = new ObjectInputStream(new FileInputStream(filterStateFile));
                FilterState filterState = (FilterState)oInput.readObject();
                return filterState.filterStrings;
            }
            catch(ClassNotFoundException ClEx)
            {
                //Write error to log file
            }
            catch(FileNotFoundException ex)
            {
                //Write error to log file
            }
            catch(IOException IOEx)
            {
                //Write error to log file
            }
        }
        return null;
    }


}
