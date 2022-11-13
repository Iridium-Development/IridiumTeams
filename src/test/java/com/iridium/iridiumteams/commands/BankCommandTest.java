package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.gui.BankGUI;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankCommandTest {

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
    public void executeBankCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test bank");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().dontHaveTeam
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBankCommand__Success() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        serverMock.dispatchCommand(playerMock, "test bank");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof BankGUI<?,?>);
    }

    @Test
    public void executeBankAdminCommand__InvalidSyntax() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test bank give");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().bankCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBankAdminCommand__NoTeam() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        serverMock.dispatchCommand(playerMock, "test bank give "+playerMock.getName()+" experience 100");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().teamDoesntExistByName
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBankAdminCommand__NoPermissions() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test bank give "+playerMock.getName()+" experience 100");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noPermission
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBankAdminCommand__InvalidBankItem() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test bank give "+playerMock.getName()+" invalid 100");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noSuchBankItem
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBankAdminCommand__NotANumber() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test bank give "+playerMock.getName()+" experience NotANumber");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().notANumber
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBankAdminCommand__Give() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test bank give "+playerMock.getName()+" experience 100");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().gaveBank
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
                .replace("%amount%", "100.0")
                .replace("%item%", "experience")
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBankAdminCommand__Remove() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test bank remove "+playerMock.getName()+" experience 100");


        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().removedBank
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
                .replace("%amount%", "100.0")
                .replace("%item%", "experience")
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBankAdminCommand__Set() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test bank set "+playerMock.getName()+" experience 100");


        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().setBank
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%player%", playerMock.getName())
                .replace("%amount%", "100.0")
                .replace("%item%", "experience")
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeBankAdminCommand__unknown() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).setOp().withTeam(testTeam).build();

        serverMock.dispatchCommand(playerMock, "test bank unknown "+playerMock.getName()+" experience 100");

        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getCommands().bankCommand.syntax
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void tabCompleteBankCommand__NoPermissions(){
        PlayerMock playerMock1 = new UserBuilder(serverMock).build();

        assertEquals(List.of(), serverMock.getCommandTabComplete(playerMock1, "test bank "));
    }

    @Test
    public void tabCompleteBankCommand__WithPermissions(){
        PlayerMock playerMock1 = new UserBuilder(serverMock).setOp().build();

        assertEquals(List.of("give", "remove", "set"), serverMock.getCommandTabComplete(playerMock1, "test bank "));
        assertEquals(List.of(playerMock1.getName()), serverMock.getCommandTabComplete(playerMock1, "test bank give "));
        assertEquals(List.of("experience", "money"), serverMock.getCommandTabComplete(playerMock1, "test bank give "+playerMock1.getName()+" "));
        assertEquals(List.of("1", "10", "100"), serverMock.getCommandTabComplete(playerMock1, "test bank give "+playerMock1.getName()+" money "));
    }

}