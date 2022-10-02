package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.gui.ConfirmationGUI;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteCommandTest {

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
    public void executeDeleteCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test delete");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteCommand__NotOwnerOrBypassing() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test delete");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotDeleteTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteCommand__Owner() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).withRank(Rank.OWNER.getId()).build();

        serverMock.dispatchCommand(playerMock, "test delete");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof ConfirmationGUI);
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteCommand__Bypass() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).setBypassing().build();

        serverMock.dispatchCommand(playerMock, "test delete");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof ConfirmationGUI);
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteCommand__Executes() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).setBypassing().build();

        serverMock.dispatchCommand(playerMock, "test delete");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof ConfirmationGUI);
        playerMock.simulateInventoryClick(TestPlugin.getInstance().getInventories().confirmationGUI.yes.slot);
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamDeleted
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
        ));
        playerMock.assertNoMoreSaid();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);
        assertEquals(0, user.getTeamID());
    }

    @Test
    public void executeDeleteCommand__Other__NoPermissions() {
        PlayerMock adminPlayerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(adminPlayerMock, "test delete unknown");
        adminPlayerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noPermission
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        adminPlayerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteCommand__Other__NoTeamFound() {
        PlayerMock adminPlayerMock = new UserBuilder(serverMock).setOp().build();

        serverMock.dispatchCommand(adminPlayerMock, "test delete unknown");
        adminPlayerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamDoesntExistByName
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        adminPlayerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteCommand__Other__Executes() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).setBypassing().build();
        PlayerMock adminPlayerMock = new UserBuilder(serverMock).setOp().build();

        serverMock.dispatchCommand(adminPlayerMock, "test delete " + playerMock.getName());
        assertTrue(adminPlayerMock.getOpenInventory().getTopInventory().getHolder() instanceof ConfirmationGUI);
        adminPlayerMock.simulateInventoryClick(TestPlugin.getInstance().getInventories().confirmationGUI.yes.slot);
        adminPlayerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().deletedPlayerTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", testTeam.getName())
        ));
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamDeleted
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", adminPlayerMock.getName())
        ));
        playerMock.assertNoMoreSaid();
        adminPlayerMock.assertNoMoreSaid();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);
        assertEquals(0, user.getTeamID());
    }

}