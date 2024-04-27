package com.iridium.iridiumteams.listeners;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerInteractListenerTest {

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
    public void onPlayerInteract__Visitor_Cannot_Set_Spawner() {

        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.SPAWNERS, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        CreatureSpawnerMock spawnerMock = new CreatureSpawnerMock();


        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                new ItemStack(Material.PIG_SPAWN_EGG),
                spawnerMock.getBlock(),
                BlockFace.EAST
                );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertTrue(playerInteractEvent.isCancelled());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotBreakSpawners
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));

        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Booster_Not_Active() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        CreatureSpawnerMock spawner = new CreatureSpawnerMock();

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                new ItemStack(Material.PIG_SPAWN_EGG),
                spawner.getBlock(),
                BlockFace.DOWN
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertEquals(4, spawner.getSpawnCount());
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