package com.iridium.iridiumteams.listeners;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.block.LeavesDecayEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeavesDecayListenerTest {

    private ServerMock serverMock;

    @BeforeEach
    public void setUp() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void onLeavesDecay_ShouldCancelEvent_WhenLeafDecayIsDisabled() {
        TestTeam team = new TeamBuilder().withSetting(SettingType.LEAF_DECAY, "Disabled").build();
        TeamManager.teamViaLocation = Optional.of(team);

        World world = serverMock.addSimpleWorld("world");
        LeavesDecayEvent leavesDecayEvent = new LeavesDecayEvent(new Location(world, 0, 0, 0).getBlock());
        Bukkit.getPluginManager().callEvent(leavesDecayEvent);

        assertTrue(leavesDecayEvent.isCancelled());
    }

    @Test
    public void onLeavesDecay_ShouldNotCancelEvent_WhenLeafDecayIsEnabled() {
        TestTeam team = new TeamBuilder().withSetting(SettingType.LEAF_DECAY, "Enabled").build();
        TeamManager.teamViaLocation = Optional.of(team);

        World world = serverMock.addSimpleWorld("world");
        LeavesDecayEvent leavesDecayEvent = new LeavesDecayEvent(new Location(world, 0, 0, 0).getBlock());
        Bukkit.getPluginManager().callEvent(leavesDecayEvent);

        assertFalse(leavesDecayEvent.isCancelled());
    }

    @Test
    public void onLeavesDecay_ShouldNotCancelEvent_WhenNoTeamExists() {
        World world = serverMock.addSimpleWorld("world");
        LeavesDecayEvent leavesDecayEvent = new LeavesDecayEvent(new Location(world, 0, 0, 0).getBlock());
        Bukkit.getPluginManager().callEvent(leavesDecayEvent);

        assertFalse(leavesDecayEvent.isCancelled());
    }
}