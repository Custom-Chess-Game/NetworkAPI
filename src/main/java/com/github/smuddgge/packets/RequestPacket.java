package com.github.smuddgge.packets;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>Represents a request packet</h2>
 * Request packets can be sent to get data from the server
 */
public class RequestPacket extends Packet {

    private final Map<String, Object> requestPacket = new HashMap<>();

    /**
     * Used to create a request packet
     * @param name Name of the request packet
     */
    public RequestPacket(String name) {
        this.requestPacket.put("name", name);
        this.packageRequest();
    }

    /**
     * Used to package the request packet
     */
    public void packageRequest() {
        this.packet.put("request", this.requestPacket);
    }

    /**
     * Used to get the request packet
     * @return Event packet as a packet
     */
    public Packet getRequestPacketMap() {
        return this.getPacketFromKey("request");
    }

    /**
     * Used to get the name from the packet
     * @return Request name
     */
    public String getName() {
        return (String) this.getRequestPacketMap().getMap().get("name");
    }

    /**
     * Used to check if a packet contains a request
     * @param packet Packet to check
     * @return True if it contains a request
     */
    public static boolean containsRequest(Packet packet) {
        return packet.getMap().containsKey("request");
    }

    /**
     * Used to convert a json string into a request packet
     * @param json Json string to convert
     * @return Instance of the request packet
     */
    public static RequestPacket getRequestPacket(String json) {
        Packet packet = Packet.getPacket(json);
        RequestPacket requestPacket = new RequestPacket((String) packet.getPacketFromKey("request").getMap().get("name"));
        requestPacket.packet = packet.getMap();
        return requestPacket;
    }
}
