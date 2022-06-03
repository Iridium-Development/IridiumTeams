package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.apache.commons.lang.NotImplementedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandTest {

    private ServerMock serverMock;
    private Command<TestTeam, User> command;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
        command = new Command<>();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void executeCommand__NotAPlayer() {
        ConsoleCommandSenderMock commandSender = new ConsoleCommandSenderMock();

        command.execute(commandSender, new String[]{}, TestPlugin.getInstance());

        commandSender.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().mustBeAPlayer
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix))
        );
        commandSender.assertNoMoreSaid();
    }

    @Test
    public void executeCommand__DontHaveTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        command.execute(playerMock, new String[]{}, TestPlugin.getInstance());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix))
        );
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeCommand__HasTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        assertThrows(NotImplementedException.class, () -> command.execute(playerMock, new String[]{}, TestPlugin.getInstance()));
    }

    @Test
    public void tabCompleteCommand__EmptyList() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Collections.emptyList(), command.onTabComplete(playerMock, new String[]{}, TestPlugin.getInstance()));
    }
}