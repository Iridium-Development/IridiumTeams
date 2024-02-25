package com.iridium.iridiumteams.support.stackers;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.support.spawners.SpawnerSupport;
import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedBlock;
import dev.rosewood.rosestacker.stack.StackedSpawner;
import org.bukkit.entity.EntityType;

public class RoseStackerSupport<T extends Team, U extends IridiumUser<T>> implements StackerSupport<T>, SpawnerSupport<T> {

    private final IridiumTeams<T, U> iridiumTeams;

    public RoseStackerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public int getExtraBlocks(T team, XMaterial material) {

        int stackedBlocks = 0;

        for (StackedBlock stackedBlock : RoseStackerAPI.getInstance().getStackedBlocks().values()) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, stackedBlock.getLocation())) continue;
            if (material != XMaterial.matchXMaterial(stackedBlock.getBlock().getType())) continue;
            stackedBlocks += (stackedBlock.getStackSize() - 1);
        }

        return stackedBlocks;
    }

    @Override
    public int getExtraSpawners(T team, EntityType entityType) {

        int stackedSpawners = 0;
        for (StackedSpawner stackedSpawner : RoseStackerAPI.getInstance().getStackedSpawners().values()) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, stackedSpawner.getLocation())) continue;
            if (stackedSpawner.getSpawner().getSpawnedType() != entityType) continue;
            stackedSpawners += (stackedSpawner.getStackSize() - 1);
        }

        return stackedSpawners;
    }
}