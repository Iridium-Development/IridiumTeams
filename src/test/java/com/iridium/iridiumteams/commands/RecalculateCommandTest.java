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

class RecalculateCommandTest {

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
    public void executeRecalculateCommand__NoPermission() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test recalculate");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noPermission.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeRecalculateCommand__AlreadyRecalculating() {
        TestPlugin.getInstance().setRecalculating(true);
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.recalculate").build();

        serverMock.dispatchCommand(playerMock, "test recalculate");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().calculationAlreadyInProcess
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeRecalculateCommand__Success() {
        PlayerMock playerMock = new UserBuilder(serverMock).withPermission("iridiumteams.recalculate").build();

        serverMock.dispatchCommand(playerMock, "test recalculate");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().calculatingTeams
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
                .replace("%minutes%", String.valueOf(0))
                .replace("%seconds%", String.valueOf(0))
                .replace("%amount%", String.valueOf(0))
        ));
        playerMock.assertNoMoreSaid();
    }
}