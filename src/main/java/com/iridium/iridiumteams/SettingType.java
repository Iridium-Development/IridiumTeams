package com.iridium.iridiumteams;

import lombok.Getter;

@Getter
public enum SettingType {
    VALUE_VISIBILITY,
    TEAM_TYPE,
    MOB_SPAWNING,
    LEAF_DECAY,
    ICE_FORM,
    FIRE_SPREAD,
    CROP_TRAMPLE,
    WEATHER,
    TIME,
    ENTITY_GRIEF,
    TNT_DAMAGE,
    TEAM_VISITING;

    private final String settingKey = this.name();
}
