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

    private final String color;

    public AboutCommand(String color) {
        super(Arrays.asList("about", "version"), "Display plugin info", "", "");
        this.color = color;
    }

    @Override
    public void execute(CommandSender sender, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        sender.sendMessage(StringUtils.color("&7Plugin Name: " + color + iridiumTeams.getDescription().getName()));
        sender.sendMessage(StringUtils.color("&7Plugin Version: " + color + iridiumTeams.getDescription().getVersion()));
        sender.sendMessage(StringUtils.color("&7Plugin Author: " + color + "Peaches_MLG"));
        sender.sendMessage(StringUtils.color("&7Plugin Donations: " + color + "www.patreon.com/Peaches_MLG"));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        // We currently don't want to tab-completion here
        // Return a new List, so it isn't a list of online players
        return Collections.emptyList();
    }

}
