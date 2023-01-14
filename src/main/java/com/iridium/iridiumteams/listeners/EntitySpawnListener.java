
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamSetting;
import lombok.AllArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Optional;

@AllArgsConstructor
public class EntitySpawnListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onEntitySpawn(EntitySpawnEvent event) {
        Optional<T> currentTeam = iridiumTeams.getTeamManager().getTeamViaLocation(event.getEntity().getLocation());
        if (currentTeam.isPresent()) {
            TeamSetting teamSetting = iridiumTeams.getTeamManager().getTeamSetting(currentTeam.get(), SettingType.MOB_SPAWNING.getSettingKey());
            if (teamSetting.getValue().equalsIgnoreCase("Disabled") && event.getEntity() instanceof LivingEntity && event.getEntityType() != EntityType.ARMOR_STAND) {
                event.setCancelled(true);
            }
        }

        event.getEntity().setMetadata("team_spawned", new FixedMetadataValue(iridiumTeams, currentTeam.map(T::getId).orElse(0)));
    }


}
