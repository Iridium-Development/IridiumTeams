package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.dependencies.xseries.XPotion;
import com.iridium.iridiumteams.enhancements.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Enhancements {

    public Enhancement<FarmingEnhancementData> farmingEnhancement = new Enhancement<>(true, EnhancementType.BOOSTER, new Item(XMaterial.WHEAT, 10, 1, "&b&lFarming Booster", Arrays.asList(
            "&7Increase the speed at which crops grow.",
            "",
            "&c&lInformation:",
            "&c&l * &7Time Remaining: &c%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
            "&c&l * &7Current Level: %current_level%",
            "&c&l * &7Booster Cost: $%cost%",
            "",
            "&c&l[!] &cLeft Click to Purchase Level %next_level%."
    )), new ImmutableMap.Builder<Integer, FarmingEnhancementData>()
            .put(1, new FarmingEnhancementData(5, 10000, new HashMap<>(), 1.5))
            .put(2, new FarmingEnhancementData(10, 10000, new HashMap<>(), 2))
            .put(3, new FarmingEnhancementData(15, 10000, new HashMap<>(), 2.5))
            .build());
    public Enhancement<SpawnerEnhancementData> spawnerEnhancement = new Enhancement<>(true, EnhancementType.BOOSTER, new Item(XMaterial.SPAWNER, 12, 1, "&b&lSpawner Booster", Arrays.asList(
            "&7Increase your spawner speeds.",
            "",
            "&c&lInformation:",
            "&c&l * &7Time Remaining: &c%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
            "&c&l * &7Current Level: %current_level%",
            "&c&l * &7Booster Cost: $%cost%",
            "",
            "&c&l[!] &cLeft Click to Purchase Level %next_level%."
    )), new ImmutableMap.Builder<Integer, SpawnerEnhancementData>()
            .put(1, new SpawnerEnhancementData(5, 10000, new HashMap<>(), 6))
            .put(2, new SpawnerEnhancementData(10, 10000, new HashMap<>(), 8))
            .put(3, new SpawnerEnhancementData(15, 10000, new HashMap<>(), 10))
            .build());
    public Enhancement<ExperienceEnhancementData> experienceEnhancement = new Enhancement<>(true, EnhancementType.BOOSTER, new Item(XMaterial.EXPERIENCE_BOTTLE, 14, 1, "&b&lExperience Booster", Arrays.asList(
            "&7Increase how much experience you get.",
            "",
            "&c&lInformation:",
            "&c&l * &7Time Remaining: &c%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
            "&c&l * &7Current Level: %current_level%",
            "&c&l * &7Booster Cost: $%cost%",
            "",
            "&c&l[!] &cLeft Click to Purchase Level %next_level%."
    )), new ImmutableMap.Builder<Integer, ExperienceEnhancementData>()
            .put(1, new ExperienceEnhancementData(5, 10000, new HashMap<>(), 1.5))
            .put(2, new ExperienceEnhancementData(10, 10000, new HashMap<>(), 2))
            .put(3, new ExperienceEnhancementData(15, 10000, new HashMap<>(), 2.5))
            .build());
    public Enhancement<FlightEnhancementData> flightEnhancement = new Enhancement<>(true, EnhancementType.BOOSTER, new Item(XMaterial.FEATHER, 10, 1, "&c&lFlight Booster", Arrays.asList(
            "&7Gain access to fly.",
            "",
            "&c&lInformation:",
            "&c&l * &7Time Remaining: &c%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
            "&c&l * &7Current Level: %current_level%",
            "&c&l * &7Booster Cost: $%cost%",
            "",
            "&c&l[!] &cLeft Click to Purchase Level %next_level%."
    )), new ImmutableMap.Builder<Integer, FlightEnhancementData>()
            .put(1, new FlightEnhancementData(5, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.MEMBERS_ANYWHERE)))
            .put(2, new FlightEnhancementData(10, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS)))
            .build());
    public Map<String, Enhancement<PotionEnhancementData>> potionEnhancements = new ImmutableMap.Builder<String, Enhancement<PotionEnhancementData>>()
            .put("haste", new Enhancement<>(
                    true, EnhancementType.UPGRADE, new Item(XMaterial.DIAMOND_PICKAXE, 10, 1, "&c&lHaste Booster", Arrays.asList(
                    "&7Gain a Haste Potion Effect.",
                    "",
                    "&c&lInformation:",
                    "&c&l * &7Time Remaining: &c%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                    "&c&l * &7Current Level: %current_level%",
                    "&c&l * &7Booster Cost: $%cost%",
                    "",
                    "&c&l[!] &cLeft Click to Purchase Level %next_level%."
            )), new ImmutableMap.Builder<Integer, PotionEnhancementData>()
                    .put(1, new PotionEnhancementData(5, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 1, XPotion.FAST_DIGGING))
                    .put(2, new PotionEnhancementData(10, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 2, XPotion.FAST_DIGGING))
                    .put(3, new PotionEnhancementData(15, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 3, XPotion.FAST_DIGGING))
                    .build()
            ))
            .put("speed", new Enhancement<>(
                    true, EnhancementType.UPGRADE, new Item(XMaterial.SUGAR, 12, 1, "&c&lSpeed Booster", Arrays.asList(
                    "&7Gain a Speed Potion Effect.",
                    "",
                    "&c&lInformation:",
                    "&c&l * &7Time Remaining: &c%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                    "&c&l * &7Current Level: %current_level%",
                    "&c&l * &7Booster Cost: $%cost%",
                    "",
                    "&c&l[!] &cLeft Click to Purchase Level %next_level%."
            )), new ImmutableMap.Builder<Integer, PotionEnhancementData>()
                    .put(1, new PotionEnhancementData(5, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 1, XPotion.SPEED))
                    .put(2, new PotionEnhancementData(10, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 2, XPotion.SPEED))
                    .put(3, new PotionEnhancementData(15, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 3, XPotion.SPEED))
                    .build()
            ))
            .put("jump", new Enhancement<>(
                    true, EnhancementType.UPGRADE, new Item(XMaterial.DIAMOND_PICKAXE, 14, 1, "&c&lJump Booster", Arrays.asList(
                    "&7Gain a Jump Boost Potion Effect.",
                    "",
                    "&c&lInformation:",
                    "&c&l * &7Time Remaining: &c%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                    "&c&l * &7Current Level: %current_level%",
                    "&c&l * &7Booster Cost: $%cost%",
                    "",
                    "&c&l[!] &cLeft Click to Purchase Level %next_level%."
            )), new ImmutableMap.Builder<Integer, PotionEnhancementData>()
                    .put(1, new PotionEnhancementData(5, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 1, XPotion.JUMP))
                    .put(2, new PotionEnhancementData(10, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 2, XPotion.JUMP))
                    .put(3, new PotionEnhancementData(15, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 3, XPotion.JUMP))
                    .build()
            ))
            .build();
}
