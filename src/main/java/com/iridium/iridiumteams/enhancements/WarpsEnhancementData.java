package com.iridium.iridiumteams.enhancements;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class WarpsEnhancementData extends EnhancementData {
    public int warps;

    public WarpsEnhancementData(int minLevel, int money, Map<String, Double> bankCosts, int warps) {
        super(minLevel, money, bankCosts);
        this.warps = warps;
    }
}
