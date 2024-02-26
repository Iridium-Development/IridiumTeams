/**package com.iridium.iridiumteams.support;

import com.craftaro.ultimatestacker.api.stack.block.BlockStack;
import com.craftaro.ultimatestacker.api.stack.spawner.SpawnerStack;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.entity.EntityType;
import com.craftaro.ultimatestacker.api.*;

public class UltimateStackerSupport <T extends Team, U extends IridiumUser<T>> implements StackerSupport<T>, SpawnerSupport<T> {

    private final IridiumTeams<T, U> iridiumTeams;

    public UltimateStackerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public int getExtraBlocks(T team, XMaterial material) {

        int stackedBlocks = 0;

        >> the issue is that BlockStack & SpawnerStack extend Data from com.craftaro.core.database.Data
        >> and the API either does not include this class or shades it incorrectly
        >> meaning we can't use it

        for (BlockStack blockStack : UltimateStackerApi.getBlockStackManager().getStacks()) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, blockStack.getLocation())) continue;
            if (material != XMaterial.matchXMaterial(blockStack.getLocation().getBlock().getType())) continue;
            stackedBlocks += (blockStack.getAmount() - 1);
        }

        return stackedBlocks;
    }

    @Override
    public int getExtraSpawners(T team, EntityType entityType) {

        int stackedSpawners = 0;
        for(SpawnerStack spawnerStack : UltimateStackerApi.getSpawnerStackManager().getStacks()) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, spawnerStack.getLocation())) continue;
            if(spawnerStack.calculateSpawnCount(entityType) == 0) continue;
            stackedSpawners += (spawnerStack.getAmount() - 1);
        }

        return stackedSpawners;
    }
}*/
