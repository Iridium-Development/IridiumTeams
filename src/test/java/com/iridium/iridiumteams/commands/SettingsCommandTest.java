package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.gui.SettingsGUI;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SettingsCommandTest {

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
    public void executeSettingsCommand__InvalidSyntax() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test settings invalid");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().settingsCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSettingsCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test settings");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSettingsCommand__OpensGUI() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test settings");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof SettingsGUI<?, ?>);
    }

    @Test
    public void executeSettingsCommand__NoPermission() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test settings time day");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotChangeSettings
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSettingsCommand__UnknownSetting() {
        TestTeam testTeam = new TeamBuilder().withPermission(1, PermissionType.SETTINGS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test settings unknown Enabled");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().invalidSetting
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSettingsCommand__UnknownSettingValue() {
        TestTeam testTeam = new TeamBuilder().withPermission(1, PermissionType.SETTINGS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test settings weather unknown");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().invalidSettingValue
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSettingsCommand__SettingSet() {
        TestTeam testTeam = new TeamBuilder().withPermission(1, PermissionType.SETTINGS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test settings time day");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().settingSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%setting%", "Time")
                .replace("%value%", "Day")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals("Day", TestPlugin.getInstance().getTeamManager().getTeamSetting(testTeam, SettingType.TIME.getSettingKey()).getValue());
    }

    @Test
    public void settingsCommand__TabComplete() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Arrays.asList("CropTrample", "EntityGrief", "FireSpread", "IceForm", "JoinType", "LeafDecay", "MobSpawning", "Time", "TnTDamage", "ValueVisibility", "Visiting", "Weather"), serverMock.getCommandTabComplete(playerMock, "test settings "));

        assertEquals(Arrays.asList("Disabled", "Enabled"), serverMock.getCommandTabComplete(playerMock, "test settings IceForm "));

        assertEquals(Arrays.asList("Raining", "Server", "Sunny"), serverMock.getCommandTabComplete(playerMock, "test settings Weather "));

        assertEquals(Arrays.asList("Day", "Midnight", "Morning", "Night", "Noon", "Server", "Sunrise", "Sunset"), serverMock.getCommandTabComplete(playerMock, "test settings time "));

        assertEquals(Arrays.asList("Private", "Public"), serverMock.getCommandTabComplete(playerMock, "test settings JoinType "));

        assertEquals(Collections.emptyList(), serverMock.getCommandTabComplete(playerMock, "test settings invalid "));
    }
}