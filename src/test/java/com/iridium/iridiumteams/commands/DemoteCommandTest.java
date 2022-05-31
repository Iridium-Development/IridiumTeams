package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DemoteCommandTest {

    private ServerMock serverMock;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void executeDemoteCommandInvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test demote");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getConfiguration().prefix + " &7/team demote <player>"));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDemoteCommandNoFaction() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test demote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDemoteCommandUserNotInFaction() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test demote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userNotInYourTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDemoteCommandCannotDemote_LowerRank() {
        TestTeam team = new TeamBuilder().withPermission(8, PermissionType.DEMOTE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(8).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(8).build();

        serverMock.dispatchCommand(playerMock, "test demote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotDemoteUser.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDemoteCommandCannotDemote_BelowMember() {
        TestTeam team = new TeamBuilder().withPermission(8, PermissionType.DEMOTE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(8).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(1).build();

        serverMock.dispatchCommand(playerMock, "test demote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotDemoteUser.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDemoteCommandCannotDemote_NoPermission() {
        TestTeam team = new TeamBuilder().withPermission(8, PermissionType.DEMOTE, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(8).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(1).build();

        serverMock.dispatchCommand(playerMock, "test demote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotDemoteUser.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDemoteCommandSuccessful() {
        TestTeam team = new TeamBuilder().withPermission(8, PermissionType.DEMOTE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(8).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(2).build();

        serverMock.dispatchCommand(playerMock, "test demote " + otherPlayer.getDisplayName());

        assertEquals(1, TestPlugin.getInstance().getUserManager().getUser(otherPlayer).getUserRank());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().demotedPlayer
                .replace("%player%", otherPlayer.getName())
                .replace("%rank%", "Member")
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        otherPlayer.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userDemotedPlayer
                .replace("%demoter%", playerMock.getName())
                .replace("%player%", otherPlayer.getName())
                .replace("%rank%", "Member")
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        otherPlayer.assertNoMoreSaid();
    }
}