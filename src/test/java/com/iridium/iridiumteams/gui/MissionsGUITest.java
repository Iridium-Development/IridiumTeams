package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.missions.MissionType;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MissionsGUITest {

    private final Map<Integer, Material> dailyInventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(9, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(10, Material.SUGAR_CANE)
            .put(11, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(12, Material.BONE)
            .put(13, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(14, Material.BREAD)
            .put(15, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(16, Material.GOLD_ORE)
            .put(17, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .build();

    private final Map<Integer, Material> onceInventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(0, Material.OAK_LOG)
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
    public void DailyMissionsGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().withMissions("farmer", "hunter", "baker", "miner").build();
        MissionGUI<TestTeam, User> missionGUI = new MissionGUI<>(testTeam, MissionType.DAILY, null, TestPlugin.getInstance());
        Inventory inventory = missionGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(dailyInventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void OnceMissionsGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().build();
        MissionGUI<TestTeam, User> missionGUI = new MissionGUI<>(testTeam, MissionType.ONCE, null, TestPlugin.getInstance());
        Inventory inventory = missionGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(onceInventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }
}