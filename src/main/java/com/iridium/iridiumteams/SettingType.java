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
    WEATHER, //todo
    TIME, //todo
    ENTITY_GRIEF,
    TNT_DAMAGE,
    TEAM_VISITING; //todo
    /*
    /is visit
    Player Move Event -> if event.getFrom is in claim teleport home, otherwise event.set cancelled
    Player Teleport Event -> if event.getFrom is in claim teleport home, otherwise event.set cancelled
     */


    private final String settingKey = this.name();
}
