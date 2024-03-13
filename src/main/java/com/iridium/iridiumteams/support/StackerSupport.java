package com.iridium.iridiumteams.support;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.database.Team;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import java.util.List;

public interface StackerSupport<T extends Team> {
    int getExtraBlocks(T team, XMaterial material, List<Block> blocks);
    List<Block> getBlocksStacked(Chunk chunk);
    boolean isStackedBlock(Block block);
    int stackerStackAmount(Block block);
    String supportProvider();
}
