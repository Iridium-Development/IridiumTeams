package com.iridium.iridiumteams.database;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_bank")
public class TeamWarp extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "location")
    private Location location;

    @DatabaseField(columnName = "name", unique = true)
    private String name;

    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = "description")
    @Setter
    private String description;

    @DatabaseField(columnName = "icon")
    @Setter
    private XMaterial icon;

    @DatabaseField(columnName = "create_time")
    private LocalDateTime createTime;

    public TeamWarp(@NotNull Team team, Location location, String name) {
        super(team);
        this.location = location;
        this.name = name;
        this.icon = XMaterial.STONE;
        this.description = "";
        this.createTime = LocalDateTime.now();
    }

    public TeamWarp(@NotNull Team team, Location location, String name, String password) {
        super(team);
        this.location = location;
        this.name = name;
        this.password = password;
        this.icon = XMaterial.STONE;
        this.description = "";
        this.createTime = LocalDateTime.now();
    }
}
