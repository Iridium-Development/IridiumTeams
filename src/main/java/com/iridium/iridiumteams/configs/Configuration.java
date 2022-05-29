package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Configuration {
    public String prefix = "&c&lIridiumTeams &8»";
    public String dateTimeFormat = "EEEE, MMMM dd HH:mm:ss";

    public Map<Integer, String> userRank = new ImmutableMap.Builder<Integer, String>()
            .put(0, "Member")
            .put(1, "Moderator")
            .put(2, "CoOwner")
            .put(3, "Owner")
            .build();
}
