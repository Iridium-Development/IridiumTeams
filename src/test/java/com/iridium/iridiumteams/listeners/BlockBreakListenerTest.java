package com.iridium.iridiumteams.listeners;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockBreakListenerTest {

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
    public void onBlockBreakNotCancelled__NotInTerritory() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertFalse(playerMock.simulateBlockBreak(playerMock.getLocation().getBlock()).isCancelled());
    }

    @Test
    public void onBlockBreakNotCancelled__HasPermissions() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_BREAK, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        assertFalse(playerMock.simulateBlockBreak(playerMock.getLocation().getBlock()).isCancelled());
    }

    @Test
    public void onBlockBreakNotCancelled__IsTrusted() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_BREAK, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTrust(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        assertFalse(playerMock.simulateBlockBreak(playerMock.getLocation().getBlock()).isCancelled());
    }

    @Test
    public void onBlockBreakCancelled__NoPermissions() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_BREAK, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        assertTrue(playerMock.simulateBlockBreak(playerMock.getLocation().getBlock()).isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotBreakBlocks
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }
}