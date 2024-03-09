package com.iridium.iridiumteams.support;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedBlock;
import dev.rosewood.rosestacker.stack.StackedSpawner;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

import java.util.*;

public class RoseStackerSupport<T extends Team, U extends IridiumUser<T>> implements StackerSupport<T>, SpawnerSupport<T> {

    private final IridiumTeams<T, U> iridiumTeams;

    public RoseStackerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public String supportProvider() {
        return "RoseStacker";
    }

    @Override
    public boolean isStackedBlock(Block block) {
        return RoseStackerAPI.getInstance().isBlockStacked(block);
    }

    @Override
    public boolean isStackedSpawner(Block block) {
        return RoseStackerAPI.getInstance().isSpawnerStacked(block);
    }

    private StackedBlock getStackedBlock(Block block) {
        return RoseStackerAPI.getInstance().getStackedBlock(block);
    }

    private StackedSpawner getStackedSpawner(Block block) {
        return RoseStackerAPI.getInstance().getStackedSpawner(block);
    }

    private List<StackedBlock> getStackedBlocks(List<Block> blocks) {
        List<StackedBlock> stackedBlocks = new ArrayList<>(Collections.emptyList());
        for(Block block : blocks) {
            stackedBlocks.add(getStackedBlock(block));
        }
        return stackedBlocks;
    }

    private List<StackedSpawner> getStackedSpawners(List<Block> blocks) {
        List<StackedSpawner> stackedSpawners = new ArrayList<>(Collections.emptyList());
        for(Block block : blocks) {
            stackedSpawners.add(getStackedSpawner(block));
        }
        return stackedSpawners;
    }

    @Override
    public int getExtraBlocks(T team, XMaterial material, List<Block> blocks) {

        int stackedBlocks = 0;
        for (StackedBlock stackedBlock : getStackedBlocks(blocks)) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, stackedBlock.getLocation())) continue;
            if (material != XMaterial.matchXMaterial(stackedBlock.getBlock().getType())) continue;
            stackedBlocks += (stackedBlock.getStackSize() - 1);
        }

        return stackedBlocks;
    }

    @Override
    public int getExtraSpawners(T team, EntityType entityType, List<Block> blocks) {

        int stackedSpawners = 0;
        for (StackedSpawner stackedSpawner : getStackedSpawners(blocks)) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, stackedSpawner.getLocation())) continue;
            if (stackedSpawner.getSpawner().getSpawnedType() != entityType) continue;
            stackedSpawners += (stackedSpawner.getStackSize() - 1);
        }

        return stackedSpawners;
    }
}