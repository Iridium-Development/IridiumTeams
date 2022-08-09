package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import org.bukkit.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteWarpCommandTest {

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
    public void executeDeleteWarpCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test deletewarp");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteWarpCommand__InvalidSyntax() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test deletewarp");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().deleteWarpCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteWarpCommand__NoPermissions() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.MANAGE_WARPS, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test deletewarp name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotManageWarps
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteWarpCommand__WarpDoesntExists() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test deletewarp name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().unknownWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDeleteWarpCommand__Success() {
        TestTeam team = new TeamBuilder().withWarp("name", "", new Location(null, 0, 0, 0)).withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test deletewarp name");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().deletedWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                        .replace("%player%", playerMock.getName())
                .replace("%name%", "name")
        ));
        playerMock.assertNoMoreSaid();
        assertFalse(TestPlugin.getInstance().getTeamManager().getTeamWarp(team, "name").isPresent());
    }

    @Test
    public void tabCompleteDeleteWarpCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Collections.emptyList(), serverMock.getCommandTabComplete(playerMock, "test deletewarp "));
    }

    @Test
    public void tabCompleteDeleteWarpCommand__WithWarps() {
        TestTeam team = new TeamBuilder().withWarp("warp", "", null).withWarp("name", "", null).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        assertEquals(Arrays.asList("name", "warp"), serverMock.getCommandTabComplete(playerMock, "test deletewarp "));
    }


}