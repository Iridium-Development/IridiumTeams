package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.SettingType;
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

class SettingsGUITest {

    private ServerMock serverMock;

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(10, Material.GUNPOWDER)
            .put(11, Material.SUNFLOWER)
            .put(12, Material.SPAWNER)
            .put(13, Material.JUNGLE_LEAVES)
            .put(14, Material.ICE)
            .put(15, Material.FLINT_AND_STEEL)
            .put(16, Material.WHEAT_SEEDS)
            .put(19, Material.BLAZE_POWDER)
            .put(20, Material.CLOCK)
            .put(21, Material.ENDER_PEARL)
            .put(22, Material.TNT)
            .put(23, Material.BEACON)
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
    public void SettingsGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().build();
        SettingsGUI<TestTeam, User> settingsGUI = new SettingsGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = settingsGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void SettingsGUI__OnClick() {
        TestTeam testTeam = new TeamBuilder().withPermission(1, PermissionType.SETTINGS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        SettingsGUI<TestTeam, User> settingsGUI = new SettingsGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = settingsGUI.getInventory();

        playerMock.openInventory(inventory);
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(10);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().settingSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%setting%", "JoinType")
                .replace("%value%", "Public")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals("Public", TestPlugin.getInstance().getTeamManager().getTeamSetting(testTeam, SettingType.TEAM_TYPE.getSettingKey()).getValue());
    }
}