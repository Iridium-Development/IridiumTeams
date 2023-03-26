package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class TeamLevelUpEvent<T extends Team, U extends IridiumUser<T>> extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final T team;
    private final int level;

    public TeamLevelUpEvent(T team, int level) {
        this.team = team;
        this.level = level;
    }

    public boolean isFirstTimeAsLevel() {
        return team.getExperience() > team.getMaxExperience();
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
