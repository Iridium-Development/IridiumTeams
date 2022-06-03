package com.iridium.iridiumteams.managers;

import com.iridium.iridiumteams.CreateCancelledException;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamInvite;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class TeamManager<T extends Team, U extends IridiumUser<T>> {
    public abstract Optional<T> getTeamViaID(int id);

    public abstract Optional<T> getTeamViaName(String name);

    public abstract Optional<T> getTeamViaLocation(Location location);

    public abstract Optional<T> getTeamViaNameOrPlayer(String name);

    public abstract List<U> getTeamMembers(T team);

    public abstract CompletableFuture<T> createTeam(@NotNull Player owner, @NotNull String name) throws CreateCancelledException;

    public abstract void deleteTeam(T team, U user);

    public int getUserRank(T team, U user) {
        if (user.getTeam().map(T::getId).orElse(0) == team.getId()) return user.getUserRank();
        // if they are not in the same team, they are a visitor
        return Rank.VISITOR.getId();
    }

    public abstract boolean getTeamPermission(Team team, int rank, String permission);

    public abstract void setTeamPermission(Team team, int rank, String permission, boolean allowed);

    public boolean getTeamPermission(T team, U user, String permission) {
        return user.isBypassing() || getTeamPermission(team, getUserRank(team, user), permission);
    }

    public boolean getTeamPermission(T team, U user, PermissionType permissionType) {
        return getTeamPermission(team, user, permissionType.getPermissionKey()) || user.isBypassing();
    }

    public boolean getTeamPermission(Location location, U user, String permission) {
        return getTeamViaLocation(location).map(team -> getTeamPermission(team, user, permission)).orElse(true);
    }

    public abstract Optional<TeamInvite> getTeamInvite(T team, U user);

    public abstract List<TeamInvite> getTeamInvites(T team);

    public abstract void createTeamInvite(T team, U user, U invitee);

    public abstract void deleteTeamInvite(TeamInvite teamInvite);

}
