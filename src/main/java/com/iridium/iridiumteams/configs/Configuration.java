package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.NumberFormatter;
import com.iridium.iridiumteams.UserRank;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Configuration {
    public String prefix;

    public NumberFormatter numberFormatter;
    public int minTeamNameLength;
    public int maxTeamNameLength;
    public Map<Integer, UserRank> userRanks;
    public UserRank visitor;
    public UserRank owner;
    public String teamInfoTitle;
    public String teamInfoTitleFiller;
    public List<String> teamInfo;

    public Configuration() {
        this("&c", "Team", "IridiumTeams");
    }

    public Configuration(String color, String team, String pluginName) {
        this.prefix = color + "&l" + pluginName + " &8»";
        this.numberFormatter = new NumberFormatter();
        this.minTeamNameLength = 3;
        this.maxTeamNameLength = 20;
        this.userRanks = new ImmutableMap.Builder<Integer, UserRank>()
                .put(1, new UserRank("Member", new Item(XMaterial.STONE_AXE, 12, 1, "&9&lMember", Collections.emptyList())))
                .put(2, new UserRank("Moderator", new Item(XMaterial.IRON_AXE, 13, 1, "&5&lModerator", Collections.emptyList())))
                .put(3, new UserRank("CoOwner", new Item(XMaterial.GOLDEN_AXE, 14, 1, "&2&lCoOwner", Collections.emptyList())))
                .build();
        this.visitor = new UserRank("Visitor", new Item(XMaterial.WOODEN_AXE, 11, 1, "&7&lVisitor", Collections.emptyList()));
        this.owner = new UserRank("Owner", new Item(XMaterial.DIAMOND_AXE, 15, 1, "&c&lOwner", Collections.emptyList()));
        this.teamInfoTitle = "&8[ %" + team.toLowerCase() + "_name% &8]";
        this.teamInfoTitleFiller = "&8&m ";
        this.teamInfo = Arrays.asList(
                color + "Description: &7%" + team.toLowerCase() + "_description%",
                color + "Rank: &7#%" + team.toLowerCase() + "_rank%",
                color + "Value: &7%" + team.toLowerCase() + "_value%",
                color + "Online Members(%" + team.toLowerCase() + "_members_online_count%/%" + team.toLowerCase() + "_members_count%): &7%" + team.toLowerCase() + "_members_online%",
                color + "Offline Members(%" + team.toLowerCase() + "_members_offline_count%/%" + team.toLowerCase() + "_members_count%): &7%" + team.toLowerCase() + "_members_offline%"
        );

    }
}
