package Factories;

import reader.Message;

public class U32_U32_Payload extends PayloadDescriptor
{
    @Override
    public String[] processPayload(Canmsg2j can)
    {
        String[] payloads = new String[8];
        can.in_2int();
        payloads[0] = Integer.toString(can.p0);
        payloads[1] = Integer.toString(can.p1);
        return payloads;
    }

    @Override
    public String exportPayload(Message message)
    {
        return "";
    }
}
