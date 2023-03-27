package com.iridium.testplugin.managers;

import com.iridium.iridiumteams.database.IridiumUserProfile;
import com.iridium.iridiumteams.managers.IridiumUserManager;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserManager implements IridiumUserManager<TestTeam, User> {
    public static List<User> users;

    public UserManager() {
        users = new ArrayList<>();
    }

    @Override
    public @NotNull User getUser(@NotNull OfflinePlayer offlinePlayer) {
        Optional<User> userOptional = getUserByUUID(offlinePlayer.getUniqueId());
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            Optional<String> name = Optional.ofNullable(offlinePlayer.getName());
            User user = new User(offlinePlayer.getUniqueId(), name.orElse(""));
            users.add(user);
            return user;
        }
    }

    public Optional<User> getUserByUUID(@NotNull UUID uuid) {
        return users.stream().filter(user -> user.getUuid() == uuid).findFirst();
    }

    @Override
    public Optional<IridiumUserProfile<TestTeam>> getUserProfile(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfile'");
    }

    @Override
    public List<IridiumUserProfile<TestTeam>> getUserProfiles(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfiles'");
    }
}
