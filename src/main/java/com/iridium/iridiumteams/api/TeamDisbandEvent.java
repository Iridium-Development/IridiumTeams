package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.Team;

public class TeamDisbandEvent extends TeamEvent<Team> {

    /**
     * The constructor of this event
     *
     * @param team The disbanded team.
     */
    public TeamDisbandEvent(Team team) {
        super(team);
    }
}
