package com.iridium.iridiumteams.teleport;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a delayed teleport request for a player
 */
@AllArgsConstructor
@Getter
@Setter
public class TeleportRequest<T extends Team, U extends IridiumUser<T>> {

    private final UUID playerId;
    private final Location destination;
    private final Location originalLocation;
    private final LocalDateTime requestTime;
    private final TeleportType teleportType;
    private final T team;
    private final String teleportName; // Warp name if warp teleport
    private BukkitRunnable delayTask;
    private BukkitRunnable countdownTask;
    private int remainingSeconds;

    public TeleportRequest(UUID playerId, Location destination, Location originalLocation,
            TeleportType teleportType, T team, String teleportName, int delaySeconds) {
        this.playerId = playerId;
        this.destination = destination;
        this.originalLocation = originalLocation;
        this.requestTime = LocalDateTime.now();
        this.teleportType = teleportType;
        this.team = team;
        this.teleportName = teleportName;
        this.remainingSeconds = delaySeconds;
        this.delayTask = null;
        this.countdownTask = null;
    }

    /**
     * Get the player associated with this teleport request
     * 
     * @return Player object or null if player is offline
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }

    /**
     * Check if the player has moved significantly from their original location
     * 
     * @param threshold Movement threshold in blocks
     * @return true if player has moved beyond threshold
     */
    public boolean hasPlayerMoved(double threshold) {
        Player player = getPlayer();
        if (player == null)
            return true;

        Location currentLocation = player.getLocation();

        // Check if they're in the same world
        if (!currentLocation.getWorld().equals(originalLocation.getWorld())) {
            return true;
        }

        // Calculate distance moved
        double distance = currentLocation.distance(originalLocation);
        return distance > threshold;
    }

    /**
     * Cancel any running tasks associated with this teleport request
     */
    public void cancelTasks() {
        if (delayTask != null && !delayTask.isCancelled()) {
            delayTask.cancel();
        }
        if (countdownTask != null && !countdownTask.isCancelled()) {
            countdownTask.cancel();
        }
    }
}