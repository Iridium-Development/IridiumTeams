package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class InfoCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public InfoCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length == 0) {
            Optional<T> userTeam = iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID());
            if (!userTeam.isPresent()) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().dontHaveTeam
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return false;
            }
            sendTeamInfo(player, userTeam.get(), iridiumTeams);
            return true;
        }
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaNameOrPlayer(String.join(" ", args));
        if (!team.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamDoesntExistByName
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        sendTeamInfo(player, team.get(), iridiumTeams);
        return true;
    }

    public void sendTeamInfo(Player player, T team, IridiumTeams<T, U> iridiumTeams) {
        List<Placeholder> placeholderList = iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(team);
        player.sendMessage(StringUtils.color(StringUtils.getCenteredMessage(StringUtils.processMultiplePlaceholders(iridiumTeams.getConfiguration().teamInfoTitle, placeholderList), iridiumTeams.getConfiguration().teamInfoTitleFiller)));
        for (String line : iridiumTeams.getConfiguration().teamInfo) {
            player.sendMessage(StringUtils.color(StringUtils.processMultiplePlaceholders(line, placeholderList)));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
