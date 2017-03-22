package Factories;

import reader.Message;



public class CableTensionMessageDescriptor extends MessageDescriptor
{



    public Message processMessage(String[] msg, Canmsg2j cur_can, int index)
    {
        Message message = new Message();
        String[] payloads;
        String displayid = Integer.toHexString(cur_can.id);
        message.setOriginalID(cur_can.id);
        message.setId(displayid);
        message.setLogSystem(msg[0]);
        message.setMessage(msg[1]);
        message.setName("Cable Tension");
        message.setDlc(cur_can.dlc);
        message.setIndex(index);
        if(cur_can.dlc == 5)
        {
            U8_FF_Payload payloadDescriptor = new U8_FF_Payload();
            payloads = payloadDescriptor.processPayload(cur_can);
            message.setPayloads(payloads);
        }
        return message;
    }

    public String exportMessage(Message message)
    {
        U8_FF_Payload payloadDescriptor = new U8_FF_Payload();
        return payloadDescriptor.exportPayload(message);
    }
}
