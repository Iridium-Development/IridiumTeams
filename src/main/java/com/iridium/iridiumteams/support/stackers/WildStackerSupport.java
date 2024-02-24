package com.iridium.iridiumteams.support.stackers;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.bgsoftware.wildstacker.api.objects.StackedBarrel;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;

public class WildStackerSupport<T extends Team, U extends IridiumUser<T>> implements StackerSupport {

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
}