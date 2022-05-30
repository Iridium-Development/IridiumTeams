package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamInvite;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UnInviteCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public UnInviteCommand() {
        super(Collections.singletonList("uninvite"), "UnInvite a member to your team", "%prefix% &7/team uninvite <player>", "");
    }

    @Override
    public void execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        U offlinePlayer = iridiumTeams.getUserManager().getUser(Bukkit.getServer().getOfflinePlayer(args[0]));
        Optional<TeamInvite> factionInvite = iridiumTeams.getTeamManager().getTeamInvite(team, offlinePlayer);
        if (!factionInvite.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noActiveInvite.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }

        iridiumTeams.getTeamManager().deleteTeamInvite(factionInvite.get());
        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamInviteRevoked
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%player%", offlinePlayer.getName())
        ));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
