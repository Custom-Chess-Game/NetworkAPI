package com.github.smuddgge.tests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.database.data.GameRecord;
import com.github.smuddgge.database.data.PlayerRecord;
import com.github.smuddgge.events.*;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.requests.PlayerMoveRequest;
import com.github.smuddgge.results.ResultChecker;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.PlayerStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * Used to test events
 */
public class TestEvents {

    @Test
    public void testPlayerUpdateEvent() throws Exception {
        Server server = MockServer.startAndGet(16000);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 16000);
        clientConnection.setDebugMode(true);

        PlayerRecord playerRecord = new PlayerRecord();
        playerRecord.uuid = String.valueOf(UUID.randomUUID());
        playerRecord.name = "Smudge";
        playerRecord.joinDate = "2022";

        clientConnection.getNetworkManager().broadcastEvent(new DatabasePlayerUpdateEvent(playerRecord));
    }

    @Test
    public void testGameUpdateEvent() throws Exception {
        Server server = MockServer.startAndGet(16001);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 16001);
        clientConnection.setDebugMode(true);

        GameRecord gameRecord = new GameRecord();
        gameRecord.uuid = String.valueOf(UUID.randomUUID());
        gameRecord.player1 = String.valueOf(UUID.randomUUID());
        gameRecord.player2 = String.valueOf(UUID.randomUUID());
        gameRecord.log = "[log]";
        gameRecord.timeStamp = String.valueOf(System.currentTimeMillis());

        clientConnection.getNetworkManager().broadcastEvent(new DatabaseGameUpdateEvent(gameRecord));
    }

    @Test
    public void testGameRoomCreateEvent() throws Exception {
        Server server = MockServer.startAndGet(17200);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17200);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));
        clientConnection.getNetworkManager().broadcastEvent(new GameRoomCreateEvent("Room1", UUID.randomUUID()));
    }

    @Test
    public void testGameRoomDeleteEvent() throws Exception {
        Server server = MockServer.startAndGet(17201);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17201);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));
        clientConnection.getNetworkManager().broadcastEvent(new GameRoomCreateEvent("Room1", UUID.randomUUID()));

        Thread.sleep(100);

        clientConnection.getNetworkManager().broadcastEvent(new GameRoomDeleteEvent());
    }

    @Test
    public void testGameRoomJoinEvent() throws Exception {
        Server server = MockServer.startAndGet(17222);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17222);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        ClientConnection clientConnection2 = new ClientConnection("localhost", 17222);
        clientConnection2.setDebugMode(true);

        clientConnection2.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Fluff", UUID.randomUUID()));
        clientConnection2.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        UUID uuid = UUID.randomUUID();

        clientConnection.getNetworkManager().broadcastEvent(new GameRoomCreateEvent("Room1", uuid));

        Thread.sleep(100);

        clientConnection2.getNetworkManager().broadcastEvent(new GameRoomJoinEvent(uuid));
    }

    @Test
    public void testPlayerConnectionEvent() throws Exception {
        Server server = MockServer.startAndGet(17300);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17300);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
    }

    @Test
    public void testPlayerDisconnectEvent() throws IOException {
        Server server = MockServer.startAndGet(17301);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17301);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerDisconnectEvent());
    }

    @Test
    public void testPlayerMoveEvent() throws Exception {
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
    }

    @Test
    public void testPlayerStatusEvent() throws IOException {
        Server server = MockServer.startAndGet(17302);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 17302);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.IN_GAME));
    }
}
