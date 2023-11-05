package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.dependencies.xseries.XSound;
import com.iridium.iridiumcore.utils.NumberFormatter;
import com.iridium.iridiumteams.Reward;
import com.iridium.iridiumteams.UserRank;

import java.util.*;

public class Configuration {
    public String prefix;
    public boolean updateChecks;

    public String dateTimeFormat;
    public NumberFormatter numberFormatter;
    public boolean createRequiresName;
    public boolean preventTntGriefing;
    public boolean patreonMessage;
    public int minTeamNameLength;
    public int maxTeamNameLength;

    public int recalculateInterval;
    public int forceRecalculateInterval;
    public Map<Integer, UserRank> userRanks;
    public UserRank visitor;
    public UserRank owner;
    public String teamInfoTitle;
    public String teamInfoTitleFiller;
    public List<String> teamInfo;
    public List<String> noneChatAlias;
    public List<String> teamChatAlias;
    public Map<Integer, Integer> teamTopSlots;

    public Map<Integer, Integer> teamWarpSlots;


    /**
     * The Rewards the island gets for leveling up
     * The integer represents the reward they will get, if the island level is divisible by this number they will get that reward
     * Higest number takes priority
     * <p>
     * e.g. 1 will give the reward to every level since every number is divisible by 1
     * 5 will give the reward to levels 5 10 15 20 25 ect since they are divisible by 5
     */
    public Map<Integer, Reward> levelRewards;
    public List<String> whitelistedWorlds;

    public Configuration() {
        this("&c", "Team", "IridiumTeams");
    }

    public Configuration(String color, String team, String pluginName) {
        this.prefix = color + "&l" + pluginName + " &8»";
        this.updateChecks = true;
        this.dateTimeFormat = "EEEE, MMMM dd HH:mm:ss";
        this.numberFormatter = new NumberFormatter();
        this.createRequiresName = true;
        this.preventTntGriefing = true;
        this.patreonMessage = true;
        this.minTeamNameLength = 3;
        this.maxTeamNameLength = 20;
        this.recalculateInterval = 5;
        this.forceRecalculateInterval = 1;
        this.userRanks = new ImmutableMap.Builder<Integer, UserRank>()
                .put(1, new UserRank("Member", new Item(XMaterial.STONE_AXE, 12, 1, "&9&lMember", Collections.emptyList())))
                .put(2, new UserRank("Moderator", new Item(XMaterial.IRON_AXE, 13, 1, "&5&lModerator", Collections.emptyList())))
                .put(3, new UserRank("CoOwner", new Item(XMaterial.GOLDEN_AXE, 14, 1, "&2&lCoOwner", Collections.emptyList())))
                .build();
        this.visitor = new UserRank("Visitor", new Item(XMaterial.WOODEN_AXE, 11, 1, "&7&lVisitor", Collections.emptyList()));
        this.owner = new UserRank("Owner", new Item(XMaterial.DIAMOND_AXE, 15, 1, "&c&lOwner", Collections.emptyList()));
        this.teamInfoTitle = "&8[ " + color + "&l%" + team.toLowerCase() + "_name% &8]";
        this.teamInfoTitleFiller = "&8&m ";
        this.teamInfo = Arrays.asList(
                color + "Description: &7%" + team.toLowerCase() + "_description%",
                color + "Level: &7%" + team.toLowerCase() + "_level% (#%" + team.toLowerCase() + "_experience_rank%)",
                color + "Value: &7%" + team.toLowerCase() + "_value% (#%" + team.toLowerCase() + "_value_rank%)",
                color + "Online Members (%" + team.toLowerCase() + "_members_online_count%/%" + team.toLowerCase() + "_members_count%): &7%" + team.toLowerCase() + "_members_online%",
                color + "Offline Members (%" + team.toLowerCase() + "_members_offline_count%/%" + team.toLowerCase() + "_members_count%): &7%" + team.toLowerCase() + "_members_offline%"
        );

        this.noneChatAlias = Arrays.asList("n", "none");
        this.teamChatAlias = Arrays.asList(team.toLowerCase().substring(0, 1), team.toLowerCase());
        this.teamTopSlots = new ImmutableMap.Builder<Integer, Integer>()
                .put(1, 14)
                .put(2, 22)
                .put(3, 23)
                .put(4, 24)
                .put(5, 30)
                .put(6, 31)
                .put(7, 32)
                .put(8, 33)
                .put(9, 34)
                .build();
        this.teamWarpSlots = ImmutableMap.<Integer, Integer>builder()
                .put(1, 9)
                .put(2, 11)
                .put(3, 13)
                .put(4, 15)
                .put(5, 17)
                .build();

        this.levelRewards = ImmutableMap.<Integer, Reward>builder()
                .put(1, new Reward(new Item(XMaterial.EXPERIENCE_BOTTLE, 1, "&b&lLevel %"+team.toLowerCase()+"_level% Reward", Arrays.asList(
                        "&7"+team+" Level %"+team.toLowerCase()+"_level% Rewards:",
                        "&b&l* &b200 Money",
                        "",
                        "&b&l[!] &bLeft click to redeem"
                )), Collections.emptyList(), 0, new HashMap<>(), 200, 0, XSound.ENTITY_PLAYER_LEVELUP))

                .put(5, new Reward(new Item(XMaterial.EXPERIENCE_BOTTLE, 1, "&b&lLevel %"+team.toLowerCase()+"_level% Reward", Arrays.asList(
                        "&7"+team+" Level %"+team.toLowerCase()+"_level% Rewards:",
                        "&b&l* &b2000 Money",
                        "",
                        "&b&l[!] &bLeft click to redeem"
                )), Collections.emptyList(), 0, new HashMap<>(), 2000, 0, XSound.ENTITY_PLAYER_LEVELUP))
                .build();

        this.whitelistedWorlds = Collections.emptyList();
    }
}
