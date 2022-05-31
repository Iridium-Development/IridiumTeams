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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SetHomeCommandTest {
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
    public void executeDescriptionCommandNoFaction() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test sethome");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDescriptionCommandNoPermission() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.SETHOME, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test sethome");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notInTeamLand.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDescriptionCommandNotInLand() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.SETHOME, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test sethome");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notInTeamLand
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDescriptionCommandSuccessful() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.SETHOME, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        TestPlugin.getInstance().getTeamManager().setTeamClaim(team, playerMock.getLocation());

        serverMock.dispatchCommand(playerMock, "test sethome");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().homeSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(team.getHome(), playerMock.getLocation());
    }
}