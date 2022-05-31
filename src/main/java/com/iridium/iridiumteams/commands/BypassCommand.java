package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class BypassCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public BypassCommand() {
        super(Collections.singletonList("bypass"), "Bypass Team restrictions", "/team bypass", "iridiumteams.bypass");
    }

    @Override
    public void execute(U user, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        user.setBypassing(!user.isBypassing());
        player.sendMessage(StringUtils.color((user.isBypassing() ? iridiumTeams.getMessages().nowBypassing : iridiumTeams.getMessages().noLongerBypassing)
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
        ));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        // We currently don't want to tab-completion here
        // Return a new List, so it isn't a list of online players
        return Collections.emptyList();
    }

}
