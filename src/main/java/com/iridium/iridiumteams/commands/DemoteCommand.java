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
import java.util.stream.Collectors;

@NoArgsConstructor
public class DemoteCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public DemoteCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }

        OfflinePlayer targetPlayer = Bukkit.getServer().getOfflinePlayer(args[0]);
        U targetUser = iridiumTeams.getUserManager().getUser(targetPlayer);

        if (targetUser.getTeamID() != team.getId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().userNotInYourTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        int nextRank = targetUser.getUserRank() - 1;

        if (!DoesRankExist(nextRank, iridiumTeams) || IsHigherRank(targetUser, user) || !iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.DEMOTE)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotDemoteUser
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        targetUser.setUserRank(nextRank);

        for (U member : iridiumTeams.getTeamManager().getTeamMembers(team)) {
            Player teamMember = Bukkit.getPlayer(member.getUuid());
            if (teamMember != null) {
                if (teamMember.equals(player)) {
                    teamMember.sendMessage(StringUtils.color(iridiumTeams.getMessages().demotedPlayer
                            .replace("%player%", targetUser.getName())
                            .replace("%rank%", iridiumTeams.getUserRanks().get(nextRank).name)
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    ));
                } else {
                    teamMember.sendMessage(StringUtils.color(iridiumTeams.getMessages().userDemotedPlayer
                            .replace("%demoter%", player.getName())
                            .replace("%player%", targetUser.getName())
                            .replace("%rank%", iridiumTeams.getUserRanks().get(nextRank).name)
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    ));
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    private boolean DoesRankExist(int rank, IridiumTeams<T, U> iridiumTeams) {
        if (rank < 1) return false;
        return iridiumTeams.getUserRanks().containsKey(rank);
    }

    private boolean IsHigherRank(U target, U user) {
        if (target.getUserRank() == Rank.OWNER.getId()) return true;
        if (user.getUserRank() == Rank.OWNER.getId()) return false;
        if (user.isBypassing()) return false;
        return target.getUserRank() >= user.getUserRank();
    }

}
