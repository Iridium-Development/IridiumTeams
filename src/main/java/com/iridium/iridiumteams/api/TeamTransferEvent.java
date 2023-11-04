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
public class TeamTransferEvent<T extends Team, U extends IridiumUser<T>, U2 extends IridiumUser<T>> extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final T team;
    private final U oldUser;
    private final U2 newUser;
    private boolean cancelled;

    public TeamTransferEvent(final T team, U oldUser, U2 newUser) {
        this.team = team;
        this.oldUser = oldUser;
        this.newUser = newUser;
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
