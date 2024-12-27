package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

@AllArgsConstructor
public class PlayerMoveListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Location to = event.getTo();
        if (to == null) return; // This is possible apparently?

        Location from = event.getFrom();

        // might help speed things up - if the next location does not change blocks, why do anything?
        if ((from.getBlockX() == to.getBlockX()) && (from.getBlockZ() == to.getBlockZ()) && (from.getBlockY() == to.getBlockY())) return;

        Player player = event.getPlayer();

        Optional<T> fromTeam = iridiumTeams.getTeamManager().getTeamViaPlayerLocation(event.getPlayer(), from);
        Optional<T> toTeam = iridiumTeams.getTeamManager().getTeamViaPlayerLocation(event.getPlayer(), to);

        if (fromTeam.isPresent()) {
            iridiumTeams.getTeamManager().sendTeamTime(player);
            iridiumTeams.getTeamManager().sendTeamWeather(player);
        }

        if (toTeam.isPresent() && !iridiumTeams.getTeamManager().canVisit(player, toTeam.get())) {
            event.setCancelled(true);
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotVisit
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix))
            );
            return;
        }

        // we should only be checking if the player is flying if the flight enhancement is enabled (this is a global config setting)
        // we're not an anti-cheat, we don't care otherwise
        U user = iridiumTeams.getUserManager().getUser(player);
        if (iridiumTeams.getEnhancements().flightEnhancement.enabled && user.isFlying()) {
            if (!user.canFly(iridiumTeams) && player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
                user.setFlying(false);
                player.setFlying(false);
                player.setAllowFlight(false);

                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().flightDisabled
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix))
                );
            }
        }

        if (!toTeam.isPresent()) return;
        if (!toTeam.map(T::getId).orElse(-99999).equals(fromTeam.map(T::getId).orElse(-99999))) {
            iridiumTeams.getTeamManager().sendTeamTitle(player, toTeam.get());
        }
    }
}
