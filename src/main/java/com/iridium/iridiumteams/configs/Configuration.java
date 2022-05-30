package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.UserRank;

import java.util.Collections;
import java.util.Map;

public class Configuration {
    public String prefix = "&c&lIridiumTeams &8»";
    public String dateTimeFormat = "EEEE, MMMM dd HH:mm:ss";
    public int minTeamNameLength = 3;
    public int maxTeamNameLength = 20;

    public Map<Integer, UserRank> userRanks = new ImmutableMap.Builder<Integer, UserRank>()
            .put(1, new UserRank("Member", new Item(XMaterial.STONE_AXE, 12, 1, "&b&lMember", Collections.emptyList())))
            .put(2, new UserRank("Moderator", new Item(XMaterial.IRON_AXE, 13, 1, "&c9&lModerator", Collections.emptyList())))
            .put(3, new UserRank("CoOwner", new Item(XMaterial.GOLDEN_AXE, 14, 1, "&2&lCoOwner", Collections.emptyList())))
            .put(4, new UserRank("Owner", new Item(XMaterial.DIAMOND_AXE, 15, 1, "&c&lOwner", Collections.emptyList())))
            .build();

    public UserRank visitor = new UserRank("Visitor", new Item(XMaterial.WOODEN_AXE, 11, 1, "&7&lVisitor", Collections.emptyList()));
}
