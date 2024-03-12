package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.SpawnerEnhancementData;
import com.iridium.iridiumteams.support.SpawnerSupport;
import com.iridium.iridiumteams.support.StackerSupport;
import lombok.AllArgsConstructor;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import java.util.HashSet;

@AllArgsConstructor
public class SpawnerSpawnListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onCreatureSpawn(SpawnerSpawnEvent event) {
        iridiumTeams.getTeamManager().getTeamViaLocation(event.getLocation()).ifPresent(team -> {
            Enhancement<SpawnerEnhancementData> spawnerEnhancement = iridiumTeams.getEnhancements().spawnerEnhancement;
            TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(team, "spawner");
            SpawnerEnhancementData data = spawnerEnhancement.levels.get(teamEnhancement.getLevel());
            CreatureSpawner spawner = event.getSpawner();

            if (!teamEnhancement.isActive(spawnerEnhancement.type)) return;
            if (data == null) return;

            HashSet<SpawnerSupport> spawnerSupportList = iridiumTeams.getSupportManager().getSpawnerSupport();

            if (spawnerSupportList.isEmpty()) {
                if (iridiumTeams.getConfiguration().multiplicativeSpawners)
                    spawner.setSpawnCount(spawner.getSpawnCount() * data.spawnCount);
                else spawner.setSpawnCount(data.spawnCount);
            }

            else {
                for(SpawnerSupport spawnerSupport : spawnerSupportList) {
                    if (!spawnerSupport.isStackedSpawner(spawner.getBlock())) continue;
                    if (iridiumTeams.getConfiguration().multiplicativeSpawners) {
                        spawner.setSpawnCount(spawnerSupport.spawnerSpawnAmount(spawner) * data.spawnCount);
                        continue;
                    }
                    spawner.setSpawnCount(spawnerSupport.spawnerStackAmount(spawner) * data.spawnCount);
                }
            }

            spawner.update(true);
        });
    }
}
