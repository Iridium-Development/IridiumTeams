package com.iridium.iridiumteams.teleport;

/**
 * Enumeration of reasons why a teleport might be cancelled
 */
public enum TeleportCancelReason {
    /**
     * Player moved beyond the allowed threshold
     */
    PLAYER_MOVED,
    
    /**
     * Player took damage
     */
    PLAYER_DAMAGED,
    
    /**
     * Player went offline
     */
    PLAYER_OFFLINE,
    
    /**
     * Player initiated a new teleport request
     */
    NEW_REQUEST,
    
    /**
     * Player teleported via another method
     */
    OTHER_TELEPORT,
    
    /**
     * Others idk
     */
    OTHER
}