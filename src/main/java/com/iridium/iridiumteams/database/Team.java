package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@DatabaseTable(tableName = "teams")
public class Team {

    @DatabaseField(columnName = "id", canBeNull = false, generatedId = true)
    private int id;

    @DatabaseField(columnName = "name", canBeNull = false)
    private @NotNull String name;

    @DatabaseField(columnName = "description", canBeNull = false)
    private @NotNull String description;

    @DatabaseField(columnName = "create_time")
    private LocalDateTime createTime;

    @DatabaseField(columnName = "home")
    private Location home;

    @DatabaseField(columnName = "experience")
    private int experience;

    public int getLevel() {
        return (int) Math.floor(Math.cbrt(experience));
    }
}
