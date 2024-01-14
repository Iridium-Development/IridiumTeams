
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamSetting;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class EntityInteractListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;
    private final List<XMaterial> redstoneTriggers = Arrays.asList(XMaterial.LEVER, XMaterial.STRING, XMaterial.TRIPWIRE, XMaterial.TRIPWIRE_HOOK, XMaterial.SCULK_SENSOR, XMaterial.CALIBRATED_SCULK_SENSOR);

    @EventHandler(ignoreCancelled = true)
    public void onEntityInteract(EntityInteractEvent event) {
        iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation()).ifPresent(team -> {
            TeamSetting teamSetting = iridiumTeams.getTeamManager().getTeamSetting(team, SettingType.CROP_TRAMPLE.getSettingKey());
            if (teamSetting.getValue().equalsIgnoreCase("Disabled") && (XMaterial.matchXMaterial(event.getBlock().getType()) == XMaterial.FARMLAND)) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Player player = event.getPlayer();
        U user = iridiumTeams.getUserManager().getUser(player);

        iridiumTeams.getTeamManager().getTeamViaLocation(event.getClickedBlock().getLocation()).ifPresent(team -> {
            if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.OPEN_CONTAINERS.getPermissionKey()) && event.getClickedBlock().getState() instanceof InventoryHolder) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotOpenContainers
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                event.setCancelled(true);
            }

            if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.DOORS.getPermissionKey()) && isDoor(XMaterial.matchXMaterial(event.getClickedBlock().getType()))) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotOpenDoors
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                event.setCancelled(true);
            }

            if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.REDSTONE.getPermissionKey()) && isRedstoneTrigger(XMaterial.matchXMaterial(event.getClickedBlock().getType()))) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotTriggerRedstone
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                event.setCancelled(true);
            }

            if (event.getAction() == Action.PHYSICAL) {
                TeamSetting cropTrampleTeamSetting = iridiumTeams.getTeamManager().getTeamSetting(team, SettingType.CROP_TRAMPLE.getSettingKey());
                if (cropTrampleTeamSetting.getValue().equalsIgnoreCase("Disabled") && (XMaterial.matchXMaterial(event.getClickedBlock().getType()) == XMaterial.FARMLAND)) {
                    event.setCancelled(true);
                }
            }
        });
    }

    private boolean isRedstoneTrigger(XMaterial xMaterial) {
        return redstoneTriggers.contains(xMaterial) || xMaterial.name().contains("_BUTTON") || xMaterial.name().contains("_PRESSURE_PLATE");
    }

    private boolean isDoor(XMaterial material) {
        return material.name().toLowerCase().contains("_door") || material.name().toLowerCase().contains("fence_gate") || material.name().toLowerCase().contains("trapdoor");
    }

}
