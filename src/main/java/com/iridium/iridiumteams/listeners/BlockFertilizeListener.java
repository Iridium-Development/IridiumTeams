
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;

import java.util.Optional;

@AllArgsConstructor
public class BlockFertilizeListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockFertilize(BlockFertilizeEvent event) {
        Player player = event.getPlayer();

        Optional<T> currentTeam = iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation());
        int currentTeamId = currentTeam.map(T::getId).orElse(0);

        if (player != null && currentTeam.isPresent()) {
            U user = iridiumTeams.getUserManager().getUser(player);
            if (!iridiumTeams.getTeamManager().getTeamPermission(currentTeam.get(), user, PermissionType.BLOCK_PLACE)) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotBreakBlocks
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                event.setCancelled(true);
                return;
            }
        }

        event.getBlocks().removeIf(blockState -> {
            Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(blockState.getLocation());
            return team.map(T::getId).orElse(currentTeamId) != currentTeamId;
        });
    }

}
