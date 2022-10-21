package com.iridium.iridiumteams.enhancements;

import com.iridium.iridiumcore.utils.Placeholder;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class WarpsEnhancementData extends EnhancementData {
    public int warps;

    public WarpsEnhancementData(int minLevel, int money, Map<String, Double> bankCosts, int warps) {
        super(minLevel, money, bankCosts);
        this.warps = warps;
    }

    @Override
    public List<Placeholder> getPlaceholders() {
        return Arrays.asList(
                new Placeholder("warps", String.valueOf(warps))
        );
    }
}
