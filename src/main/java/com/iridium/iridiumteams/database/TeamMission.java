package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_mission")
public class TeamMission extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "mission_name", uniqueCombo = true)
    private String missionName;

    @DatabaseField(columnName = "mission_index")
    private int missionIndex;

    @Setter
    @DatabaseField(columnName = "progress")
    private int progress;
    @DatabaseField(columnName = "expiration")
    private LocalDateTime expiration;

    public TeamMission(@NotNull Team team, String missionName, int missionIndex, LocalDateTime expiration) {
        super(team);
        this.missionName = missionName;
        this.missionIndex = missionIndex;
        this.expiration = expiration;
        this.progress = 0;
    }
}
