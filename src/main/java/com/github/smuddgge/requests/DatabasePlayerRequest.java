package com.github.smuddgge.requests;

import com.github.smuddgge.database.Record;
import com.github.smuddgge.database.data.PlayerRecord;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

/**
 * Request is used to get a players record from the database
 */
public class DatabasePlayerRequest extends Request {

    /**
     * Used to register the request with the server
     */
    public DatabasePlayerRequest() {
    }

    /**
     * Used to create a database request to the server
     *
     * @param key   The key to look for in the database
     * @param value The value to match the key
     */
    public DatabasePlayerRequest(String key, Object value) {
        this.getRequestPacket().addCredential("key", key);
        this.getRequestPacket().addCredential("value", value);
    }

    @Override
    public Object onRequest(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        String key = (String) credentials.getMap().get("key");
        Object value = credentials.getMap().get("value");

        ArrayList<Record> records = server.getDatabase().getTable("Player").getRecord(key, value);
        PlayerRecord playerRecord = (PlayerRecord) records.get(0);

        Gson gson = new Gson();

        String playerRecordString = gson.toJson(playerRecord);
        Map<String, Object> playerRecordMap = gson.fromJson(playerRecordString, Map.class);

        return playerRecordMap;
    }
}
