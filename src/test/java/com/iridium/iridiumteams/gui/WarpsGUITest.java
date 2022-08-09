package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WarpsGUITest {

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(9, Material.STONE)
            .put(10, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(11, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(12, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(13, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(14, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(15, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(16, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(17, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .build();

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
    public void WarpsGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().withWarp("test", "", null).build();
        WarpsGUI<TestTeam, User> warpsGUI = new WarpsGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = warpsGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void WarpsGUI__LeftClick() {
        Location location = new Location(null, 100, 0, 100);
        TestTeam testTeam = new TeamBuilder().withWarp("test", null, location).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        WarpsGUI<TestTeam, User> warpsGUI = new WarpsGUI<>(testTeam, null, TestPlugin.getInstance());
        playerMock.openInventory(warpsGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.LEFT, 9);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teleportingWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", "test")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(location, playerMock.getLocation());
    }

    @Test
    public void WarpsGUI__RightClick() {
        Location location = new Location(null, 100, 0, 100);
        TestTeam testTeam = new TeamBuilder().withPermission(1, PermissionType.MANAGE_WARPS, true).withWarp("test", null, location).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withRank(1).withTeam(testTeam).build();

        WarpsGUI<TestTeam, User> warpsGUI = new WarpsGUI<>(testTeam, null, TestPlugin.getInstance());
        playerMock.openInventory(warpsGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.RIGHT, 9);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().deletedWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
                .replace("%name%", "test")
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(TestPlugin.getInstance().getTeamManager().getTeamWarp(testTeam, "test").isPresent());
    }
}