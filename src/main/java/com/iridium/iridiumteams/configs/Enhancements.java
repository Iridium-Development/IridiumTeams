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

    public Enhancement<FarmingEnhancementData> farmingEnhancement;
    public Enhancement<SpawnerEnhancementData> spawnerEnhancement;
    public Enhancement<ExperienceEnhancementData> experienceEnhancement;
    public Enhancement<FlightEnhancementData> flightEnhancement;
    public Map<String, Enhancement<PotionEnhancementData>> potionEnhancements;

    public Enhancements() {
        this("&c");
    }

    public Enhancements(String color) {
        farmingEnhancement = new Enhancement<>(true, EnhancementType.BOOSTER, new Item(XMaterial.WHEAT, 10, 1, color + "&lFarming Booster", Arrays.asList(
                "&7Increase the speed at which crops grow.",
                "",
                color + "&lInformation:",
                color + "&l * &7Time Remaining: " + color + "%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                color + "&l * &7Current Level: %current_level%",
                color + "&l * &7Booster Cost: $%cost%",
                "",
                color + "&l[!] " + color + "Left Click to Purchase Level %next_level%."
        )), new ImmutableMap.Builder<Integer, FarmingEnhancementData>()
                .put(1, new FarmingEnhancementData(5, 10000, new HashMap<>(), 1.5))
                .put(2, new FarmingEnhancementData(10, 10000, new HashMap<>(), 2))
                .put(3, new FarmingEnhancementData(15, 10000, new HashMap<>(), 2.5))
                .build());
        spawnerEnhancement = new Enhancement<>(true, EnhancementType.BOOSTER, new Item(XMaterial.SPAWNER, 12, 1, color + "&lSpawner Booster", Arrays.asList(
                "&7Increase your spawner speeds.",
                "",
                color + "&lInformation:",
                color + "&l * &7Time Remaining: " + color + "%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                color + "&l * &7Current Level: %current_level%",
                color + "&l * &7Booster Cost: $%cost%",
                "",
                color + "&l[!] " + color + "Left Click to Purchase Level %next_level%."
        )), new ImmutableMap.Builder<Integer, SpawnerEnhancementData>()
                .put(1, new SpawnerEnhancementData(5, 10000, new HashMap<>(), 6))
                .put(2, new SpawnerEnhancementData(10, 10000, new HashMap<>(), 8))
                .put(3, new SpawnerEnhancementData(15, 10000, new HashMap<>(), 10))
                .build());
        experienceEnhancement = new Enhancement<>(true, EnhancementType.BOOSTER, new Item(XMaterial.EXPERIENCE_BOTTLE, 14, 1, color + "&lExperience Booster", Arrays.asList(
                "&7Increase how much experience you get.",
                "",
                color + "&lInformation:",
                color + "&l * &7Time Remaining: " + color + "%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                color + "&l * &7Current Level: %current_level%",
                color + "&l * &7Booster Cost: $%cost%",
                "",
                color + "&l[!] " + color + "Left Click to Purchase Level %next_level%."
        )), new ImmutableMap.Builder<Integer, ExperienceEnhancementData>()
                .put(1, new ExperienceEnhancementData(5, 10000, new HashMap<>(), 1.5))
                .put(2, new ExperienceEnhancementData(10, 10000, new HashMap<>(), 2))
                .put(3, new ExperienceEnhancementData(15, 10000, new HashMap<>(), 2.5))
                .build());
        flightEnhancement = new Enhancement<>(true, EnhancementType.BOOSTER, new Item(XMaterial.FEATHER, 16, 1, color + "&lFlight Booster", Arrays.asList(
                "&7Gain access to fly.",
                "",
                color + "&lInformation:",
                color + "&l * &7Time Remaining: " + color + "%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                color + "&l * &7Current Level: %current_level%",
                color + "&l * &7Booster Cost: $%cost%",
                "",
                color + "&l[!] " + color + "Left Click to Purchase Level %next_level%."
        )), new ImmutableMap.Builder<Integer, FlightEnhancementData>()
                .put(1, new FlightEnhancementData(5, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.MEMBERS_ANYWHERE)))
                .put(2, new FlightEnhancementData(10, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS)))
                .build());
        potionEnhancements = new ImmutableMap.Builder<String, Enhancement<PotionEnhancementData>>()
                .put("haste", new Enhancement<>(
                        true, EnhancementType.UPGRADE, new Item(XMaterial.DIAMOND_PICKAXE, 10, 1, color + "&lHaste Booster", Arrays.asList(
                        "&7Gain a Haste Potion Effect.",
                        "",
                        color + "&lInformation:",
                        color + "&l * &7Time Remaining: " + color + "%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                        color + "&l * &7Current Level: %current_level%",
                        color + "&l * &7Booster Cost: $%cost%",
                        "",
                        color + "&l[!] " + color + "Left Click to Purchase Level %next_level%."
                )), new ImmutableMap.Builder<Integer, PotionEnhancementData>()
                        .put(1, new PotionEnhancementData(5, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 1, XPotion.FAST_DIGGING))
                        .put(2, new PotionEnhancementData(10, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 2, XPotion.FAST_DIGGING))
                        .put(3, new PotionEnhancementData(15, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 3, XPotion.FAST_DIGGING))
                        .build()
                ))
                .put("speed", new Enhancement<>(
                        true, EnhancementType.UPGRADE, new Item(XMaterial.SUGAR, 12, 1, color + "&lSpeed Booster", Arrays.asList(
                        "&7Gain a Speed Potion Effect.",
                        "",
                        color + "&lInformation:",
                        color + "&l * &7Time Remaining: " + color + "%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                        color + "&l * &7Current Level: %current_level%",
                        color + "&l * &7Booster Cost: $%cost%",
                        "",
                        color + "&l[!] " + color + "Left Click to Purchase Level %next_level%."
                )), new ImmutableMap.Builder<Integer, PotionEnhancementData>()
                        .put(1, new PotionEnhancementData(5, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 1, XPotion.SPEED))
                        .put(2, new PotionEnhancementData(10, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 2, XPotion.SPEED))
                        .put(3, new PotionEnhancementData(15, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 3, XPotion.SPEED))
                        .build()
                ))
                .put("jump", new Enhancement<>(
                        true, EnhancementType.UPGRADE, new Item(XMaterial.FEATHER, 14, 1, color + "&lJump Booster", Arrays.asList(
                        "&7Gain a Jump Boost Potion Effect.",
                        "",
                        color + "&lInformation:",
                        color + "&l * &7Time Remaining: " + color + "%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                        color + "&l * &7Current Level: %current_level%",
                        color + "&l * &7Booster Cost: $%cost%",
                        "",
                        color + "&l[!] " + color + "Left Click to Purchase Level %next_level%."
                )), new ImmutableMap.Builder<Integer, PotionEnhancementData>()
                        .put(1, new PotionEnhancementData(5, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 1, XPotion.JUMP))
                        .put(2, new PotionEnhancementData(10, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 2, XPotion.JUMP))
                        .put(3, new PotionEnhancementData(15, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 3, XPotion.JUMP))
                        .build()
                ))
                .put("regeneration", new Enhancement<>(
                        true, EnhancementType.UPGRADE, new Item(XMaterial.GOLDEN_APPLE, 16, 1, color + "&lRegen Booster", Arrays.asList(
                        "&7Gain a Regeneration Potion Effect.",
                        "",
                        color + "&lInformation:",
                        color + "&l * &7Time Remaining: " + color + "%timeremaining_minutes% minutes and %timeremaining_seconds% seconds",
                        color + "&l * &7Current Level: %current_level%",
                        color + "&l * &7Booster Cost: $%cost%",
                        "",
                        color + "&l[!] " + color + "Left Click to Purchase Level %next_level%."
                )), new ImmutableMap.Builder<Integer, PotionEnhancementData>()
                        .put(1, new PotionEnhancementData(5, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 1, XPotion.REGENERATION))
                        .put(2, new PotionEnhancementData(10, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 2, XPotion.REGENERATION))
                        .put(3, new PotionEnhancementData(15, 10000, new HashMap<>(), Collections.singletonList(EnhancementAffectsType.VISITORS), 3, XPotion.REGENERATION))
                        .build()
                ))
                .build();
    }
}
