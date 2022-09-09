package com.github.smuddgge.tests.requests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;
import com.github.smuddgge.events.PlayerConnectionEvent;
import com.github.smuddgge.events.PlayerStatusEvent;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.requests.ClientListRequest;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.PlayerStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

public class TestPlayerNameRequest {

    @Test
    public void test() throws IOException {
        Server server = MockServer.startAndGet(17300);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17300);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        Object request = clientConnection.getNetworkManager().request(new ClientListRequest());

        Console.print(ConsoleColour.PINK + "From Server : " + request);
    }
}
