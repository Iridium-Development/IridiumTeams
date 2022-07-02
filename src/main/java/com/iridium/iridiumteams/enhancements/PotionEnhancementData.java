package com.iridium.iridiumteams.enhancements;

import com.iridium.iridiumcore.dependencies.xseries.XPotion;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class PotionEnhancementData extends EnhancementData {
    public int strength;
    public XPotion potion;
    public List<EnhancementAffectsType> enhancementAffectsType;

    public PotionEnhancementData(int minLevel, int money, Map<String, Double> bankCosts, List<EnhancementAffectsType> enhancementAffectsType, int strength, XPotion potion) {
        super(minLevel, money, bankCosts);
        this.strength = strength;
        this.potion = potion;
        this.enhancementAffectsType = enhancementAffectsType;
    }
}
