package com.github.smuddgge.packets;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>Represents data sent over a socket</h2>
 * Data is stored in a map and converted into a
 * json format to be sent over the socket.
 */
public class Packet {

    protected Map<String, Object> packet;

    public Packet() {
        this.packet = new HashMap<>();
    }

    public Packet(Map<String, Object> packet) {
        this.packet = packet;
    }

    /**
     * Used to add data to the packet
     * @param data Data to add to the packet
     */
    public void append(Map<String, Object> data) {
        this.packet.putAll(data);
    }

    /**
     * Used to add data to the packet
     */
    public void append(String key, Object value) {
        this.packet.put(key, value);
    }

    /**
     * Used to get the map of the packet
     * @return Map of the packet
     */
    public Map<String, Object> getMap() {
        return this.packet;
    }

    /**
     * Get the packet as a raw json
     * @return Json string
     */
    public String getRaw() {
        Gson gson = new Gson();
        return gson.toJson(this.packet);
    }

    public Packet getPacketFromKey(String key) {
        Map<?, ?> unknownPacket = (Map<?, ?>) this.packet.get(key);
        return Packet.getPacket(unknownPacket);
    }


    /**
     * Used to turn a json string into a packet
     * @param json Json string to convert
     * @return Instance of the json as a packet
     */
    public static Packet getPacket(String json) {
        Gson gson = new Gson();

        Map<?, ?> unknownPacket = gson.fromJson(json, Map.class);
        return Packet.getPacket(unknownPacket);
    }

    public static Packet getPacket(Map<?, ?> unknownPacket) {
        Map<String, Object> packet = new HashMap<>();

        for (Map.Entry<?, ?> entry : unknownPacket.entrySet()) {
            if (!(entry.getKey() instanceof String)) continue;
            packet.put((String) entry.getKey(), entry.getValue());
        }

        return new Packet(packet);
    }
}
