package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.testplugin.TestPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AboutCommandTest {

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
    public void executeAboutCommand() {
        PlayerMock playerMock = serverMock.addPlayer("player");
        serverMock.dispatchCommand(playerMock, "test about");
        playerMock.assertSaid(StringUtils.color("&7Plugin Name: &cIridiumTest"));
        playerMock.assertSaid(StringUtils.color("&7Plugin Version: &c" + TestPlugin.getInstance().getDescription().getVersion()));
        playerMock.assertSaid(StringUtils.color("&7Plugin Author: &cPeaches_MLG"));
        playerMock.assertSaid(StringUtils.color("&7Plugin Donations: &cwww.patreon.com/Peaches_MLG"));
        playerMock.assertNoMoreSaid();
    }
}