package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.gui.ConfirmationGUI;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class ConfirmableCommandTest {
    private ServerMock serverMock;
    private TestConfirmableCommand command;
    private IridiumTeams<TestTeam, User> iridiumTeams;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
        this.iridiumTeams = TestPlugin.getInstance();
        this.command = new TestConfirmableCommand(true);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testExecuteWithoutConfirmation() {
        TestConfirmableCommand command = new TestConfirmableCommand(false);
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);
        TestTeam team = new TeamBuilder().build();

        command.execute(user, team, new String[]{}, iridiumTeams);

        assertTrue(command.isCommandValidCalled);
        assertTrue(command.executeAfterConfirmationCalled);
    }

    @Test
    public void testExecuteWithConfirmation() {
        TestConfirmableCommand command = new TestConfirmableCommand(true);
        PlayerMock player = new UserBuilder(serverMock).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(player);
        TestTeam team = new TeamBuilder().build();

        command.execute(user, team, new String[]{}, iridiumTeams);

        assertTrue(command.isCommandValidCalled);

        serverMock.getScheduler().performOneTick();
        assertTrue(player.getOpenInventory().getTopInventory().getHolder() instanceof ConfirmationGUI);

        assertFalse(command.executeAfterConfirmationCalled);
    }

    @Test
    public void testConfirmationGUI_Accept() {
        TestConfirmableCommand command = new TestConfirmableCommand(true);
        PlayerMock player = new UserBuilder(serverMock).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(player);
        TestTeam team = new TeamBuilder().build();

        command.execute(user, team, new String[]{}, iridiumTeams);

        assertFalse(command.executeAfterConfirmationCalled);

        serverMock.getScheduler().performOneTick();

        Inventory openInventory = player.getOpenInventory().getTopInventory();
        assertTrue(openInventory.getHolder() instanceof ConfirmationGUI);

        InventoryClickEvent confirmClickEvent = player.simulateInventoryClick(TestPlugin.getInstance().getInventories().confirmationGUI.yes.slot);
        assertTrue(confirmClickEvent.isCancelled());
        assertNull(player.getOpenInventory().getTopInventory());
        assertTrue(command.executeAfterConfirmationCalled);
    }

    @Test
    public void testConfirmationGUI_Deny() {
        TestConfirmableCommand command = new TestConfirmableCommand(true);
        PlayerMock player = new UserBuilder(serverMock).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(player);
        TestTeam team = new TeamBuilder().build();

        command.execute(user, team, new String[]{}, iridiumTeams);

        assertFalse(command.executeAfterConfirmationCalled);

        serverMock.getScheduler().performOneTick();

        Inventory openInventory = player.getOpenInventory().getTopInventory();
        assertTrue(openInventory.getHolder() instanceof ConfirmationGUI);

        InventoryClickEvent denyClickEvent = player.simulateInventoryClick(TestPlugin.getInstance().getInventories().confirmationGUI.no.slot);
        assertTrue(denyClickEvent.isCancelled());
        assertNull(player.getOpenInventory().getTopInventory());
        assertFalse(command.executeAfterConfirmationCalled);
    }

    private class TestConfirmableCommand extends ConfirmableCommand<TestTeam, User> {
        boolean isCommandValidCalled = false;
        boolean executeAfterConfirmationCalled = false;

        public TestConfirmableCommand(boolean requiresConfirmation) {
            super(Collections.emptyList(), "", "", "", 0, requiresConfirmation);
        }

        @Override
        protected boolean isCommandValid(User user, TestTeam team, String[] arguments, IridiumTeams<TestTeam, User> iridiumTeams) {
            isCommandValidCalled = true;
            return true;
        }

        @Override
        protected void executeAfterConfirmation(User user, TestTeam team, String[] arguments, IridiumTeams<TestTeam, User> iridiumTeams) {
            executeAfterConfirmationCalled = true;
        }
    }
}