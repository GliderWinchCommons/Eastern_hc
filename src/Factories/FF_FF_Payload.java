package Factories;

import reader.Message;

public class FF_FF_Payload extends PayloadDescriptor
{

    @Override
    public String[] processPayload(Canmsg2j cur_can)
    {
        String[] payloads = new String[8];
        cur_can.in_2int();
        payloads[0] = Double.toString(Float.intBitsToFloat(cur_can.p0));
        payloads[1] = Double.toString(Float.intBitsToFloat(cur_can.p1));
        return payloads;
    }

    @Override
    public String exportPayload(Message message)
    {
        return "";
    }
}
