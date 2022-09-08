package com.github.smuddgge.mocks.server;

import com.github.smuddgge.server.Server;

import java.io.IOException;

/**
 * Used to create a mock server
 */
public class MockServer {

    /**
     * Used to start up a local host server
     * @param port Port to use
     * @return Instance of the server
     */
    public static Server startAndGet(int port) throws IOException {
        Server server = new Server(port);

        new Thread(server::run).start();

        return server;
    }
}
