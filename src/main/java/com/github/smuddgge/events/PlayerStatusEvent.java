package com.github.smuddgge.events;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.PlayerStatus;

public class PlayerStatusEvent extends Event {

    private PlayerStatus playerStatus = PlayerStatus.WAITING;

    public PlayerStatusEvent() {}

    public PlayerStatusEvent(PlayerStatus playerStatus) {
        this.getEventPacket().addCredential("status", playerStatus.toString());
    }

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        this.playerStatus = PlayerStatus.valueOf((String) credentials.getMap().get("status"));
    }

    public Object getStatus() {
        return this.playerStatus;
    }
}
