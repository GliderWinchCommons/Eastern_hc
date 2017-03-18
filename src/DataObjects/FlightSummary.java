package DataObjects;

/**
 *
 * @author Noah Fujioka
 */

import DatabaseUtilities.DatabaseEntrySelect;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;

public class FlightSummary {
    private final Timestamp startTimestamp;
    private final String pilotFirstName;
    private final String pilotMiddleName;
    private final String pilotLastName;
    private final String gliderNnumber;
    
    //constructors
    
    public FlightSummary(Timestamp startTimestamp, String pilotFirstName, 
            String pilotLastName, String pilotMiddleName, String gliderNnumber){
            this.startTimestamp = startTimestamp;
            this.pilotFirstName = pilotFirstName;
            this.pilotLastName = pilotLastName;
            this.pilotMiddleName = pilotMiddleName;
            this.gliderNnumber = gliderNnumber;
    }
    
    //getters and setters
    public void setCurrentDataObjectSet(){
        try{
            DatabaseEntrySelect.setCurrentDataObjectSetToFlight(this);
        }catch(Exception e){
            System.out.println("Error 404: Flight not found");
            //e.printStackTrace();
        }
    }
    
    public String getFirst() {
        return this.pilotFirstName;
    }
    
    public String getLast() {
        return this.pilotLastName;
    }
    
    public String getMiddle() {
        return this.pilotMiddleName;
    }
    
    public LocalDateTime getLocalTime() {
        return this.startTimestamp.toLocalDateTime();
    }
    
    public String getGliderNumber() {
        return this.gliderNnumber;
    }
    
    public Timestamp getStartTimestamp(){
        return startTimestamp;
    }
    
    public class ByFirstName implements Comparator<FlightSummary>{

        @Override
        public int compare(FlightSummary f1, FlightSummary f2) {
            return f1.getFirst().compareTo(f2.getFirst());
        }

        @Override
        public Comparator reversed() {
            return Comparator.super.reversed(); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

    /*
     * comparators for sorting flight summery objects
     */
    public class ByLastName implements Comparator<FlightSummary> {

        @Override
        public int compare(FlightSummary f1, FlightSummary f2) {
            return f1.getLast().compareTo(f2.getLast());
        }

        @Override
        public Comparator reversed() {
            return Comparator.super.reversed(); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public class ByMiddleName implements Comparator<FlightSummary>{

        @Override
        public int compare(FlightSummary f1, FlightSummary f2) {
            return f1.getMiddle().compareTo(f2.getMiddle());
        }

        @Override
        public Comparator reversed() {
            return Comparator.super.reversed(); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public class ByGlider implements Comparator<FlightSummary>{

        @Override
        public int compare(FlightSummary f1, FlightSummary f2) {
            return f1.getGliderNumber().compareTo(f2.getGliderNumber());
        }

        @Override
        public Comparator reversed() {
            return Comparator.super.reversed(); //To change body of generated methods, choose Tools | Templates.
        }   
    }
    
    public class ByTime implements Comparator<FlightSummary>{

        @Override
        public int compare(FlightSummary f1, FlightSummary f2) {
            return f1.getLocalTime().compareTo(f2.getLocalTime());
        }

        @Override
        public Comparator reversed() {
            return Comparator.super.reversed(); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
