package Factories;

import reader.Message;

/**
 * MessageDescriptor is a class that is used to process and return a Message object. Each child of MessageDescriptor
 * overrides the processMessage to process a specific type of message.
 */

public abstract class MessageDescriptor
{
    public abstract Message processMessage(String[] message_row, Canmsg2j cur_can, int messageIndex);

    public abstract String exportMessage(Message message);
}
