package com.iridium.iridiumteams.support;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.moyskleytech.obsidianstacker.api.StackerAPI;
import com.moyskleytech.obsidianstacker.api.Stack;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ObsidianStackerSupport<T extends Team, U extends IridiumUser<T>> implements StackerSupport<T> {

    private final IridiumTeams<T, U> iridiumTeams;

    public ObsidianStackerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public String supportProvider() {
        return "ObsidianStacker";
    }

    @Override
    public boolean isStackedBlock(Block block) {
        Optional<Stack> stackedBlock = StackerAPI.getInstance().getStack(block);
        return stackedBlock.isPresent();
    }

    private Stack getStackedBlock(Block block) {
        return StackerAPI.getInstance().getStack(block).get();
    }

    private List<Stack> getStackedBlocks(List<Block> blocks) {
        List<Stack> stackedBlocks = new ArrayList<>(Collections.emptyList());
        for(Block block : blocks) {
            stackedBlocks.add(getStackedBlock(block));
        }
        return stackedBlocks;
    }

    @Override
    public int getExtraBlocks(T team, XMaterial material, List<Block> blocks) {
        int stackedBlocks = 0;
        for (Stack stack : getStackedBlocks(blocks)) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, stack.getEntity().getLocation())) continue;
            if (material != XMaterial.matchXMaterial(stack.getEntity().getLocation().getBlock().getType())) continue;
            stackedBlocks += (stack.getCount() - 1);
        }

        return stackedBlocks;
    }
}
