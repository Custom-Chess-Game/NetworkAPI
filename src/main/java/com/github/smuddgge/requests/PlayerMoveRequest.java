package com.github.smuddgge.requests;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.GameRoom;

/**
 * Used to get the other players game move
 */
public class PlayerMoveRequest extends Request {

    @Override
    public Object onRequest(Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        GameRoom gameRoom = server.getGameRoom(serverThreadNetworkManager.getPlayerProfile().uuid);

        return gameRoom.getMoveAndReset(gameRoom.getOtherPlayer(serverThreadNetworkManager.getPlayerProfile().uuid));
    }
}
