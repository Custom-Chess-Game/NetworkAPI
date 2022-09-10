package com.github.smuddgge.managers;

import com.github.smuddgge.connections.Connection;
import com.github.smuddgge.connections.ServerThreadConnection;
import com.github.smuddgge.events.Event;
import com.github.smuddgge.packets.EventPacket;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.packets.RequestPacket;
import com.github.smuddgge.requests.Request;
import com.github.smuddgge.server.Server;

import java.util.Objects;

public class ServerThreadNetworkManager extends NetworkManager {

    /**
     * Instance of the server the thread is running on
     */
    private final Server server;

    /**
     * Used to create a network manager
     * Used to create a server specific manager
     * @param connection Connection to the socket
     */
    public ServerThreadNetworkManager(Connection connection, Server server) {
        super(connection);

        this.server = server;
    }

    /**
     * Used to interpret data sent to the server
     * @param data Data sent to the server
     */
    public void interpret(String data) {
        Packet packet = Packet.getPacket(data);

        // Check if the packet contains an event
        if (EventPacket.containsEvent(packet)) {
            this.interpretEvent(EventPacket.getEventPacket(data));
        }

        // Check if the packet contains a request
        if (RequestPacket.containsRequest(packet)) {
            this.interpretRequest(RequestPacket.getRequestPacket(data));
        }
    }

    /**
     * Used to interpret events sent to the server
     */
    private void interpretEvent(EventPacket eventPacket) {
        for (Event event : this.events) {
            if (!Objects.equals(eventPacket.getName(), event.getEventName())) continue;
            event.update(eventPacket.getEventCredentialsMap(), this.server, this);
        }
    }

    /**
     * Used to interpret requests sent to the server
     */
    private void interpretRequest(RequestPacket requestPacket) {
        for (Request request : this.requests) {
            if (!Objects.equals(requestPacket.getName(), request.getName())) continue;
            this.connection.send(request.getResponsePacket(this.server, this));
        }
    }

    /**
     * Used to get an event instance
     * @param event Event to get
     * @return Event instance
     */
    public Event getEvent(Class<?> event) {
        for (Event temp : this.events) {
            if (temp.getClass() != event) continue;
            return temp;
        }
        return null;
    }

    /**
     * Used to get the server thread connection
     * @return The server thread connection
     */
    public ServerThreadConnection getServerThread() {
        return (ServerThreadConnection) this.connection;
    }
}
