package com.iridium.iridiumteams.database;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_warps")
public class TeamWarp extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "location")
    private Location location;

    @DatabaseField(columnName = "name", uniqueCombo = true)
    private String name;

    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "icon")
    private XMaterial icon;

    @DatabaseField(columnName = "user", canBeNull = false)
    private UUID user;
    @DatabaseField(columnName = "create_time")
    private LocalDateTime createTime;

    public TeamWarp(@NotNull Team team, UUID user, Location location, String name) {
        super(team);
        this.location = location;
        this.name = name;
        this.icon = XMaterial.STONE;
        this.description = "";
        this.user = user;
        this.createTime = LocalDateTime.now();
    }

    public TeamWarp(@NotNull Team team, UUID user, Location location, String name, String password) {
        super(team);
        this.location = location;
        this.name = name;
        this.password = password;
        this.icon = XMaterial.STONE;
        this.description = "";
        this.user = user;
        this.createTime = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        setChanged(true);
    }

    public void setIcon(XMaterial icon) {
        this.icon = icon;
        setChanged(true);
    }
}
