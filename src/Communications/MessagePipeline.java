package Communications;

import DataObjects.LaunchParameterGenerator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

/**
 *
 * @author deh extended by HGJ
 */

public class MessagePipeline implements Runnable {
    
    private ArrayList<Observer> observers;
    private String oldMessage = "";
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    //private OutputStreamWriter writer;
    
    private String currentMessage = "";
    public  String selectedMessage ="";     // GUI: Formatted payload
    public  String selectedMessage2 = "";   // GUI: DLC field
    public  String selectedMessage3 = "";   // GUI: Payload field (raw hex)
    public  static Canmsg2j cmsg;
    
    private static MessagePipeline instance = null;
    private boolean running = false;
    private boolean connected = false;
    private int curMessageIndex = 0;
    private MessageListener listener = new MessageListener();
    private DataRelay relay = new DataRelay();
    
    private float[] launchparameter_flt;
    
    boolean debugMode = false;
    private int seqsend;
    private int working_burst_size;

    
    public static MessagePipeline getInstance()
    {
        if(instance == null)
        {
            instance = new MessagePipeline();
            instance.observers = new ArrayList<>();
            instance.init();
            instance.listener.attachRelay(instance.relay);
            instance.relay.setParent(instance.listener);
        }
        return instance;
    }
   
    
    public void init()
    {
        
        running = true;
    }
    
    public boolean connect(String address, int port)
    {
        try {
            socket = new Socket(address, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            connected = true;
            System.out.println("Connected");
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        } 
    }
    
    public void disconnect()
    {
        connected = false;
        currentMessage = "";
    }
    
    public void attach(Observer ob)
    {
        observers.add(ob);
    }
    
    public void ReadFromSocket()
    {
        int sw = 0;
        try {
            String s = reader.readLine();
            currentMessage = s;

        Canmsg2j cmsg = new Canmsg2j();
        cmsg.convert_msgtobin(s);
        selectedMessage2 = String.format("%d",cmsg.dlc);
        selectedMessage3 = "";
        
       
            if(cmsg.id == PccFinal.CMD_LAUNCH_HDSHK_REQ_MC){ 
            
            // Check if payload size is sufficiently useful
            if (cmsg.dlc < 2){
                // Too few to be useful
                s = "ERR: dlc < 2";
            }
            else{
                // Check type of command
                if (cmsg.pb[6+0] == PccFinal.CMD_LAUNCH_PARM_HDSHK){
                 // Here, Handshake request  
                    if (cmsg.dlc != 4){
                        System.out.format("Rcvd wrong dlc for handshake: %d\n",cmsg.dlc);
                    }
                    else{ // Here, dlc is correct for a handshake request msg
                        // Select the smaller of the two burst sizes
                        
                       
                        if (cmsg.pb[6+1] < PccFinal.LAUNCH_PARAM_BURST_SIZE){
                            working_burst_size = cmsg.pb[6+1];
                        }
                        else{
                             working_burst_size = PccFinal.LAUNCH_PARAM_BURST_SIZE;
                        }
                        
                        LaunchParameterGenerator lpGen = new LaunchParameterGenerator();
                        launchparameter_flt = lpGen.GenerateParameters();
                        
                        System.out.format("HANDSHAKE RCVD\n");
                        // Setup payload for the response to the handshake request
                        cmsg.pb[6+1] = (byte) working_burst_size;
                        if(lpGen.CanGenerate())
                            cmsg.pb[6+2] = (byte) launchparameter_flt.length;
                        else
                            cmsg.pb[6+2] = (byte) 0;
                        cmsg.pb[6+3] = 1;   // Version
                        SendCANmsg(cmsg);   // Send msg using gui selected CAN id
                    }
                }
                else{
                    if (cmsg.pb[6+0] == PccFinal.CMD_SEND_LAUNCH_PARM){
                        // Here burst request
                        if (cmsg.pb[6+1] > launchparameter_flt.length){
                            // Here starting index is out-of-range
                            System.out.format("Burst request index out-of-range %d\n", cmsg.pb[6+1]);
                        }
                        else{
                            int idx = cmsg.pb[6+1] & 0xFF;// int idx = cmsg.pb[6+1] original adding this it works
                            
                            System.out.format("idx = " + idx + "\n");
                            int ib = 0;
                            Canmsg2j cmsg_r = new Canmsg2j();
                            cmsg_r.dlc = 6;   // Payload length for parameter msg
                       
                            
                            
                            while((ib < working_burst_size) && (idx < launchparameter_flt.length)){
                                int j = Float.floatToRawIntBits(launchparameter_flt[idx]);
                                cmsg_r.pb[6+0] = PccFinal.CMD_SEND_LAUNCH_PARM;
                                cmsg_r.pb[6+1] = (byte) idx;
                                cmsg_r.pb[6+2] = (byte) (j >>  0); // Load float bits into payload
                                cmsg_r.pb[6+3] = (byte) (j >>  8);
                                cmsg_r.pb[6+4] = (byte) (j >> 16);
                                cmsg_r.pb[6+5] = (byte) (j >> 24);
                                SendCANmsg(cmsg_r);   // Send msg using gui selected CAN id
                                idx += 1; ib += 1;
                            }
                            
                           
                        }
                    }               
                    else
                    {
                        System.out.format("Command Code not recognized %02X\n",cmsg.dlc+6+0 );
                    }
                }         
            }
            }
           } catch (IOException ex) {
            ex.printStackTrace();
        }
       }

        
     public static DataRelay getDataRelay()
    {
        if(instance != null) return instance.relay;
        else return null;
    }

    
    private void SendCANmsg(Canmsg2j cmsg){
        cmsg.pb[0] = (byte)(seqsend & 0xff); // Sequence number
        seqsend += 1;
        cmsg.id = PccFinal.CMD_LAUNCH_HDSHK_REQ_HC;//cmi1.can_hex.intValue(); // CAN ID
        String s = cmsg.out_prep(); 
        WriteToSocket(s);      
    }
        
    public void WriteToSocket(String s)
    {
        try {
            writer.write(s);
            writer.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }       
    }
    
    public void run()
    {
        while(running)
        {
            if(connected)
            {
                ReadFromSocket();
            }
        }
    }
    
    public void close()
    {
        running = false;
        try {
            socket.close();
            writer.close();
            reader.close();
        } catch (IOException ex) {
        }
    }
}
