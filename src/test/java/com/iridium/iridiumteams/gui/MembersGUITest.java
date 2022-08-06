package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.Rank;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MembersGUITest {

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
    public void membersGUIIsEmpty() {
        TestTeam testTeam = new TeamBuilder().build();
        MembersGUI<TestTeam, User> membersGUI = new MembersGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = membersGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(Material.BLACK_STAINED_GLASS_PANE, inventory.getContents()[i].getType());
        }
    }

    @Test
    public void membersGUIHasFivePlayers() {
        TestTeam testTeam = new TeamBuilder().withMembers(5, serverMock).build();
        MembersGUI<TestTeam, User> membersGUI = new MembersGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = membersGUI.getInventory();
        for (int i = 0; i < 5; i++) {
            assertEquals(Material.PLAYER_HEAD, inventory.getContents()[i].getType());
        }
        for (int i = 5; i < inventory.getSize(); i++) {
            assertEquals(Material.BLACK_STAINED_GLASS_PANE, inventory.getContents()[i].getType());
        }
    }

    @Test
    public void membersGUIInventoryClickDemote() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).withRank(Rank.OWNER.getId()).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).withTeam(testTeam).withRank(2).build());
        TestPlugin.getInstance().getTeamManager().createTeamInvite(testTeam, user, user);

        MembersGUI<TestTeam, User> membersGUI = new MembersGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(membersGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, 1, ClickType.LEFT, InventoryAction.UNKNOWN);
        serverMock.getPluginManager().callEvent(inventoryClickEvent);
        assertTrue(inventoryClickEvent.isCancelled());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().demotedPlayer.replace("%player%", user.getName()).replace("%rank%", "Member").replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
        assertEquals(1, user.getUserRank());
    }

    @Test
    public void membersGUIInventoryClickKick() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).withRank(Rank.OWNER.getId()).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).withTeam(testTeam).withRank(1).build());
        TestPlugin.getInstance().getTeamManager().createTeamInvite(testTeam, user, user);

        MembersGUI<TestTeam, User> membersGUI = new MembersGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(membersGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, 1, ClickType.LEFT, InventoryAction.UNKNOWN);
        serverMock.getPluginManager().callEvent(inventoryClickEvent);
        assertTrue(inventoryClickEvent.isCancelled());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().playerKicked.replace("%player%", user.getName()).replace("%kicker%", playerMock.getName()).replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
        assertEquals(0, user.getTeamID());
    }

    @Test
    public void membersGUIInventoryClickPromote() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).withRank(Rank.OWNER.getId()).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).withTeam(testTeam).withRank(2).build());
        TestPlugin.getInstance().getTeamManager().createTeamInvite(testTeam, user, user);

        MembersGUI<TestTeam, User> membersGUI = new MembersGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(membersGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(playerMock.getOpenInventory(), InventoryType.SlotType.CONTAINER, 1, ClickType.RIGHT, InventoryAction.UNKNOWN);
        serverMock.getPluginManager().callEvent(inventoryClickEvent);
        assertTrue(inventoryClickEvent.isCancelled());

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().promotedPlayer.replace("%player%", user.getName()).replace("%rank%", "CoOwner").replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
        assertEquals(3, user.getUserRank());
    }
}