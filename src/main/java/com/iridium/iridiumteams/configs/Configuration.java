package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.UserRank;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Configuration {
    public String prefix;
    public int minTeamNameLength;
    public int maxTeamNameLength;
    public Map<Integer, UserRank> userRanks;
    public UserRank visitor;
    public UserRank owner;
    public String teamInfoTitle;
    public String teamInfoTitleFiller;
    public List<String> teamInfo;

    public Configuration() {
        this("&c", "Team");
    }

    public Configuration(String color, String team) {
        this.prefix = color + "&l" + team + " &8»";
        this.minTeamNameLength = 3;
        this.maxTeamNameLength = 20;
        this.userRanks = new ImmutableMap.Builder<Integer, UserRank>()
                .put(1, new UserRank("Member", new Item(XMaterial.STONE_AXE, 12, 1, "&b&lMember", Collections.emptyList())))
                .put(2, new UserRank("Moderator", new Item(XMaterial.IRON_AXE, 13, 1, "&c9&lModerator", Collections.emptyList())))
                .put(3, new UserRank("CoOwner", new Item(XMaterial.GOLDEN_AXE, 14, 1, "&2&lCoOwner", Collections.emptyList())))
                .build();
        this.visitor = new UserRank("Visitor", new Item(XMaterial.WOODEN_AXE, 11, 1, "&7&lVisitor", Collections.emptyList()));
        this.owner = new UserRank("Owner", new Item(XMaterial.DIAMOND_AXE, 15, 1, "&c&lOwner", Collections.emptyList()));
        this.teamInfoTitle = "&8[ %" + team + "% &8]";
        this.teamInfoTitleFiller = "&8&m ";
        this.teamInfo = Arrays.asList(
                "&cDescription: &7%team_description%",
                "&cRank: &7#%team_rank%",
                "&cValue: &7%team_value%",
                "&cOnline Members(%team_members_online_count%/%team_members_count%): &7%team_members_online%",
                "&cOffline Members(%team_members_offline_count%/%team_members_count%): &7%team_members_offline%"
        );

    }
}
