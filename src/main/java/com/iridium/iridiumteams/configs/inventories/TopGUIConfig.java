package com.iridium.iridiumteams.configs.inventories;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TopGUIConfig extends SingleItemGUI{
    public Item filler;

    public TopGUIConfig(int size, String title, Background background, Item item, Item filter) {
        super(size, title, background, item);
        this.filler = filter;
    }
}
