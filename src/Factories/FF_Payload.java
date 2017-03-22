package Factories;


import reader.Message;

public class FF_Payload extends PayloadDescriptor
{

    @Override
    public String[] processPayload(Canmsg2j cur_can)
    {
        String[] payloads = new String[8];
        cur_can.in_1int_n(1);
        double p1 = Float.intBitsToFloat(cur_can.p0);
        payloads[0] = Double.toString(p1);
        return payloads;
    }

    @Override
    public String exportPayload(Message message)
    {
        return "";
    }
}
