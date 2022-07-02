package com.iridium.iridiumteams.enhancements;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class SpawnerEnhancementData extends EnhancementData {
    public int spawnCount;

    public SpawnerEnhancementData(int minLevel, int money, Map<String, Double> bankCosts, int spawnCount) {
        super(minLevel, money, bankCosts);
        this.spawnCount = spawnCount;
    }
}
