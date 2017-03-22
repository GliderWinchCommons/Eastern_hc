package reader;

/**
 * This class is used to hold the processed message information for each message read in from the log file.
 * It uses Properties because that allows the values to be called to populate the TableView. A Message
 * should never be instantiated outside of a MessageDescriptor.
 */

import Factories.Canmsg2j;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;




public class Message
{
    private int intID;
    private SimpleStringProperty logSystem;// The message's originating system
    private SimpleStringProperty message;// Raw message
    private SimpleStringProperty id;// Hex Message ID
    private SimpleStringProperty name;// Human readable name of the message
    private SimpleIntegerProperty dlc;// Data-length code. This indicates how many payload bytes are used.
    private SimpleStringProperty payload0;
    private SimpleStringProperty payload1;
    private SimpleStringProperty payload2;
    private SimpleStringProperty payload3;
    private SimpleStringProperty payload4;
    private SimpleStringProperty payload5;
    private SimpleStringProperty payload6;
    private SimpleStringProperty payload7;
    private SimpleIntegerProperty index;// Indicates the line number of the message. Starts at 0
    private int originalID;


    public Message()
    {
        // Properties must be initialized, or the TableView will throw a NullPointerException when it tries
        // to call the get methods to populate itself.
        logSystem = new SimpleStringProperty("");
        message = new SimpleStringProperty("");
        id = new SimpleStringProperty("0");
        name = new SimpleStringProperty("");
        payload0 = new SimpleStringProperty("");
        payload1 = new SimpleStringProperty("");
        payload2 = new SimpleStringProperty("");
        payload3 = new SimpleStringProperty("");
        payload4 = new SimpleStringProperty("");
        payload5 = new SimpleStringProperty("");
        payload6 = new SimpleStringProperty("");
        payload7 = new SimpleStringProperty("");
        dlc = new SimpleIntegerProperty(0);
        index = new SimpleIntegerProperty(0);
    }

    public Message(Canmsg2j can, String[] row, int idx)
    {
        logSystem = new SimpleStringProperty(row[0]);
        message = new SimpleStringProperty(row[1]);
        id = new SimpleStringProperty("");
        this.setId(Integer.toHexString(can.id));
        name = new SimpleStringProperty("Unknown");
        payload0 = new SimpleStringProperty("");
        payload1 = new SimpleStringProperty("");
        payload2 = new SimpleStringProperty("");
        payload3 = new SimpleStringProperty("");
        payload4 = new SimpleStringProperty("");
        payload5 = new SimpleStringProperty("");
        payload6 = new SimpleStringProperty("");
        payload7 = new SimpleStringProperty("");
        dlc = new SimpleIntegerProperty(can.dlc);
        index = new SimpleIntegerProperty(idx);
    }


    public String getLogSystem()
    {
        return logSystem.get();
    }

    public String getMessage()
    {
        return message.get();
    }

    public String getId()
    {
        return id.get();
    }

    public String getName()
    {
        return name.get();
    }

    public int getDlc()
    {
        return dlc.get();
    }

    public String getPayload0()
    {
        return payload0.get();
    }

    public String getPayload1()
    {
        return payload1.get();
    }

    public String getPayload2()
    {
        return payload2.get();
    }

    public String getPayload3()
    {
        return payload3.get();
    }

    public String getPayload4()
    {
        return payload4.get();
    }

    public String getPayload5()
    {
        return payload5.get();
    }

    public String getPayload6()
    {
        return payload6.get();
    }

    public String getPayload7()
    {
        return payload7.get();
    }

    public int getIndex()
    {
        return index.get();
    }

    public int getIntID()
    {
        return intID;
    }

    public void setLogSystem(String system)
    {
        logSystem.set(system);
    }

    public void setMessage(String msg)
    {
        message.set(msg);
    }

    /**
     * This method sets the ID property, but as part of the process it checks index 3-6 inclusive to see if
     * they are set to 0. If they are, then it indicates a short ID is used and those indeces are replaced
     * with " " to easily identify it as a short ID message.
     *
     * @param ID Message ID to be processed and displayed.
     */
    public void setId(String ID)
    {
        if(ID.length() == 8 && ID.substring(3, 7).equals("0000"))
        {
            String displayID = ID.substring(0,3) + "    " + ID.charAt(7);
            id.set(displayID);
        }
        else
        {
            id.set(ID);
        }
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public void setDlc(int dlc)
    {
        this.dlc.set(dlc);
    }

    public void setPayload0(String p0)
    {
        payload0.set(p0);
    }

    public void setPayload1(String p1)
    {
        payload1.set(p1);
    }

    public void setPayload2(String p2)
    {
        payload2.set(p2);
    }

    public void setPayload3(String p3)
    {
        payload3.set(p3);
    }

    public void setPayload4(String p4)
    {
        payload4.set(p4);
    }

    public void setPayload5(String p5)
    {
        payload5.set(p5);
    }

    public void setPayload6(String p6)
    {
        payload6.set(p6);
    }

    public void setPayload7(String p7)
    {
        payload7.set(p7);
    }

    public void setPayloads(String[] payloads)
    {
        this.setPayload0(payloads[0]);
        this.setPayload1(payloads[1]);
        this.setPayload2(payloads[2]);
        this.setPayload3(payloads[3]);
        this.setPayload4(payloads[4]);
        this.setPayload5(payloads[5]);
        this.setPayload6(payloads[6]);
        this.setPayload7(payloads[7]);
    }

    public int getOriginalID()
    {
        return originalID;
    }

    public void setOriginalID(int id)
    {
        originalID = id;
    }

    public void setIndex(int idx)
    {
        index.set(idx);
    }


}
