package com.iridium.iridiumteams.missions;

import com.cryptomorin.xseries.XSound;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumteams.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private int minLevel;
    private XSound completeSound;
    private Reward reward;
    private String message;
    private List<MissionDependency> missionDependencies;

    public MissionData(Item item, List<String> missions, int minLevel, Reward reward, String message) {
        this.item = item;
        this.missions = missions;
        this.minLevel = minLevel;
        this.completeSound = XSound.ENTITY_PLAYER_LEVELUP;
        this.reward = reward;
        this.message = message;
        this.missionDependencies = new ArrayList<>();
    }

    public MissionData(Item item, List<String> missions, int minLevel, Reward reward, String message, List<MissionDependency> missionDependencies) {
        this.item = item;
        this.missions = missions;
        this.minLevel = minLevel;
        this.completeSound = XSound.ENTITY_PLAYER_LEVELUP;
        this.reward = reward;
        this.message = message;
        this.missionDependencies = missionDependencies;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionDependency {
        private String mission;
        private int level;
    }
}