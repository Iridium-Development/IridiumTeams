package com.iridium.iridiumteams.enhancements;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class FarmingEnhancementData extends EnhancementData {
    public double farmingModifier;

    public FarmingEnhancementData(int minLevel, int money, Map<String, Double> bankCosts, double farmingModifier) {
        super(minLevel, money, bankCosts);
        this.farmingModifier = farmingModifier;
    }
}
