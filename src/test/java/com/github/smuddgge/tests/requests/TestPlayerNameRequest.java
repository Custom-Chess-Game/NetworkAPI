package com.github.smuddgge.tests.requests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;
import com.github.smuddgge.events.PlayerConnectionEvent;
import com.github.smuddgge.events.PlayerDisconnectEvent;
import com.github.smuddgge.events.PlayerStatusEvent;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.requests.ClientListRequest;
import com.github.smuddgge.results.ResultChecker;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.PlayerStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class TestPlayerNameRequest {

    @Test
    public void testConnectingAndDisconnecting() throws IOException, InterruptedException {
        Server server = MockServer.startAndGet(17300);
        server.setDebugMode(true);

        ClientConnection client1 = new ClientConnection("localhost", 17300);
        client1.setDebugMode(true);

        ClientConnection client2 = new ClientConnection("localhost", 17300);
        client2.setDebugMode(true);

        client1.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.fromString("015d976d-4bb1-46e9-80a7-1dbae4b8bb53")));
        client1.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        client2.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("John", UUID.randomUUID()));

        Console.print(ConsoleColour.PINK + "Clients : " + client2.getNetworkManager().request(new ClientListRequest()));

        client1.getNetworkManager().broadcastEvent(new PlayerDisconnectEvent());

        Thread.sleep(100);

        Object result = client2.getNetworkManager().request(new ClientListRequest());
        Console.print(ConsoleColour.PINK + "Clients : " + result);
    }
}
