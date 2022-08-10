package com.iridium.iridiumteams;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XSound;
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

    private Item item;
    private List<String> missions;
    private MissionType missionType;
    private XSound completeSound;
    private Reward reward;
    private String message;

    /**
     * The default constructor.
     *
     * @param item        The item which represents this mission in the missions GUI
     * @param missions    A list of the conditions for this mission
     * @param missionType The type of this mission
     * @param reward      The reward for this mission
     * @param message     The messages which should be sent after completing this mission
     */
    public Mission(Item item, List<String> missions, MissionType missionType, Reward reward, String message) {
        this.item = item;
        this.missions = missions;
        this.missionType = missionType;
        this.completeSound = XSound.ENTITY_PLAYER_LEVELUP;
        this.reward = reward;
        this.message = message;
    }

}
