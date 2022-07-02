package com.iridium.iridiumteams.listeners;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerExpChangeListenerTest {

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
    public void onPlayerExpChange__BoosterNotActive() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        PlayerExpChangeEvent playerExpChangeEvent = new PlayerExpChangeEvent(playerMock, 10);
        serverMock.getPluginManager().callEvent(playerExpChangeEvent);

        assertEquals(10, playerExpChangeEvent.getAmount());
    }

    @Test
    public void onPlayerExpChange__BoosterActive() {
        TestTeam team = new TeamBuilder().withEnhancement("experience", 1).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        PlayerExpChangeEvent playerExpChangeEvent = new PlayerExpChangeEvent(playerMock, 10);
        serverMock.getPluginManager().callEvent(playerExpChangeEvent);

        assertEquals(15, playerExpChangeEvent.getAmount());
    }
}