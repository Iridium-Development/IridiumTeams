package com.iridium.iridiumteams.support;

import com.iridium.iridiumteams.database.Team;
import org.bukkit.entity.EntityType;

public interface SpawnerSupport<T extends Team> {
    int getExtraSpawners(T team, EntityType entityType);
}
