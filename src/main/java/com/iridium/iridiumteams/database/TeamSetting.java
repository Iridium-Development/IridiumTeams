package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_settings")
public final class TeamSetting extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "setting", canBeNull = false, uniqueCombo = true)
    private String setting;

    @DatabaseField(columnName = "value", canBeNull = false)
    private String value;

    public TeamSetting(Team team, String setting, String value) {
        super(team);
        this.setting = setting;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
        setChanged(true);
    }
}
