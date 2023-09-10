package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class TeamEvent<T extends Team> extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final T team;

    public TeamEvent(T team) {
        this.team = team;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
