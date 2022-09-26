package com.github.smuddgge.events;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.EventPacket;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;

/**
 * Represents an event object
 */
public abstract class Event {

    private final EventPacket eventPacket;

    /**
     * Used to create a new event
     */
    public Event() {
        this.eventPacket = new EventPacket(this.getEventName());
    }

    /**
     * Used to get the name of the event
     *
     * @return Name of the event
     */
    public String getEventName() {
        return this.getClass().getName();
    }

    /**
     * Used when the event packet is sent to the server
     *
     * @param credentials                Credentials from the packet
     * @param server                     The instance of the server running the thread
     * @param serverThreadNetworkManager The thread network manager
     */
    public abstract void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager);

    /**
     * Used to get the event packet
     *
     * @return The event packet
     */
    public EventPacket getEventPacket() {
        return this.eventPacket;
    }
}
