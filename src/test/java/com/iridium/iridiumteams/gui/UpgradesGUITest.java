package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpgradesGUITest {

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(9, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(10, Material.DIAMOND_PICKAXE)
            .put(11, Material.PLAYER_HEAD)
            .put(12, Material.SUGAR)
            .put(13, Material.END_PORTAL_FRAME)
            .put(14, Material.FEATHER)
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
    public void UpgradesGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().build();
        UpgradesGUI<TestTeam, User> upgradesGUI = new UpgradesGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = upgradesGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(),"Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void UpgradesGUI__Click() {
        TestTeam testTeam = new TeamBuilder().withExperience(125).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        TestPlugin.getInstance().getEconomy().depositPlayer(playerMock, 10000);
        UpgradesGUI<TestTeam, User> upgradesGUI = new UpgradesGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(upgradesGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.LEFT, 10);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().purchasedUpgrade
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%upgrade%", "haste")
        ));
        playerMock.assertNoMoreSaid();
    }
}