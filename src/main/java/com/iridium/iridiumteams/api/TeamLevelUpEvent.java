package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TeamLevelUpEvent<T extends Team, U extends IridiumUser<T>> extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final T team;

    public TeamLevelUpEvent(T team) {
        this.team = team;
    }

    public boolean isFirstTimeAsLevel() {
        return team.getExperience() >= team.getMaxExperience();
    }

    public T getTeam() {
        return team;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
