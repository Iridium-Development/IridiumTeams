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
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Optional;

@AllArgsConstructor
public class BlockPlaceListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (iridiumTeams.getTeamManager().isBankItem(event.getItemInHand())) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        U user = iridiumTeams.getUserManager().getUser(player);
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation());

        if (team.isPresent()) {
            if (!iridiumTeams.getTeamManager().getTeamPermission(team.get(), user, PermissionType.BLOCK_PLACE)) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotPlaceBlocks
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                event.setCancelled(true);
            }
        } else {
            iridiumTeams.getTeamManager().handleBlockPlaceOutsideTerritory(event);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorBlockPlace(BlockPlaceEvent event) {
        U user = iridiumTeams.getUserManager().getUser(event.getPlayer());
        XMaterial material = XMaterial.matchXMaterial(event.getBlock().getType());
        iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).ifPresent(team -> {
            iridiumTeams.getMissionManager().handleMissionUpdate(team, event.getBlock().getLocation().getWorld(), "PLACE", material.name(), 1);
        });
        iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation()).ifPresent(team -> {
            TeamBlock teamBlock = iridiumTeams.getTeamManager().getTeamBlock(team, material);
            teamBlock.setAmount(teamBlock.getAmount() + 1);

            if (event.getBlock().getState() instanceof CreatureSpawner) {
                CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlock().getState();
                TeamSpawners teamSpawners = iridiumTeams.getTeamManager().getTeamSpawners(team, creatureSpawner.getSpawnedType());
                teamSpawners.setAmount(teamSpawners.getAmount() + 1);
            }
        });
    }
}
