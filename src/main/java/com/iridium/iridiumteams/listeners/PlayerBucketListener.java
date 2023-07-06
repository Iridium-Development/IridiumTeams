
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import java.util.Optional;

@AllArgsConstructor
public class PlayerBucketListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        onBucketEvent(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBucketFillEvent(PlayerBucketFillEvent event) {
        onBucketEvent(event);
    }

    public void onBucketEvent(PlayerBucketEvent event) {
        Player player = event.getPlayer();
        U user = iridiumTeams.getUserManager().getUser(player);
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation());
        if (team.isPresent()) {
            if (!iridiumTeams.getTeamManager().getTeamPermission(team.get(), user, PermissionType.BUCKET)) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotUseBuckets
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                event.setCancelled(true);
            }
        }

    }

}
