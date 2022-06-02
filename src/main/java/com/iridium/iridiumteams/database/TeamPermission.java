package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_permissions")
public final class TeamPermission extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false, uniqueCombo = true)
    private int id;

    @DatabaseField(columnName = "permission", canBeNull = false, uniqueCombo = true)
    private @NotNull String permission;

    @DatabaseField(columnName = "rank", canBeNull = false)
    private int rank;

    @DatabaseField(columnName = "allowed", canBeNull = false)
    @Setter
    private boolean allowed;

    public TeamPermission(@NotNull Team team, @NotNull String permission, int rank, boolean allowed) {
        super(team);
        this.permission = permission;
        this.rank = rank;
        this.allowed = allowed;
    }

}
