package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class KickCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public KickCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.KICK)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotKick
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[0]);
        U kickedPlayer = iridiumTeams.getUserManager().getUser(offlinePlayer);
        if (team.getId() != kickedPlayer.getTeamID()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().userNotInYourTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (offlinePlayer.getUniqueId() == player.getUniqueId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotKickYourself
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (kickedPlayer.getUserRank() >= user.getUserRank() && !user.isBypassing() && user.getUserRank() != Rank.OWNER.getId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotKickHigherRank
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        kickedPlayer.setTeam(null);
        Optional.ofNullable(kickedPlayer.getPlayer()).ifPresent(player1 -> player1.sendMessage(StringUtils.color(iridiumTeams.getMessages().youHaveBeenKicked
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%player%", player.getName())
        )));
        iridiumTeams.getTeamManager().getTeamMembers(team).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(player1 ->
                player1.sendMessage(StringUtils.color(iridiumTeams.getMessages().playerKicked
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%player%", kickedPlayer.getName())
                        .replace("%kicker%", player.getName())
                ))
        );
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
