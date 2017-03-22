package Factories;

import reader.Message;

public class U32_Payload extends PayloadDescriptor
{

    @Override
    public String[] processPayload(Canmsg2j cur_can)
    {
        String[] payloads = new String[8];
        cur_can.in_1int();
        payloads[0] = Integer.toString(cur_can.p0);
        return payloads;
    }

    @Override
    public String exportPayload(Message message)
    {
        return "";
    }
}
