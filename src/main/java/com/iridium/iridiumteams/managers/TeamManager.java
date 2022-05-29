package com.iridium.iridiumteams.managers;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface TeamManager<T extends Team, U extends IridiumUser<T>> {

    Optional<T> getTeamViaID(int id);

    Optional<T> getTeamViaName(String name);

    List<U> getTeamMembers(T team);

    CompletableFuture<T> createTeam(@NotNull Player owner, @NotNull String name);


}
