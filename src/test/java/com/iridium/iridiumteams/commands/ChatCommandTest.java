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

class ChatCommandTest {
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
    public void executeChatCommand__InvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test chat");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().chatCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeChatCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test chat");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeChatCommand__UnknownChatType() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test chat invalidtype");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().unknownChatType
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%type%", "invalidtype")
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeChatCommand__Success() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test chat t");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().setChatType
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%type%", "Team")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals("Team", TestPlugin.getInstance().getUserManager().getUser(playerMock).getChatType());
    }

    @Test
    public void tabCompleteChatCommand(){
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(Arrays.asList("n", "none", "t", "team"), serverMock.getCommandTabComplete(playerMock, "test chat "));
    }
}