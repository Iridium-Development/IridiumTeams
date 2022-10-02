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

class DescriptionCommandTest {
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
    public void executeDescriptionCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test description my new awesome description");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDescriptionCommand__InvalidSyntax() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test description");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().descriptionCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDescriptionCommand__NoPermission() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.DESCRIPTION, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test description my new awesome description");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotChangeDescription.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDescriptionCommand__Successful() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.DESCRIPTION, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test description my new awesome description");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().descriptionChanged
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
                .replace("%description%", "my new awesome description")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(team.getDescription(), "my new awesome description");
    }

    @Test
    public void executeDescriptionCommand__Other__Executes() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).setBypassing().build();
        PlayerMock adminPlayerMock = new UserBuilder(serverMock).setOp().build();

        serverMock.dispatchCommand(adminPlayerMock, "test description " + playerMock.getName() + " my new awesome description");
        adminPlayerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().changedPlayerDescription
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", testTeam.getName())
                .replace("%description%", "my new awesome description")
        ));
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().descriptionChanged
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", adminPlayerMock.getName())
                .replace("%description%", "my new awesome description")
        ));
        playerMock.assertNoMoreSaid();
        adminPlayerMock.assertNoMoreSaid();
        assertEquals(testTeam.getDescription(), "my new awesome description");
    }
}