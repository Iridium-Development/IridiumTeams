package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InviteCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public InviteCommand() {
        super(Collections.singletonList("invite"), "Invite a member to your team", "%prefix% &7/team invite <player>", "");
    }

    @Override
    public void execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.INVITE)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotInvite
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        Player invitee = Bukkit.getServer().getPlayer(args[0]);
        if (invitee == null) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notAPlayer
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        U offlinePlayerUser = iridiumTeams.getUserManager().getUser(invitee);
        if (offlinePlayerUser.getTeam().map(T::getId).orElse(0) == team.getId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().userAlreadyInTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        if (iridiumTeams.getTeamManager().getTeamInvite(team, offlinePlayerUser).isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().inviteAlreadyPresent
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }

        iridiumTeams.getTeamManager().createTeamInvite(team, offlinePlayerUser, user);
        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamInviteSent
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%player%", offlinePlayerUser.getName())
        ));
        invitee.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamInviteReceived
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%player%", player.getName())
        ));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
