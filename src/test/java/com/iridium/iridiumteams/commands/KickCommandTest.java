package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KickCommandTest {

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
    public void executeKickCommand__InvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test kick");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().kickCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeKickCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test kick OtherPlayer");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeKickCommand__NoPermission() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test kick OtherPlayer");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotKick.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeKickCommand__PlayerNotInTeam() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.KICK, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test kick " + otherPlayer.getName());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userNotInYourTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeKickCommand__PlayerHigherRank() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.KICK, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(2).build();

        serverMock.dispatchCommand(playerMock, "test kick " + otherPlayer.getName());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotKickHigherRank.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeKickCommand__PlayerCannotKickSelf() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(Rank.OWNER.getId()).build();

        serverMock.dispatchCommand(playerMock, "test kick " + playerMock.getName());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotKickYourself.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeKickCommand__Successful() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.KICK, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(2).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test kick " + otherPlayer.getName());

        assertEquals(0, TestPlugin.getInstance().getUserManager().getUser(otherPlayer).getTeamID());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().playerKicked
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", otherPlayer.getName())
                .replace("%kicker%", playerMock.getName())
        ));
        playerMock.assertNoMoreSaid();
        otherPlayer.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().youHaveBeenKicked
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
        ));
        otherPlayer.assertNoMoreSaid();
    }

    @Test
    public void executeKickCommand__SuccessfulOwner() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(Rank.OWNER.getId()).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test kick " + otherPlayer.getName());

        assertEquals(0, TestPlugin.getInstance().getUserManager().getUser(otherPlayer).getTeamID());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().playerKicked
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", otherPlayer.getName())
                .replace("%kicker%", playerMock.getName())
        ));
        playerMock.assertNoMoreSaid();
        otherPlayer.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().youHaveBeenKicked
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
        ));
        otherPlayer.assertNoMoreSaid();
    }

    @Test
    public void executeKickCommand__SuccessfulBypassing() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.KICK, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).setBypassing().build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test kick " + otherPlayer.getName());

        assertEquals(0, TestPlugin.getInstance().getUserManager().getUser(otherPlayer).getTeamID());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().playerKicked
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", otherPlayer.getName())
                .replace("%kicker%", playerMock.getName())
        ));
        playerMock.assertNoMoreSaid();
        otherPlayer.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().youHaveBeenKicked
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
        ));
        otherPlayer.assertNoMoreSaid();
    }

    @Test
    public void tabCompleteKickCommand(){
        PlayerMock playerMock1 = new UserBuilder(serverMock).build();
        PlayerMock playerMock2 = new UserBuilder(serverMock).build();

        assertEquals(Arrays.asList(playerMock1.getName(), playerMock2.getName()), serverMock.getCommandTabComplete(playerMock1, "test kick "));
    }
}