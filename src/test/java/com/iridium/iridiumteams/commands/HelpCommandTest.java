package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpCommandTest {

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
    public void executeHelpCommand__Success__NoReloadPermissions() {
        TestPlugin.getInstance().getCommandManager().getCommands().clear();
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().helpCommand);
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().aboutCommand);
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().reloadCommand);
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test help");
        playerMock.assertSaid(StringUtils.color("&8&m ".repeat(25) + "&8[&c&lIridiumTeams Help&8]" + "&8&m ".repeat(25)));
        playerMock.assertSaid(StringUtils.color("&c/t about&r: &7View information about the plugin"));
        playerMock.assertSaid(StringUtils.color("&c/t help&r: &7Show all the plugin commands"));
        playerMock.assertSaid(StringUtils.color("&f&c<<"));
        playerMock.assertSaid(StringUtils.color("&f &7Page 1 of 1 "));
        playerMock.assertSaid(StringUtils.color("&f&c>>"));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeHelpCommand__Success() {
        TestPlugin.getInstance().getCommandManager().getCommands().clear();
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().helpCommand);
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().aboutCommand);
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().reloadCommand);
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.reload").build();
        serverMock.dispatchCommand(playerMock, "test help");
        playerMock.assertSaid(StringUtils.color("&8&m ".repeat(25) + "&8[&c&lIridiumTeams Help&8]" + "&8&m ".repeat(25)));
        playerMock.assertSaid(StringUtils.color("&c/t about&r: &7View information about the plugin"));
        playerMock.assertSaid(StringUtils.color("&c/t help&r: &7Show all the plugin commands"));
        playerMock.assertSaid(StringUtils.color("&c/t reload&r: &7Reload the plugin's configurations"));
        playerMock.assertSaid(StringUtils.color("&f&c<<"));
        playerMock.assertSaid(StringUtils.color("&f &7Page 1 of 1 "));
        playerMock.assertSaid(StringUtils.color("&f&c>>"));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeHelpCommand__Success__OutOfBounds() {
        TestPlugin.getInstance().getCommandManager().getCommands().clear();
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().helpCommand);
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().aboutCommand);
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().reloadCommand);
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.reload").build();
        serverMock.dispatchCommand(playerMock, "test help 2");
        playerMock.assertSaid(StringUtils.color("&8&m ".repeat(25) + "&8[&c&lIridiumTeams Help&8]" + "&8&m ".repeat(25)));
        playerMock.assertSaid(StringUtils.color("&c/t about&r: &7View information about the plugin"));
        playerMock.assertSaid(StringUtils.color("&c/t help&r: &7Show all the plugin commands"));
        playerMock.assertSaid(StringUtils.color("&c/t reload&r: &7Reload the plugin's configurations"));
        playerMock.assertSaid(StringUtils.color("&f&c<<"));
        playerMock.assertSaid(StringUtils.color("&f &7Page 1 of 1 "));
        playerMock.assertSaid(StringUtils.color("&f&c>>"));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void helpCommand__TabComplete() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(Arrays.asList("1", "2", "3", "4", "5", "6"), serverMock.getCommandTabComplete(playerMock, "test help "));
    }
}