package com.iridium.iridiumteams.support;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.database.Team;

public interface StackerSupport<T extends Team> {
    int getExtraBlocks(T team, XMaterial material);
}
