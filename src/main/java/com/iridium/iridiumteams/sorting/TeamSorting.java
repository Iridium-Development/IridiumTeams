package com.iridium.iridiumteams.sorting;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class TeamSorting<T extends Team> {

    public Item item;
    public boolean enabled;

    public abstract List<T> getSortedTeams(IridiumTeams<T, ?> iridiumTeams);

    public int getRank(T team, IridiumTeams<T, ?> iridiumTeams) {
        List<T> teams = getSortedTeams(iridiumTeams);
        return teams.indexOf(team) + 1;
    }

}
