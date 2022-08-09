package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.configs.inventories.InventoryConfig;
import com.iridium.testplugin.TestPlugin;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryConfigGUITest {

    private InventoryConfig inventoryConfig;

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(0, Material.DIAMOND_BLOCK)
            .build();

    private ServerMock serverMock;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
        inventoryConfig = new InventoryConfig(
                9,
                "Title",
                new Background(ImmutableMap.<Integer, Item>builder().build()),
                ImmutableMap.<String, Item>builder()
                        .put("t create", new Item(XMaterial.DIAMOND_BLOCK, 0, 1, "", Collections.emptyList()))
                        .build()
        );
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void InventoryConfigGUI__HasItems() {
        InventoryConfigGUI inventoryConfigGUI = new InventoryConfigGUI(inventoryConfig);
        Inventory inventory = inventoryConfigGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void InventoryConfigGUI__Click() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        InventoryConfigGUI inventoryConfigGUI = new InventoryConfigGUI(inventoryConfig);
        playerMock.openInventory(inventoryConfigGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(0);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().createCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }
}