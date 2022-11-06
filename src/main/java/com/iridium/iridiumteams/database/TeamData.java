package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TeamData extends DatabaseObject{
    @DatabaseField(columnName = "team_id", canBeNull = false, uniqueCombo = true)
    private int teamID;

    public TeamData(Team team) {
        this.teamID = team.getId();
    }
}
