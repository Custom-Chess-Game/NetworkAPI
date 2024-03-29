package com.github.smuddgge.events;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.GameRoom;

/**
 * Event used to delete the game room they are in
 */
public class GameRoomDeleteEvent extends Event {

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        // Check if the game room exists, and they are in the game room
        GameRoom gameRoom = server.getGameRoom(serverThreadNetworkManager.getPlayerProfile());
        if (gameRoom == null) return;
        if (!gameRoom.containsPlayer(serverThreadNetworkManager.getPlayerProfile().uuid)) return;

        // Remove the game room
        server.removeGameRoom(gameRoom);
    }
}
