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
import org.bukkit.Material;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockPlaceListenerTest {

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
    public void onBlockPlaceNotCancelled__NotInTerritory() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertFalse(playerMock.simulateBlockPlace(Material.STONE, playerMock.getLocation()).isCancelled());
    }

    @Test
    public void onBlockPlaceNotCancelled__HasPermissions() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_PLACE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        assertFalse(playerMock.simulateBlockPlace(Material.STONE, playerMock.getLocation()).isCancelled());
    }

    @Test
    public void onBlockPlaceNotCancelled__IsTrusted() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_PLACE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTrust(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        assertFalse(playerMock.simulateBlockPlace(Material.STONE, playerMock.getLocation()).isCancelled());
    }

    @Test
    public void onBlockPlaceCancelled__NoPermissions() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_PLACE, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        assertTrue(playerMock.simulateBlockPlace(Material.STONE, playerMock.getLocation()).isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotPlaceBlocks
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }
}