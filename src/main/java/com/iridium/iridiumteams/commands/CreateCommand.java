package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public CreateCommand() {
        super(Collections.singletonList("create"), "Create a new team", "%prefix% &7/team create <name>", "");
    }

    @Override
    public void execute(U user, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length < 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        if (iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().alreadyHaveTeam.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }

        String factionName = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
        if (iridiumTeams.getTeamManager().getTeamViaName(factionName).isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameAlreadyExists.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        iridiumTeams.getTeamManager().createTeam(player, factionName).thenAccept(faction ->
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamCreated.replace("%prefix%", iridiumTeams.getConfiguration().prefix)))
        );
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        // We currently don't want to tab-completion here
        // Return a new List, so it isn't a list of online players
        return Collections.emptyList();
    }

}
