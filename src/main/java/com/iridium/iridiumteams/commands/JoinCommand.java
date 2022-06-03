package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamInvite;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JoinCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public JoinCommand(List<String> args, String description, String syntax, String permission){
        super(args, description, syntax, permission);
    }

    @Override
    public void execute(U user, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        if(iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).isPresent()){
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().alreadyHaveTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaNameOrPlayer(args[0]);
        if (!team.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamDoesntExistByName
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        Optional<TeamInvite<T>> teamInvite = iridiumTeams.getTeamManager().getTeamInvite(team.get(), user);
        if (!teamInvite.isPresent() && !user.isBypassing()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noActiveInvite
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }

        user.setTeam(team.get());
        teamInvite.ifPresent(invite -> iridiumTeams.getTeamManager().deleteTeamInvite(invite));

        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().joinedTeam
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%name%", team.get().getName())
        ));

        iridiumTeams.getTeamManager().getTeamMembers(team.get()).stream()
                .map(U::getPlayer)
                .forEach(teamMember -> {
                    if (teamMember != null && teamMember != player) {
                        teamMember.sendMessage(StringUtils.color(iridiumTeams.getMessages().userJoinedTeam
                                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                                .replace("%player%", player.getName())
                        ));
                    }
                });
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
