package com.github.smuddgge.requests;

import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.packets.Packet;
import com.github.smuddgge.server.Server;
import com.github.smuddgge.utility.GameRoom;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to request the list of game rooms
 */
public class GameRoomListRequest extends Request {

    @Override
    public Object onRequest(Packet credentials, Server server, ServerThreadNetworkManager serverThreadNetworkManager) {
        Map<String, Object> gameRooms = new HashMap<>();

        for (GameRoom gameRoom : server.getGameRooms()) {
            Map<String, Object> gameRoomData = new HashMap<>();

            gameRoomData.put("name", gameRoom.name);
            gameRoomData.put("isFull", gameRoom.isFull());
            gameRoomData.put("player1", gameRoom.player1);

            gameRooms.put(String.valueOf(gameRoom.uuid), gameRoomData);
        }

        return gameRooms;
    }
}
