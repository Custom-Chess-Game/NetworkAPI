package com.github.smuddgge.managers;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.events.Event;
import com.github.smuddgge.packets.ResponsePacket;
import com.github.smuddgge.requests.Request;

import java.io.IOException;

public class ClientNetworkManager extends NetworkManager {

    /**
     * Used to create a network manager
     * Used to create a client specific network manager
     *
     * @param connection Connection to the socket
     */
    public ClientNetworkManager(ClientConnection connection) {
        super(connection);
    }

    /**
     * Used to broadcast an event to the server
     *
     * @param event Event to broadcast
     */
    public void broadcastEvent(Event event) {
        this.connection.send(event.getEventPacket().getRaw());
    }

    public Object request(Request request) {
        try {
            ClientConnection clientConnection = (ClientConnection) this.connection;
            String json = clientConnection.request(request.getRequestPacket().getRaw());
            ResponsePacket responsePacket = ResponsePacket.getResponsePacket(json);

            return responsePacket.getData();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
