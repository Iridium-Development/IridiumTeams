package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.database.TeamWarp;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditWarpCommandTest {

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
    public void executeEditWarpCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test editwarp");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeEditWarpCommand__InvalidSyntax() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test editwarp");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().editWarpCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeEditWarpCommand__NoPermissions() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.MANAGE_WARPS, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test editwarp name icon DIAMOND_BLOCK");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotManageWarps
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeEditWarpCommand__WarpDoesntExists() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test editwarp name icon material");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().unknownWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeEditWarpCommand__InvalidEdit() {
        TestTeam team = new TeamBuilder().withWarp("name", "", null).withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test editwarp name invalid");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().editWarpCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeEditWarpCommand__EditIcon__InvalidSyntax() {
        TestTeam team = new TeamBuilder().withWarp("name", "", null).withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test editwarp name icon");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().editWarpCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeEditWarpCommand__EditIcon__UnknownMaterial() {
        TestTeam team = new TeamBuilder().withWarp("name", "", null).withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test editwarp name icon material");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noSuchMaterial
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeEditWarpCommand__EditIcon__Success() {
        TestTeam team = new TeamBuilder().withWarp("name", "", null).withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test editwarp name icon DIAMOND_BLOCK");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().warpIconSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(XMaterial.DIAMOND_BLOCK, TestPlugin.getInstance().getTeamManager().getTeamWarp(team, "name").map(TeamWarp::getIcon).orElse(XMaterial.AIR));
    }

    @Test
    public void executeEditWarpCommand__EditDescription__InvalidSyntax() {
        TestTeam team = new TeamBuilder().withWarp("name", "", null).withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test editwarp name description");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().editWarpCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeEditWarpCommand__EditDescription__Success() {
        TestTeam team = new TeamBuilder().withWarp("name", "", null).withPermission(1, PermissionType.MANAGE_WARPS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);

        serverMock.dispatchCommand(playerMock, "test editwarp name description My warps description.");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().warpDescriptionSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertEquals("My warps description.", TestPlugin.getInstance().getTeamManager().getTeamWarp(team, "name").map(TeamWarp::getDescription).orElse(""));
    }

    @Test
    public void tabCompleteEditWarpCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Collections.emptyList(), serverMock.getCommandTabComplete(playerMock, "test editwarp "));
    }

    @Test
    public void tabCompleteEditWarpCommand__WithWarps() {
        TestTeam team = new TeamBuilder().withWarp("warp", "", null).withWarp("name", "", null).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        assertEquals(Arrays.asList("name", "warp"), serverMock.getCommandTabComplete(playerMock, "test editwarp "));
    }

    @Test
    public void tabCompleteEditWarpCommand__EditOptions() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        assertEquals(Arrays.asList("description", "icon"), serverMock.getCommandTabComplete(playerMock, "test editwarp name "));
    }

    @Test
    public void tabCompleteEditWarpCommand__MaterialOptions() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        assertTrue(serverMock.getCommandTabComplete(playerMock, "test editwarp name icon ").contains("DIAMOND_BLOCK"));
    }

    @Test
    public void tabCompleteEditWarpCommand__DescriptionOptions() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        assertEquals(Collections.emptyList(), serverMock.getCommandTabComplete(playerMock, "test editwarp name descriptiopn "));
    }


}