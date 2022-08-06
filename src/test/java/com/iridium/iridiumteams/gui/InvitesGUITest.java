package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
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

import static org.junit.jupiter.api.Assertions.*;

class InvitesGUITest {

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
    public void invitesGUIIsEmpty() {
        TestTeam testTeam = new TeamBuilder().build();
        InvitesGUI<TestTeam, User> invitesGUI = new InvitesGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = invitesGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(Material.BLACK_STAINED_GLASS_PANE, inventory.getContents()[i].getType());
        }
    }

    @Test
    public void invitesGUIHasFivePlayers() {
        TestTeam testTeam = new TeamBuilder().build();
        for (int i = 0; i < 5; i++) {
            User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).build());
            TestPlugin.getInstance().getTeamManager().createTeamInvite(testTeam, user, user);
        }
        InvitesGUI<TestTeam, User> invitesGUI = new InvitesGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = invitesGUI.getInventory();
        for (int i = 0; i < 5; i++) {
            assertEquals(Material.PLAYER_HEAD, inventory.getContents()[i].getType());
        }
        for (int i = 5; i < inventory.getSize(); i++) {
            assertEquals(Material.BLACK_STAINED_GLASS_PANE, inventory.getContents()[i].getType());
        }
    }

    @Test
    public void invitesGUIInventoryClick() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).build());
        TestPlugin.getInstance().getTeamManager().createTeamInvite(testTeam, user, user);

        InvitesGUI<TestTeam, User> invitesGUI = new InvitesGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(invitesGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, 0, ClickType.LEFT, InventoryAction.UNKNOWN);
        serverMock.getPluginManager().callEvent(inventoryClickEvent);
        assertTrue(inventoryClickEvent.isCancelled());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamInviteRevoked
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", user.getName())
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(TestPlugin.getInstance().getTeamManager().getTeamInvite(testTeam, user).isPresent());
    }
}