package com.iridium.testplugin.managers;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.managers.IridiumUserManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class UserManager implements IridiumUserManager<Team, IridiumUser<Team>> {
    @Override
    public @NotNull IridiumUser<Team> getUser(@NotNull OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public Optional<IridiumUser<Team>> getUserByUUID(@NotNull UUID uuid) {
        return Optional.empty();
    }
}
