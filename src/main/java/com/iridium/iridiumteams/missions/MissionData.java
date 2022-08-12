package com.iridium.iridiumteams.missions;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XSound;
import com.iridium.iridiumteams.Reward;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents an Island mission.
 * Serialized in the Configuration files.
 */
@NoArgsConstructor
@Getter
public class MissionData {

    private Item item;
    private List<String> missions;
    private XSound completeSound;
    private Reward reward;
    private String message;

    public MissionData(Item item, List<String> missions, Reward reward, String message) {
        this.item = item;
        this.missions = missions;
        this.completeSound = XSound.ENTITY_PLAYER_LEVELUP;
        this.reward = reward;
        this.message = message;
    }

}
