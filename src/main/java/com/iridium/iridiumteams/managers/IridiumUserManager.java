package com.iridium.iridiumteams.managers;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class IridiumUserManager<T extends Team, U extends IridiumUser<T>> {

    public @NotNull U getUser(@NotNull OfflinePlayer offlinePlayer) {
        return null;
    }

    public Optional<U> getUserByUUID(@NotNull UUID uuid) {
        return null;
    }
}
