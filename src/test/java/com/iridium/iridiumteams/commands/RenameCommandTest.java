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

class RenameCommandTest {
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
    public void executeRenameCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test rename my new awesome name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeRenameCommand____InvalidSyntax() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test rename");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().renameCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeRenameCommand____NoPermission() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        TestPlugin.getInstance().getTeamManager().setTeamPermission(team, 1, PermissionType.RENAME.getPermissionKey(), false);

        serverMock.dispatchCommand(playerMock, "test rename my new awesome name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotChangeName.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeRenameCommand__TeamNameTooShort() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.RENAME, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test rename a");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamNameTooShort
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%min_length%", String.valueOf(TestPlugin.getInstance().getConfiguration().minTeamNameLength))
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeRenameCommand__TeamNameTooLong() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.RENAME, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test rename areallyreallylongteamname");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamNameTooLong
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%max_length%", String.valueOf(TestPlugin.getInstance().getConfiguration().maxTeamNameLength))
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeRenameCommand__TeamNameTaken() {
        new TeamBuilder("test").build();
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.RENAME, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test rename test");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamNameAlreadyExists
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeRenameCommand____Successful() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.RENAME, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test rename my new awesome name");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().nameChanged
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
                .replace("%name%", "my new awesome name")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(team.getName(), "my new awesome name");
    }

    @Test
    public void executeRenameCommand__Other__Executes() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).setBypassing().build();
        PlayerMock adminPlayerMock = new UserBuilder(serverMock).setOp().build();

        serverMock.dispatchCommand(adminPlayerMock, "test rename " + playerMock.getName() + " my new awesome name");
        adminPlayerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().changedPlayerName
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", "my new awesome name")
                .replace("%player%", playerMock.getName())
        ));
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().nameChanged
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", adminPlayerMock.getName())
                .replace("%name%", "my new awesome name")
        ));
        playerMock.assertNoMoreSaid();
        adminPlayerMock.assertNoMoreSaid();
        assertEquals(testTeam.getName(), "my new awesome name");
    }
}