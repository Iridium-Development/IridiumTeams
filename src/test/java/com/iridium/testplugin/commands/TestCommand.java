package com.iridium.testplugin.commands;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.commands.Command;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TestCommand extends Command<TestTeam, User> {

    public static boolean hasCalled;

    public TestCommand() {
        super(List.of("test"), "Description", "/test test", "iridiumteams.test", 5);
        hasCalled = false;
    }

    @Override
    public boolean execute(User user, TestTeam team, String[] arguments, IridiumTeams<TestTeam, User> iridiumTeams) {
        hasCalled = true;
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<TestTeam, User> iridiumTeams) {
        return Arrays.asList("c", "d", "A");
    }
}
