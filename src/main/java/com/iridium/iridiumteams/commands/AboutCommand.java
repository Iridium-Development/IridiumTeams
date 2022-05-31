package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AboutCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public AboutCommand() {
        super(Arrays.asList("about", "version"), "Display plugin info", "", "");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        sender.sendMessage(StringUtils.color("&7Plugin Name: " + iridiumTeams.getCommandManager().getColor() + iridiumTeams.getDescription().getName()));
        sender.sendMessage(StringUtils.color("&7Plugin Version: " + iridiumTeams.getCommandManager().getColor() + iridiumTeams.getDescription().getVersion()));
        sender.sendMessage(StringUtils.color("&7Plugin Author: " + iridiumTeams.getCommandManager().getColor() + "Peaches_MLG"));
        sender.sendMessage(StringUtils.color("&7Plugin Donations: " + iridiumTeams.getCommandManager().getColor() + "www.patreon.com/Peaches_MLG"));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        // We currently don't want to tab-completion here
        // Return a new List, so it isn't a list of online players
        return Collections.emptyList();
    }

}
