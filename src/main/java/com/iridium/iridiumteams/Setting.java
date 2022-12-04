package com.iridium.iridiumteams;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.fasterxml.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Setting {
    private Item item;
    private String displayName;
    private String defaultValue;
    public boolean enabled;
    @JsonIgnore
    private List<String> values;

    public Setting(Item item, String displayName, String defaultValue) {
        this.item = item;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
        this.enabled = true;
        this.values = new ArrayList<>();
    }
}
