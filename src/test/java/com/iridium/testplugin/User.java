package com.iridium.testplugin;

import com.iridium.iridiumteams.database.IridiumUser;

import java.util.UUID;

public class User extends IridiumUser<TestTeam> {
    public User(UUID uuid, String username) {
        super();
        setUuid(uuid);
        setName(username);
    }
}
