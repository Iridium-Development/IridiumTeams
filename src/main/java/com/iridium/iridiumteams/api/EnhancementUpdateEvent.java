package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class EnhancementUpdateEvent<T extends Team, U extends IridiumUser<T>> extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private T team;
    private U user;
    private int nextLevel;
    private String enhancement;
    private boolean cancelled;

    public EnhancementUpdateEvent(T team, U user, int nextLevel, String enhancement) {
        this.team = team;
        this.user = user;
        this.nextLevel = nextLevel;
        this.enhancement = enhancement;
        this.cancelled = false;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}