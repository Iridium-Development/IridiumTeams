package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetPermissionCommandTest {

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
    public void executeSetPermissionCommandNoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test setpermission");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetPermissionCommandInvalidSyntax() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test setpermission permission role invalid");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getConfiguration().prefix + " &7/team setpermission <permission> <role> (true/false)"));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetPermissionCommandInvalidPermission() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test setpermission invalid member true");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().invalidPermission
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetPermissionCommandInvalidRank() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test setpermission blockBreak invalid true");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().invalidUserRank
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetPermissionCommandCannotChangePermissions() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test setpermission blockBreak member true");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotChangePermissions
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeSetPermissionCommandSuccessOwner() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_BREAK, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).withRank(Rank.OWNER.getId()).build();

        serverMock.dispatchCommand(playerMock, "test setpermission blockBreak member true");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().permissionSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%permission%", "blockBreak")
                .replace("%rank%", "Member")
                .replace("%allowed%", "true")
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamPermission(team, 1, PermissionType.BLOCK_BREAK.getPermissionKey()));
    }

    @Test
    public void executeSetPermissionCommandToggleSuccess() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_BREAK, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).setBypassing().withTeam(team).withRank(2).build();

        serverMock.dispatchCommand(playerMock, "test setpermission blockBreak member");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().permissionSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%permission%", "blockBreak")
                .replace("%rank%", "Member")
                .replace("%allowed%", "true")
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamPermission(team, 1, PermissionType.BLOCK_BREAK.getPermissionKey()));
    }

    @Test
    public void executeSetPermissionCommandSuccess() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.BLOCK_BREAK, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).setBypassing().withTeam(team).withRank(2).build();

        serverMock.dispatchCommand(playerMock, "test setpermission blockBreak member true");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().permissionSet
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%permission%", "blockBreak")
                .replace("%rank%", "Member")
                .replace("%allowed%", "true")
        ));
        playerMock.assertNoMoreSaid();
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamPermission(team, 1, PermissionType.BLOCK_BREAK.getPermissionKey()));
    }

    @Test
    public void tabCompleteSetPermissionsCommand__FirstArgument() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(TestPlugin.getInstance().getPermissionList().keySet().stream().sorted().collect(Collectors.toList()), serverMock.getCommandTabComplete(playerMock, "test setpermission "));
    }

    @Test
    public void tabCompleteSetPermissionsCommand__SecondArgument() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Arrays.asList("CoOwner", "Member", "Moderator", "Owner", "Visitor"), serverMock.getCommandTabComplete(playerMock, "test setpermission permission "));
    }

    @Test
    public void tabCompleteSetPermissionsCommand__ThirdArgument() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Arrays.asList("false", "true"), serverMock.getCommandTabComplete(playerMock, "test setpermission permission Member "));
    }

    @Test
    public void tabCompleteSetPermissionsCommand__FourthArgument() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Collections.emptyList(), serverMock.getCommandTabComplete(playerMock, "test setpermission permission Member true "));
    }
}