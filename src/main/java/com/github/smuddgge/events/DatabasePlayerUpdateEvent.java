package com.github.smuddgge.events;

import com.github.smuddgge.database.data.PlayerRecord;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;

/**
 * <h2>Represents a player record update</h2>
 * Updates a player record in the servers database
 */
public class DatabasePlayerUpdateEvent extends Event {

    public DatabasePlayerUpdateEvent() {
    }

    /**
     * Used to create a new player update event
     *
     * @param playerRecord The players record to update in the database
     */
    public DatabasePlayerUpdateEvent(PlayerRecord playerRecord) {
        this.getEventPacket().addCredential("uuid", playerRecord.uuid);
        this.getEventPacket().addCredential("name", playerRecord.name);
        this.getEventPacket().addCredential("joinDate", playerRecord.joinDate);
    }

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        PlayerRecord playerRecord = new PlayerRecord();

        playerRecord.uuid = (String) credentials.getMap().get("uuid");
        playerRecord.name = (String) credentials.getMap().get("name");
        playerRecord.joinDate = (String) credentials.getMap().get("joinDate");

        server.getDatabase().getTable("Player").insertRecord(playerRecord);

        System.out.println(playerRecord.uuid);
    }
}
