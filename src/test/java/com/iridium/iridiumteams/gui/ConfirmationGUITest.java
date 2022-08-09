package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestRunnable;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmationGUITest {

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(9, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(10, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(11, Material.RED_STAINED_GLASS_PANE)
            .put(12, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(13, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(14, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(15, Material.GREEN_STAINED_GLASS_PANE)
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
    public void ConfirmationGUI__HasItems() {
        ConfirmationGUI<TestTeam, User> confirmationGUI = new ConfirmationGUI<>(() -> {
        }, TestPlugin.getInstance());
        Inventory inventory = confirmationGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(),"Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void ConfirmationGUI__Confirm() {
        TestRunnable runnable = new TestRunnable();

        PlayerMock playerMock = new UserBuilder(serverMock).build();

        ConfirmationGUI<TestTeam, User> confirmationGUI = new ConfirmationGUI<>(runnable, TestPlugin.getInstance());
        playerMock.openInventory(confirmationGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(15);

        assertTrue(inventoryClickEvent.isCancelled());
        assertNull(playerMock.getOpenInventory().getTopInventory());
        assertTrue(runnable.hasRan());
    }

    @Test
    public void ConfirmationGUI__Deny() {
        TestRunnable runnable = new TestRunnable();

        PlayerMock playerMock = new UserBuilder(serverMock).build();

        ConfirmationGUI<TestTeam, User> confirmationGUI = new ConfirmationGUI<>(runnable, TestPlugin.getInstance());
        playerMock.openInventory(confirmationGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(11);

        assertTrue(inventoryClickEvent.isCancelled());
        assertNull(playerMock.getOpenInventory().getTopInventory());
        assertFalse(runnable.hasRan());
    }
}