package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class RecalculateCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public RecalculateCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        if (iridiumTeams.isRecalculating()) {
            sender.sendMessage(StringUtils.color(iridiumTeams.getMessages().calculationAlreadyInProcess
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix))
            );
            return false;
        }

        int interval = iridiumTeams.getConfiguration().forceRecalculateInterval;
        List<T> teams = iridiumTeams.getTeamManager().getTeams();
        int seconds = (teams.size() * interval / 20) % 60;
        int minutes = (teams.size() * interval / 20) / 60;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!player.hasPermission(permission)) continue;
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().calculatingTeams
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%player%", sender.getName())
                    .replace("%minutes%", String.valueOf(minutes))
                    .replace("%seconds%", String.valueOf(seconds))
                    .replace("%amount%", String.valueOf(teams.size()))
            ));
        }
        iridiumTeams.setRecalculating(true);
        return true;
    }

}
