package com.iridium.iridiumteams.enhancements;

import com.iridium.iridiumcore.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class Enhancement<T extends EnhancementData> {
    public boolean enabled;
    public EnhancementType type;
    public Item item;
    public Map<Integer, T> levels;

}