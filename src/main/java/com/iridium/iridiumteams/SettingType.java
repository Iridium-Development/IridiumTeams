package com.iridium.iridiumteams;

import lombok.Getter;

@Getter
public enum SettingType {
    VALUE_VISIBILITY,
    TEAM_JOINING,
    MOB_SPAWNING,
    LEAF_DECAY,
    ICE_FORM,
    FIRE_SPREAD,
    CROP_TRAMPLE,
    WEATHER,
    TIME,
    ENDERMAN_GRIEF;


    private final String settingKey = this.name();
}
