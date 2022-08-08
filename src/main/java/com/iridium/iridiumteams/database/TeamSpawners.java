package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_spawners")
public class TeamSpawners extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "spawner", uniqueCombo = true)
    private EntityType entityType;

    @DatabaseField(columnName = "amount", canBeNull = false)
    @Setter
    private int amount;

    public TeamSpawners(@NotNull Team team, EntityType entityType, int amount) {
        super(team);
        this.entityType = entityType;
        this.amount = amount;
    }
}
