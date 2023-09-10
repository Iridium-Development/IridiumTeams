package com.iridium.iridiumteams.api;

import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.event.HandlerList;

@Getter
public class TeamLevelUpEvent extends TeamEvent<Team> {

    private static final HandlerList handlers = new HandlerList();
    private final int level;

    public TeamLevelUpEvent(Team team, int level) {
        super(team);
        this.level = level;
    }

    public boolean isFirstTimeAsLevel() {
        return getTeam().getExperience() > getTeam().getMaxExperience();
    }

}
