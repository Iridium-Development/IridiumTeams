package com.iridium.iridiumteams.support.spawners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedSpawner;
import org.bukkit.entity.EntityType;

public class RoseStackerSpawnerSupport<T extends Team, U extends IridiumUser<T>> implements SpawnerSupport {

    private final IridiumTeams<T, U> iridiumTeams;

    public RoseStackerSpawnerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public int getExtraSpawners(Team team, EntityType entityType) {

        int stackedSpawners = 0;
        for (StackedSpawner stackedSpawner : RoseStackerAPI.getInstance().getStackedSpawners().values()) {
            if (!iridiumTeams.getTeamManager().isInTeam((T) team, stackedSpawner.getLocation())) continue;
            if (stackedSpawner.getSpawner().getSpawnedType() != entityType) continue;
            stackedSpawners += (stackedSpawner.getStackSize() - 1);
        }

        return stackedSpawners;
    }
}