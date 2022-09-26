package com.github.smuddgge.requests;

import com.github.smuddgge.database.Record;
import com.github.smuddgge.database.data.GameRecord;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

/**
 * Request is used to get a game record from the database
 */
public class DatabaseGameRequest extends Request {

    /**
     * Used to register the request with the server
     */
    public DatabaseGameRequest() {
    }

    /**
     * Used to create a database request to the server
     *
     * @param key   The key to look for in the database
     * @param value The value to match the key
     */
    public DatabaseGameRequest(String key, Object value) {
        this.getRequestPacket().addCredential("key", key);
        this.getRequestPacket().addCredential("value", value);
    }

    @Override
    public Object onRequest(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        String key = (String) credentials.getMap().get("key");
        Object value = credentials.getMap().get("value");

        ArrayList<Record> records = server.getDatabase().getTable("Game").getRecord(key, value);
        GameRecord gameRecord = (GameRecord) records.get(0);

        Gson gson = new Gson();

        String gameRecordString = gson.toJson(gameRecord);
        Map<String, Object> gameRecordMap = gson.fromJson(gameRecordString, Map.class);

        return gameRecordMap;
    }
}

