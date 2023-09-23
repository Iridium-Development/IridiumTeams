package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TrustCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public TrustCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.TRUST)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotTrust
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        Player invitee = Bukkit.getServer().getPlayer(args[0]);
        if (invitee == null) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notAPlayer
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        U offlinePlayerUser = iridiumTeams.getUserManager().getUser(invitee);
        if (offlinePlayerUser.getTeamID() == team.getId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().userAlreadyInTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (iridiumTeams.getTeamManager().getTeamTrust(team, offlinePlayerUser).isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().trustAlreadyPresent
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        iridiumTeams.getTeamManager().createTeamTrust(team, offlinePlayerUser, user);
        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamTrustSent
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%player%", offlinePlayerUser.getName())
        ));
        invitee.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamTrustReceived
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%player%", player.getName())
        ));
        
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
