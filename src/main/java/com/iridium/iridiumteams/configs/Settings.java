package com.iridium.iridiumteams.configs;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.Setting;

import java.util.Arrays;

public class Settings {

    public Setting teamJoining;

    public Settings() {
        this("Team", "&c");
    }

    public Settings(String team, String color) {
        teamJoining = new Setting(new Item(XMaterial.COBBLESTONE, 10, 1, color + team + " Type", Arrays.asList("&7Set your " + team + " joining method.", "", "" + color + "&lValue", "&7%value%")), "JoinType", "Private", Arrays.asList("Private", "Public"));
    }

}
