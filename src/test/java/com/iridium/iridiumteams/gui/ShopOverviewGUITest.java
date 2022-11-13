package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopOverviewGUITest {

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(12, Material.GRASS_BLOCK)
            .put(13, Material.COOKED_CHICKEN)
            .put(14, Material.GOLD_INGOT)
            .put(21, Material.WHEAT)
            .put(22, Material.SPIDER_EYE)
            .put(23, Material.SADDLE)
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
    public void ShopOverviewGUI__HasItems() {
        ShopOverviewGUI<TestTeam, User> upgradesGUI = new ShopOverviewGUI<>(null, TestPlugin.getInstance());
        Inventory inventory = upgradesGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(),"Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void ShopOverviewGUI__Click() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        ShopOverviewGUI<TestTeam, User> upgradesGUI = new ShopOverviewGUI<>(null, TestPlugin.getInstance());

        playerMock.openInventory(upgradesGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.LEFT, 12);

        assertTrue(inventoryClickEvent.isCancelled());
        InventoryHolder inventoryHolder = playerMock.getOpenInventory().getTopInventory().getHolder();
        assertTrue(inventoryHolder instanceof ShopCategoryGUI<?,?>);
        ShopCategoryGUI shopCategoryGUI = (ShopCategoryGUI) inventoryHolder;
        assertEquals("Blocks", shopCategoryGUI.getCategoryName());
    }
}