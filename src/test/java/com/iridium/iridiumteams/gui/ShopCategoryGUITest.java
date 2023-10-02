package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.configs.Shop;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopCategoryGUITest {
    private final Map<Integer, Material> inventoryPage1Layout = new ImmutableMap.Builder<Integer, Material>()
            .put(12, Material.BUCKET)
            .put(13, Material.WATER_BUCKET)
            .put(14, Material.LAVA_BUCKET)
            .put(21, Material.NAME_TAG)
            .put(22, Material.SADDLE)
            .put(23, Material.END_PORTAL_FRAME)
            .put(29, Material.RED_STAINED_GLASS_PANE)
            .put(33, Material.LIME_STAINED_GLASS_PANE)
            .build();
    private final Map<Integer, Material> inventoryPage2Layout = new ImmutableMap.Builder<Integer, Material>()
            .put(12, Material.DIAMOND)
            .put(29, Material.RED_STAINED_GLASS_PANE)
            .put(33, Material.LIME_STAINED_GLASS_PANE)
            .build();

    private ServerMock serverMock;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);

        TestPlugin.getInstance().getShop().items = ImmutableMap.<String, List<Shop.ShopItem>>builder()
                .put("Miscellaneous", Arrays.asList(
                                new Shop.ShopItem(
                                        "&9&lBucket",
                                        XMaterial.BUCKET,
                                        1,
                                        12,
                                        new Shop.Cost(100, new HashMap<>()),
                                        new Shop.Cost(10, new HashMap<>())
                                ),
                                new Shop.ShopItem(
                                        "&9&lWater Bucket",
                                        XMaterial.WATER_BUCKET,
                                        1,
                                        13,
                                        new Shop.Cost(200, new HashMap<>()),
                                        new Shop.Cost(10, new HashMap<>())
                                ),
                                new Shop.ShopItem(
                                        "&9&lLava Bucket",
                                        XMaterial.LAVA_BUCKET,
                                        1,
                                        14,
                                        new Shop.Cost(200, new HashMap<>()),
                                        new Shop.Cost(20, new HashMap<>())
                                ),
                                new Shop.ShopItem(
                                        "&9&lName Tag",
                                        XMaterial.NAME_TAG,
                                        1,
                                        21,
                                        new Shop.Cost(200, new HashMap<>()),
                                        new Shop.Cost(30, new HashMap<>())
                                ),
                                new Shop.ShopItem(
                                        "&9&lSaddle",
                                        XMaterial.SADDLE,
                                        1,
                                        22,
                                        new Shop.Cost(300, new HashMap<>()),
                                        new Shop.Cost(30, new HashMap<>())
                                ),
                                new Shop.ShopItem(
                                        "&9&lEnd Portal Frame",
                                        XMaterial.END_PORTAL_FRAME,
                                        1,
                                        23,
                                        new Shop.Cost(5000, new HashMap<>()),
                                        new Shop.Cost(0, new HashMap<>())
                                ),
                                new Shop.ShopItem(
                                        "&9&lDiamond",
                                        XMaterial.DIAMOND,
                                        1,
                                        12,
                                        2,
                                        new Shop.Cost(100, new HashMap<>()),
                                        new Shop.Cost(10, new HashMap<>())
                                )
                        )
                )
                .build();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void ShopCategoryGUI_ShouldDisplayItems_OnPage1() {
        ShopCategoryGUI<TestTeam, User> shopCategoryGUI = new ShopCategoryGUI<>("Miscellaneous", null, 1, TestPlugin.getInstance());
        Inventory inventory = shopCategoryGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryPage1Layout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void ShopCategoryGUI_ShouldDisplayItems_OnPage2() {
        ShopCategoryGUI<TestTeam, User> shopCategoryGUI = new ShopCategoryGUI<>("Miscellaneous", null, 2, TestPlugin.getInstance());
        Inventory inventory = shopCategoryGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryPage2Layout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void ShopCategoryGUI_ShouldGoToNextPage_WhenNextPageAvailable() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        ShopCategoryGUI<TestTeam, User> shopCategoryGUI = new ShopCategoryGUI<>("Miscellaneous", null, 1, TestPlugin.getInstance());

        playerMock.openInventory(shopCategoryGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(33);

        assertTrue(inventoryClickEvent.isCancelled());
        assertEquals(2, shopCategoryGUI.getPage());
        for (int i = 0; i < playerMock.getOpenInventory().getTopInventory().getSize(); i++) {
            assertEquals(inventoryPage2Layout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), playerMock.getOpenInventory().getTopInventory().getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void ShopCategoryGUI_ShouldStayOnSamePage_WhenNoNextPageAvailable() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        ShopCategoryGUI<TestTeam, User> shopCategoryGUI = new ShopCategoryGUI<>("Miscellaneous", null, 2, TestPlugin.getInstance());

        playerMock.openInventory(shopCategoryGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(33);

        assertTrue(inventoryClickEvent.isCancelled());
        assertEquals(2, shopCategoryGUI.getPage());
        for (int i = 0; i < playerMock.getOpenInventory().getTopInventory().getSize(); i++) {
            assertEquals(inventoryPage2Layout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), playerMock.getOpenInventory().getTopInventory().getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void ShopCategoryGUI_ShouldGoToPreviousPage_WhenPreviousPageAvailable() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        ShopCategoryGUI<TestTeam, User> shopCategoryGUI = new ShopCategoryGUI<>("Miscellaneous", null, 2, TestPlugin.getInstance());

        playerMock.openInventory(shopCategoryGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(29);

        assertTrue(inventoryClickEvent.isCancelled());
        assertEquals(1, shopCategoryGUI.getPage());
        for (int i = 0; i < playerMock.getOpenInventory().getTopInventory().getSize(); i++) {
            assertEquals(inventoryPage1Layout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), playerMock.getOpenInventory().getTopInventory().getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void ShopCategoryGUI_ShouldStayOnSamePage_WhenNoPreviousPageAvailable() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        ShopCategoryGUI<TestTeam, User> shopCategoryGUI = new ShopCategoryGUI<>("Miscellaneous", null, 1, TestPlugin.getInstance());

        playerMock.openInventory(shopCategoryGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(29);

        assertTrue(inventoryClickEvent.isCancelled());
        assertEquals(1, shopCategoryGUI.getPage());
        for (int i = 0; i < playerMock.getOpenInventory().getTopInventory().getSize(); i++) {
            assertEquals(inventoryPage1Layout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), playerMock.getOpenInventory().getTopInventory().getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void ShopCategoryGUI_ShouldBuyItem_OnSamePage() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        TestPlugin.getInstance().getEconomy().depositPlayer(playerMock, 10000);
        ShopCategoryGUI<TestTeam, User> shopCategoryGUI = new ShopCategoryGUI<>("Miscellaneous", null, 1, TestPlugin.getInstance());

        playerMock.openInventory(shopCategoryGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(12);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSoundHeard(Sound.ENTITY_PLAYER_LEVELUP);
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().successfullyBought
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "1")
                .replace("%item%", StringUtils.color("&9&lBucket"))
                .replace("%vault_cost%", "100")
        ));
        playerMock.assertNoMoreSaid();

        assertEquals(1, InventoryUtils.getAmount(playerMock.getInventory(), XMaterial.BUCKET));
        assertEquals(0, InventoryUtils.getAmount(playerMock.getInventory(), XMaterial.DIAMOND));
        assertEquals(9900, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
    }

    @Test
    public void ShopCategoryGUI_ShouldNotBuyItem_WhenOnDifferentPage() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        TestPlugin.getInstance().getEconomy().depositPlayer(playerMock, 10000);
        ShopCategoryGUI<TestTeam, User> shopCategoryGUI = new ShopCategoryGUI<>("Miscellaneous", null, 2, TestPlugin.getInstance());

        playerMock.openInventory(shopCategoryGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(13);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertNoMoreSaid();

        assertEquals(10000, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
    }

    @Test
    public void ShopCategoryGUI_ShouldBuyItem_OnShiftLeftClick() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        TestPlugin.getInstance().getEconomy().depositPlayer(playerMock, 10000);
        ShopCategoryGUI<TestTeam, User> shopCategoryGUI = new ShopCategoryGUI<>("Miscellaneous", null, 1,
                TestPlugin.getInstance());

        playerMock.openInventory(shopCategoryGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(),
                ClickType.SHIFT_LEFT, 12);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSoundHeard(Sound.ENTITY_PLAYER_LEVELUP);
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().successfullyBought
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "16")
                .replace("%item%", StringUtils.color("&9&lBucket"))
                .replace("%vault_cost%", "1.6K")));
        playerMock.assertNoMoreSaid();

        assertEquals(16, InventoryUtils.getAmount(playerMock.getInventory(), XMaterial.BUCKET));
        assertEquals(0, InventoryUtils.getAmount(playerMock.getInventory(), XMaterial.DIAMOND));
        assertEquals(8400, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
    }
}