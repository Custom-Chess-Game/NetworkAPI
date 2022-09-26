package com.github.smuddgge.events;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.GameRoom;
import com.github.smuddgge.utility.PlayerStatus;

import java.util.UUID;

/**
 * Used to create a game room
 */
public class GameRoomCreateEvent extends Event {

    public GameRoomCreateEvent() {
    }

    /**
     * Used to create a game room event
     *
     * @param name Name of the game room
     * @param uuid The uuid of the game room
     */
    public GameRoomCreateEvent(String name, UUID uuid) {
        this.getEventPacket().addCredential("name", name);
        this.getEventPacket().addCredential("uuid", uuid);
    }

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        // Check if they already have a game room
        GameRoom gameRoom = server.getGameRoom(serverThreadNetworkManager.getPlayerProfile().uuid);
        if (gameRoom != null) server.removeGameRoom(gameRoom);

        // Create a new game room
        String name = (String) credentials.getMap().get("name");
        UUID uuid = UUID.fromString((String) credentials.getMap().get("uuid"));

        GameRoom newGameRoom = new GameRoom();
        newGameRoom.name = name;
        newGameRoom.uuid = uuid;
        newGameRoom.player1 = serverThreadNetworkManager.getPlayerProfile().uuid;

        serverThreadNetworkManager.getPlayerProfile().playerStatus = PlayerStatus.IN_GAME;

        // Add to active game rooms
        server.addGameRoom(newGameRoom);
    }
}
