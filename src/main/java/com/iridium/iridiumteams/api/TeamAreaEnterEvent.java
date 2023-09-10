package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamAreaEnterEvent<U> extends TeamEvent<Team> {

    private final U user;

    public TeamAreaEnterEvent(Team team, U user) {
        super(team);
        this.user = user;
    }
}