package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.gui.WarpsGUI;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WarpsCommandTest {

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
    public void executeWarpsCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test warps");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWarpsCommand__Success() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test warps");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof WarpsGUI<?,?>);
    }

}