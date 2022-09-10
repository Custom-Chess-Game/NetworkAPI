package com.github.smuddgge.tests.events;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.server.Server;
import org.junit.jupiter.api.Test;

/**
 * Used to test events
 */
public class TestEvents {

    @Test
    public void testPlayerConnectionEvent() throws Exception {
        Server server = MockServer.startAndGet(17300);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17300);
        clientConnection.setDebugMode(true);
    }
}
