package com.github.smuddgge.packets;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>Represents an event packet</h2>
 * An event packet is a collection of data describing a certain event.
 * Every event will have a name and a collection of credentials.
 * Event credentials are custom to the event and used exchange data
 * from the client to the server.
 *
 * An event pack it extended from a {@link Packet}
 */
public class EventPacket extends Packet {

    private final Map<String, Object> eventPacket = new HashMap<>();
    private final Map<String, Object> eventCredentials = new HashMap<>();

    public EventPacket(String name) {
        this.eventPacket.put("name", name);
    }

    /**
     * Used to get the event packet
     * @return Event packet as a packet
     */
    public Packet getEventPacketMap() {
        return this.getPacketFromKey("event");
    }

    /**
     * Used to get the credentials packet
     * @return Event credentials as a packet
     */
    public Packet getEventCredentialsMap() {
        return this.getEventPacketMap().getPacketFromKey("credentials");
    }

    /**
     * Used to get the name from the packet
     * @return Event name
     */
    public String getName() {
        return (String) this.getEventPacketMap().getMap().get("name");
    }

    /**
     * Used to package the credentials
     * When packaged you are able to send the data to the server
     */
    private void packageCredentials() {
        this.eventPacket.put("credentials", this.eventCredentials);
        this.append("event", this.eventPacket);
    }

    /**
     * Used to add data to the event packet
     */
    public void addCredential(String key, Object value) {
        this.eventCredentials.put(key, value);

        this.packageCredentials();
    }

    /**
     * Used to check if a packet is an event packet
     * @param packet Packet to check
     * @return True if the packet is an event packet
     */
    public static boolean containsEvent(Packet packet) {
        return packet.getMap().containsKey("event");
    }

    /**
     * Used to convert a json string into an event packet
     * @param json Json to convert
     * @return Instance of the event packet
     */
    public static EventPacket getEventPacket(String json) {
        Packet packet = Packet.getPacket(json);
        EventPacket eventPacket = new EventPacket((String) packet.getPacketFromKey("event").getMap().get("name"));
        eventPacket.packet = packet.getMap();
        return eventPacket;
    }
}
