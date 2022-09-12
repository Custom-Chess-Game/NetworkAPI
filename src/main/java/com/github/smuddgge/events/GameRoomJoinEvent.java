package com.github.smuddgge.events;

import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.GameRoom;
import com.github.smuddgge.utility.PlayerStatus;

import java.util.UUID;

/**
 * When a player joins the game room
 */
public class GameRoomJoinEvent extends Event {

    public GameRoomJoinEvent() {}

    /**
     * Used to create a game room join event
     * @param uuid UUID of the room that the player is joining
     */
    public GameRoomJoinEvent(UUID uuid) {
        this.getEventPacket().addCredential("uuid", uuid);
    }

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        // Check if there are already 2 players in the game room
        GameRoom gameRoom = server.getGameRoom(UUID.fromString((String) credentials.getMap().get("uuid")));
        if (gameRoom.isFull()) return;

        // Check if they are already in the game room
        if (gameRoom.containsPlayer(serverThreadNetworkManager.getPlayerProfile().uuid)) return;

        gameRoom.player2 = serverThreadNetworkManager.getPlayerProfile().uuid;
        serverThreadNetworkManager.getPlayerProfile().playerStatus = PlayerStatus.IN_GAME;

        if (server.getDebugMode()) Console.print("[server] " + ConsoleColour.WHITE + "Join game room event: " + gameRoom.uuid.toString());
    }
}
