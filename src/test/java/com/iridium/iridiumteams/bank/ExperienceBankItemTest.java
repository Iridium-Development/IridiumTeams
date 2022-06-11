package com.iridium.iridiumteams.bank;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.database.TeamBank;
import com.iridium.iridiumteams.utils.PlayerUtils;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExperienceBankItemTest {

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
    public void ExperienceBankItem__Withdraw__InsufficientFunds() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamBank teamBank = TestPlugin.getInstance().getTeamManager().getTeamBank(team, TestPlugin.getInstance().getBankItems().experienceBankItem.getName());

        BankResponse bankResponse = TestPlugin.getInstance().getBankItems().experienceBankItem.withdraw(playerMock, 64, teamBank, TestPlugin.getInstance());

        assertEquals(0, bankResponse.getAmount());
        assertFalse(bankResponse.isSuccess());
    }

    @Test
    public void ExperienceBankItem__Withdraw__Successful() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamBank teamBank = TestPlugin.getInstance().getTeamManager().getTeamBank(team, TestPlugin.getInstance().getBankItems().experienceBankItem.getName());
        teamBank.setNumber(64);

        BankResponse bankResponse = TestPlugin.getInstance().getBankItems().experienceBankItem.withdraw(playerMock, 64, teamBank, TestPlugin.getInstance());

        assertEquals(64, bankResponse.getAmount());
        assertTrue(bankResponse.isSuccess());
        assertEquals(64, playerMock.getTotalExperience());
    }

    @Test
    public void ExperienceBankItem__Deposit__InsufficientFunds() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamBank teamBank = TestPlugin.getInstance().getTeamManager().getTeamBank(team, TestPlugin.getInstance().getBankItems().experienceBankItem.getName());

        BankResponse bankResponse = TestPlugin.getInstance().getBankItems().experienceBankItem.deposit(playerMock, 64, teamBank, TestPlugin.getInstance());

        assertEquals(0, bankResponse.getAmount());
        assertFalse(bankResponse.isSuccess());
    }

    @Test
    public void ExperienceBankItem__Deposit__Successful() {
        TestTeam team = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamBank teamBank = TestPlugin.getInstance().getTeamManager().getTeamBank(team, TestPlugin.getInstance().getBankItems().experienceBankItem.getName());
        PlayerUtils.setTotalExperience(playerMock, 64);

        BankResponse bankResponse = TestPlugin.getInstance().getBankItems().experienceBankItem.deposit(playerMock, 64, teamBank, TestPlugin.getInstance());

        assertEquals(64, bankResponse.getAmount());
        assertTrue(bankResponse.isSuccess());
        assertEquals(0, playerMock.getTotalExperience());
    }

}