package com.github.smuddgge.events;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;

public class PlayerDisconnectEvent extends Event {

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        serverThreadNetworkManager.getServerThread().stop();
        server.getConnections().remove(serverThreadNetworkManager.getServerThread());
    }
}
