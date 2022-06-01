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

class PromoteCommandTest {

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
    public void executePromoteCommandInvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test promote");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getConfiguration().prefix + " &7/team promote <player>"));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommandNoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommandUserNotInTeam() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userNotInYourTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommandCannotPromote_NoPermission() {
        TestTeam team = new TeamBuilder().withPermission(8, PermissionType.PROMOTE, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(8).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(1).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotPromoteUser.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommandCannotPromote_HigherRank() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.PROMOTE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(1).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(1).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotPromoteUser.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommandSuccessful() {
        TestTeam team = new TeamBuilder().withPermission(8, PermissionType.PROMOTE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(8).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(1).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());

        assertEquals(2, TestPlugin.getInstance().getUserManager().getUser(otherPlayer).getUserRank());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().promotedPlayer
                .replace("%player%", otherPlayer.getName())
                .replace("%rank%", "Moderator")
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        otherPlayer.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userPromotedPlayer
                .replace("%promoter%", playerMock.getName())
                .replace("%player%", otherPlayer.getName())
                .replace("%rank%", "Moderator")
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        otherPlayer.assertNoMoreSaid();
    }
}