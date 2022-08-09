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

class InvitesGUITest {

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
            User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).build());
            TestPlugin.getInstance().getTeamManager().createTeamInvite(testTeam, user, user);
        }
        InvitesGUI<TestTeam, User> invitesGUI = new InvitesGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = invitesGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(),"Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void InvitesGUI__Click() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).build());
        TestPlugin.getInstance().getTeamManager().createTeamInvite(testTeam, user, user);

        InvitesGUI<TestTeam, User> invitesGUI = new InvitesGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(invitesGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(0);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamInviteRevoked
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", user.getName())
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(TestPlugin.getInstance().getTeamManager().getTeamInvite(testTeam, user).isPresent());
    }
}