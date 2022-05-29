package com.iridium.testplugin.managers;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.commands.Command;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandManager extends com.iridium.iridiumteams.managers.CommandManager<Team, IridiumUser<Team>> {
    public CommandManager(IridiumTeams<Team, IridiumUser<Team>> iridiumTeams, String color, String command, List<Command<Team, IridiumUser<Team>>> commands) {
        super(iridiumTeams, color, command, commands);
    }

    @Override
    public void noArgsDefault(@NotNull CommandSender commandSender) {

    }
}
