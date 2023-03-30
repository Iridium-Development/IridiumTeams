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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrustsGUITest {

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(0, Material.PLAYER_HEAD)
            .put(1, Material.PLAYER_HEAD)
            .put(2, Material.PLAYER_HEAD)
            .put(3, Material.PLAYER_HEAD)
            .put(4, Material.PLAYER_HEAD)
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
    public void InvitesGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().build();
        for (int i = 0; i < 5; i++) {
            new UserBuilder(serverMock).withTrust(testTeam).build();
        }
        TrustsGUI<TestTeam, User> trustsGUI = new TrustsGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = trustsGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }
}