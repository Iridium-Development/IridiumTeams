package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.fasterxml.annotation.JsonIgnoreProperties;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.dependencies.xseries.XSound;
import com.iridium.iridiumteams.Mission;
import com.iridium.iridiumteams.MissionType;
import com.iridium.iridiumteams.Reward;

import java.util.*;

/**
 * The mission configuration used by IridiumSkyblock (missions.yml).
 * Is deserialized automatically on plugin startup and reload.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Missions {

    public Map<String, Mission> missions = ImmutableMap.<String, Mission>builder()
            .put("farmer", new Mission(new Item(XMaterial.SUGAR_CANE, 1, "&b&lFarmer",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Grow 10 Sugarcane: %progress_1%/10",
                            "&b&l* &7Grow 10 Wheat: %progress_2%/10",
                            "&b&l* &7Grow 10 Carrots: %progress_3%/10",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("GROW:SUGAR_CANE:10", "GROW:WHEAT:10", "GROW:CARROTS:10"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lFarmer Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 1000, new HashMap<>(),0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Farmer mission Completed!\n"+
                    "&b&l* &7+3 Island Experience\n"+
                    "&b&l* &7+5 Island Crystals\n"+
                    "&b&l* &7+1000 Money\n"+
                    "&7/is rewards to redeem rewards"
            ))
            .put("hunter", new Mission(new Item(XMaterial.SUGAR_CANE, 1, "&b&lFarmer",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Grow 10 Sugarcane: %progress_1%/10",
                            "&b&l* &7Grow 10 Wheat: %progress_2%/10",
                            "&b&l* &7Grow 10 Carrots: %progress_3%/10",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("GROW:SUGAR_CANE:10", "GROW:WHEAT:10", "GROW:CARROTS:10"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lFarmer Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 1000, new HashMap<>(),0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Farmer mission Completed!\n"+
                            "&b&l* &7+3 Island Experience\n"+
                            "&b&l* &7+5 Island Crystals\n"+
                            "&b&l* &7+1000 Money\n"+
                            "&7/is rewards to redeem rewards"
            ))
            .put("baker", new Mission(new Item(XMaterial.SUGAR_CANE, 1, "&b&lFarmer",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Grow 10 Sugarcane: %progress_1%/10",
                            "&b&l* &7Grow 10 Wheat: %progress_2%/10",
                            "&b&l* &7Grow 10 Carrots: %progress_3%/10",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("GROW:SUGAR_CANE:10", "GROW:WHEAT:10", "GROW:CARROTS:10"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lFarmer Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 1000, new HashMap<>(),0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Farmer mission Completed!\n"+
                            "&b&l* &7+3 Island Experience\n"+
                            "&b&l* &7+5 Island Crystals\n"+
                            "&b&l* &7+1000 Money\n"+
                            "&7/is rewards to redeem rewards"
            ))
            .put("miner", new Mission(new Item(XMaterial.SUGAR_CANE, 1, "&b&lFarmer",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Grow 10 Sugarcane: %progress_1%/10",
                            "&b&l* &7Grow 10 Wheat: %progress_2%/10",
                            "&b&l* &7Grow 10 Carrots: %progress_3%/10",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("GROW:SUGAR_CANE:10", "GROW:WHEAT:10", "GROW:CARROTS:10"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lFarmer Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 1000, new HashMap<>(),0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Farmer mission Completed!\n"+
                            "&b&l* &7+3 Island Experience\n"+
                            "&b&l* &7+5 Island Crystals\n"+
                            "&b&l* &7+1000 Money\n"+
                            "&7/is rewards to redeem rewards"
            ))
            .put("fisherman", new Mission(new Item(XMaterial.SUGAR_CANE, 1, "&b&lFarmer",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Grow 10 Sugarcane: %progress_1%/10",
                            "&b&l* &7Grow 10 Wheat: %progress_2%/10",
                            "&b&l* &7Grow 10 Carrots: %progress_3%/10",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("GROW:SUGAR_CANE:10", "GROW:WHEAT:10", "GROW:CARROTS:10"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lFarmer Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 1000, new HashMap<>(),0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Farmer mission Completed!\n"+
                            "&b&l* &7+3 Island Experience\n"+
                            "&b&l* &7+5 Island Crystals\n"+
                            "&b&l* &7+1000 Money\n"+
                            "&7/is rewards to redeem rewards"
            ))
            .put("blacksmith", new Mission(new Item(XMaterial.SUGAR_CANE, 1, "&b&lFarmer",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Grow 10 Sugarcane: %progress_1%/10",
                            "&b&l* &7Grow 10 Wheat: %progress_2%/10",
                            "&b&l* &7Grow 10 Carrots: %progress_3%/10",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("GROW:SUGAR_CANE:10", "GROW:WHEAT:10", "GROW:CARROTS:10"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lFarmer Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 1000, new HashMap<>(),0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Farmer mission Completed!\n"+
                            "&b&l* &7+3 Island Experience\n"+
                            "&b&l* &7+5 Island Crystals\n"+
                            "&b&l* &7+1000 Money\n"+
                            "&7/is rewards to redeem rewards"
            ))
            .put("brewer", new Mission(new Item(XMaterial.SUGAR_CANE, 1, "&b&lFarmer",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Grow 10 Sugarcane: %progress_1%/10",
                            "&b&l* &7Grow 10 Wheat: %progress_2%/10",
                            "&b&l* &7Grow 10 Carrots: %progress_3%/10",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("GROW:SUGAR_CANE:10", "GROW:WHEAT:10", "GROW:CARROTS:10"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lFarmer Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 1000, new HashMap<>(),0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Farmer mission Completed!\n"+
                            "&b&l* &7+3 Island Experience\n"+
                            "&b&l* &7+5 Island Crystals\n"+
                            "&b&l* &7+1000 Money\n"+
                            "&7/is rewards to redeem rewards"
            ))
            .build();

    public List<Integer> dailySlots = Arrays.asList(10, 12, 14, 16);

    public Map<MissionType, Item> missionTypes = ImmutableMap.<MissionType, Item>builder()
            .put(MissionType.DAILY, new Item())
            .put(MissionType.WEEKLY, new Item())
            .put(MissionType.ONCE, new Item())
            .build();
    public Map<String, List<String>> customMaterialLists = ImmutableMap.<String, List<String>>builder()
            .put("LOGS", Arrays.asList(
                    "OAK_LOG",
                    "BIRCH_LOG",
                    "SPRUCE_LOG",
                    "DARK_OAK_LOG",
                    "ACACIA_LOG",
                    "JUNGLE_LOG",
                    "CRIMSON_STEM",
                    "WARPED_STEM"
            ))
            .build();

}
