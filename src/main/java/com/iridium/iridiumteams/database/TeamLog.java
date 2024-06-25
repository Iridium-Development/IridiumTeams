package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_log")
public class TeamLog extends DatabaseObject {
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "team_id", canBeNull = false)
    private int teamID;

    @DatabaseField(columnName = "user")
    private UUID user;

    @DatabaseField(columnName = "action")
    private String action;

    @DatabaseField(columnName = "amount")
    private double amount;

    @DatabaseField(columnName = "location")
    private Location location;

    @DatabaseField(columnName = "date_time")
    private LocalDateTime dateTime;

    @DatabaseField(columnName = "type")
    private String type;

    public TeamLog(@NotNull Team team, UUID user, String action, double amount, Location location, LocalDateTime dateTime, String type) {
        this.teamID = team.getId();
        this.user = user;
        this.action = action;
        this.amount = amount;
        this.location = location;
        this.dateTime = dateTime;
        this.type = type;
    }
}
