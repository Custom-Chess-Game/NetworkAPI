package com.github.smuddgge.requests;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.RequestPacket;
import com.github.smuddgge.packets.ResponsePacket;
import com.github.smuddgge.server.Server;

/**
 * Represents a request to the server
 */
public abstract class Request {

    private final RequestPacket requestPacket;

    /**
     * Used to create a new request
     */
    public Request() {
        this.requestPacket = new RequestPacket(this.getName());
    }

    /**
     * Used to get the name of the request
     * @return Name of the request
     */
    public String getName() {
        return this.getClass().getName();
    }

    /**
     * Called when the request reaches the server
     * @param server Instance of the server
     * @return Object data
     */
    public abstract Object onRequest(Server server, ServerThreadNetworkManager serverThreadNetworkManager);

    /**
     * Used to get the request packet
     * @return The request packet
     */
    public RequestPacket getRequestPacket() {
        return this.requestPacket;
    }

    /**
     * Used to get the response packet
     * @param server Instance of the server
     * @return The raw json string
     */
    public String getResponsePacket(Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        Object responseData = this.onRequest(server, serverThreadNetworkManager);
        ResponsePacket responsePacket = new ResponsePacket(this.getName());
        responsePacket.setData(responseData);
        return responsePacket.getRaw();
    }
}
