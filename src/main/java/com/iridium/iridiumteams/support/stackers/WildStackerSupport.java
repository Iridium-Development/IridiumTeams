package com.iridium.iridiumteams.support.stackers;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.bgsoftware.wildstacker.api.objects.StackedBarrel;
import com.bgsoftware.wildstacker.api.objects.StackedSpawner;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.support.spawners.SpawnerSupport;
import org.bukkit.entity.EntityType;

public class WildStackerSupport<T extends Team, U extends IridiumUser<T>> implements StackerSupport, SpawnerSupport {

    private final IridiumTeams<T, U> iridiumTeams;

    public WildStackerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public int getExtraBlocks(Team team, XMaterial material) {

        int stackedBlocks = 0;
        for (StackedBarrel stackedBarrel : WildStackerAPI.getWildStacker().getSystemManager().getStackedBarrels()) {
            if (!iridiumTeams.getTeamManager().isInTeam((T) team, stackedBarrel.getLocation())) continue;
            if (material != XMaterial.matchXMaterial(stackedBarrel.getType())) continue;

            if (material == XMaterial.matchXMaterial(stackedBarrel.getType())) {
                stackedBlocks += stackedBarrel.getStackAmount();
            }
        }

        return stackedBlocks;
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