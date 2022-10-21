package com.iridium.iridiumteams.enhancements;

import com.iridium.iridiumcore.utils.Placeholder;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class MembersEnhancementData extends EnhancementData {
    public int members;

    public MembersEnhancementData(int minLevel, int money, Map<String, Double> bankCosts, int members) {
        super(minLevel, money, bankCosts);
        this.members = members;
    }

    @Override
    public List<Placeholder> getPlaceholders() {
        return Arrays.asList(
                new Placeholder("members", String.valueOf(members))
        );
    }
}
