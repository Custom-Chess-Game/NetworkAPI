package com.github.smuddgge.events;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;

import java.util.UUID;

/**
 * When a player connects to the server
 */
public class PlayerConnectionEvent extends Event {

    public PlayerConnectionEvent() {
    }

    /**
     * Used to create a connection event
     *
     * @param name Clients name
     * @param uuid The clients uuid
     */
    public PlayerConnectionEvent(String name, UUID uuid) {
        this.getEventPacket().addCredential("name", name);
        this.getEventPacket().addCredential("uuid", uuid.toString());
    }

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        serverThreadNetworkManager.getPlayerProfile().name = (String) credentials.getMap().get("name");
        serverThreadNetworkManager.getPlayerProfile().uuid = UUID.fromString((String) credentials.getMap().get("uuid"));
    }
}
