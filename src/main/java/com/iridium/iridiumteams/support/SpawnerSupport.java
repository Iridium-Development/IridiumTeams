package com.iridium.iridiumteams.support;

import com.iridium.iridiumteams.database.Team;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

import java.util.List;

public interface SpawnerSupport<T extends Team> {
    int getExtraSpawners(T team, EntityType entityType, List<Block> blocks);
    boolean isStackedSpawner(Block block);
    String supportProvider();
}
