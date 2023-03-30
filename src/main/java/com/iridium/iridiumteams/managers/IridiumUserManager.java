package com.iridium.iridiumteams.managers;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IridiumUserManager<T extends Team, U extends IridiumUser<T>> {

    @NotNull U getUser(@NotNull OfflinePlayer offlinePlayer);

    Optional<U> getUserByUUID(@NotNull UUID uuid);

    List<U> getUsers();
}
