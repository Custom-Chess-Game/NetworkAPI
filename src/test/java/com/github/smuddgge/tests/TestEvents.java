package com.github.smuddgge.tests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.database.data.GameRecord;
import com.github.smuddgge.database.data.PlayerRecord;
import com.github.smuddgge.events.*;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.AddressDistributer;
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
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", port);
        clientConnection.setDebugMode(true);

        PlayerRecord playerRecord = new PlayerRecord();
        playerRecord.uuid = String.valueOf(UUID.randomUUID());
        playerRecord.name = "Smudge";
        playerRecord.joinDate = "2022";

        clientConnection.getNetworkManager().broadcastEvent(new DatabasePlayerUpdateEvent(playerRecord));
    }

    @Test
    public void testGameUpdateEvent() throws Exception {
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", port);
        clientConnection.setDebugMode(true);

        GameRecord gameRecord = new GameRecord();
        gameRecord.uuid = String.valueOf(UUID.randomUUID());
        gameRecord.player1 = String.valueOf(UUID.randomUUID());
        gameRecord.player2 = String.valueOf(UUID.randomUUID());
        gameRecord.winningPlayer = gameRecord.player1;
        gameRecord.winningColour = "WHITE";
        gameRecord.log = "[log]";
        gameRecord.timeStamp = String.valueOf(System.currentTimeMillis());

        clientConnection.getNetworkManager().broadcastEvent(new DatabaseGameUpdateEvent(gameRecord));
    }

    @Test
    public void testGameRoomCreateEvent() throws Exception {
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", port);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));
        clientConnection.getNetworkManager().broadcastEvent(new GameRoomCreateEvent("Room1", UUID.randomUUID()));
    }

    @Test
    public void testGameRoomDeleteEvent() throws Exception {
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", port);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));
        clientConnection.getNetworkManager().broadcastEvent(new GameRoomCreateEvent("Room1", UUID.randomUUID()));

        Thread.sleep(100);

        clientConnection.getNetworkManager().broadcastEvent(new GameRoomDeleteEvent());
    }

    @Test
    public void testGameRoomJoinEvent() throws Exception {
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", port);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        ClientConnection clientConnection2 = new ClientConnection("localhost", port);
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
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", port);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
    }

    @Test
    public void testPlayerDisconnectEvent() throws IOException {
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", port);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerDisconnectEvent());
    }

    @Test
    public void testPlayerMoveEvent() throws Exception {
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", port);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerConnectionEvent("Smudge", UUID.randomUUID()));
        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.WAITING));

        ClientConnection clientConnection2 = new ClientConnection("localhost", port);
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
        int port = AddressDistributer.next();
        Server server = MockServer.startAndGet(port);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", port);
        clientConnection.setDebugMode(true);

        clientConnection.getNetworkManager().broadcastEvent(new PlayerStatusEvent(PlayerStatus.IN_GAME));
    }
}
