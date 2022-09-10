package com.github.smuddgge.requests;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.server.Server;

public class PlayerGameRoomRequest extends Request {

    @Override
    public Object onRequest(Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        return server.getGameRoom(serverThreadNetworkManager.getPlayerProfile().uuid);
    }
}
