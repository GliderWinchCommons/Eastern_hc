package Communications;

import DataObjects.CurrentLaunchInformation;

public class MessageListener implements Observer
{
    static final float TWO_PI = 2.0f * (float) Math.PI;

    //for now, leave these defined here. I am working on a constant loader that will let you define
    // these in a file then have them globally accessible
    static final int ID_OFFSET = 21;
    static final int ID_SCALE = 1 << ID_OFFSET;
    static final int TIME_MESSAGE_ID = 256 * ID_SCALE;              // 0x200
    static final int MOTOR_MESSAGE_ID = 292 * ID_SCALE;             // 0x248
    static final int TORQUE_COMMAND_MESSAGE_ID = 300 * ID_SCALE;    // 0x258
    static final int STATE_MESSAGE_ID = 304 * ID_SCALE;             // 0x260
    static final int PARAM_REQUEST_MESSAGE_ID = 312 * ID_SCALE;     // 0x270 
    static final int LAUNCH_PARAM_MESSAGE_ID = 320 * ID_SCALE;      // 0x280
    static final int CP_CL_RMT_MESSAGE_ID = 328 * ID_SCALE;         // 0x290
    static final int CP_CL_LCL_MESSAGE_ID = 329 * ID_SCALE;         // 0x292
    static final int CP_INPUTS_RMT_MESSAGE_ID = 330 * ID_SCALE;     // 0x294
    static final int CP_INPUTS_LCL_MESSAGE_ID = 331 * ID_SCALE;     // 0x296
    static final int CP_OUTPUTS_MESSAGE_ID = 336 * ID_SCALE;        // 0x2A0
    static final int ORIENTATION_ID = 385 * ID_SCALE;               // 0x302
    static final int DRUM_MESSAGE_ID = 432 * ID_SCALE;              // 0x360
    static final int TENSION_MESSAGE_ID = 448 * ID_SCALE;           // 0x380
    static final int CABLE_ANGLE_MESSAGE_ID = 464 * ID_SCALE;       // 0x3a0
    static final int ZERO_ODOMETER_ID = 672 * ID_OFFSET;            // 0x540
    static final int ZERO_TENSIOMETER_ID = 681 * ID_OFFSET;         // 0x552
    static final int DENSITY_ALTITUDE_ID = 689 * ID_SCALE;          // 0x562
    static final int WIND_ID = 690 * ID_SCALE;                      // 0x564
    static final int BATTERY_SYSTEM_ID = 704 * ID_SCALE;            // 0x580
    
   
    private final int NUMBER_DRUMS = 1;
    private final int NUMBER_CICS = 5;
    private final int NUMBER_MOTORS = 1;
    private float[] torqueGain = new float[NUMBER_MOTORS];

    
    static final int ticsPerSec = 16;   //  should be power of 2
    static final float secPerTic = 1.0f / ticsPerSec;

    private int currentState;
    //private int lastState;    

    public int intUnixTime;

    private int activeDrum = 0;

    private double currentUnixTime;

    private DataRelay relay = null;
    
    //  variables for CIC3 filters, 
    static final int filterOuputsPerSecond = 4;
    
    private final int decimationFactor = ticsPerSec / filterOuputsPerSecond;
    private final float gainCIC = decimationFactor * decimationFactor
            * decimationFactor;
    private final float cicGroupDelaySamples = ((decimationFactor - 1) / 2.0f)
            * 3.0f;
    //  Actual computations use sample values at one tic prior
    private final float groupDelay = (cicGroupDelaySamples - 1) * secPerTic;
    private int downCounter;

    private long[] i1 = new long[NUMBER_CICS];
    private long[] i2 = new long[NUMBER_CICS];
    private long[] i3 = new long[NUMBER_CICS];
    private long[] c1 = new long[NUMBER_CICS];
    private long[] c2 = new long[NUMBER_CICS];
    private long[] c3 = new long[NUMBER_CICS];

    private int nullMessageCnt = 0;
    private int cpOutputCnt = 0;
    
    //  variables, scales, and offsets for last measurements recieved in messages
    private float[] motorTorque = new float[NUMBER_MOTORS];
    private float lastCommandedDrumTorque = 0;   //  composite drum torque commanded (no losses)
    private final float cicTorqueScale = 100;

    private float lastCableTension = 0;
    private final float cicTensionScale = 100;

    private float lastCableSpeed = 0;
    private final float cicCableSpeedScale = 100;    

    private float lastCableAngle = 0;
    private final float cicCableAngleScale = 100;

    private float lastCableOut = 0;
    private final float cicCableOutScale = 100;

    private float[] filteredData = new float[NUMBER_CICS];
    private float[] cicScale = new float[NUMBER_CICS];
    
    //  status history valables
    private byte previousTimeStatus;
    private byte previousTensionStatus;
    private byte previousDrumStatus;
    private byte previousCableAngleStatus;    

    public MessageListener()
    {
        currentUnixTime = 0.0;
        torqueGain[0] = 7.0f;
        
        cicScale[0] = cicTorqueScale;
        cicScale[1] = cicTensionScale;
        cicScale[2] = cicCableSpeedScale;
        cicScale[3] = cicCableAngleScale;
        cicScale[4] = cicCableOutScale;
    }

    //my code will call this function
    public void attachRelay(DataRelay rIn)
    {
        relay = rIn;
    }

    public void update(String msg)
    {
        byte status;

        //  check for empty string
        if (msg.equals("") || (relay == null))
        {
            // System.out.println("Null message received: " + ++nullMessageCnt);
            return;
        }
        //decode the CANBUS message here
        CanCnvt canIn = new CanCnvt();//I believe this is the correct call
        canIn.convert_msgtobin(msg);

        switch (canIn.id)
        {
            case TIME_MESSAGE_ID:
            {
                if (canIn.dlc == 1)
                {
                    currentUnixTime = (double)intUnixTime + (double)(canIn.get_byte(0) * secPerTic);
                } else
                {
                    intUnixTime = canIn.get_int(0);
                    currentUnixTime = intUnixTime;
                    status = canIn.get_byte(4);
                    if (previousTimeStatus != status)
                    {
                        //relay.sendTimeStatus(status, 
                        //        intUnixTime);
                        previousTimeStatus = status;
                    }                        
                }
                //   0 - torque, 1 -  tension, 2 - cable speed, 3 - cable angle, 
                //   4 - cable out        
                updateIntegrators(0, lastCommandedDrumTorque * cicScale[0]);
                updateIntegrators(1, lastCableTension * cicScale[1]);
                updateIntegrators(2, lastCableSpeed * cicScale[2]);
                updateIntegrators(3, lastCableAngle * cicScale[3]);
                updateIntegrators(4, lastCableOut * cicScale[4]);
                if (--downCounter < 0)
                {
                    downCounter = decimationFactor - 1; //  reset down counter                    
                    for (int i = 0; i < NUMBER_CICS; i++)
                    {
                        filteredData[i] = updateCombs(i) 
                                / (gainCIC * cicScale[i]);                       
                    }
                    //  send filtered values                    
                    relay.sendFilteredData(filteredData, groupDelay);
                    
                    //System.out.println("Filtered Outputs: " + filteredData[0]
                    //        + " " + filteredData[1] + " " + filteredData[2]
                    //        + " " + filteredData[3] + " " + filteredData[4]);                    
                }
            return;
            }

            case ORIENTATION_ID:
                {
                    float pitch = canIn.get_halffloat(0);
                    float roll = canIn.get_halffloat(2);
                    float magnetic = canIn.get_halffloat(4);
                    status = canIn.get_byte(2);
                    //System.out.println("Orientation: " + pitch
                        //+ " " +  roll + " " + magnetic);
                    //relay.sendMagneticStatus(status,
                    //        pitch, roll, magnetic);                   
                    return;      
                }
            
            case STATE_MESSAGE_ID:
            {
                currentState = canIn.get_byte(0) & 0x1f;
                activeDrum = (canIn.get_byte(0) >> 5) & 0x7;
                System.out.println("State and Active Drum: " + currentState
                        + "  " +  activeDrum);
                relay.sendState(currentState, activeDrum);
                return;
            }

            case PARAM_REQUEST_MESSAGE_ID:
            {
                relay.sendLaunchParameterRequest();
                System.out.println("Launch Parameter Request Message Received");
                return;
            }
        }

        {   //  IDs with 3 mapped lsbs
            int messageDrum = (canIn.id & 0x00e00000) >> ID_OFFSET;  
            switch (canIn.id & 0xff000000)    
            {
                case (DRUM_MESSAGE_ID):
                {
                    status = canIn.get_byte(6);
                    if (status >= 0)
                    {
                        if (messageDrum == activeDrum) //  3 lsbs match active drum
                        {
                            lastCableOut = canIn.get_halffloat(0);
                            lastCableSpeed = canIn.get_halffloat(2)
                                    * canIn.get_halffloat(4) * TWO_PI;
                        }
                    } else if (previousDrumStatus != status)
                    {
                        //relay.sendDrumStatus(status, messageDrum, 
                        //  canIn.get_halffloat(0), canIn.get_halffloat(2), 
                        //  canIn.get_halffloat(4, ));   
                        previousDrumStatus = status;
                    }
                    return;
                }

                case (TENSION_MESSAGE_ID):
                {
                    status = canIn.get_byte(3);
                    if (status >= 0)
                    {
                        if (messageDrum == activeDrum) //  3 lsbs match active drum
                        {
                            lastCableTension = canIn.get_halffloat(0);
                        }
                    } else if(previousTensionStatus != status)
                    {
                        //relay.sendTensionStatus(status, 
                        //        messageDrum, canIn.get_halffloat(0)); 
                        previousTensionStatus = status;
                    }
                    return;
                }

                case (CABLE_ANGLE_MESSAGE_ID):
                {
                    status = canIn.get_byte(3);
                    if (status >= 0)
                    {
                        if (messageDrum == activeDrum) //  3 lsbs match active drum
                        {
                            lastCableAngle = canIn.get_halffloat(0);
                        }
                    } else if (previousCableAngleStatus != status)
                    {
                        //relay.CableAngleStatus(status,
                        //        messageDrum, canIn.get_halffloat(0));
                        previousCableAngleStatus = status;
                    }
                    return;
                }
            }
        }

        {   //  IDs with 2 mapped lsbs 
            int messageMotor = (canIn.id & 0x00600000) >> ID_OFFSET;
            switch (canIn.id & 0xff800000)    
            {
                case MOTOR_MESSAGE_ID:
                {                    
                    float rps = canIn.get_halffloat(0);
                    float revolutions = canIn.get_halffloat(2);
                    float temperature = (float) canIn.get_byte(4);
                    status = canIn.get_byte(5);
                    if (status >= 0)
                    {
                        //  nothing needed now
                    } else if (status != 0)
                    {
                        //relay.sendMotorStatus(status,
                        //        messageMotor, speed, revolutions,
                        //        temperature);
                    }
                    return;
                }

                case TORQUE_COMMAND_MESSAGE_ID: //  command message, no status
                {
                    motorTorque[messageMotor] = canIn.get_halffloat(0);
                    lastCommandedDrumTorque = 0;
                    //System.out.println("Commanded Motor Torque: " + motorTorque[messageMotor]
                    //        + "  " + messageMotor);
                    for (int i = 0; i < NUMBER_MOTORS; i++)
                    {
                        lastCommandedDrumTorque += motorTorque[i] * torqueGain[i];
                    }
                    return;   
                }
                 
                case BATTERY_SYSTEM_ID:
                {
                    float voltage = canIn.get_halffloat(0);
                    float current = canIn.get_halffloat(2);
                    float temperature = (float) canIn.get_byte(4);
                    status = canIn.get_byte(5);
                    System.out.println("Battery System: " + voltage
                        + " " +  current + " " + temperature);
                    //relay.sendBatteryStatus(status,
                    //        messageMotor, voltage, current, temperature);                    messageProcessed = true;
                    return;                    
                }           
            }
        }
        
        {   //  lower priority and occurence messages   
            switch (canIn.id)
            {
                case DENSITY_ALTITUDE_ID:
                {
                    float pressure = canIn.get_halffloat(0);
                    float temperature = (float) canIn.get_byte(2);
                    float humidity = (float) canIn.get_byte(3);
                    status = canIn.get_byte(4);
                    System.out.println("Density Altitude: " + pressure
                        + " " +  temperature + " " + humidity);
                    relay.sendDensityAltitudeStatus(status, pressure, temperature, humidity);                   
                    return;      
                }
                
                case WIND_ID:
                {
                    float direction = (float) canIn.get_byte(0);
                    float speed = canIn.get_halffloat(2);                    
                    float gust = (float) canIn.get_byte(4);
                    status = canIn.get_byte(6);
                    System.out.println("Wind: " + direction
                        + " " +  speed + " " + gust);
                    relay.sendWindStatus(status,speed, direction, gust);                   
                    return;      
                }                
            }
        }
    }

    private void updateIntegrators(int filterIndex, float filterInput)
    {
        i1[filterIndex] += Math.round(filterInput);
        i2[filterIndex] += i1[filterIndex];
        i3[filterIndex] += i2[filterIndex];
    }

    private float updateCombs(int filterIndex)
    {
        long ctmpe, ctmpo;
        ctmpo = i3[filterIndex] - c1[filterIndex];
        c1[filterIndex] = i3[filterIndex];
        ctmpe = ctmpo - c2[filterIndex];
        c2[filterIndex] = ctmpo;
        ctmpo = ctmpe - c3[filterIndex];
        c3[filterIndex] = ctmpe;
        return ((float) ctmpo);
    }

    public void update()
    {
    } //this guy is not used, but needed because we are implementing Observer
    
    public double getCurrentUnixTime()
    {
        return currentUnixTime;
    }
    
    public boolean calculateLaunchParameters()
    {
        CurrentLaunchInformation cli = CurrentLaunchInformation.getCurrentLaunchInformation();
        boolean isReady = cli.IsReady();
        if(!isReady) return false;
        //we can use cli to get all the info needed...
        //and set our array of doubles with the necessary values.
        
        return true;
    }
    
    public void sendLaunchParameters(boolean launchReady)
    {
        if(!launchReady)
        {
            //here we can alert the operator
            //or send any necessary msgs back to the MC
        }
        else
        {
            MessagePipeline pipe = MessagePipeline.getInstance();
            CanCnvt testMsg = new CanCnvt();
            testMsg.id = LAUNCH_PARAM_MESSAGE_ID;
            testMsg.set_int(-1, 0);
            testMsg.set_int(255, 4);
            testMsg.dlc = 5;

            pipe.WriteToSocket(testMsg.msg_prep());
            //....
            //....send all messages needed
        }
    }
}
