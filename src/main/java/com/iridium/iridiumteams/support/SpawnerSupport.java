package com.iridium.iridiumteams.support;

import com.iridium.iridiumteams.database.Team;

import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import java.util.List;

public interface SpawnerSupport<T extends Team> {
    int getExtraSpawners(T team, EntityType entityType, List<CreatureSpawner> spawners);
    boolean isStackedSpawner(Block block);
    int spawnerStackAmount(CreatureSpawner spawner);
    int spawnerSpawnAmount(CreatureSpawner spawner);
    String supportProvider();
}
