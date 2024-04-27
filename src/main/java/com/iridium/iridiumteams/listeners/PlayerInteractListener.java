package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamSpawners;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.Optional;

@AllArgsConstructor
public class PlayerInteractListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        U user = iridiumTeams.getUserManager().getUser(player);
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(event.getClickedBlock().getLocation());

        if (event.getClickedBlock().getState() instanceof CreatureSpawner) {
            if (!XMaterial.matchXMaterial(event.getItem().getType()).name().contains("SPAWN_EGG")) return;

            if (team.isPresent()) {
                if (!iridiumTeams.getTeamManager().getTeamPermission(team.get(), user, PermissionType.SPAWNERS)) {
                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotBreakSpawners
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    ));
                    event.setCancelled(true);
                    return;
                }

                CreatureSpawner creatureSpawner = (CreatureSpawner) event.getClickedBlock().getState();
                SpawnEggMeta spawnEggMeta = (SpawnEggMeta) event.getItem().getItemMeta();
                EntityType newEntityType = spawnEggMeta.getSpawnedEntity().getEntityType();

                TeamSpawners teamSpawners = iridiumTeams.getTeamManager().getTeamSpawners(team.get(), creatureSpawner.getSpawnedType());
                teamSpawners.setAmount(Math.max(0, teamSpawners.getAmount() - 1));

                teamSpawners = iridiumTeams.getTeamManager().getTeamSpawners(team.get(), newEntityType);
                teamSpawners.setAmount(teamSpawners.getAmount() + 1);
            }
        }

        if (event.getClickedBlock().getState() instanceof Sign
                || event.getClickedBlock().getType() == Material.ITEM_FRAME
                || event.getClickedBlock().getType() == Material.GLOW_ITEM_FRAME) {

            if (team.isPresent()) {
                if (!iridiumTeams.getTeamManager().getTeamPermission(team.get(), user, PermissionType.BLOCK_BREAK)) {
                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotBreakBlocks
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    ));
                    event.setCancelled(true);
                }
            }
        }
    }
}