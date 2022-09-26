package com.github.smuddgge.requests;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;

/**
 * Request is used to get information on the game room they are currently in
 */
public class GameRoomRequest extends Request {

    @Override
    public Object onRequest(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        return server.getGameRoom(serverThreadNetworkManager.getPlayerProfile());
    }
}
