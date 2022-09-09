package com.github.smuddgge.tests.events;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.events.PlayerConnectionEvent;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.results.ResultChecker;
import com.github.smuddgge.server.Server;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));

        ServerThreadNetworkManager serverThreadNetworkManager =server.getConnections().get(0).getNetworkManager();

        Thread.sleep(100);

        PlayerConnectionEvent connectionEvent = (PlayerConnectionEvent) serverThreadNetworkManager.getEvent(PlayerConnectionEvent.class);
        new ResultChecker().expect(connectionEvent.getName(), "Smudge");
    }
}
