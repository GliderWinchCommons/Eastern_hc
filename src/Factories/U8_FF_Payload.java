package Factories;


import reader.Message;

import java.text.DecimalFormat;

public class U8_FF_Payload extends PayloadDescriptor
{


    @Override
    public String[] processPayload(Canmsg2j can)
    {
        String[] payloads = new String[8];
        int p0 = can.pb[6] & 0xFF; //Convert int to uint8_t
        can.in_1int_n(1);
        double p1 = Float.intBitsToFloat(can.p0);
        payloads[0] = Integer.toString(p0);
        payloads[1] = Double.toString(p1);
        return payloads;
    }

    @Override
    public String exportPayload(Message message)
    {
        String formattedPayloads = "";
        DecimalFormat intFormatter = new DecimalFormat("000000000000000");
        //DecimalFormat floatFormatter = new DecimalFormat("0.#########E000");
        int p0 = Integer.parseInt(message.getPayload0());
        formattedPayloads += intFormatter.format(p0) + " ";
        float p1 = Float.parseFloat(message.getPayload1());
        formattedPayloads += String.format("%15.7E", p1) + " ";
        //Unused payloads are just assigned 0
        formattedPayloads += intFormatter.format(0) + " ";
        formattedPayloads += intFormatter.format(0) + " ";
        formattedPayloads += intFormatter.format(0) + " ";
        formattedPayloads += intFormatter.format(0) + " ";
        formattedPayloads += intFormatter.format(0) + " ";
        formattedPayloads += intFormatter.format(0);
        return formattedPayloads;
    }
}
