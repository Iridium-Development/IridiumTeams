package com.iridium.iridiumteams.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.commands.Command;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.commands.TestCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CommandManagerTest {

    private ServerMock serverMock;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
        TestPlugin.getInstance().getCommandManager().getCommands().clear();
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().aboutCommand);
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().reloadCommand);
        TestPlugin.getInstance().getCommandManager().registerCommand(TestPlugin.getInstance().getCommands().bypassCommand);
        TestPlugin.getInstance().getCommandManager().registerCommand(new TestCommand());
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }


    @Test
    public void commandManagerCommands__HasNoArgsConstructor() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        TestPlugin.getInstance().getCommandManager().getCommands().clear();
        TestPlugin.getInstance().getCommandManager().registerCommands();
        for (Command<?, ?> command : TestPlugin.getInstance().getCommandManager().getCommands()) {
            Optional<Constructor<?>> constructorOptional = Arrays.stream(command.getClass().getConstructors()).filter(constructor -> constructor.getParameterCount() == 0).findFirst();
            assertTrue(constructorOptional.isPresent(), command.getClass().getSimpleName() + " Does not have default constructor");
            constructorOptional.get().newInstance();
        }
    }

    @Test
    public void CommandManager__NoArgs() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test");

        playerMock.assertSaid("No Argument Method hit");
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void CommandManager__NoPermission() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test test");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noPermission
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    //TODO wait for https://github.com/MockBukkit/MockBukkit/pull/497
//    @Test
//    public void CommandManager__NotPlayer() {
//        ConsoleCommandSenderMock consoleCommandSenderMock = new ConsoleCommandSenderMock();
//
//        serverMock.dispatchCommand(consoleCommandSenderMock, "test test");
//
//        consoleCommandSenderMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().mustBeAPlayer
//                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
//        ));
//        consoleCommandSenderMock.assertNoMoreSaid();
//    }

    @Test
    public void CommandManager__NotInTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.test").build();

        serverMock.dispatchCommand(playerMock, "test test");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void CommandManager__Success() {
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.test").withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test test");

        playerMock.assertNoMoreSaid();
        assertTrue(TestCommand.hasCalled);
    }

    @Test
    public void CommandManager__Cooldown() {
        // Lets change the message so it doesnt include the exact timings to avoid flakey tests
        TestPlugin.getInstance().getMessages().activeCooldown = "%prefix% &7You cannot do that due to a running cooldown, please wait";

        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.test").withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test test");

        playerMock.assertNoMoreSaid();
        assertTrue(TestCommand.hasCalled);

        TestCommand.hasCalled = false;

        serverMock.dispatchCommand(playerMock, "test test");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().activeCooldown
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(TestCommand.hasCalled);
    }

    @Test
    public void CommandManager__UnknownCommand() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test invalid");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().unknownCommand
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void CommandManager__TabComplete__NoPermissions() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(List.of("about"), serverMock.getCommandTabComplete(playerMock, "test "));
    }

    @Test
    public void CommandManager__TabComplete__WithPermissions() {
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.reload", "iridiumteams.bypass", "iridiumteams.test").build();
        assertEquals(Arrays.asList("about", "bypass", "reload", "test"), serverMock.getCommandTabComplete(playerMock, "test "));
    }

    @Test
    public void CommandManager__TabComplete__Command() {
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.test").build();
        assertEquals(Arrays.asList("A", "c", "d"), serverMock.getCommandTabComplete(playerMock, "test test "));
    }

    @Test
    public void CommandManager__TabComplete__Command__NoPermission() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(Collections.emptyList(), serverMock.getCommandTabComplete(playerMock, "test test "));
    }

    @Test
    public void CommandManager__TabComplete__UnknownCommand() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(Collections.emptyList(), serverMock.getCommandTabComplete(playerMock, "test unknownCommand "));
    }
}