package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public ReloadCommand() {
        super(Collections.singletonList("reload"), "Reload the plugin configurations", "", "iridiumteams.reload");
    }

    @Override
    public void execute(CommandSender sender, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        iridiumTeams.loadConfigs();
        sender.sendMessage(StringUtils.color(iridiumTeams.getMessages().reloaded.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        // We currently don't want to tab-completion here
        // Return a new List, so it isn't a list of online players
        return Collections.emptyList();
    }

}
