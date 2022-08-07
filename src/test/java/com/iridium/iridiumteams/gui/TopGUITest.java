package com.iridium.iridiumteams.gui;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.sorting.ExperienceTeamSort;
import com.iridium.iridiumteams.sorting.ValueTeamSort;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TopGUITest {

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
    public void topGUI__SortValue() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TopGUI<TestTeam, User> topGUI = new TopGUI<>(TestPlugin.getInstance().getTop().experienceTeamSort, null, TestPlugin.getInstance());
        playerMock.openInventory(topGUI.getInventory());
        playerMock.simulateInventoryClick(TestPlugin.getInstance().getTop().valueTeamSort.item.slot);
        assertTrue(topGUI.getSortingType() instanceof ValueTeamSort<TestTeam>);
    }

    @Test
    public void topGUI__SortExperience() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TopGUI<TestTeam, User> topGUI = new TopGUI<>(TestPlugin.getInstance().getTop().valueTeamSort, null, TestPlugin.getInstance());
        playerMock.openInventory(topGUI.getInventory());
        playerMock.simulateInventoryClick(TestPlugin.getInstance().getTop().experienceTeamSort.item.slot);
        assertTrue(topGUI.getSortingType() instanceof ExperienceTeamSort<TestTeam>);
    }

    @Test
    public void topGUI__PreviousPage__Error() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TopGUI<TestTeam, User> topGUI = new TopGUI<>(TestPlugin.getInstance().getTop().experienceTeamSort, null, TestPlugin.getInstance());
        playerMock.openInventory(topGUI.getInventory());
        playerMock.simulateInventoryClick(TestPlugin.getInstance().getInventories().topGUI.size - 7);
        assertEquals(1, topGUI.getPage());
    }

    @Test
    public void topGUI__PreviousPage__Success() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TopGUI<TestTeam, User> topGUI = new TopGUI<>(TestPlugin.getInstance().getTop().experienceTeamSort, null, TestPlugin.getInstance());
        topGUI.setPage(2);
        playerMock.openInventory(topGUI.getInventory());
        playerMock.simulateInventoryClick(TestPlugin.getInstance().getInventories().topGUI.size - 7);
        assertEquals(1, topGUI.getPage());
    }

    @Test
    public void topGUI__NextPage__Error() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TopGUI<TestTeam, User> topGUI = new TopGUI<>(TestPlugin.getInstance().getTop().experienceTeamSort, null, TestPlugin.getInstance());
        playerMock.openInventory(topGUI.getInventory());
        playerMock.simulateInventoryClick(TestPlugin.getInstance().getInventories().topGUI.size - 3);
        assertEquals(1, topGUI.getPage());
    }

    @Test
    public void topGUI__NextPage__Success() {
        for (int i = 0; i < 10; i++) {
            new TeamBuilder().build();
        }
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TopGUI<TestTeam, User> topGUI = new TopGUI<>(TestPlugin.getInstance().getTop().experienceTeamSort, null, TestPlugin.getInstance());
        playerMock.openInventory(topGUI.getInventory());
        playerMock.simulateInventoryClick(TestPlugin.getInstance().getInventories().topGUI.size - 3);
        assertEquals( 2, topGUI.getPage());
    }
}