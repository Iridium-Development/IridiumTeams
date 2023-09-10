package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

@Getter
public class TeamPreDisbandEvent<U> extends TeamEvent<Team> implements Cancellable {

    @Setter
    boolean cancelled;
    public U user;

    /**
     * The constructor of this event
     *
     * @param team The team to disband.
     * @param user who disbands.
     */
    public TeamPreDisbandEvent(Team team, U user) {
        super(team);
        this.user = user;
    }
}
