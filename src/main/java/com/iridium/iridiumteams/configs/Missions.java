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
                    )), Collections.emptyList(), 3, new HashMap<>(), 0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Farmer mission Completed!\n" +
                            "&b&l* &7+3 Island Experience\n" +
                            "&b&l* &7+5 Island Crystals\n" +
                            "&b&l* &7+1000 Money\n" +
                            "&7/is rewards to redeem rewards"
            ))

            .put("hunter", new Mission(new Item(XMaterial.BONE, 1, "&b&lHunter",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Kill 10 Zombies: %progress_1%/10",
                            "&b&l* &7Kill 10 Skeletons: %progress_2%/10",
                            "&b&l* &7Kill 10 Creepers: %progress_3%/10",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("KILL:ZOMBIE:10", "KILL:SKELETON:10", "KILL:CREEPER:10"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lHunter Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 3, new HashMap<>(), 0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Hunter mission Completed!\n" +
                            "&b&l* &7+3 Island Experience\n" +
                            "&b&l* &7+5 Island Crystals\n" +
                            "&b&l* &7+1000 Money\n" +
                            "&7/is rewards to redeem rewards"
            ))

            .put("baker", new Mission(new Item(XMaterial.BREAD, 1, "&b&lBaker",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Bake 64 Bread: %progress_1%/64",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.singletonList("CRAFT:BREAD:64"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lBaker Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 3, new HashMap<>(), 0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Baker mission Completed!\n" +
                            "&b&l* &7+3 Island Experience\n" +
                            "&b&l* &7+5 Island Crystals\n" +
                            "&b&l* &7+1000 Money\n" +
                            "&7/is rewards to redeem rewards"
            ))

            .put("miner", new Mission(new Item(XMaterial.GOLD_ORE, 1, "&b&lMiner",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Mine 15 Iron Ores: %progress_1%/15",
                            "&b&l* &7Mine 30 Coal Ores: %progress_2%/30",
                            "&b&l* &7Mine 1 Diamond Ore: %progress_3%/1",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("MINE:IRON_ORE:15", "MINE:COAL_ORE:30", "MINE:DIAMOND_ORE:1"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lMiner Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 3, new HashMap<>(), 0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Miner mission Completed!\n" +
                            "&b&l* &7+3 Island Experience\n" +
                            "&b&l* &7+5 Island Crystals\n" +
                            "&b&l* &7+1000 Money\n" +
                            "&7/is rewards to redeem rewards"
            ))

            .put("fisherman", new Mission(new Item(XMaterial.FISHING_ROD, 1, "&b&lFisherman",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Catch 10 Fish: %progress_1%/10",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.singletonList("FISH:ANY:10"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lFisherman Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 3, new HashMap<>(), 0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Fisherman mission Completed!\n" +
                            "&b&l* &7+3 Island Experience\n" +
                            "&b&l* &7+5 Island Crystals\n" +
                            "&b&l* &7+1000 Money\n" +
                            "&7/is rewards to redeem rewards"
            ))

            .put("blacksmith", new Mission(new Item(XMaterial.IRON_INGOT, 1, "&b&lBlacksmith",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Smelt 30 Iron Ores: %progress_1%/30",
                            "&b&l* &7Smelt 15 Gold Ores: %progress_2%/15",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Arrays.asList("SMELT:" + (XMaterial.supports(17) ? XMaterial.RAW_IRON.name() : XMaterial.IRON_ORE.name()) + ":30", "SMELT:" + (XMaterial.supports(17) ? XMaterial.RAW_GOLD.name() : XMaterial.GOLD_ORE.name()) + ":15"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lBlacksmith Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 3, new HashMap<>(), 0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Blacksmith mission Completed!\n" +
                            "&b&l* &7+3 Island Experience\n" +
                            "&b&l* &7+5 Island Crystals\n" +
                            "&b&l* &7+1000 Money\n" +
                            "&7/is rewards to redeem rewards"
            ))

            .put("brewer", new Mission(new Item(XMaterial.POTION, 1, "&b&lPotion Brewer",
                    Arrays.asList(
                            "&7Complete Island Missions to gain rewards",
                            "&7Which can be used to purchase Island Upgrades",
                            "",
                            "&b&lObjectives:",
                            "&b&l* &7Brew 3 Speed II Potions: %progress_1%/3",
                            "&b&l* &7Brew 3 Strength II Potions: %progress_2%/3",
                            "",
                            "&b&lRewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )
            ), Arrays.asList("BREW:SPEED:2:3", "BREW:STRENGTH:2:3"), MissionType.DAILY, new Reward(new Item(XMaterial.DIAMOND, 1, "&b&lPotionBrewer Reward",
                    Arrays.asList(
                            "&b&l Rewards",
                            "&b&l* &75 Island Crystals",
                            "&b&l* &7$1000"
                    )), Collections.emptyList(), 3, new HashMap<>(), 0, XSound.ENTITY_PLAYER_LEVELUP),
                    "%prefix% &7Potion Brewer mission Completed!\n" +
                            "&b&l* &7+3 Island Experience\n" +
                            "&b&l* &7+5 Island Crystals\n" +
                            "&b&l* &7+1000 Money\n" +
                            "&7/is rewards to redeem rewards"
            ))
            .build();

    public List<Integer> dailySlots = Arrays.asList(10, 12, 14, 16);
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
