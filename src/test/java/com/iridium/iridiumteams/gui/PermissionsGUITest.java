package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.*;
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

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PermissionsGUITest {

    private ServerMock serverMock;

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(10, Material.DIAMOND_PICKAXE)
            .put(11, Material.COBBLESTONE)
            .put(12, Material.BUCKET)
            .put(13, Material.SUNFLOWER)
            .put(14, Material.IRON_AXE)
            .put(15, Material.PLAYER_HEAD)
            .put(16, Material.WRITABLE_BOOK)
            .put(19, Material.OAK_DOOR)
            .put(20, Material.DIAMOND)
            .put(21, Material.IRON_BOOTS)
            .put(22, Material.DIAMOND_SWORD)
            .put(23, Material.CHEST)
            .put(24, Material.PLAYER_HEAD)
            .put(25, Material.REDSTONE)
            .put(28, Material.PAPER)
            .put(29, Material.WHITE_BED)
            .put(30, Material.SPAWNER)
            .put(31, Material.GRASS_BLOCK)
            .put(32, Material.END_PORTAL_FRAME)
            //Next and Back Button
            .put(51, Material.LIME_STAINED_GLASS_PANE)
            .put(47, Material.RED_STAINED_GLASS_PANE)
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
    public void permissionsGUIHasPermissions() {
        TestTeam testTeam = new TeamBuilder().build();
        PermissionsGUI<TestTeam, User> permissionsGUI = new PermissionsGUI<>(testTeam, 1, TestPlugin.getInstance());
        Inventory inventory = permissionsGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            Material correctType = inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE);
            assertEquals(correctType, inventory.getContents()[i].getType(), "Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void permissionsGUINextPageError() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TestTeam testTeam = new TeamBuilder().build();
        PermissionsGUI<TestTeam, User> permissionsGUI = new PermissionsGUI<>(testTeam, 1, TestPlugin.getInstance());
        playerMock.openInventory(permissionsGUI.getInventory());

        permissionsGUI.onInventoryClick(new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, TestPlugin.getInstance().getInventories().permissionsGUI.size - 3, ClickType.LEFT, InventoryAction.UNKNOWN));
        assertEquals(1, permissionsGUI.getPage());
    }

    @Test
    public void permissionsGUINextPageSuccess() {
        TestPlugin.getInstance().addPermission("test", new Permission(new Item(XMaterial.STONE, 1, 1, "", Collections.emptyList()), 2, 1));
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TestTeam testTeam = new TeamBuilder().build();
        PermissionsGUI<TestTeam, User> permissionsGUI = new PermissionsGUI<>(testTeam, 1, TestPlugin.getInstance());
        playerMock.openInventory(permissionsGUI.getInventory());

        permissionsGUI.onInventoryClick(new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, TestPlugin.getInstance().getInventories().permissionsGUI.size - 3, ClickType.LEFT, InventoryAction.UNKNOWN));
        assertEquals(2, permissionsGUI.getPage());
    }

    @Test
    public void permissionsGUIPreviousPageError() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TestTeam testTeam = new TeamBuilder().build();
        PermissionsGUI<TestTeam, User> permissionsGUI = new PermissionsGUI<>(testTeam, 1, TestPlugin.getInstance());
        playerMock.openInventory(permissionsGUI.getInventory());

        permissionsGUI.onInventoryClick(new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, TestPlugin.getInstance().getInventories().permissionsGUI.size - 7, ClickType.LEFT, InventoryAction.UNKNOWN));
        assertEquals(1, permissionsGUI.getPage());
    }

    @Test
    public void permissionsGUIPreviousPageSuccess() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TestTeam testTeam = new TeamBuilder().build();
        PermissionsGUI<TestTeam, User> permissionsGUI = new PermissionsGUI<>(testTeam, 1, 2, TestPlugin.getInstance());
        playerMock.openInventory(permissionsGUI.getInventory());

        permissionsGUI.onInventoryClick(new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, TestPlugin.getInstance().getInventories().permissionsGUI.size - 7, ClickType.LEFT, InventoryAction.UNKNOWN));
        assertEquals(1, permissionsGUI.getPage());
    }

    @Test
    public void permissionsGUICannotChangePermissions() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        PermissionsGUI<TestTeam, User> permissionsGUI = new PermissionsGUI<>(testTeam, 1, TestPlugin.getInstance());
        playerMock.openInventory(permissionsGUI.getInventory());

        permissionsGUI.onInventoryClick(new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, TestPlugin.getInstance().getPermissions().blockBreak.getItem().slot, ClickType.LEFT, InventoryAction.UNKNOWN));
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotChangePermissions
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void permissionsGUISuccessOwner() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_BREAK, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(Rank.OWNER.getId()).build();

        PermissionsGUI<TestTeam, User> permissionsGUI = new PermissionsGUI<>(team, 1, TestPlugin.getInstance());
        playerMock.openInventory(permissionsGUI.getInventory());

        permissionsGUI.onInventoryClick(new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, TestPlugin.getInstance().getPermissions().blockBreak.getItem().slot, ClickType.LEFT, InventoryAction.UNKNOWN));


        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().permissionSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%permission%", "blockBreak")
                .replace("%rank%", "Member")
                .replace("%allowed%", "true")
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamPermission(team, 1, "blockBreak"));
    }

    @Test
    public void permissionsGUISuccess() {
        TestTeam testTeam = new TeamBuilder().withPermission(1, PermissionType.BLOCK_BREAK, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).setBypassing().withTeam(testTeam).withRank(2).build();

        PermissionsGUI<TestTeam, User> permissionsGUI = new PermissionsGUI<>(testTeam, 1, TestPlugin.getInstance());
        playerMock.openInventory(permissionsGUI.getInventory());

        permissionsGUI.onInventoryClick(new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, TestPlugin.getInstance().getPermissions().blockBreak.getItem().slot, ClickType.LEFT, InventoryAction.UNKNOWN));

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().permissionSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%permission%", "blockBreak")
                .replace("%rank%", "Member")
                .replace("%allowed%", "true")
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamPermission(testTeam, 1, "blockBreak"));
    }
}