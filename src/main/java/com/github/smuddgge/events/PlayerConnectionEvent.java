package com.github.smuddgge.events;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;

import java.util.UUID;

/**
 * When a player connects to the server
 */
public class PlayerConnectionEvent extends Event {

    private String name;
    private UUID uuid;

    public PlayerConnectionEvent() {}

    /**
     * Used to create a connection event
     * @param name Clients name
     * @param uuid The clients uuid
     */
    public PlayerConnectionEvent(String name, UUID uuid) {
        this.getEventPacket().addCredential("name", name);
        this.getEventPacket().addCredential("uuid", uuid.toString());
    }

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        this.name = (String) credentials.getMap().get("name");
        this.uuid = UUID.fromString((String) credentials.getMap().get("uuid"));
    }

    /**
     * Used to get the clients name from the last update
     * @return Clients name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Used to get the clients uuid
     * @return Clients uuid
     */
    public UUID getUUID() {
        return this.uuid;
    }
}
