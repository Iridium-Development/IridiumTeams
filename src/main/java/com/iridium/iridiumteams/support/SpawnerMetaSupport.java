package com.iridium.iridiumteams.support;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import mc.rellox.spawnermeta.SpawnerMeta;
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

        List<Location> locations = new ArrayList<>();
        for(U player : iridiumTeams.getTeamManager().getTeamMembers(team)) {
            SpawnerMeta.instance().getAPI().getLocations(player.getPlayer()).load();
            locations.addAll(SpawnerMeta.instance().getAPI().getLocations(player.getPlayer()).all());
        }

        int spawners = 0;
        for(Location location : locations) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, location)) continue;
            if (SpawnerMeta.instance().getAPI().getSpawner((location).getBlock()).getType().entity() != entityType) continue;
            spawners += SpawnerMeta.instance().getAPI().getSpawner((location).getBlock()).getStack() - 1;
        }

        return spawners;
    }
}
