package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.Team;
import lombok.Getter;

@Getter
public class SettingUpdateEvent<U> extends TeamEvent<Team> {

    private final U user;
    private final String setting;
    private final String value;

    public SettingUpdateEvent(Team team, U user, String setting, String value) {
        super(team);
        this.user = user;
        this.setting = setting;
        this.value = value;
    }

}