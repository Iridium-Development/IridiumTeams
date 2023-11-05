package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamBlock;
import com.iridium.iridiumteams.database.TeamSpawners;
import lombok.AllArgsConstructor;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Optional;

@AllArgsConstructor
public class BlockBreakListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        U user = iridiumTeams.getUserManager().getUser(player);
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation());
        if (team.isPresent()) {
            if (!iridiumTeams.getTeamManager().getTeamPermission(team.get(), user, PermissionType.BLOCK_BREAK)) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotBreakBlocks
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                event.setCancelled(true);
            }
            if (!iridiumTeams.getTeamManager().getTeamPermission(team.get(), user, PermissionType.SPAWNERS) && event.getBlock().getState() instanceof CreatureSpawner) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotBreakBlocks
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                event.setCancelled(true);
            }
        } else {
            iridiumTeams.getTeamManager().handleBlockBreakOutsideTerritory(event);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorBlockBreak(BlockBreakEvent event) {
        U user = iridiumTeams.getUserManager().getUser(event.getPlayer());
        XMaterial material = XMaterial.matchXMaterial(event.getBlock().getType());
        iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).ifPresent(team -> {
            iridiumTeams.getMissionManager().handleMissionUpdate(team, event.getBlock().getLocation().getWorld(), "MINE", material.name(), 1);
        });
        iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation()).ifPresent(team -> {
            TeamBlock teamBlock = iridiumTeams.getTeamManager().getTeamBlock(team, material);
            teamBlock.setAmount(Math.max(0, teamBlock.getAmount() - 1));

            if (event.getBlock().getState() instanceof CreatureSpawner) {
                CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlock().getState();
                TeamSpawners teamSpawners = iridiumTeams.getTeamManager().getTeamSpawners(team, creatureSpawner.getSpawnedType());
                teamSpawners.setAmount(Math.max(0, teamSpawners.getAmount() - 1));
            }
        });
    }
}
