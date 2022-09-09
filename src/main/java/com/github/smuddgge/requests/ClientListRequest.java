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

            PlayerConnectionEvent playerConnectionEvent = (PlayerConnectionEvent) client.getNetworkManager().getEvent(PlayerConnectionEvent.class);
            clientInfo.put("name", playerConnectionEvent.getName());

            PlayerStatusEvent playerStatusEvent = (PlayerStatusEvent) client.getNetworkManager().getEvent(PlayerStatusEvent.class);
            clientInfo.put("status", playerStatusEvent.getStatus());

            clients.put(playerConnectionEvent.getUUID().toString(), clientInfo);
        }
        return clients;
    }
}
