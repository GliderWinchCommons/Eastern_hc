package Factories;

import reader.Message;



public class UnrecognizedMessageDescriptor extends MessageDescriptor
{
    public Message processMessage(String[] row, Canmsg2j cur_can, int index)
    {
        Message processedMessage = new Message();
        processedMessage.setName("Unknown");
        String displayid = Integer.toHexString(cur_can.id);
        processedMessage.setId(displayid);
        processedMessage.setLogSystem(row[0]);
        processedMessage.setIndex(index);
        processedMessage.setMessage(row[1]);

        return processedMessage;
    }

    public String exportMessage(Message message)
    {
        return "";
    }
}
