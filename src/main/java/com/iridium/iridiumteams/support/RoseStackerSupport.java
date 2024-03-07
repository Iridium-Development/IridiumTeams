package com.iridium.iridiumteams.support;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedBlock;
import dev.rosewood.rosestacker.stack.StackedSpawner;
import dev.rosewood.rosestacker.stack.StackingThread;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.security.KeyStore;
import java.util.*;

public class RoseStackerSupport<T extends Team, U extends IridiumUser<T>> implements StackerSupport<T>, SpawnerSupport<T> {

    private final IridiumTeams<T, U> iridiumTeams;

    public RoseStackerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public int getExtraBlocks(T team, XMaterial material) {

        int stackedBlocks = 0;

        for(StackingThread stackingThread : RoseStackerAPI.getInstance().getStackingThreads().values()) {
            for (StackedBlock stackedBlock : stackingThread.getStackedBlocks().values()) {
                if (!iridiumTeams.getTeamManager().isInTeam(team, stackedBlock.getLocation())) continue;
                if (material != XMaterial.matchXMaterial(stackedBlock.getBlock().getType())) continue;
                stackedBlocks += (stackedBlock.getStackSize() - 1);
            }
        }

        return stackedBlocks;
    }

    @Override
    public int getExtraSpawners(T team, EntityType entityType) {

        int stackedSpawners = 0;

        for(StackingThread stackingThread : RoseStackerAPI.getInstance().getStackingThreads().values()) {
            for (StackedSpawner stackedSpawner : stackingThread.getStackedSpawners().values()) {
                if (!iridiumTeams.getTeamManager().isInTeam(team, stackedSpawner.getLocation())) continue;
                if (stackedSpawner.getSpawner().getSpawnedType() != entityType) continue;
                stackedSpawners += (stackedSpawner.getStackSize() - 1);
            }
        }

        return stackedSpawners;
    }
}