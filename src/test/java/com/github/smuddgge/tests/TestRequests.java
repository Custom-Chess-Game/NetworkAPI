package com.github.smuddgge.tests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.events.*;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.requests.PlayerListRequest;
import com.github.smuddgge.requests.GameRoomListRequest;
import com.github.smuddgge.requests.GameRoomRequest;
import com.github.smuddgge.requests.PlayerMoveRequest;
import com.github.smuddgge.results.ResultChecker;
import com.github.smuddgge.results.ResultNotNull;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.PlayerStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * Used to test requests
 */
public class TestRequests {

    @Test
    public void testClientListRequest() throws Exception {
        Server server = MockServer.startAndGet(17200);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17200);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));

        ClientConnection clientConnection2 = new ClientConnection("localhost", 17200);
        clientConnection2.setDebugMode(true);

        clientConnection2.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Fluff", UUID.randomUUID()));

        new ClientConnection("localhost", 17200);

        Object response = clientConnection.getNetworkManager().request(new PlayerListRequest());
        new ResultChecker().expect(response, new ResultNotNull());
    }

    @Test
    public void testGameRoomListRequest() throws Exception {
        Server server = MockServer.startAndGet(17201);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17201);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new GameRoomCreateEvent("Room1", UUID.randomUUID()));

        Object response = clientConnection.getNetworkManager().request(new GameRoomListRequest());
        new ResultChecker().expect(response, new ResultNotNull());
    }

    @Test
    public void testGameRoomRequest() throws Exception {
        Server server = MockServer.startAndGet(17202);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17202);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new GameRoomCreateEvent("Room1", UUID.randomUUID()));

        Object response = clientConnection.getNetworkManager().request(new GameRoomRequest());
        new ResultChecker().expect(response, new ResultNotNull());
    }

    @Test
    public void testPlayerMoveRequest() throws Exception {
        Server server = MockServer.startAndGet(17203);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17203);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        ClientConnection clientConnection2 = new ClientConnection("localhost", 17203);
        clientConnection2.setDebugMode(true);

        clientConnection2.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Fluff", UUID.randomUUID()));
        clientConnection2.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        UUID uuid = UUID.randomUUID();

        clientConnection.getNetworkManager().broadcastEvent(new GameRoomCreateEvent("Room1", uuid));

        Thread.sleep(100);

        clientConnection2.getNetworkManager().broadcastEvent(new GameRoomJoinEvent(uuid));

        clientConnection.getNetworkManager().broadcastEvent(new PlayerMoveEvent("1, 1"));

        Thread.sleep(100);

        Object request = clientConnection2.getNetworkManager().request(new PlayerMoveRequest());
        new ResultChecker().expect(request, "1, 1");
    }
}
