package Factories;


import reader.Message;

public abstract class PayloadDescriptor
{
    public abstract String[] processPayload(Canmsg2j can);

    public abstract String exportPayload(Message message);
}
