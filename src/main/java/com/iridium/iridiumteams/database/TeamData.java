package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TeamData<T extends Team> {
    @DatabaseField(columnName = "team_id", canBeNull = false)
    private T team;

    public TeamData(T team) {
        this.team = team;
    }
}
