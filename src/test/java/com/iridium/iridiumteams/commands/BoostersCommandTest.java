package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.gui.BoostersGUI;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.api.EnhancementUpdateEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoostersCommandTest {

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
    public void executeBoostersCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test boosters");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBoostersCommand__Success() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test boosters");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof BoostersGUI<?, ?>);
    }

    @Test
    public void executeBoostersCommand__Buy__InvalidSyntax() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test boosters bad syntax");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().boostersCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBoostersCommand__Buy__NotBooster() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test boosters buy haste");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noSuchBooster
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBoostersCommand__Buy__LowLevel() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test boosters buy farming");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notHighEnoughLevel
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%level%", "5")
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBoostersCommand__Buy__NotEnoughMoney() {
        TestTeam testTeam = new TeamBuilder().withLevel(5).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test boosters buy farming");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notEnoughMoney
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%level%", "5")
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBoostersCommand__Buy__MaxLevel() {
        LocalDateTime currentExpiration = LocalDateTime.now().plusMinutes(1);
        TestTeam testTeam = new TeamBuilder().withLevel(50).withEnhancement("farming", 3, currentExpiration).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        TestPlugin.getInstance().getEconomy().depositPlayer(playerMock, 100000);

        serverMock.dispatchCommand(playerMock, "test boosters buy farming");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().purchasedBooster
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%booster%", "farming")
        ));
        playerMock.assertNoMoreSaid();

        assertEquals(90000, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
        assertEquals(3, TestPlugin.getInstance().getTeamManager().getTeamEnhancement(testTeam, "farming").getLevel());
        assertEquals(currentExpiration.plusHours(1), TestPlugin.getInstance().getTeamManager().getTeamEnhancement(testTeam, "farming").getExpirationTime());
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamEnhancement(testTeam, "farming").isActive());
    }

    @Test
    public void executeBoostersCommand__Buy__Success() {
        TestTeam testTeam = new TeamBuilder().withLevel(5).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        TestPlugin.getInstance().getEconomy().depositPlayer(playerMock, 100000);

        serverMock.dispatchCommand(playerMock, "test boosters buy farming");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().purchasedBooster
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%booster%", "farming")
        ));
        playerMock.assertNoMoreSaid();

        assertEquals(90000, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
        assertEquals(1, TestPlugin.getInstance().getTeamManager().getTeamEnhancement(testTeam, "farming").getLevel());
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamEnhancement(testTeam, "farming").isActive());
        assertTrue(EnhancementUpdateEvent.called);
    }

    @Test
    public void executeBoostersCommand__Buy__ResetLevel() {
        TestTeam testTeam = new TeamBuilder().withEnhancement("farming", 3, LocalDateTime.now().minusDays(1)).withLevel(5).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        TestPlugin.getInstance().getEconomy().depositPlayer(playerMock, 100000);

        serverMock.dispatchCommand(playerMock, "test boosters buy farming");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().purchasedBooster
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%booster%", "farming")
        ));
        playerMock.assertNoMoreSaid();

        assertEquals(90000, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
        assertEquals(1, TestPlugin.getInstance().getTeamManager().getTeamEnhancement(testTeam, "farming").getLevel());
        assertTrue(TestPlugin.getInstance().getTeamManager().getTeamEnhancement(testTeam, "farming").isActive());
    }

}