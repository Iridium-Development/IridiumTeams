package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamSpawners;
import lombok.AllArgsConstructor;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.Optional;

@AllArgsConstructor
public class PlayerInteractListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(!(event.getClickedBlock().getState() instanceof CreatureSpawner)) return;
        if(!XMaterial.matchXMaterial(event.getItem().getType()).name().contains("SPAWN_EGG")) return;

        Player player = event.getPlayer();
        U user = iridiumTeams.getUserManager().getUser(player);
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(event.getClickedBlock().getLocation());

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
}