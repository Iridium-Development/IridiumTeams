package com.iridium.testplugin.managers;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.commands.Command;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandManager extends com.iridium.iridiumteams.managers.CommandManager<TestTeam, User> {
    public CommandManager(IridiumTeams<TestTeam, User> iridiumTeams, String color, String command, List<Command<TestTeam, User>> commands) {
        super(iridiumTeams, color, command, commands);
    }

    @Override
    public void noArgsDefault(@NotNull CommandSender commandSender) {

    }
}
