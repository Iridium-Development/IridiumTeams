package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.configs.inventories.MissionTypeSelectorInventoryConfig;
import com.iridium.iridiumteams.gui.MissionGUI;
import com.iridium.iridiumteams.gui.MissionTypeSelectorGUI;
import com.iridium.iridiumteams.missions.MissionType;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MissionsCommandTest {

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
    public void executeMissionsCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test missions");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeMissionsCommand__Daily__Success() {
        TestPlugin.getInstance().getInventories().missionTypeSelectorGUI.daily.enabled = true;
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test missions Daily");

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof MissionGUI<?, ?>);
        MissionGUI<?, ?> missionGUI = (MissionGUI<?, ?>) playerMock.getOpenInventory().getTopInventory().getHolder();
        assertEquals(MissionType.DAILY, missionGUI.getMissionType());
    }

    @Test
    public void executeMissionsCommand__Daily__Disabled() {
        TestPlugin.getInstance().getInventories().missionTypeSelectorGUI.daily.enabled = false;
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test missions Daily");

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof MissionTypeSelectorGUI<?, ?>);
    }

    @Test
    public void executeMissionsCommand__Weekly__Success() {
        TestPlugin.getInstance().getInventories().missionTypeSelectorGUI.weekly.enabled = true;
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test missions Weekly");

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof MissionGUI<?, ?>);
        MissionGUI<?, ?> missionGUI = (MissionGUI<?, ?>) playerMock.getOpenInventory().getTopInventory().getHolder();
        assertEquals(MissionType.WEEKLY, missionGUI.getMissionType());
    }

    @Test
    public void executeMissionsCommand__Weekly__Disabled() {
        MissionTypeSelectorInventoryConfig missionTypeSelectorInventoryConfig = TestPlugin.getInstance().getInventories().missionTypeSelectorGUI;
        missionTypeSelectorInventoryConfig.weekly.enabled = false;
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test missions Weekly");

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof MissionTypeSelectorGUI<?, ?>);
    }

    @Test
    public void executeMissionsCommand__Infinite__Success() {
        TestPlugin.getInstance().getInventories().missionTypeSelectorGUI.infinite.enabled = true;
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test missions Infinite");

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof MissionGUI<?, ?>);
        MissionGUI<?, ?> missionGUI = (MissionGUI<?, ?>) playerMock.getOpenInventory().getTopInventory().getHolder();
        assertEquals(MissionType.INFINITE, missionGUI.getMissionType());
    }

    @Test
    public void executeMissionsCommand__Infinite__Disabled() {
        TestPlugin.getInstance().getInventories().missionTypeSelectorGUI.infinite.enabled = false;
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test missions Infinite");

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof MissionTypeSelectorGUI<?, ?>);
    }

    @Test
    public void executeMissionsCommand__Once__Success() {
        TestPlugin.getInstance().getInventories().missionTypeSelectorGUI.once.enabled = true;
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test missions Once");

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof MissionGUI<?, ?>);
        MissionGUI<?, ?> missionGUI = (MissionGUI<?, ?>) playerMock.getOpenInventory().getTopInventory().getHolder();
        assertEquals(MissionType.ONCE, missionGUI.getMissionType());
    }

    @Test
    public void executeMissionsCommand__Once__Disabled() {
        TestPlugin.getInstance().getInventories().missionTypeSelectorGUI.once.enabled = false;
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test missions Once");

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof MissionTypeSelectorGUI<?, ?>);
    }

    @Test
    public void executeMissionsCommand__Success() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test missions");

        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof MissionTypeSelectorGUI<?, ?>);
    }

    @Test
    public void tabCompleteMissionsCommand() {
        TestPlugin.getInstance().getInventories().missionTypeSelectorGUI.infinite.enabled = true;
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(Arrays.asList("Daily", "Infinite", "Once", "Weekly"), serverMock.getCommandTabComplete(playerMock, "test missions "));
    }
}