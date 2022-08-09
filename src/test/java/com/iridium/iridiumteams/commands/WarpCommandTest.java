package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.bukkit.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WarpCommandTest {

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
    public void executeWarpCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test warp");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWarpCommand__InvalidSyntax() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test warp");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().warpCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWarpCommand__NoWarp() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test warp name");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().unknownWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWarpCommand__InvalidPassword__NotSpecified() {
        TestTeam testTeam = new TeamBuilder().withWarp("name", "password", new Location(null, 0, 0, 0)).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test warp name");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().incorrectPassword
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWarpCommand__InvalidPassword() {
        TestTeam testTeam = new TeamBuilder().withWarp("name", "password", new Location(null, 0, 0, 0)).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test warp name pass");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().incorrectPassword
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWarpCommand__Success__WithPassword() {
        Location location = new Location(null, 100, 0, 100);
        TestTeam testTeam = new TeamBuilder().withWarp("name", "password", location).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test warp name password");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teleportingWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", "name")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(location, playerMock.getLocation());

    }

    @Test
    public void executeWarpCommand__Success() {
        Location location = new Location(null, 100, 0, 100);
        TestTeam testTeam = new TeamBuilder().withWarp("name", null, location).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test warp name");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teleportingWarp
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%name%", "name")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(location, playerMock.getLocation());

    }

    @Test
    public void tabCompleteWarpCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Collections.emptyList(), serverMock.getCommandTabComplete(playerMock, "test warp "));
    }

    @Test
    public void tabCompleteWarpCommand__WithWarps() {
        TestTeam team = new TeamBuilder().withWarp("warp", "", null).withWarp("name", "", null).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        assertEquals(Arrays.asList("name", "warp"), serverMock.getCommandTabComplete(playerMock, "test warp "));
    }

}