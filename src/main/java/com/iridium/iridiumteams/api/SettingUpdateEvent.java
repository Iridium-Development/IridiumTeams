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
public class SettingUpdateEvent<T extends Team, U extends IridiumUser<T>> extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final T team;
    private final U user;
    private final String setting;
    private final String value;
    @Setter
    private boolean cancelled;

    public SettingUpdateEvent(T team, U user, String setting, String value) {
        this.team = team;
        this.user = user;
        this.setting = setting;
        this.value = value;
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