package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.Team;
import lombok.Getter;

@Getter
public class TeamAreaLeaveEvent<U> extends TeamEvent<Team> {

    private final U user;

    public TeamAreaLeaveEvent(Team team, U user) {
        super(team);
        this.user = user;
    }
}
