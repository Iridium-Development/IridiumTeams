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
    public void executePromoteCommand__InvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test promote");
        
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().promoteCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommand__UserNotInTeam() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().userNotInYourTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommand__CannotPromote_NoPermission() {
        TestTeam team = new TeamBuilder().withPermission(8, PermissionType.PROMOTE, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(8).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(1).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotPromoteUser.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommand__CannotPromote_HigherRank() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.PROMOTE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(1).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(1).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotPromoteUser.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDemoteCommand__CannotPromote_Owner() {
        TestTeam team = new TeamBuilder().withPermission(8, PermissionType.PROMOTE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(8).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(Rank.OWNER.getId()).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotPromoteUser.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePromoteCommand__Successful() {
        TestTeam team = new TeamBuilder().withPermission(8, PermissionType.PROMOTE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(8).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(1).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());

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
        assertEquals(2, TestPlugin.getInstance().getUserManager().getUser(otherPlayer).getUserRank());
    }

    @Test
    public void executePromoteCommand__SuccessfulOwner() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(Rank.OWNER.getId()).build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).withRank(1).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());

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
        assertEquals(2, TestPlugin.getInstance().getUserManager().getUser(otherPlayer).getUserRank());
    }

    @Test
    public void executePromoteCommand__SuccessfulBypassing() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).setBypassing().build();
        PlayerMock otherPlayer = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test promote " + otherPlayer.getDisplayName());

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
        assertEquals(2, TestPlugin.getInstance().getUserManager().getUser(otherPlayer).getUserRank());
    }

    @Test
    public void tabCompletePromoteCommand(){
        PlayerMock playerMock1 = new UserBuilder(serverMock).build();
        PlayerMock playerMock2 = new UserBuilder(serverMock).build();

        assertEquals(Arrays.asList(playerMock1.getName(), playerMock2.getName()), serverMock.getCommandTabComplete(playerMock1, "test promote "));
    }
}