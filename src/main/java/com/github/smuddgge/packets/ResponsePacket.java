package com.github.smuddgge.packets;

import java.util.HashMap;
import java.util.Map;

public class ResponsePacket extends Packet {

    private final Map<String, Object> responsePacket = new HashMap<>();

    /**
     * Used to create response packet
     * @param name Name of the response
     */
    public ResponsePacket(String name) {
        this.responsePacket.put("name", name);
    }

    /**
     * Used to set the data in the response
     * @param data The response data
     */
    public void setData(Object data) {
        this.responsePacket.put("data", data);
        this.packet.put("response", this.responsePacket);
    }

    /**
     * Used to get the data from the response
     * @return Response data
     */
    public Object getData() {
        return this.getPacketFromKey("response").getMap().get("data");
    }

    /**
     * Used to convert a json string into a response packet
     * @param json Json string to convert
     * @return Instance of the response packet
     */
    public static ResponsePacket getResponsePacket(String json) {
        Packet packet = Packet.getPacket(json);
        ResponsePacket responsePacket = new ResponsePacket((String) packet.getPacketFromKey("response").getMap().get("name"));
        responsePacket.packet = packet.getMap();
        return responsePacket;
    }
}
