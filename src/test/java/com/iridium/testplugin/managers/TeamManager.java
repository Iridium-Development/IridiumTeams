package com.iridium.testplugin.managers;

import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TeamManager implements com.iridium.iridiumteams.managers.TeamManager<TestTeam, User> {

    public static List<TestTeam> teams;

    public TeamManager(){
        teams = new ArrayList<>();
    }

    @Override
    public Optional<TestTeam> getTeamViaID(int id) {
        return teams.stream().filter(testTeam -> testTeam.getId() == id).findFirst();
    }

    @Override
    public Optional<TestTeam> getTeamViaName(String name) {
        return teams.stream().filter(testTeam -> testTeam.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public List<User> getTeamMembers(TestTeam team) {
        return UserManager.users.stream().filter(user -> user.getTeamID() == team.getId()).collect(Collectors.toList());
    }

    @Override
    public CompletableFuture<TestTeam> createTeam(@NotNull Player owner, @NotNull String name) {
        TestTeam testTeam = new TestTeam(name);
        User user = TestPlugin.getInstance().getUserManager().getUser(owner);

        user.setTeam(testTeam);
        teams.add(testTeam);
        return CompletableFuture.completedFuture(testTeam);
    }
}
