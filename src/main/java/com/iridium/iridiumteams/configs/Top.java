package com.iridium.iridiumteams.configs;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.sorting.ExperienceTeamSort;
import com.iridium.iridiumteams.sorting.ValueTeamSort;

import java.util.Collections;

public class Top<T extends Team> {
    public ValueTeamSort<T> valueTeamSort = new ValueTeamSort<>(new Item(XMaterial.DIAMOND, 18, 1, "&9&lSort By Value", Collections.emptyList()));
    public ExperienceTeamSort<T> experienceTeamSort = new ExperienceTeamSort<>(new Item(XMaterial.EXPERIENCE_BOTTLE, 27, 1, "&e&lSort By Experience", Collections.emptyList()));
}
