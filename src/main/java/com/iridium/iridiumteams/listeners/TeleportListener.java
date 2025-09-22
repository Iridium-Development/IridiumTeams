package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.teleport.TeleportCancelReason;
import com.iridium.iridiumteams.teleport.TeleportManager;
import com.iridium.iridiumteams.teleport.TeleportRequest;
import lombok.AllArgsConstructor;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * Handles events that should cancel delayed teleports
 */
@AllArgsConstructor
public class TeleportListener<T extends Team, U extends IridiumUser<T>> implements Listener {

    private final IridiumTeams<T, U> iridiumTeams;

    /**
     * Cancel teleport if player moves beyond threshold
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getTo() == null) return;

        handleTeleportRequest(event.getPlayer(), (player, request) -> {
            if (hasPlayerMovedBlock(event) &&
                    request.hasPlayerMoved(iridiumTeams.getConfiguration().teleportMovementThreshold)) {
                iridiumTeams.getTeleportManager()
                        .cancelTeleport(player.getUniqueId(), TeleportCancelReason.PLAYER_MOVED);
            }
        });
    }

    /**
     * Cancel teleport if player takes damage
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        UUID playerId = player.getUniqueId();
        TeleportManager<T, U> teleportManager = iridiumTeams.getTeleportManager();

        if (teleportManager.hasActiveTeleport(playerId)) {
            teleportManager.cancelTeleport(playerId, TeleportCancelReason.PLAYER_DAMAGED);
        }
    }

    /**
     * Cancel teleport if player quits
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        TeleportManager<T, U> teleportManager = iridiumTeams.getTeleportManager();
        if (teleportManager.hasActiveTeleport(playerId)) {
            teleportManager.cancelTeleport(playerId, TeleportCancelReason.PLAYER_OFFLINE);
        }
    }

    /**
     * Cancel teleport if player teleports via another method
     * (but allow the intended delayed teleport to go through)
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getTo() == null) return;

        handleTeleportRequest(event.getPlayer(), (player, request) -> {
            if (locationsAreClose(event.getTo(), request.getDestination(), 0.5)) {
                iridiumTeams.getTeleportManager()
                        .cancelTeleport(player.getUniqueId(), TeleportCancelReason.OTHER_TELEPORT);
            }
        });
    }

    /**
     * Shared helper for fetching and processing teleport requests.
     * Honestly, I just found the duplicate code warning from Intellij annoying...
     */
    private void handleTeleportRequest(Player player, BiConsumer<Player, TeleportRequest<T, U>> consumer) {
        UUID playerId = player.getUniqueId();
        TeleportManager<T, U> teleportManager = iridiumTeams.getTeleportManager();

        Optional<TeleportRequest<T, U>> requestOpt = teleportManager.getTeleportRequest(playerId);
        requestOpt.ifPresent(request -> consumer.accept(player, request));
    }

    private boolean hasPlayerMovedBlock(PlayerMoveEvent event) {
        if (event.getTo() == null) return false;

        return event.getFrom().getBlockX() != event.getTo().getBlockX()
                || event.getFrom().getBlockY() != event.getTo().getBlockY()
                || event.getFrom().getBlockZ() != event.getTo().getBlockZ();
    }

    private boolean locationsAreClose(Location loc1, Location loc2, double threshold) {
        if (loc1 == null || loc2 == null) return false;
        if (loc1.getWorld() == null || loc2.getWorld() == null) return false;
        if (!loc1.getWorld().equals(loc2.getWorld())) return false;

        return loc1.distance(loc2) <= threshold;
    }
}
