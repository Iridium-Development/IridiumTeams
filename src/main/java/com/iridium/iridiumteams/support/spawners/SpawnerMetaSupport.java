package com.iridium.iridiumteams.support.spawners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import mc.rellox.spawnermeta.SpawnerMeta;
import mc.rellox.spawnermeta.api.configuration.ILocations;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class SpawnerMetaSupport<T extends Team, U extends IridiumUser<T>> implements SpawnerSupport<T> {

    private final IridiumTeams<T, U> iridiumTeams;

    public SpawnerMetaSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }


    @Override
    public int getExtraSpawners(T team, EntityType entityType) {

        List<ILocations> locations = new ArrayList<>();
        for(U player : iridiumTeams.getTeamManager().getTeamMembers(team)) {
            locations.add(SpawnerMeta.instance().getAPI().getLocations(player.getPlayer()));
        }

        int spawners = 0;
        for(ILocations location : locations) {
            location.load();
            if (!iridiumTeams.getTeamManager().isInTeam(team, (Location) location)) continue;
            if (SpawnerMeta.instance().getAPI().getSpawner(((Location) location).getBlock()).getType().entity() != entityType) continue;
            spawners += SpawnerMeta.instance().getAPI().getSpawner(((Location) location).getBlock()).getStack() - 1;
        }

        return spawners;
    }
}
