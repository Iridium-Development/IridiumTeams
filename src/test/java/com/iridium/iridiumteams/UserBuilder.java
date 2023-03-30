package com.iridium.iridiumteams;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumteams.utils.PlayerUtils;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;

public class UserBuilder {
    private final ServerMock serverMock;
    private final PlayerMock playerMock;
    private final User user;

    public UserBuilder(ServerMock serverMock) {
        this.serverMock = serverMock;
        this.playerMock = serverMock.addPlayer();
        this.user = TestPlugin.getInstance().getUserManager().getUser(playerMock);
    }

    public UserBuilder(ServerMock serverMock, String playerName) {
        this.serverMock = serverMock;
        this.playerMock = serverMock.addPlayer(playerName);
        this.user = TestPlugin.getInstance().getUserManager().getUser(playerMock);
    }

    public UserBuilder withExperience(int experience) {
        PlayerUtils.setTotalExperience(playerMock, experience);
        return this;
    }

    public UserBuilder withTeamInvite(TestTeam testTeam) {
        TestPlugin.getInstance().getTeamManager().createTeamInvite(testTeam, user, user);
        return this;
    }

    public UserBuilder withChatType(String chatType) {
        user.setChatType(chatType);
        return this;
    }

    public UserBuilder withTrust(TestTeam testTeam) {
        TestPlugin.getInstance().getTeamManager().createTeamTrust(testTeam, user, user);
        return this;
    }

    public UserBuilder withTeam(TestTeam testTeam) {
        user.setTeam(testTeam);
        return this;
    }

    public UserBuilder withRank(int rank) {
        user.setUserRank(rank);
        return this;
    }

    public UserBuilder withPermission(String... permissions) {
        for (String permission : permissions) {
            playerMock.addAttachment(TestPlugin.getInstance(), permission, true);
        }
        return this;
    }

    public UserBuilder setBypassing() {
        user.setBypassing(true);
        return this;
    }

    public UserBuilder setOp() {
        playerMock.setOp(true);
        return this;
    }

    public PlayerMock build() {
        return playerMock;
    }

}
