/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communications;

import java.util.ArrayList;

/**
 *
 * @author jtroxel
 */
public class ReplayStreamer {
    
    private final MessagePipeline pipe;
    private final DataRelay relay;
    private ArrayList<String> msgs;
    
    public ReplayStreamer()
    {
        pipe = MessagePipeline.getInstance();
        relay = MessagePipeline.getDataRelay();
        msgs = new ArrayList<>();
    }
    
    public void loadStream(int timeStamp)
    {
        //SELECT * FROM PREVIOUS_LAUNCHES WHERE FLIGHT_ID = ID??
    }
    
    public int startStream(int startTime)
    {
        int curTime = startTime;
        int curIndex = 0;
        while(curIndex < msgs.size())
        {
            relay.forwardMessage(msgs.get(curIndex));
            //wait for some time???
            curIndex++;
        }
        
        return curTime;
    }
}
