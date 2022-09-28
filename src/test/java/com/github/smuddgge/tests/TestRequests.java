package com.github.smuddgge.tests;

import com.github.smuddgge.connections.ClientConnection;
import com.github.smuddgge.database.data.GameRecord;
import com.github.smuddgge.database.data.PlayerRecord;
import com.github.smuddgge.events.*;
import com.github.smuddgge.mocks.server.MockServer;
import com.github.smuddgge.requests.*;
import com.github.smuddgge.results.ResultChecker;
import com.github.smuddgge.results.ResultNotNull;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.PlayerStatus;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Used to test requests
 */
public class TestRequests {

    @Test
    public void testDatabaseGameListRequest() throws Exception {
        Server server = MockServer.startAndGet(15000);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 15000);
        clientConnection.setDebugMode(true);

        GameRecord gameRecord = new GameRecord();
        gameRecord.uuid = String.valueOf(UUID.randomUUID());
        gameRecord.player1 = String.valueOf(UUID.randomUUID());
        gameRecord.player2 = String.valueOf(UUID.randomUUID());
        gameRecord.log = "[log]";
        gameRecord.timeStamp = String.valueOf(System.currentTimeMillis());

        clientConnection.getNetworkManager().broadcastEvent(new DatabaseGameUpdateEvent(gameRecord));

        Object response = clientConnection.getNetworkManager().request(new DatabaseGameListRequest("uuid", gameRecord.uuid));
        Map<String, Object> responseMap = (Map<String, Object>) response;

        for (Map.Entry<String, Object> entry : responseMap.entrySet()) {
            Gson gson = new Gson();
            String responseJson = gson.toJson(entry.getValue());
            GameRecord responseGameRecord = gson.fromJson(responseJson, GameRecord.class);

            if (!Objects.equals(responseGameRecord.uuid, gameRecord.uuid)) continue;

            new ResultChecker()
                    .expect(gameRecord.uuid, responseGameRecord.uuid)
                    .expect(gameRecord.player1, responseGameRecord.player1)
                    .expect(gameRecord.player2, responseGameRecord.player2)
                    .expect(gameRecord.log, responseGameRecord.log)
                    .expect(gameRecord.timeStamp, responseGameRecord.timeStamp);

            return;
        }

        throw new Exception("Responce does not contain the new record");
    }

    @Test
    public void testDatabaseGameRequest() throws Exception {
        Server server = MockServer.startAndGet(16000);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 16000);
        clientConnection.setDebugMode(true);

        GameRecord gameRecord = new GameRecord();
        gameRecord.uuid = String.valueOf(UUID.randomUUID());
        gameRecord.player1 = String.valueOf(UUID.randomUUID());
        gameRecord.player2 = String.valueOf(UUID.randomUUID());
        gameRecord.log = "[log]";
        gameRecord.timeStamp = String.valueOf(System.currentTimeMillis());

        clientConnection.getNetworkManager().broadcastEvent(new DatabaseGameUpdateEvent(gameRecord));

        Object response = clientConnection.getNetworkManager().request(new DatabaseGameRequest("uuid", gameRecord.uuid));
        Map<String, Object> responseMap = (Map<String, Object>) response;
        Gson gson = new Gson();
        String responseJson = gson.toJson(responseMap);
        GameRecord responseGameRecord = gson.fromJson(responseJson, GameRecord.class);

        new ResultChecker()
                .expect(gameRecord.uuid, responseGameRecord.uuid)
                .expect(gameRecord.player1, responseGameRecord.player1)
                .expect(gameRecord.player2, responseGameRecord.player2)
                .expect(gameRecord.log, responseGameRecord.log)
                .expect(gameRecord.timeStamp, responseGameRecord.timeStamp);
    }

    @Test
    public void testDatabasePlayerRequest() throws Exception {
        Server server = MockServer.startAndGet(16001);
        server.setDebugMode(true);

        ClientConnection clientConnection = new ClientConnection("localhost", 16001);
        clientConnection.setDebugMode(true);

        PlayerRecord playerRecord = new PlayerRecord();
        playerRecord.uuid = String.valueOf(UUID.randomUUID());
        playerRecord.name = "Smudge";
        playerRecord.joinDate = "2022";

        clientConnection.getNetworkManager().broadcastEvent(new DatabasePlayerUpdateEvent(playerRecord));

        Object response = clientConnection.getNetworkManager().request(new DatabasePlayerRequest("uuid", playerRecord.uuid));
        Map<String, Object> responseMap = (Map<String, Object>) response;
        Gson gson = new Gson();
        String responseJson = gson.toJson(responseMap);
        PlayerRecord responsePlayerRecord = gson.fromJson(responseJson, PlayerRecord.class);

        new ResultChecker()
                .expect(playerRecord.uuid, responsePlayerRecord.uuid)
                .expect(playerRecord.name, responsePlayerRecord.name)
                .expect(playerRecord.joinDate, responsePlayerRecord.joinDate);
    }

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
