package com.github.smuddgge.connections;

import java.io.IOException;

/**
 * Represents the connection to the server
 */
public class ClientConnection extends Connection {

    /**
     * Used to initialise the connection to the server
     * @param host The host
     *             localhost if running on the same machine
     * @param port The port the server is running on
     * @throws IOException Connection error
     */
    public ClientConnection(String host, int port) throws IOException {
        super(host, port);
    }

    /**
     * Used to request something from the server
     * @param request Information to send to the server
     * @return Result from the server
     */
    public String request(String request) throws IOException {
        // Send request to the server
        this.send(request);

        // Read response from the server
        return this.read();
    }
}
