package com.iridium.testplugin.managers;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.CreateCancelledException;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.*;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TeamManager extends com.iridium.iridiumteams.managers.TeamManager<TestTeam, User> {

    public static List<TestTeam> teams;
    public static List<TeamPermission> teamPermissions;
    public static List<TeamInvite> teamInvites;
    public static List<TeamSpawners> teamSpawners;
    public static List<TeamBlock> teamBlocks;
    public static List<TeamWarp> teamWarps;
    public static List<TeamMission> teamMissions;
    public static Optional<TestTeam> teamViaLocation;
    public static Map<String, TeamEnhancement> teamEnhancements;
    public static Map<String, TeamBank> teamBank;
    public static boolean cancelsCreate;

    public TeamManager() {
        super(TestPlugin.getInstance());
        teams = new ArrayList<>();
        teamPermissions = new ArrayList<>();
        teamInvites = new ArrayList<>();
        teamViaLocation = Optional.empty();
        teamBank = new HashMap<>();
        teamEnhancements = new HashMap<>();
        cancelsCreate = false;
        teamBlocks = new ArrayList<>();
        teamSpawners = new ArrayList<>();
        teamWarps = new ArrayList<>();
        teamMissions = new ArrayList<>();
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
        Optional<TestTeam> team = getTeamViaID(TestPlugin.getInstance().getUserManager().getUser(targetPlayer).getTeamID());
        if (team.isEmpty()) {
            return getTeamViaName(name);
        }
        return team;
    }

    @Override
    public List<TestTeam> getTeams() {
        return teams;
    }

    @Override
    public List<User> getTeamMembers(TestTeam team) {
        return UserManager.users.stream().filter(user -> user.getTeamID() == team.getId()).collect(Collectors.toList());
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
    public boolean getTeamPermission(TestTeam team, int rank, String permission) {
        if (rank == Rank.OWNER.getId()) return true;
        return teamPermissions.stream()
                .filter(teamPermission -> teamPermission.getTeamID() == team.getId() && teamPermission.getPermission().equals(permission))
                .findFirst()
                .map(TeamPermission::isAllowed)
                .orElse(TestPlugin.getInstance().getPermissionList().get(permission).getDefaultRank() <= rank);
    }

    @Override
    public void setTeamPermission(TestTeam team, int rank, String permission, boolean allowed) {
        Optional<TeamPermission> teamPermission = teamPermissions.stream()
                .filter(perm -> perm.getTeamID() == team.getId() && perm.getPermission().equals(permission))
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
                .filter(teamInvite -> teamInvite.getTeamID() == team.getId())
                .filter(teamInvite -> teamInvite.getUser() == user.getUuid())
                .findFirst();
    }

    @Override
    public List<TeamInvite> getTeamInvites(TestTeam team) {
        return teamInvites.stream()
                .filter(teamInvite -> teamInvite.getTeamID() == team.getId())
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

    @Override
    public TeamBank getTeamBank(TestTeam team, String bankItem) {
        if (!teamBank.containsKey(bankItem)) {
            teamBank.put(bankItem, new TeamBank(team, bankItem, 0));
        }
        return teamBank.get(bankItem);
    }

    @Override
    public TeamSpawners getTeamSpawners(TestTeam team, EntityType entityType) {
        Optional<TeamSpawners> spawners = teamSpawners.stream().filter(s -> s.getTeamID() == team.getId() && s.getEntityType() == entityType).findFirst();
        if (spawners.isPresent()) {
            return spawners.get();
        }
        TeamSpawners s = new TeamSpawners(team, entityType, 0);
        teamSpawners.add(s);
        return s;
    }

    @Override
    public TeamBlock getTeamBlock(TestTeam team, XMaterial xMaterial) {
        Optional<TeamBlock> blocks = teamBlocks.stream().filter(s -> s.getTeamID() == team.getId() && s.getXMaterial() == xMaterial).findFirst();
        if (blocks.isPresent()) {
            return blocks.get();
        }
        TeamBlock b = new TeamBlock(team, xMaterial, 0);
        teamBlocks.add(b);
        return b;
    }

    @Override
    public TeamEnhancement getTeamEnhancement(TestTeam team, String enhancement) {
        if (!teamEnhancements.containsKey(enhancement)) {
            teamEnhancements.put(enhancement, new TeamEnhancement(team, enhancement, 0));
        }
        return teamEnhancements.get(enhancement);
    }

    @Override
    public CompletableFuture<Void> recalculateTeam(TestTeam team) {
        return CompletableFuture.runAsync(() -> {
        });
    }

    @Override
    public void createWarp(TestTeam team, UUID creator, Location location, String name, String password) {
        teamWarps.add(new TeamWarp(team, creator, location, name, password));
    }

    @Override
    public void deleteWarp(TeamWarp teamWarp) {
        teamWarps.remove(teamWarp);
    }

    @Override
    public List<TeamWarp> getTeamWarps(TestTeam team) {
        return teamWarps.stream().filter(teamWarp -> teamWarp.getTeamID() == team.getId()).collect(Collectors.toList());
    }

    @Override
    public Optional<TeamWarp> getTeamWarp(TestTeam team, String name) {
        return teamWarps.stream().filter(teamWarp -> teamWarp.getTeamID() == team.getId() && teamWarp.getName().equals(name)).findFirst();
    }

    @Override
    public List<TeamMission> getTeamMissions(TestTeam team) {
        return teamMissions.stream()
                .filter(teamMission -> teamMission.getTeamID()==team.getId())
                .collect(Collectors.toList());
    }

    @Override
    public TeamMission getTeamMission(TestTeam team, String missionName, int missionIndex) {
        Optional<TeamMission> teamMission = teamMissions.stream().filter(mission -> mission.getTeamID()==team.getId() && mission.getMissionName().equals(missionName) && mission.getMissionIndex()==missionIndex).findFirst();
        if (teamMission.isPresent()) {
            return teamMission.get();
        }
        TeamMission newTeamMission = new TeamMission(team, missionName, missionIndex, LocalDateTime.now().plusHours(1));
        teamMissions.add(newTeamMission);
        return newTeamMission;
    }

    @Override
    public void deleteTeamMission(TeamMission teamMission) {
        teamMissions.remove(teamMission);
    }
}
