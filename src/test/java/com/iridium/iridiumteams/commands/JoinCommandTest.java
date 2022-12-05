package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JoinCommandTest {
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
    public void executeJoinCommand__BadSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test join");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().joinCommand.syntax.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeJoinCommand__AlreadyInTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test join InvalidTeamName");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().alreadyHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeJoinCommand__TeamDoesntExist() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test join InvalidTeamName");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamDoesntExistByName
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeJoinCommand__NoInvite() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test join " + team.getName());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noActiveInvite
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeJoinCommand__LimitReached() {
        TestTeam team = new TeamBuilder().build();
        for (int i = 0; i < 5; i++) {
            new UserBuilder(serverMock).withTeam(team).build();
        }
        PlayerMock playerMock = new UserBuilder(serverMock).withTeamInvite(team).build();

        serverMock.dispatchCommand(playerMock, "test join " + team.getName());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().memberLimitReached
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeJoinCommand__WithPublicTeam() {
        TestTeam team = new TeamBuilder().withSetting(SettingType.TEAM_TYPE, "Public").build();
        PlayerMock teamMember = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test join " + team.getName());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().joinedTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", team.getName())
        ));
        playerMock.assertNoMoreSaid();
        teamMember.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userJoinedTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
        ));
        teamMember.assertNoMoreSaid();
        assertEquals(team.getId(), user.getTeamID());
        assertFalse(TestPlugin.getInstance().getTeamManager().getTeamInvite(team, user).isPresent());
    }

    @Test
    public void executeJoinCommand__WithInvite() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock teamMember = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeamInvite(team).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test join " + team.getName());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().joinedTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", team.getName())
        ));
        playerMock.assertNoMoreSaid();
        teamMember.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userJoinedTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
        ));
        teamMember.assertNoMoreSaid();
        assertEquals(team.getId(), user.getTeamID());
        assertFalse(TestPlugin.getInstance().getTeamManager().getTeamInvite(team, user).isPresent());
    }

    @Test
    public void executeJoinCommand__Bypassing() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock teamMember = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock playerMock = new UserBuilder(serverMock).setBypassing().build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test join " + team.getName());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().joinedTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", team.getName())
        ));
        playerMock.assertNoMoreSaid();
        teamMember.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userJoinedTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
        ));
        teamMember.assertNoMoreSaid();
        assertEquals(team.getId(), user.getTeamID());
        assertFalse(TestPlugin.getInstance().getTeamManager().getTeamInvite(team, user).isPresent());
    }

    @Test
    public void tabCompleteJoinCommand(){
        PlayerMock playerMock1 = new UserBuilder(serverMock).build();
        PlayerMock playerMock2 = new UserBuilder(serverMock).build();

        assertEquals(Arrays.asList(playerMock1.getName(), playerMock2.getName()), serverMock.getCommandTabComplete(playerMock1, "test join "));
    }
}