package com.github.smuddgge.events;

import com.github.smuddgge.database.data.GameRecord;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;

/**
 * <h2>Represents a game record update</h2>
 * Updates a game record in the servers database
 */
public class DatabaseGameUpdateEvent extends Event {

    public DatabaseGameUpdateEvent() {
    }

    /**
     * Used to create a new game update event
     *
     * @param gameRecord The game record to update in the database
     */
    public DatabaseGameUpdateEvent(GameRecord gameRecord) {
        this.getEventPacket().addCredential("uuid", gameRecord.uuid);
        this.getEventPacket().addCredential("player1", gameRecord.player1);
        this.getEventPacket().addCredential("player2", gameRecord.player2);
        this.getEventPacket().addCredential("winningPlayer", gameRecord.winningPlayer);
        this.getEventPacket().addCredential("winningColour", gameRecord.winningColour);
        this.getEventPacket().addCredential("timeStamp", gameRecord.timeStamp);
        this.getEventPacket().addCredential("log", gameRecord.log);
    }

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        GameRecord gameRecord = new GameRecord();

        gameRecord.uuid = (String) credentials.getMap().get("uuid");
        gameRecord.player1 = (String) credentials.getMap().get("player1");
        gameRecord.player2 = (String) credentials.getMap().get("player2");
        gameRecord.winningPlayer = (String) credentials.getMap().get("winningPlayer");
        gameRecord.winningColour = (String) credentials.getMap().get("winningColour");
        gameRecord.timeStamp = (String) credentials.getMap().get("timeStamp");
        gameRecord.log = (String) credentials.getMap().get("log");

        server.getDatabase().getTable("Game").insertRecord(gameRecord);
    }
}
