package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.EnhancementType;
import com.iridium.iridiumteams.enhancements.SpawnerEnhancementData;
import lombok.AllArgsConstructor;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

@AllArgsConstructor
public class SpawnerSpawnListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onCreatureSpawn(SpawnerSpawnEvent event) {
        iridiumTeams.getTeamManager().getTeamViaLocation(event.getLocation()).ifPresent(team -> {
            Enhancement<SpawnerEnhancementData> spawnerEnhancement = iridiumTeams.getEnhancements().spawnerEnhancement;
            TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(team, "spawner");
            if (!teamEnhancement.isActive() && spawnerEnhancement.type == EnhancementType.BOOSTER) return;
            CreatureSpawner spawner = event.getSpawner();
            int spawnCount = spawnerEnhancement.levels.get(teamEnhancement.getLevel()).spawnCount;
            spawner.setSpawnCount(spawnCount);
            spawner.update(true);
        });
    }
}
