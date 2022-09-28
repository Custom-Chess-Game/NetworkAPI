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
     *
     * @param connection Connection to the socket
     */
    public NetworkManager(Connection connection) {
        this.connection = connection;

        // Add events
        this.events.add(new DatabasePlayerUpdateEvent());
        this.events.add(new DatabaseGameUpdateEvent());

        this.events.add(new GameRoomCreateEvent());
        this.events.add(new GameRoomDeleteEvent());
        this.events.add(new GameRoomJoinEvent());

        this.events.add(new PlayerConnectionEvent());
        this.events.add(new PlayerDisconnectEvent());
        this.events.add(new PlayerMoveEvent());
        this.events.add(new PlayerStatusEvent());

        // Add requests
        this.requests.add(new DatabaseGameListRequest());
        this.requests.add(new DatabaseGameRequest());
        this.requests.add(new DatabasePlayerRequest());

        this.requests.add(new GameRoomListRequest());
        this.requests.add(new GameRoomRequest());

        this.requests.add(new PlayerListRequest());
        this.requests.add(new PlayerMoveRequest());
    }
}
