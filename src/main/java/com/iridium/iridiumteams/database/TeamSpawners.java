package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private int amount;

    public TeamSpawners(@NotNull Team team, EntityType entityType, int amount) {
        super(team);
        this.entityType = entityType;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        setChanged(true);
    }
}
