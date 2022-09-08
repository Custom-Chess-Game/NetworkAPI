package com.github.smuddgge.tests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Used to test the server connection class
 * {@link ClientConnection}
 */
public class TestServerConnection {

    public static void main(String[] args) throws IOException {
        Server server = MockServer.startAndGet(17300);

        ClientConnection serverConnection = new ClientConnection("localhost", 17300);
        serverConnection.setDebugMode(true);

        ClientConnection serverConnection2 = new ClientConnection("localhost", 17300);
        serverConnection2.setDebugMode(true);
    }

    @Test
    public void testInitiate() throws IOException {
        Server server = MockServer.startAndGet(17300);

        ClientConnection serverConnection = new ClientConnection("localhost", 17300);
        serverConnection.setDebugMode(true);

        ClientConnection serverConnection2 = new ClientConnection("localhost", 17300);
        serverConnection2.setDebugMode(true);

        server.stop();
    }
}
