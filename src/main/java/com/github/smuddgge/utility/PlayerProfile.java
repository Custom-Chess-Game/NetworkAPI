package com.github.smuddgge.utility;

import java.util.UUID;

/**
 * Represents a players profile
 */
public class PlayerProfile {

    public UUID uuid;
    public String name;

    public PlayerStatus playerStatus = PlayerStatus.OFFLINE_MODE;
}
