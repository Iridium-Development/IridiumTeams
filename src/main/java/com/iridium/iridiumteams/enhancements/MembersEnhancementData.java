package com.iridium.iridiumteams.enhancements;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class MembersEnhancementData extends EnhancementData {
    public int members;

    public MembersEnhancementData(int minLevel, int money, Map<String, Double> bankCosts, int members) {
        super(minLevel, money, bankCosts);
        this.members = members;
    }
}
