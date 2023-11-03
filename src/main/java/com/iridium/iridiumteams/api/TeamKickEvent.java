package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class TeamKickEvent<T extends Team, U extends IridiumUser<T>, U2 extends IridiumUser<T>> extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final T team;
    private final U user;
    private final U2 kickerUser;

    public TeamKickEvent(final T team, U user, U2 kickerUser) {
        this.team = team;
        this.user = user;
        this.kickerUser = kickerUser;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
