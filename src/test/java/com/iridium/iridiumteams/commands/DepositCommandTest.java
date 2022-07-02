package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestBankItem;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepositCommandTest {

    private ServerMock serverMock;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
        TestPlugin.getInstance().getBankItemList().clear();
        TestPlugin.getInstance().addBankItem(new TestBankItem());
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void executeDepositCommand__InvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(new TeamBuilder().build()).build();

        serverMock.dispatchCommand(playerMock, "test deposit");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().depositCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDepositCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test deposit ");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDepositCommand__NoBankItem() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test deposit NoItem 10");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noSuchBankItem.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDepositCommand__NotANumber() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test deposit TestBankItem a");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notANumber.replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDepositCommand__InsufficientFunds() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();

        serverMock.dispatchCommand(playerMock, "test deposit TestBankItem 100");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().insufficientFundsToDeposit
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%type%", "testBankItem")
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeDepositCommand__Successful() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TestBankItem.playerBalances.put(playerMock.getUniqueId(), 50);

        serverMock.dispatchCommand(playerMock, "test deposit TestBankItem 100");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().bankDeposited
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%type%", "testBankItem")
                .replace("%amount%", "50.0")
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(0, TestBankItem.playerBalances.get(playerMock.getUniqueId()));
    }

    @Test
    public void tabCompleteDepositCommand__BankItem() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(Collections.singletonList("testBankItem"), serverMock.getCommandTabComplete(playerMock, "test deposit "));
    }

    @Test
    public void tabCompleteDepositCommand__Number() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        assertEquals(Arrays.asList("100", "1000", "10000", "100000"), serverMock.getCommandTabComplete(playerMock, "test deposit testBankItem "));
    }
}