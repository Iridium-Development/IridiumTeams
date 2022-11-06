package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@DatabaseTable(tableName = "teams")
public abstract class Team extends DatabaseObject{

    @DatabaseField(columnName = "id", canBeNull = false, generatedId = true)
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "description", canBeNull = false)
    private @NotNull String description;

    @DatabaseField(columnName = "create_time")
    private LocalDateTime createTime;

    @DatabaseField(columnName = "home")
    private Location home;

    @DatabaseField(columnName = "experience")
    private int experience;

    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }

    public int getLevel() {
        return (int) Math.floor(Math.cbrt(experience));
    }

    public abstract double getValue();

    public void setId(int id) {
        this.id = id;
        setChanged(true);
    }

    public void setName(String name) {
        this.name = name;
        setChanged(true);
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
        setChanged(true);
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        setChanged(true);
    }

    public void setHome(Location home) {
        this.home = home;
        setChanged(true);
    }
}
