package com.github.smuddgge.requests;

import com.github.smuddgge.database.Record;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to get a list of games that contain the provided key and value
 */
public class DatabaseGameListRequest extends Request {

    /**
     * Used to register the request with the server
     */
    public DatabaseGameListRequest() {
    }

    /**
     * Used to create a database request to the server
     *
     * @param key   The key to look for in the database
     * @param value The value to match the key
     */
    public DatabaseGameListRequest(String key, Object value) {
        this.getRequestPacket().addCredential("key", key);
        this.getRequestPacket().addCredential("value", value);
    }

    @Override
    public Object onRequest(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        String key = (String) credentials.getMap().get("key");
        Object value = credentials.getMap().get("value");

        ArrayList<Record> records = server.getDatabase().getTable("Game").getRecord(key, value);

        Gson gson = new Gson();
        Map<String, Object> responseMap = new HashMap<>();

        int index = 0;
        for (Record record : records) {
            String gameRecordString = gson.toJson(record);
            Map<String, Object> gameRecordMap = gson.fromJson(gameRecordString, Map.class);

            responseMap.put(String.valueOf(index), gameRecordMap);
            index++;
        }

        return responseMap;
    }
}