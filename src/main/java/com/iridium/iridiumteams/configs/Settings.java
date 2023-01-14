package com.iridium.iridiumteams.configs;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.Setting;

import java.util.Arrays;

public class Settings {

    public Setting teamJoining;
    public Setting teamValue;
    public Setting mobSpawning;
    public Setting leafDecay;
    public Setting iceForm;
    public Setting fireSpread;
    public Setting cropTrample;
    public Setting weather;
    public Setting time;
    public Setting entityGrief;
    public Setting tntDamage;
    public Setting visiting;

    public Settings() {
        this("Team", "&c");
    }

    public Settings(String team, String color) {
        teamJoining = new Setting(new Item(XMaterial.GUNPOWDER, 10, 1, color + team + " Type", Arrays.asList("&7Set your " + team + " joining method.", "", "" + color + "&lValue", "&7%value%")), "JoinType", "Private");
        teamValue = new Setting(new Item(XMaterial.SUNFLOWER, 11, 1, color + team + " Value Visibility", Arrays.asList("&7Set your " + team + " value.", "", "" + color + "&lValue", "&7%value%")), "ValueVisibility", "Public");
        mobSpawning = new Setting(new Item(XMaterial.SPAWNER, 12, 1, color + team + " Mob Spawning", Arrays.asList("&7Control Mob Spawning on your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "MobSpawning", "Enabled");
        leafDecay = new Setting(new Item(XMaterial.JUNGLE_LEAVES, 13, 1, color + team + " Leaf Decay", Arrays.asList("&7Control Leaf Decay on your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "LeafDecay", "Disabled");
        iceForm = new Setting(new Item(XMaterial.ICE, 14, 1, color + team + " Ice Form", Arrays.asList("&7Control Ice Forming on your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "IceForm", "Disabled");
        fireSpread = new Setting(new Item(XMaterial.FLINT_AND_STEEL, 15, 1, color + team + " Fire Spread", Arrays.asList("&7Control Fire Spread on your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "FireSpread", "Disabled");
        cropTrample = new Setting(new Item(XMaterial.WHEAT_SEEDS, 16, 1, color + team + " Crop Trample", Arrays.asList("&7Control Trampling Crops on your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "CropTrample", "Enabled");
        weather = new Setting(new Item(XMaterial.BLAZE_POWDER, 19, 1, color + team + " Weather", Arrays.asList("&7Control Weather on your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "Weather", "Server");
        time = new Setting(new Item(XMaterial.CLOCK, 20, 1, color + team + " Time", Arrays.asList("&7Control Time on your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "Time", "Server");
        entityGrief = new Setting(new Item(XMaterial.ENDER_PEARL, 21, 1, color + team + " Entity Grief", Arrays.asList("&7Control Entity Grief on your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "EntityGrief", "Disabled");
        tntDamage = new Setting(new Item(XMaterial.TNT, 22, 1, color + team + " TnT Damage", Arrays.asList("&7Control TnT Damage on your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "TnTDamage", "Enabled");
        visiting = new Setting(new Item(XMaterial.BEACON, 23, 1, color + team + " Visiting", Arrays.asList("&7Control if people can visit your " + team + ".", "", "" + color + "&lValue", "&7%value%")), "Visiting", "Enabled");
    }

}
