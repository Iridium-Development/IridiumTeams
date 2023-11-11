package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.enhancements.EnhancementAffectsType;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import com.iridium.testplugin.managers.TeamManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlyCommandTest {

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
    public void executeFlyCommand_ShouldSendMessage_WhenWeDontHaveATeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test fly");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeFlyCommand_ShouldNotWork_WhenTheBoosterIsNotActive() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test fly");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightNotActive
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeFlyCommand_ShouldMakePlayerFly_WhenTheyHaveTheOverridePermission() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withPermission("iridiumteams.fly").build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test fly on");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightEnabled
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(user.isFlying());
        assertTrue(playerMock.isFlying());
        assertTrue(playerMock.getAllowFlight());
    }

    @Test
    public void executeFlyCommand_ShouldSendMessage_WhenWeProvideInvalidSyntax() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test fly gia");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().flyCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeFlyCommand_ShouldToggleFlight_WhenWeAreBypassing() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).setBypassing().build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test fly");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightEnabled
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(user.isFlying());
        assertTrue(playerMock.isFlying());
        assertTrue(playerMock.getAllowFlight());

        serverMock.dispatchCommand(playerMock, "test fly");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightDisabled
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(user.isFlying());
        assertFalse(playerMock.isFlying());
        assertFalse(playerMock.getAllowFlight());
    }

    @Test
    public void executeFlyCommand_ShouldEnableFlight_WhenWeAreBypassing() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).setBypassing().build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test fly on");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightEnabled
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(user.isFlying());
        assertTrue(playerMock.isFlying());
        assertTrue(playerMock.getAllowFlight());
    }

    @Test
    public void executeFlyCommand_ShouldDisableFlight_WhenWeAreBypassing() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).setBypassing().build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test fly off");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightDisabled
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(user.isFlying());
        assertFalse(playerMock.isFlying());
        assertFalse(playerMock.getAllowFlight());
    }

    @Test
    public void executeFlyCommand_ShouldToggleFlight_WhenWeHaveTheFlightBoosterActive() {
        LocalDateTime currentExpiration = LocalDateTime.now().plusMinutes(1);
        TestTeam team = new TeamBuilder().withEnhancement("flight", 2, currentExpiration).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test fly");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightEnabled
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(user.isFlying());
        assertTrue(playerMock.isFlying());
        assertTrue(playerMock.getAllowFlight());

        serverMock.dispatchCommand(playerMock, "test fly");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightDisabled
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(user.isFlying());
        assertFalse(playerMock.isFlying());
        assertFalse(playerMock.getAllowFlight());
    }

    @Test
    public void executeFlyCommand_ShouldEnableFlight_WhenWeHaveTheFlightBoosterActive() {
        LocalDateTime currentExpiration = LocalDateTime.now().plusMinutes(1);
        TestTeam team = new TeamBuilder().withEnhancement("flight", 2, currentExpiration).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test fly on");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightEnabled
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(user.isFlying());
        assertTrue(playerMock.isFlying());
        assertTrue(playerMock.getAllowFlight());
    }

    @Test
    public void executeFlyCommand_ShouldDisableFlight_WhenWeHaveTheFlightBoosterActive() {
        LocalDateTime currentExpiration = LocalDateTime.now().plusMinutes(1);
        TestTeam team = new TeamBuilder().withEnhancement("flight", 2, currentExpiration).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test fly off");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().flightDisabled
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(user.isFlying());
        assertFalse(playerMock.isFlying());
        assertFalse(playerMock.getAllowFlight());
    }

    @Test
    public void canFly__Booster_Not_Active() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        assertFalse(TestPlugin.getInstance().getCommands().flyCommand.canFly(playerMock, TestPlugin.getInstance()));
    }

    @Test
    public void canFly__Your_Booster_Active() {
        TestPlugin.getInstance().getEnhancements().flightEnhancement.levels.get(1).enhancementAffectsType = Collections.singletonList(EnhancementAffectsType.MEMBERS_ANYWHERE);
        TestTeam team = new TeamBuilder().withEnhancement("flight", 1).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        assertTrue(TestPlugin.getInstance().getCommands().flyCommand.canFly(playerMock, TestPlugin.getInstance()));
    }

    @Test
    public void canFly__Visitors_Booster_Active() {
        TestPlugin.getInstance().getEnhancements().flightEnhancement.levels.get(2).enhancementAffectsType = Collections.singletonList(EnhancementAffectsType.VISITORS);
        TestTeam team = new TeamBuilder().withEnhancement("flight", 2).build();
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TeamManager.teamViaLocation = Optional.of(team);
        assertTrue(TestPlugin.getInstance().getCommands().flyCommand.canFly(playerMock, TestPlugin.getInstance()));
    }

    @Test
    public void tabCompleteFlyCommand() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Arrays.asList("disable", "enable", "off", "on"), serverMock.getCommandTabComplete(playerMock, "test fly "));
    }
}