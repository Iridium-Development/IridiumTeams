package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BypassCommandTest {

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
    public void executeBypassCommand__NoPermission() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test bypass");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noPermission
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBypassCommand__On() {
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.bypass").build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test bypass");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().nowBypassing
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(user.isBypassing());
    }

    @Test
    public void executeBypassCommand__Off() {
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.bypass").setBypassing().build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test bypass");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noLongerBypassing
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(user.isBypassing());
    }
}