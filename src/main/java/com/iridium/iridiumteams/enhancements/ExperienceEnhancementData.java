package com.iridium.iridiumteams.enhancements;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class ExperienceEnhancementData extends EnhancementData {
    public double experienceModifier;

    public ExperienceEnhancementData(int minLevel, int money, Map<String, Double> bankCosts, double experienceModifier) {
        super(minLevel, money, bankCosts);
        this.experienceModifier = experienceModifier;
    }
}
