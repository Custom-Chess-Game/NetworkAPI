package com.github.smuddgge.tests;

import com.github.smuddgge.connections.ServerConnection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Used to test the server connection class
 * {@link com.github.smuddgge.connections.ServerConnection}
 */
public class TestServerConnection {

    @Test
    public void testInitiate() throws IOException {
        ServerConnection serverConnection = new ServerConnection("localhost", 17300);
        serverConnection.send("ping");
    }
}
