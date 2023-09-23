package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamInvite;
import com.iridium.iridiumteams.database.TeamTrust;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UnTrustCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public UnTrustCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        U offlinePlayer = iridiumTeams.getUserManager().getUser(Bukkit.getServer().getOfflinePlayer(args[0]));
        Optional<TeamTrust> teamTrust = iridiumTeams.getTeamManager().getTeamTrust(team, offlinePlayer);
        if (!teamTrust.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noActiveTrust.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }

        iridiumTeams.getTeamManager().deleteTeamTrust(teamTrust.get());
        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamTrustRevoked
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%player%", offlinePlayer.getName())
        ));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
