package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateCommandTest {

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
    public void executeCreateCommand__InvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test create");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().createCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeCreateCommand__AlreadyHaveTeam() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test create test");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().alreadyHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeCreateCommand__AlreadyHaveTeam__NoName() {
        TestPlugin.getInstance().getConfiguration().createRequiresName = false;
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test create");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().alreadyHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeCreateCommand__RequiresName() {
        TestPlugin.getInstance().getConfiguration().createRequiresName = true;
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test create");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().createCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeCreateCommand__TeamNameTooShort() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test create a");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamNameTooShort
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%min_length%", String.valueOf(TestPlugin.getInstance().getConfiguration().minTeamNameLength))
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeCreateCommand__TeamNameTooLong() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test create areallyreallylongteamname");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamNameTooLong
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%max_length%", String.valueOf(TestPlugin.getInstance().getConfiguration().maxTeamNameLength))
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeCreateCommand__TeamNameTaken() {
        new TeamBuilder("test").build();
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test create test");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamNameAlreadyExists
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeCreateCommand__Success() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        User user = TestPlugin.getInstance().getUserManager().getUser(playerMock);

        serverMock.dispatchCommand(playerMock, "test create test");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamCreated
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(Rank.OWNER.getId(), user.getUserRank());
    }
}