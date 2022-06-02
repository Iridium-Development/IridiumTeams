package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UnInviteCommandTest {

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

        serverMock.dispatchCommand(playerMock, "test uninvite");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getConfiguration().prefix + " &7/team uninvite <player>"));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeInviteCommandNoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test uninvite Player");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeInviteCommandPlayerNoInvite() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test uninvite " + otherPlayer.getName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noActiveInvite.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeInviteCommandSuccessful() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        TestPlugin.getInstance().getTeamManager().createTeamInvite(team, TestPlugin.getInstance().getUserManager().getUser(otherPlayer), TestPlugin.getInstance().getUserManager().getUser(playerMock));

        serverMock.dispatchCommand(playerMock, "test uninvite " + otherPlayer.getName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamInviteRevoked
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", otherPlayer.getName())
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(TestPlugin.getInstance().getTeamManager().getTeamInvite(team, TestPlugin.getInstance().getUserManager().getUser(otherPlayer)).isPresent());
    }

    @Test
    public void tabCompleteUnInviteCommand(){
        PlayerMock playerMock1 = new UserBuilder(serverMock).build();
        PlayerMock playerMock2 = new UserBuilder(serverMock).build();

        assertEquals(Arrays.asList(playerMock1.getName(), playerMock2.getName()), serverMock.getCommandTabComplete(playerMock1, "test uninvite "));
    }

}