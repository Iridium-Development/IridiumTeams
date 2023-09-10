package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.api.TeamAreaEnterEvent;
import com.iridium.iridiumteams.api.TeamAreaLeaveEvent;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
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
        Player player = event.getPlayer();
        Location to = event.getTo();
        Location from = event.getFrom();
        if (to == null) return; // This is possible apparently?
        if (to.getBlockX() != from.getBlockX() || to.getBlockZ() != from.getBlockZ()) {
            U user = iridiumTeams.getUserManager().getUser(player);
            Optional<T> toTeam = iridiumTeams.getTeamManager().getTeamViaLocation(to);
            Optional<T> fromTeam = iridiumTeams.getTeamManager().getTeamViaPlayerLocation(event.getPlayer());
            //Checks toTeam is present and is in same island
            if (toTeam.map(t1 -> fromTeam.map(t2 -> !t2.equals(t1)).orElse(true)).orElse(false)) {
                if (!iridiumTeams.getTeamManager().canVisit(player, toTeam.get())) {
                    event.setCancelled(true);
                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotVisit
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix))
                    );
                    return;
                }
                iridiumTeams.getTeamManager().sendTeamTitle(player, toTeam.get());
                iridiumTeams.getTeamManager().sendTeamTime(player);
                iridiumTeams.getTeamManager().sendTeamWeather(player);
                Bukkit.getPluginManager().callEvent(new TeamAreaEnterEvent<>(toTeam.get(), user));
            }
            //Checks fromTeam is present and the player leaves
            if (fromTeam.map(t1 -> toTeam.map(t2 -> !t2.equals(t1)).orElse(true)).orElse(false)) {
                Bukkit.getPluginManager().callEvent(new TeamAreaLeaveEvent<>(fromTeam.get(), user));
            }
            if (user.isFlying() && !user.canFly(iridiumTeams)) {
                user.setFlying(false);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().flightDisabled
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix))
                );
            }
        }
    }

}
