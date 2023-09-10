package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

@Getter
public class EnhancementUpdateEvent<U> extends TeamEvent<Team> implements Cancellable {

    private final U user;
    private final int nextLevel;
    private final String enhancement;
    @Setter
    private boolean cancelled;

    public EnhancementUpdateEvent(Team team, U user, int nextLevel, String enhancement) {
        super(team);
        this.user = user;
        this.nextLevel = nextLevel;
        this.enhancement = enhancement;
    }

}