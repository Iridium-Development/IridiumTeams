package com.iridium.testplugin.managers;

import com.iridium.iridiumteams.CreateCancelledException;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamInvite;
import com.iridium.iridiumteams.database.TeamPermission;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TeamManager extends com.iridium.iridiumteams.managers.TeamManager<TestTeam, User> {

    public static List<TestTeam> teams;
    public static List<TeamPermission> teamPermissions;
    public static List<TeamInvite> teamInvites;
    public static Optional<TestTeam> teamViaLocation;
    public static boolean cancelsCreate;

    public TeamManager() {
        teams = new ArrayList<>();
        teamPermissions = new ArrayList<>();
        teamInvites = new ArrayList<>();
        teamViaLocation = Optional.empty();
        cancelsCreate = false;
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
    public Optional<TestTeam> getTeamViaLocation(Location location) {
        return teamViaLocation;
    }

    @Override
    public Optional<TestTeam> getTeamViaNameOrPlayer(String name) {
        if (name == null || name.equals("")) return Optional.empty();
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(name);
        Optional<TestTeam> team = getTeamViaID(TestPlugin.getInstance().getUserManager().getUser(targetPlayer).getTeam().map(TestTeam::getId).orElse(0));
        if (team.isEmpty()) {
            return getTeamViaName(name);
        }
        return team;
    }

    @Override
    public List<User> getTeamMembers(TestTeam team) {
        return UserManager.users.stream().filter(user -> user.getTeam().map(TestTeam::getId).orElse(0) == team.getId()).collect(Collectors.toList());
    }

    @Override
    public CompletableFuture<TestTeam> createTeam(@NotNull Player owner, @NotNull String name) throws CreateCancelledException {
        if (cancelsCreate) throw new CreateCancelledException();
        TestTeam testTeam = new TestTeam(name);
        User user = TestPlugin.getInstance().getUserManager().getUser(owner);

        user.setTeam(testTeam);
        teams.add(testTeam);
        return CompletableFuture.completedFuture(testTeam);
    }

    @Override
    public void deleteTeam(TestTeam team, User user) {
        teams.remove(team);
    }

    @Override
    public boolean getTeamPermission(Team team, int rank, String permission) {
        if (rank == Rank.OWNER.getId()) return true;
        return teamPermissions.stream()
                .filter(teamPermission -> teamPermission.getTeam().getId() == team.getId() && teamPermission.getPermission().equals(permission))
                .findFirst()
                .map(TeamPermission::isAllowed)
                .orElse(TestPlugin.getInstance().getPermissionList().get(permission).getDefaultRank() <= rank);
    }

    @Override
    public void setTeamPermission(Team team, int rank, String permission, boolean allowed) {
        Optional<TeamPermission> teamPermission = teamPermissions.stream()
                .filter(perm -> perm.getTeam().getId() == team.getId() && perm.getPermission().equals(permission))
                .findFirst();
        if (teamPermission.isPresent()) {
            teamPermission.get().setAllowed(allowed);
        } else {
            teamPermissions.add(new TeamPermission(team, permission, rank, allowed));
        }
    }

    @Override
    public Optional<TeamInvite> getTeamInvite(TestTeam team, User user) {
        return teamInvites.stream()
                .filter(teamInvite -> teamInvite.getTeam().getId() == team.getId())
                .filter(teamInvite -> teamInvite.getUser() == user.getUuid())
                .findFirst();
    }

    @Override
    public List<TeamInvite> getTeamInvites(TestTeam team) {
        return teamInvites.stream()
                .filter(teamInvite -> teamInvite.getTeam().getId() == team.getId())
                .collect(Collectors.toList());
    }

    @Override
    public void createTeamInvite(TestTeam team, User user, User invitee) {
        teamInvites.add(new TeamInvite(team, user.getUuid(), invitee.getUuid()));
    }

    @Override
    public void deleteTeamInvite(TeamInvite teamInvite) {
        teamInvites.remove(teamInvite);
    }
}
