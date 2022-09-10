package com.github.smuddgge.tests.requests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;
import com.github.smuddgge.events.PlayerConnectionEvent;
import com.github.smuddgge.events.PlayerMoveEvent;
import com.github.smuddgge.events.PlayerStatusEvent;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.requests.CreateGameRoomRequest;
import com.github.smuddgge.requests.PlayerMoveRequest;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.PlayerStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

public class TestGameMoveRequest {

    @Test
    public void test() throws IOException {
        Server server = MockServer.startAndGet(17300);
        server.setDebugMode(true);

        // First client
        ClientConnection client = new ClientConnection("localhost", 17300);
        client.setDebugMode(true);

        UUID clientUUID = UUID.randomUUID();

        client.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", clientUUID));
        client.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        // Second client
        ClientConnection client2 = new ClientConnection("localhost", 17300);
        client2.setDebugMode(true);

        UUID client2UUID = UUID.randomUUID();

        client2.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Fluff", client2UUID));
        client2.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        // Create a room
        boolean success = (boolean) client.getNetworkManager().request(new CreateGameRoomRequest());
        Console.print(ConsoleColour.PINK + "[Result] Create game room: " + success);

        // Client 1 make a move
        client.getNetworkManager().broadcastEvent(new PlayerMoveEvent("[1, 1]"));
        Console.print(ConsoleColour.PINK + "[Result] Broadcast event: PlayerMoveEvent([1, 1])");

        // Client 2 get move made
        String move = (String) client2.getNetworkManager().request(new PlayerMoveRequest());
        Console.print(ConsoleColour.PINK + "[Result] Get clients move: " + move);

        // Client 2 get move - should be null
        move = (String) client2.getNetworkManager().request(new PlayerMoveRequest());
        Console.print(ConsoleColour.PINK + "[Result] Get clients move: " + move);
    }
}
