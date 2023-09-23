package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamWarp;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class WarpCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public WarpCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1 && args.length != 2) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        Optional<TeamWarp> teamWarp = iridiumTeams.getTeamManager().getTeamWarp(team, args[0]);
        if (!teamWarp.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().unknownWarp
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (teamWarp.get().getPassword() != null) {
            if (args.length != 2 || !teamWarp.get().getPassword().equals(args[1])) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().incorrectPassword
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return false;
            }
        }
        
        if (iridiumTeams.getTeamManager().teleport(player, teamWarp.get().getLocation(), team)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teleportingWarp
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%name%", teamWarp.get().getName())
            ));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        List<TeamWarp> teamWarps = iridiumTeams.getTeamManager().getTeamWarps(team);
        return teamWarps.stream().map(TeamWarp::getName).collect(Collectors.toList());
    }
}
