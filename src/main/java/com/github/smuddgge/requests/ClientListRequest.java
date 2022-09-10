package com.github.smuddgge.requests;

import com.github.smuddgge.connections.ServerThreadConnection;
import com.github.smuddgge.events.PlayerConnectionEvent;
import com.github.smuddgge.events.PlayerStatusEvent;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.server.Server;

import java.util.HashMap;
import java.util.Map;

public class ClientListRequest extends Request {

    @Override
    public Object onRequest(Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        Map<String, Object> clients = new HashMap<>();
        for (ServerThreadConnection client : server.getConnections()) {
            Map<String, Object> clientInfo = new HashMap<>();

            clientInfo.put("name", client.getNetworkManager().getPlayerProfile().name);
            clientInfo.put("status", client.getNetworkManager().getPlayerProfile().playerStatus);

            clients.put(serverThreadNetworkManager.getPlayerProfile().uuid.toString(), clientInfo);
        }
        return clients;
    }
}
