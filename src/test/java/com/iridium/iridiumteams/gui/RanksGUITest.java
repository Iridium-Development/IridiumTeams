package com.iridium.iridiumteams.gui;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RanksGUITest {

    private ServerMock serverMock;

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(11, Material.WOODEN_AXE)
            .put(12, Material.STONE_AXE)
            .put(13, Material.IRON_AXE)
            .put(14, Material.GOLDEN_AXE)
            .put(15, Material.DIAMOND_AXE)
            .build();

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
    public void RanksGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().build();
        RanksGUI<TestTeam, User> ranksGUI = new RanksGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = ranksGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(),"Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void RanksGUI__Click() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        RanksGUI<TestTeam, User> ranksGUI = new RanksGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(ranksGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(11);

        assertTrue(inventoryClickEvent.isCancelled());
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof PermissionsGUI<?, ?>);
        assertEquals(Rank.VISITOR.getId(), ((PermissionsGUI<?, ?>) playerMock.getOpenInventory().getTopInventory().getHolder()).getRank());
    }
}