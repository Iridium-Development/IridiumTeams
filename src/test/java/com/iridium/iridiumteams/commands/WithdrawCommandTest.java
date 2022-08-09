package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WithdrawCommandTest {

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
    public void executeWithdrawCommand__InvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test withdraw");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().withdrawCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWithdrawCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test withdraw ");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWithdrawCommand__NoBankItem() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test withdraw NoItem 10");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noSuchBankItem.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWithdrawCommand__NotANumber() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test withdraw money a");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notANumber.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWithdrawCommand__InsufficientFunds() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test withdraw money 100");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().insufficientFundsToWithdraw
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%type%", "money")
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeWithdrawCommand__Successful() {
        TestTeam team = new TeamBuilder().withBank("money", 150).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test withdraw money 100");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().bankWithdrew
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%type%", "money")
                .replace("%amount%", "100.0")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(100.00, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
        assertEquals(50, TestPlugin.getInstance().getTeamManager().getTeamBank(team, "money").getNumber());
    }

    @Test
    public void tabCompleteWithdrawCommand__BankItem() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(Arrays.asList("experience", "money"), serverMock.getCommandTabComplete(playerMock, "test withdraw "));
    }

    @Test
    public void tabCompleteWithdrawCommand__Number() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(Arrays.asList("100", "1000", "10000", "100000"), serverMock.getCommandTabComplete(playerMock, "test withdraw money "));
    }
}