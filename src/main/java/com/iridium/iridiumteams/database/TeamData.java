package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TeamData {
    @DatabaseField(columnName = "team_id", canBeNull = false)
    private Team team;

    public TeamData(Team team) {
        this.team = team;
    }
}
