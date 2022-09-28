package com.github.smuddgge.tests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.results.ResultChecker;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.AddressDistributer;
import org.junit.jupiter.api.Test;

/**
 * Used to test the server connection class
 * {@link ClientConnection}
 */
public class TestConnections {

    @Test
    public void testSendingData() throws Exception {
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection serverConnection = new ClientConnection("localhost", port);
        serverConnection.setDebugMode(true);
        serverConnection.send("ping");

        server.stop();
    }
}
