package com.github.smuddgge.events;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.GameRoom;
import com.github.smuddgge.utility.PlayerProfile;

/**
 * Fired when a player makes a move on the chess board
 */
public class PlayerMoveEvent extends Event {

    public PlayerMoveEvent() {}

    /**
     * Used to create a player move event
     * @param move Move made on the chess board
     */
    public PlayerMoveEvent(String move) {
        this.getEventPacket().addCredential("move", move);
    }

    @Override
    public void update(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        GameRoom gameRoom = server.getGameRoom(serverThreadNetworkManager.getPlayerProfile());

        String move = (String) credentials.getMap().get("move");

        gameRoom.moves.add(move);
        gameRoom.setMoveForPlayer(serverThreadNetworkManager.getPlayerProfile().uuid, move);
    }
}
