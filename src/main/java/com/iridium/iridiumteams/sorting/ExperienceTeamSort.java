package com.iridium.iridiumteams.sorting;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.Team;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExperienceTeamSort<T extends Team> extends TeamSorting<T> {

    public ExperienceTeamSort(Item item) {
        this.item = item;
        this.enabled = true;
    }

    @Override
    public List<T> getSortedTeams(IridiumTeams<T, ?> iridiumTeams) {
        return iridiumTeams.getTeamManager().getTeams().stream()
                .sorted(Comparator.comparing(T::getExperience).reversed())
                .collect(Collectors.toList());
    }
}
