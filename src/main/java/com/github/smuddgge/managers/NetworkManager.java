package com.github.smuddgge.managers;

import com.github.smuddgge.connections.Connection;
import com.github.smuddgge.events.*;
import com.github.smuddgge.requests.*;

import java.util.ArrayList;

/**
 * Represents a manager of a network socket
 * This will be used to interact with the connections to the sockets
 */
public class NetworkManager {

    protected final ArrayList<Event> events = new ArrayList<>();
    protected final ArrayList<Request> requests = new ArrayList<>();

    /**
     * The connection to the socket
     */
    protected Connection connection;

    /**
     * Used to create a network manager
     * @param connection Connection to the socket
     */
    public NetworkManager(Connection connection) {
        this.connection = connection;

        // Add events
        this.events.add(new PlayerConnectionEvent());
        this.events.add(new PlayerDisconnectEvent());
        this.events.add(new PlayerMoveEvent());
        this.events.add(new PlayerStatusEvent());

        // Add requests
        this.requests.add(new ClientListRequest());
        this.requests.add(new CreateGameRoomRequest());
        this.requests.add(new PlayerMoveRequest());
        this.requests.add(new PlayerGameRoomRequest());
    }
}
