package com.iridium.iridiumteams.managers;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TeamManager<T extends Team, U extends IridiumUser<T>> {

    public Optional<T> getTeamViaID(int id) {
        return Optional.empty();
    }

    public Optional<T> getTeamViaName(String name) {
        return Optional.empty();
    }

    public List<U> getFactionMembers(T team) {
        return Collections.emptyList();
    }

}
