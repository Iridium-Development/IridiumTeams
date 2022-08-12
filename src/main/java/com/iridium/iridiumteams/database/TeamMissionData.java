package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_mission_data")
public class TeamMissionData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "mission_id", uniqueCombo = true)
    private int missionID;

    @DatabaseField(columnName = "mission_index", uniqueCombo = true)
    private int missionIndex;

    @Setter
    @DatabaseField(columnName = "progress")
    private int progress;

    public TeamMissionData(TeamMission teamMission, int missionIndex) {
        this.missionID = teamMission.getId();
        this.missionIndex = missionIndex;
    }
}
