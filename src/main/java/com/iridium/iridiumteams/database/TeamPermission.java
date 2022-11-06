package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_permissions")
public final class TeamPermission extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "permission", canBeNull = false, uniqueCombo = true)
    private String permission;

    @DatabaseField(columnName = "rank", canBeNull = false)
    private int rank;

    @DatabaseField(columnName = "allowed", canBeNull = false)
    private boolean allowed;

    public TeamPermission(Team team, String permission, int rank, boolean allowed) {
        super(team);
        this.permission = permission;
        this.rank = rank;
        this.allowed = allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
        setChanged(true);
    }
}
