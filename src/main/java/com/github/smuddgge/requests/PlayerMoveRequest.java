package com.github.smuddgge.requests;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.GameRoom;

/**
 * Used to get the other players game move
 */
public class PlayerMoveRequest extends Request {

    @Override
    public Object onRequest(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        GameRoom gameRoom = server.getGameRoom(serverThreadNetworkManager.getPlayerProfile());

        return gameRoom.getMoveAndReset(gameRoom.getOtherPlayer(serverThreadNetworkManager.getPlayerProfile().uuid));
    }
}
