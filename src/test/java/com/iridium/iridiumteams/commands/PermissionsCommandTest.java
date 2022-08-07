package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.gui.PermissionsGUI;
import com.iridium.iridiumteams.gui.RanksGUI;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PermissionsCommandTest {

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
    public void executePermissionsCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test permissions");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executePermissionsCommand__NoArgsSuccess() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test permissions");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof RanksGUI<?, ?>);
    }

    @Test
    public void executePermissionsCommand__WithArgsSuccess() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test permissions Member");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof PermissionsGUI<?, ?>);
    }

    @Test
    public void executePermissionsCommand__WithInvalidArgs() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test permissions InvalidRank");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().invalidUserRank
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
    }

    @Test
    public void tabCompletePermissionsCommand(){
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        assertEquals(Arrays.asList("CoOwner", "Member", "Moderator", "Owner", "Visitor"), serverMock.getCommandTabComplete(playerMock, "test permissions "));
    }
}