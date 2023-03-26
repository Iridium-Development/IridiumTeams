package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.dependencies.xseries.XSound;
import com.iridium.iridiumteams.Reward;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.utils.PlayerUtils;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RewardsGUITest {

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(0, Material.DIAMOND)
            .put(47, Material.RED_STAINED_GLASS_PANE)
            .put(51, Material.LIME_STAINED_GLASS_PANE)
            .put(53, Material.SUNFLOWER)
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
    public void RewardsGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().withReward(new Reward(new Item(XMaterial.DIAMOND, 1, "&c&lReward", Collections.emptyList()), Collections.emptyList(), 1000, new HashMap<>(), 0,10, XSound.ENTITY_PLAYER_LEVELUP)).build();
        RewardsGUI<TestTeam, User> rewardsGUI = new RewardsGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = rewardsGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void RewardsGUI__OnInventoryClick__RedeemReward() {
        TestTeam testTeam = new TeamBuilder().withReward(new Reward(
                new Item(XMaterial.DIAMOND, 1, "&c&lReward", Collections.emptyList()), Collections.emptyList(),
                1000, new ImmutableMap.Builder<String, Double>().put("money", 10000.00).build(), 1000,1, XSound.ENTITY_PLAYER_LEVELUP
        )).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        RewardsGUI<TestTeam, User> rewardsGUI = new RewardsGUI<>(testTeam, null, TestPlugin.getInstance());
        playerMock.openInventory(rewardsGUI.getInventory());

        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.LEFT, 0);

        assertTrue(inventoryClickEvent.isCancelled());
        assertEquals(0, TestPlugin.getInstance().getTeamManager().getTeamRewards(testTeam).size());
        playerMock.assertSoundHeard(Sound.ENTITY_PLAYER_LEVELUP);
        assertEquals(1000, PlayerUtils.getTotalExperience(playerMock));
        assertEquals(1000, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
        assertEquals(10000, TestPlugin.getInstance().getTeamManager().getTeamBank(testTeam, "money").getNumber());
        assertEquals(1, testTeam.getExperience());
    }

    @Test
    public void RewardsGUI__OnInventoryClick__RedeemAllRewards() {
        TestTeam testTeam = new TeamBuilder().withReward(
                new Reward(
                        new Item(XMaterial.DIAMOND, 1, "&c&lReward", Collections.emptyList()), Collections.emptyList(),
                        1000, new ImmutableMap.Builder<String, Double>().put("money", 10000.00).build(), 1000,1, XSound.ENTITY_PLAYER_LEVELUP
                ),
                new Reward(
                        new Item(XMaterial.DIAMOND, 1, "&c&lReward", Collections.emptyList()), Collections.emptyList(),
                        1000, new ImmutableMap.Builder<String, Double>().put("money", 10000.00).build(), 1000,1, XSound.ENTITY_PLAYER_LEVELUP
                )
        ).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        RewardsGUI<TestTeam, User> rewardsGUI = new RewardsGUI<>(testTeam, null, TestPlugin.getInstance());
        playerMock.openInventory(rewardsGUI.getInventory());

        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.LEFT, 53);

        assertTrue(inventoryClickEvent.isCancelled());
        assertEquals(0, TestPlugin.getInstance().getTeamManager().getTeamRewards(testTeam).size());
        playerMock.assertSoundHeard(Sound.ENTITY_PLAYER_LEVELUP);
        assertEquals(2000, PlayerUtils.getTotalExperience(playerMock));
        assertEquals(2000, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
        assertEquals(20000, TestPlugin.getInstance().getTeamManager().getTeamBank(testTeam, "money").getNumber());
        assertEquals(2, testTeam.getExperience());
    }

}