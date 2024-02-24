package com.iridium.iridiumteams.support.stackers;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedBlock;

public class RoseStackerSupport<T extends Team, U extends IridiumUser<T>> implements StackerSupport {

    private final IridiumTeams<T, U> iridiumTeams;

    public RoseStackerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public int getExtraBlocks(Team team, XMaterial material) {

        int stackedBlocks = 0;

        for (StackedBlock stackedBlock : RoseStackerAPI.getInstance().getStackedBlocks().values()) {
            if (!iridiumTeams.getTeamManager().isInTeam((T)team, stackedBlock.getLocation())) continue;
            if (material != XMaterial.matchXMaterial(stackedBlock.getBlock().getType())) continue;
            stackedBlocks += (stackedBlock.getStackSize() - 1);
        }

        return stackedBlocks;
    }
}