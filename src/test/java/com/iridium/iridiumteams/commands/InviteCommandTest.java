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

import static org.junit.jupiter.api.Assertions.assertTrue;

class InviteCommandTest {

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
    public void executeInviteCommandBadSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test invite");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getConfiguration().prefix + " &7/team invite <player>"));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeInviteCommandNoFaction() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test invite Player");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeInviteCommandNoPermission() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test invite Player");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotInvite.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeInviteCommandPlayerDoesntExist() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TestPlugin.getInstance().getTeamManager().setTeamPermission(team, 1, PermissionType.INVITE.getPermissionKey(), true);

        serverMock.dispatchCommand(playerMock, "test invite OtherPlayer");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notAPlayer.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeInviteCommandPlayerAlreadyInYourFaction() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).build();

        TestPlugin.getInstance().getTeamManager().setTeamPermission(team, 1, PermissionType.INVITE.getPermissionKey(), true);

        serverMock.dispatchCommand(playerMock, "test invite " + otherPlayer.getName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userAlreadyInTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeInviteCommandPlayerAlreadyInvited() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        TestPlugin.getInstance().getTeamManager().setTeamPermission(team, 1, PermissionType.INVITE.getPermissionKey(), true);
        TestPlugin.getInstance().getTeamManager().createTeamInvite(team, TestPlugin.getInstance().getUserManager().getUser(otherPlayer), TestPlugin.getInstance().getUserManager().getUser(playerMock));

        serverMock.dispatchCommand(playerMock, "test invite " + otherPlayer.getName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().inviteAlreadyPresent.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeInviteCommandSuccessful() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        TestPlugin.getInstance().getTeamManager().setTeamPermission(team, 1, PermissionType.INVITE.getPermissionKey(), true);

        serverMock.dispatchCommand(playerMock, "test invite " + otherPlayer.getName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamInviteSent
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", otherPlayer.getName())
        ));
        playerMock.assertNoMoreSaid();
        otherPlayer.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamInviteReceived
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
        ));
        otherPlayer.assertNoMoreSaid();
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamInvite(team, TestPlugin.getInstance().getUserManager().getUser(otherPlayer)).isPresent());
    }

}