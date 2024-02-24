package com.iridium.iridiumteams.support.spawners;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.entity.EntityType;

public interface SpawnerSupport<T extends Team> {
    int getExtraSpawners(T team, EntityType entityType);
}
