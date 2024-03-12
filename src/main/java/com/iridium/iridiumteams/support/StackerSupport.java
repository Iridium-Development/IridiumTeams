package com.iridium.iridiumteams.support;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.database.Team;

import org.bukkit.block.Block;

import java.util.List;

public interface StackerSupport<T extends Team> {
    int getExtraBlocks(T team, XMaterial material, List<Block> blocks);
    boolean isStackedBlock(Block block);
    String supportProvider();
}
