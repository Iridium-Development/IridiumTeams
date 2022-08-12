package com.iridium.iridiumteams.missions;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents an Island mission.
 * Serialized in the Configuration files.
 */
@NoArgsConstructor
@Getter
public class Mission {

    List<MissionData> missionData;
    MissionType missionType;

    public Mission(List<MissionData> missionData, MissionType missionType) {
        this.missionData = missionData;
        this.missionType = missionType;
    }

}
