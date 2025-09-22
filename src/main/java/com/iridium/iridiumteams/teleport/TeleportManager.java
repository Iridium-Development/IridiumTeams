package com.iridium.iridiumteams.teleport;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages delayed teleportation for IridiumTeams
 */
public class TeleportManager<T extends Team, U extends IridiumUser<T>> {

    private final IridiumTeams<T, U> iridiumTeams;
    private final Map<UUID, TeleportRequest<T, U>> activeRequests = new ConcurrentHashMap<>();

    public TeleportManager(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    /**
     * Start a delayed teleport for a player
     * 
     * @param player       The player to teleport
     * @param destination  The destination location
     * @param team         The team associated with this teleport
     * @param teleportType The type of teleport (HOME, WARP, etc.)
     * @param teleportName The name of the teleport (for warps)
     * @return true if teleport was started, false if player already has an active
     *         request
     */
    public boolean startTeleport(Player player, Location destination, T team,
            TeleportType teleportType, String teleportName) {
        UUID playerId = player.getUniqueId();

        if (activeRequests.containsKey(playerId)) {
            cancelTeleport(playerId, TeleportCancelReason.NEW_REQUEST);
        }

        if (player.hasPermission(iridiumTeams.getConfiguration().teleportBypassPermission)) {
            return instantTeleport(player, destination);
        }

        int delay = iridiumTeams.getConfiguration().teleportDelay;
        if (delay <= 0) {
            return instantTeleport(player, destination);
        }

        TeleportRequest<T, U> request = new TeleportRequest<>(
                playerId, destination, player.getLocation(),
                teleportType, team, teleportName, delay);

        activeRequests.put(playerId, request);

        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teleportStarted
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)));

        startCountdownTask(request);
        startDelayTask(request);

        return true;
    }

    /**
     * Cancel a player's teleport request
     * 
     * @param playerId The player's UUID
     * @param reason   The reason for cancellation
     */
    public void cancelTeleport(UUID playerId, TeleportCancelReason reason) {
        TeleportRequest<T, U> request = activeRequests.remove(playerId);
        
        if (request == null) return;
        request.cancelTasks();

        Optional<Player> player = request.getPlayer();
        if (player.isPresent() && player.get().isOnline()) {
            String message = getTeleportCancelMessage(reason);
            if (message != null) {
                player.get().sendMessage(StringUtils.color(message
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            }
        }

    }

    /**
     * Get a player's active teleport request
     * 
     * @param playerId The player's UUID
     * @return Optional containing the teleport request, or empty if none exists
     */
    public Optional<TeleportRequest<T, U>> getTeleportRequest(UUID playerId) {
        return Optional.ofNullable(activeRequests.get(playerId));
    }

    /**
     * Check if a player has an active teleport request
     * 
     * @param playerId The player's UUID
     * @return true if player has an active request
     */
    public boolean hasActiveTeleport(UUID playerId) {
        return activeRequests.containsKey(playerId);
    }

    /**
     * Perform an instant teleport without delay
     * 
     * @param player      The player to teleport
     * @param destination The destination location
     * @return true if teleport was successful
     */
    private boolean instantTeleport(Player player, Location destination) {
        player.setFallDistance(0);
        PaperLib.teleportAsync(player, destination);
        player.teleport(destination);
        return true;
    }

    /**
     * Start the countdown task for a teleport request
     * 
     * @param request The teleport request
     */
    private void startCountdownTask(TeleportRequest<T, U> request) {
        BukkitRunnable countdownTask = new BukkitRunnable() {
            @Override
            public void run() {
                Optional<Player> player = request.getPlayer();
                if (!player.isPresent() || !player.get().isOnline()) {
                    cancelTeleport(request.getPlayerId(), TeleportCancelReason.PLAYER_OFFLINE);
                    return;
                }

                if (request.hasPlayerMoved(iridiumTeams.getConfiguration().teleportMovementThreshold)) {
                    cancelTeleport(request.getPlayerId(), TeleportCancelReason.PLAYER_MOVED);
                    return;
                }

                if (request.getRemainingSeconds() > 0) {
                    player.get().sendMessage(StringUtils.color(iridiumTeams.getMessages().teleportDelay
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                            .replace("%delay%", String.valueOf(request.getRemainingSeconds()))));

                    request.setRemainingSeconds(request.getRemainingSeconds() - 1);
                }
            }
        };

        request.setCountdownTask(countdownTask);

        countdownTask.runTaskTimer(iridiumTeams, 0L, 20L);
    }

    /**
     * Start the delay task for a teleport request
     * 
     * @param request The teleport request
     */
    private void startDelayTask(TeleportRequest<T, U> request) {
        BukkitRunnable delayTask = new BukkitRunnable() {
            @Override
            public void run() {
                UUID playerId = request.getPlayerId();
                Optional<Player> optionalPlayer = request.getPlayer();

                if (!optionalPlayer.isPresent() || !optionalPlayer.get().isOnline()) {
                    activeRequests.remove(playerId);
                    return;
                }

                Player player = optionalPlayer.get();

                if (request.hasPlayerMoved(iridiumTeams.getConfiguration().teleportMovementThreshold)) {
                    cancelTeleport(playerId, TeleportCancelReason.PLAYER_MOVED);
                    return;
                }

                if (request.getCountdownTask() != null) {
                    request.getCountdownTask().cancel();
                }
                activeRequests.remove(playerId);

                instantTeleport(player, request.getDestination());
                sendSuccessMessage(player, request);
            }
        };

        request.setDelayTask(delayTask);

        long delayTicks = request.getRemainingSeconds() * 20L;
        delayTask.runTaskLater(iridiumTeams, delayTicks);
    }

    /**
     * Send appropriate success message after teleport
     * 
     * @param player  The player who teleported
     * @param request The completed teleport request
     */
    private void sendSuccessMessage(Player player, TeleportRequest<T, U> request) {
        String message;
        switch (request.getTeleportType()) {
            case HOME:
                message = iridiumTeams.getMessages().teleportingHome;
                break;
            case WARP:
                message = iridiumTeams.getMessages().teleportingWarp
                        .replace("%name%", request.getTeleportName() != null ? request.getTeleportName() : "unknown");
                break;
            default:
                message = iridiumTeams.getMessages().teleportStarted;
                break; // Generic message
        }

        player.sendMessage(StringUtils.color(message
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
    }

    /**
     * Get the appropriate cancellation message for a given reason
     * 
     * @param reason The cancellation reason
     * @return The message string or null if no message should be sent
     */
    private String getTeleportCancelMessage(TeleportCancelReason reason) {
        switch (reason) {
            case PLAYER_MOVED:
                return iridiumTeams.getMessages().teleportCancelledMovement;
            case PLAYER_DAMAGED:
                return iridiumTeams.getMessages().teleportCancelledDamage;
            case PLAYER_OFFLINE:
            case NEW_REQUEST:
                return null;
            default:
                return iridiumTeams.getMessages().teleportCancelled;
        }
    }

    /**
     * Clean up when plugin disables
     */
    public void shutdown() {
        for (TeleportRequest<T, U> request : activeRequests.values()) {
            request.cancelTasks();
        }
        activeRequests.clear();
    }
}