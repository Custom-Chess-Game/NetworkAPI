package com.github.smuddgge.requests;

import com.github.smuddgge.connections.ServerThreadConnection;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;

import java.util.HashMap;
import java.util.Map;

public class PlayerListRequest extends Request {

    @Override
    public Object onRequest(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        Map<String, Object> clients = new HashMap<>();

        for (ServerThreadConnection client : server.getConnections()) {
            Map<String, Object> clientInfo = new HashMap<>();

            if (client.getNetworkManager().getPlayerProfile().uuid == null) continue;

            clientInfo.put("name", client.getNetworkManager().getPlayerProfile().name);
            clientInfo.put("status", client.getNetworkManager().getPlayerProfile().playerStatus);

            clients.put(client.getNetworkManager().getPlayerProfile().uuid.toString(), clientInfo);
        }

        return clients;
    }
}
