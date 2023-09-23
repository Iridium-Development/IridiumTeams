package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandSender;

import java.util.List;

@NoArgsConstructor
public class AboutCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public AboutCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        sender.sendMessage(StringUtils.color("&7Plugin Name: " + iridiumTeams.getCommandManager().getColor() + iridiumTeams.getDescription().getName()));
        sender.sendMessage(StringUtils.color("&7Plugin Version: " + iridiumTeams.getCommandManager().getColor() + iridiumTeams.getDescription().getVersion()));
        sender.sendMessage(StringUtils.color("&7Plugin Author: " + iridiumTeams.getCommandManager().getColor() + "Peaches_MLG"));
        sender.sendMessage(StringUtils.color("&7Plugin Donations: " + iridiumTeams.getCommandManager().getColor() + "www.patreon.com/Peaches_MLG"));
        return true;
    }

}
