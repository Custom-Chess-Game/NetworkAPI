package com.github.smuddgge.tests.connections;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.results.ResultChecker;
import com.github.smuddgge.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Used to test the server connection class
 * {@link ClientConnection}
 */
public class TestConnections {

    /**
     * Used to test server connection
     * @param args No arguments needed
     * @throws IOException Network error
     */
    public static void main(String[] args) throws IOException {
        Server server = MockServer.startAndGet(17300);

        ClientConnection serverConnection = new ClientConnection("localhost", 17300);
        serverConnection.setDebugMode(true);

        ClientConnection serverConnection2 = new ClientConnection("localhost", 17300);
        serverConnection2.setDebugMode(true);
    }

    @Test
    public void testSendingData() throws Exception {
        Server server = MockServer.startAndGet(17300);

        ClientConnection serverConnection = new ClientConnection("localhost", 17300);
        serverConnection.setDebugMode(true);
        serverConnection.send("ping");

        server.getConnections().get(0).setDebugMode(true);

        String serverOut = server.getConnections().get(0).read();

        new ResultChecker().expect(serverOut, "ping");

        server.stop();
    }
}
