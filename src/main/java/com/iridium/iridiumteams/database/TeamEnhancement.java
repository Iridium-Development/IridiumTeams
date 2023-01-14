package com.iridium.iridiumteams.database;

import com.iridium.iridiumteams.enhancements.EnhancementType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_enhancements")
public final class TeamEnhancement extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "enhancement_name", canBeNull = false, uniqueCombo = true)
    private String enhancementName;

    @DatabaseField(columnName = "level", canBeNull = false)
    private int level;

    @DatabaseField(columnName = "start_time", canBeNull = false)
    private LocalDateTime expirationTime;

    public TeamEnhancement(@NotNull Team team, String enhancementName, int level) {
        super(team);
        this.enhancementName = enhancementName;
        this.level = level;
        this.expirationTime = LocalDateTime.now();
    }

    public boolean isActive() {
        return LocalDateTime.now().until(expirationTime, ChronoUnit.SECONDS) > 0;
    }

    public boolean isActive(EnhancementType enhancementType) {
        return enhancementType == EnhancementType.UPGRADE || isActive();
    }


    public long getRemainingTime() {
        return LocalDateTime.now().until(expirationTime, ChronoUnit.SECONDS);
    }

    public void setLevel(int level) {
        this.level = level;
        setChanged(true);
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
        setChanged(true);
    }
}
