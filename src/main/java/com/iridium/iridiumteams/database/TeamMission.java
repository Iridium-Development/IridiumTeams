package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_mission")
public class TeamMission extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "mission_name", uniqueCombo = true)
    private String missionName;

    @DatabaseField(columnName = "mission_level", uniqueCombo = true)
    @Setter
    private int missionLevel;
    @DatabaseField(columnName = "expiration")
    private LocalDateTime expiration;

    public TeamMission(@NotNull Team team, String missionName, LocalDateTime expiration) {
        super(team);
        this.missionName = missionName;
        this.expiration = expiration;
        this.missionLevel = 0;
    }

    public long getRemainingTime() {
        return LocalDateTime.now().until(expiration, ChronoUnit.SECONDS);
    }
}
