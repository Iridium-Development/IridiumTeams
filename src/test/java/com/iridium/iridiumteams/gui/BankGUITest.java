package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.utils.PlayerUtils;
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

class BankGUITest {

    private final Map<Integer, Material> inventoryLayout = new ImmutableMap.Builder<Integer, Material>()
            .put(9, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(10, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(11, Material.PAPER)
            .put(12, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(13, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(14, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
            .put(15, Material.EXPERIENCE_BOTTLE)
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
    public void BankGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().build();
        BankGUI<TestTeam, User> bankGUI = new BankGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = bankGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(),"Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void BankGUI__LeftClick() {
        TestTeam testTeam = new TeamBuilder().withBank("experience", 150).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        BankGUI<TestTeam, User> bankGUI = new BankGUI<>(testTeam, null, TestPlugin.getInstance());
        playerMock.openInventory(bankGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.LEFT, 15);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().bankWithdrew
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "100.0")
                .replace("%type%", "experience")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(50, TestPlugin.getInstance().getTeamManager().getTeamBank(testTeam, "experience").getNumber());
        assertEquals(100, PlayerUtils.getTotalExperience(playerMock));
    }

    @Test
    public void BankGUI__ShiftLeftClick() {
        TestTeam testTeam = new TeamBuilder().withBank("experience", 150).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        BankGUI<TestTeam, User> bankGUI = new BankGUI<>(testTeam, null, TestPlugin.getInstance());
        playerMock.openInventory(bankGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.SHIFT_LEFT, 15);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().bankWithdrew
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "150.0")
                .replace("%type%", "experience")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(0, TestPlugin.getInstance().getTeamManager().getTeamBank(testTeam, "experience").getNumber());
        assertEquals(150, PlayerUtils.getTotalExperience(playerMock));
    }

    @Test
    public void BankGUI__RightClick() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withExperience(150).withTeam(testTeam).build();

        BankGUI<TestTeam, User> bankGUI = new BankGUI<>(testTeam, null, TestPlugin.getInstance());
        playerMock.openInventory(bankGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.RIGHT, 15);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().bankDeposited
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "100.0")
                .replace("%type%", "experience")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(100, TestPlugin.getInstance().getTeamManager().getTeamBank(testTeam, "experience").getNumber());
        assertEquals(50, PlayerUtils.getTotalExperience(playerMock));
    }

    @Test
    public void BankGUI__ShiftRightClick() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withExperience(150).withTeam(testTeam).build();

        BankGUI<TestTeam, User> bankGUI = new BankGUI<>(testTeam, null, TestPlugin.getInstance());
        playerMock.openInventory(bankGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.SHIFT_RIGHT, 15);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().bankDeposited
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "150.0")
                .replace("%type%", "experience")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(150, TestPlugin.getInstance().getTeamManager().getTeamBank(testTeam, "experience").getNumber());
        assertEquals(0, PlayerUtils.getTotalExperience(playerMock));
    }
}