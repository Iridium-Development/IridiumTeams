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
@DatabaseTable(tableName = "team_enhancements")
public final class TeamEnhancement extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false, uniqueCombo = true)
    private int id;

    @DatabaseField(columnName = "enhancement_name", canBeNull = false, uniqueCombo = true)
    private String enhancementName;

    @DatabaseField(columnName = "level", canBeNull = false)
    @Setter
    private int level;

    @DatabaseField(columnName = "start_time", canBeNull = false)
    @Setter
    private LocalDateTime startTime;

    public TeamEnhancement(@NotNull Team team, String enhancementName, int level) {
        super(team);
        this.enhancementName = enhancementName;
        this.level = level;
        this.startTime = LocalDateTime.MIN;
    }

    public boolean isActive() {
        return LocalDateTime.now().until(startTime, ChronoUnit.SECONDS) > 0;
    }


    public long getRemainingTime() {
        return LocalDateTime.now().until(startTime, ChronoUnit.SECONDS);
    }

}
