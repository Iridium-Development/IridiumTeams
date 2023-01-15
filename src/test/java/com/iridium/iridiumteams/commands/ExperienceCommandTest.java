package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExperienceCommandTest {

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
    public void executeExperienceCommand__InvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test experience invalid synrax");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().experienceCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeExperienceCommand__DefaultsToInfoCommand() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test experience");

        List<Placeholder> placeholderList = TestPlugin.getInstance().getTeamsPlaceholderBuilder().getPlaceholders(testTeam);
        playerMock.assertSaid(StringUtils.color(StringUtils.getCenteredMessage(StringUtils.processMultiplePlaceholders(TestPlugin.getInstance().getConfiguration().teamInfoTitle, placeholderList), TestPlugin.getInstance().getConfiguration().teamInfoTitleFiller)));
        for (String line : TestPlugin.getInstance().getConfiguration().teamInfo) {
            playerMock.assertSaid(StringUtils.color(StringUtils.processMultiplePlaceholders(line, placeholderList)));
        }
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeExperienceCommand__NoPermissions() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test experience give "+team.getName()+" 100");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noPermission
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeExperienceCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().build();

        serverMock.dispatchCommand(playerMock, "test experience give "+playerMock.getName()+" 100");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamDoesntExistByName
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeExperienceCommand__WithTeam__Give() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().build();

        serverMock.dispatchCommand(playerMock, "test experience give "+team.getName()+" 100");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().gaveExperience
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", team.getName())
                .replace("%amount%", "100")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(100, team.getExperience());
    }

    @Test
    public void executeExperienceCommand__WithTeam__Set() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().build();
        team.setExperience(50000);

        serverMock.dispatchCommand(playerMock, "test experience set "+team.getName()+" 100");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().setExperience
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", team.getName())
                .replace("%amount%", "100")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(100, team.getExperience());
    }

    @Test
    public void executeExperienceCommand__WithTeam__Remove() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().build();
        team.setExperience(100);

        serverMock.dispatchCommand(playerMock, "test experience remove "+team.getName()+" 10");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().removedExperience
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", team.getName())
                .replace("%amount%", "10")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(90, team.getExperience());
    }

    @Test
    public void executeExperienceCommand__WithTeam__Set__NonNegative() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().build();
        team.setExperience(50000);

        serverMock.dispatchCommand(playerMock, "test experience set "+team.getName()+" -100000");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().setExperience
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", team.getName())
                .replace("%amount%", "0")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(0, team.getExperience());
    }

    @Test
    public void executeExperienceCommand__WithTeam__Remove__NonNegative() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().build();
        team.setExperience(100);

        serverMock.dispatchCommand(playerMock, "test experience remove "+team.getName()+" 100000");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().removedExperience
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", team.getName())
                .replace("%amount%", "100")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(0, team.getExperience());
    }
}