package com.iridium.testplugin;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.IridiumUserProfile;

import java.util.UUID;

public class User extends IridiumUser<TestTeam> {
    public User(UUID uuid, String username) {
        super();
        setUuid(uuid);
        setName(username);
    }

    IridiumUserProfile<TestTeam> profileCache = new IridiumUserProfile<>();

    @Override
    public IridiumUserProfile<TestTeam> getActiveProfile() {
        return profileCache;
    }

    @Override
    public void setActiveProfile(IridiumUserProfile<TestTeam> profile) {
        this.profileCache = profile;
        this.activeProfileId = profile.getId();
    }
}
