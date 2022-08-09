package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
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

class MembersGUITest {

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
    public void MembersGUI__HasItems() {
        TestTeam testTeam = new TeamBuilder().withMembers(5, serverMock).build();
        MembersGUI<TestTeam, User> membersGUI = new MembersGUI<>(testTeam, null, TestPlugin.getInstance());
        Inventory inventory = membersGUI.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            assertEquals(inventoryLayout.getOrDefault(i, Material.BLACK_STAINED_GLASS_PANE), inventory.getContents()[i].getType(),"Item on slot " + i + " not as expected");
        }
    }

    @Test
    public void MembersGUI__LeftClick_Demote() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).withRank(Rank.OWNER.getId()).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).withTeam(testTeam).withRank(2).build());

        MembersGUI<TestTeam, User> membersGUI = new MembersGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(membersGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.LEFT, 1);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().demotedPlayer
                .replace("%player%", user.getName())
                .replace("%rank%", "Member")
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(1, user.getUserRank());
    }

    @Test
    public void MembersGUI__LeftClick_Kick() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).withRank(Rank.OWNER.getId()).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).withTeam(testTeam).withRank(1).build());

        MembersGUI<TestTeam, User> membersGUI = new MembersGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(membersGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.LEFT, 1);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().playerKicked
                .replace("%player%", user.getName())
                .replace("%kicker%", playerMock.getName())
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(0, user.getTeamID());
    }

    @Test
    public void MembersGUI__RightClick() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).withRank(Rank.OWNER.getId()).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(new UserBuilder(serverMock).withTeam(testTeam).withRank(2).build());

        MembersGUI<TestTeam, User> membersGUI = new MembersGUI<>(testTeam, null, TestPlugin.getInstance());

        playerMock.openInventory(membersGUI.getInventory());
        InventoryClickEvent inventoryClickEvent = playerMock.simulateInventoryClick(playerMock.getOpenInventory(), ClickType.RIGHT, 1);

        assertTrue(inventoryClickEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().promotedPlayer
                .replace("%player%", user.getName())
                .replace("%rank%", "CoOwner")
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(3, user.getUserRank());
    }
}