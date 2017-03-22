package Factories;

import reader.Message;


public class U8_U8_FF_Payload extends PayloadDescriptor
{

    public String[] processPayload(Canmsg2j can)
    {
        String[] payloads = new String[8];
        return payloads;
    }

    @Override
    public String exportPayload(Message message)
    {
        return "";
    }
}
