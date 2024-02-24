package com.iridium.iridiumteams.support.spawners;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.bgsoftware.wildstacker.api.objects.StackedSpawner;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.entity.EntityType;

public class WildStackerSpawnerSupport<T extends Team, U extends IridiumUser<T>> implements SpawnerSupport {

    private final IridiumTeams<T, U> iridiumTeams;

    public WildStackerSpawnerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public int getExtraSpawners(Team team, EntityType entityType) {

        int stackedSpawners = 0;
        for (StackedSpawner stackedSpawner : WildStackerAPI.getWildStacker().getSystemManager().getStackedSpawners()) {
            if (!iridiumTeams.getTeamManager().isInTeam((T) team, stackedSpawner.getLocation())) continue;
            if (stackedSpawner.getSpawnedType() != entityType) continue;
            stackedSpawners += stackedSpawner.getStackAmount();
        }

        return stackedSpawners;
    }
}