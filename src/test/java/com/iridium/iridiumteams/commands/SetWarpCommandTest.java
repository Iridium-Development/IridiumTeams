package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.utils.LocationUtils;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import org.bukkit.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetWarpCommandTest {

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
    public void executeSetWarpCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test setwarp");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetWarpCommand__InvalidSyntax() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test setwarp");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().setWarpCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetWarpCommand__NotSafe() {
        LocationUtils.setSafeTesting(false);
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test setwarp name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notSafe
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetWarpCommand__NotInClaim() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test setwarp name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notInTeamLand
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetWarpCommand__NoPermissions() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.MANAGE_WARPS, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test setwarp name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotManageWarps
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetWarpCommand__WarpAlreadyExists() {
        TestTeam team = new TeamBuilder()
                .withWarp("name", "", new Location(null, 0, 0, 0))
                .withPermission(1, PermissionType.MANAGE_WARPS, true)
                .withEnhancement("warps", 2)
                .build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test setwarp name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().warpAlreadyExists
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetWarpCommand__LimitReached() {
        TestTeam team = new TeamBuilder().withWarp("name", "", new Location(null, 0, 0, 0)).withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test setwarp name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().warpLimitReached
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetWarpCommand__Success() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test setwarp name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().createdWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", "name")
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamWarp(team, "name").isPresent());
    }

    @Test
    public void executeSetWarpCommand__Success__WithPassword() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test setwarp name password");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().createdWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", "name")
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamWarp(team, "name").isPresent());
        assertEquals("password", TestPlugin.getInstance().getTeamManager().getTeamWarp(team, "name").get().getPassword());
    }

}