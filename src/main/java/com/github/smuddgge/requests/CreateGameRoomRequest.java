package com.github.smuddgge.requests;

import com.github.smuddgge.connections.Connection;
import com.github.smuddgge.connections.ServerThreadConnection;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.GameRoom;
import com.github.smuddgge.utility.PlayerStatus;

/**
 * Used to create a game room with other players
 * Request returns false if the server was unable to create a room
 * Request returns true if game room was created
 */
public class CreateGameRoomRequest extends Request {

    @Override
    public Object onRequest(Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        // Check if they already have an active game room
        for (GameRoom gameRoom : server.getGameRooms()) {
            if (gameRoom.player1 == serverThreadNetworkManager.getPlayerProfile().uuid) return false;
            if (gameRoom.player2 == serverThreadNetworkManager.getPlayerProfile().uuid) return false;
        }

        // Get available player
        ServerThreadConnection player = null;
        for (ServerThreadConnection connection : server.getConnections()) {
            if (connection.getNetworkManager().getPlayerProfile().playerStatus != PlayerStatus.WAITING) continue;
            player = connection;
        }

        if (player == null) return false;

        // Create a new game room
        GameRoom gameRoom = new GameRoom();
        gameRoom.player1 = serverThreadNetworkManager.getPlayerProfile().uuid;
        gameRoom.player2 = player.getNetworkManager().getPlayerProfile().uuid;

        server.addGameRoom(gameRoom);

        return true;
    }
}
