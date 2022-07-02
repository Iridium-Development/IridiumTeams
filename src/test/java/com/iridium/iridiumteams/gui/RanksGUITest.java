package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
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
    public void ranksGUIHasRanks() {
        TestTeam testTeam = new TeamBuilder().build();
        RanksGUI<TestTeam, User> ranksGUI = new RanksGUI<>(testTeam, TestPlugin.getInstance());
        Inventory inventory = ranksGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType());
        }
    }

    @Test
    public void clickingRanksGUIMovesToPermissionsGUI() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        RanksGUI<TestTeam, User> ranksGUI = new RanksGUI<>(testTeam, TestPlugin.getInstance());

        playerMock.openInventory(ranksGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, 11, ClickType.LEFT, InventoryAction.UNKNOWN);
        serverMock.getPluginManager().callEvent(inventoryClickEvent);
        assertTrue(inventoryClickEvent.isCancelled());

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof PermissionsGUI<?, ?>);
        assertEquals(Rank.VISITOR.getId(), ((PermissionsGUI<?, ?>) playerMock.getOpenInventory().getTopInventory().getHolder()).getRank());
    }
}