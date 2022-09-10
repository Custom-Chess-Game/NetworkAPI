package com.github.smuddgge.tests.requests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;
import com.github.smuddgge.events.PlayerConnectionEvent;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.requests.ClientListRequest;
import com.github.smuddgge.results.ResultChecker;
import com.github.smuddgge.results.ResultNotNull;
import com.github.smuddgge.server.Server;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

public class TestClientListRequest {

    @Test
    public void testConnectingAndDisconnecting() throws Exception {
        Server server = MockServer.startAndGet(17300);
        server.setDebugMode(true);

        ClientConnection client = new ClientConnection("localhost", 17300);
        client.setDebugMode(true);

        client.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.fromString("138dca15-9e21-4785-8063-c6f1ce4fe443")));

        Map<String, Object> result = (Map<String, Object>) client.getNetworkManager().request(new ClientListRequest());
        Console.print(ConsoleColour.PINK + "[Result] " + result);
        new ResultChecker().expect(result.get("138dca15-9e21-4785-8063-c6f1ce4fe443"), new ResultNotNull());
    }
}
