package com.iridium.testplugin.managers;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandManager extends com.iridium.iridiumteams.managers.CommandManager<TestTeam, User> {
    public CommandManager(IridiumTeams<TestTeam, User> iridiumTeams, String color, String command) {
        super(iridiumTeams, color, command);
    }

    @Override
    public void noArgsDefault(@NotNull CommandSender commandSender) {
        commandSender.sendMessage("No Argument Method hit");
    }
}
