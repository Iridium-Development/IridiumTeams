package com.iridium.iridiumteams.enhancements;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class FlightEnhancementData extends EnhancementData {

    public List<EnhancementAffectsType> enhancementAffectsType;

    public FlightEnhancementData(int minLevel, int money, Map<String, Double> bankCosts, List<EnhancementAffectsType> enhancementAffectsType) {
        super(minLevel, money, bankCosts);
        this.enhancementAffectsType = enhancementAffectsType;
    }
}
