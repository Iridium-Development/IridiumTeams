package com.iridium.testplugin.managers;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class TeamManager implements com.iridium.iridiumteams.managers.TeamManager<Team, IridiumUser<Team>> {
    @Override
    public Optional<Team> getTeamViaID(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Team> getTeamViaName(String name) {
        return Optional.empty();
    }

    @Override
    public List<IridiumUser<Team>> getTeamMembers(Team team) {
        return null;
    }

    @Override
    public CompletableFuture<Team> createTeam(@NotNull Player owner, @NotNull String name) {
        return null;
    }
}
