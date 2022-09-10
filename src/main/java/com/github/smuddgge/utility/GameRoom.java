package com.github.smuddgge.utility;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a game room
 */
public class GameRoom {

    public UUID player1;
    public UUID player2;

    public ArrayList<String> moves = new ArrayList<>();

    public String latestMovePlayer1 = null;
    public String latestMovePlayer2 = null;

    /**
     * Used to check if a player is in this room
     * @param uuid UUID of the player
     * @return True if they are in the room
     */
    public boolean containsPlayer(UUID uuid) {
        if (this.player1 == uuid) return true;
        return this.player2 == uuid;
    }

    /**
     * Set a move for a player
     * @param uuid UUID of the player that moved
     */
    public void setMoveForPlayer(UUID uuid, String move) {
        if (this.player1 == uuid) this.latestMovePlayer1 = move;
        if (this.player2 == uuid) this.latestMovePlayer2 = move;
    }

    /**
     * Used to get a players move and reset it afterwards
     * @param uuid UUID of player to get
     * @return The players move
     */
    public String getMoveAndReset(UUID uuid) {
        String toReturn = null;
        if (this.player1 == uuid) {
           toReturn = this.latestMovePlayer1;
           this.latestMovePlayer1 = null;
        }
        if (this.player2 == uuid) {
            toReturn = this.latestMovePlayer2;
            this.latestMovePlayer2 = null;
        }

        return toReturn;
    }

    /**
     * Used to get the opposite player given a player
     * @param uuid UUID of a player
     * @return The other player in the game room
     */
    public UUID getOtherPlayer(UUID uuid) {
        if (this.player1 == uuid) return this.player2;
        if (this.player2 == uuid) return this.player1;
        return null;
    }
}
