package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class DeleteCommand<T extends Team, U extends IridiumUser<T>> extends ConfirmableCommand<T, U> {
    public String adminPermission;

    public DeleteCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds, String adminPermission, boolean requiresConfirmation) {
        super(args, description, syntax, permission, cooldownInSeconds, requiresConfirmation);
        this.adminPermission = adminPermission;
    }

    @Override
    public boolean execute(U user, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (arguments.length == 1) {
            if (!player.hasPermission(adminPermission)) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noPermission
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return false;
            }

            Optional<T> team = iridiumTeams.getTeamManager().getTeamViaNameOrPlayer(arguments[0]);
            if (!team.isPresent()) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamDoesntExistByName
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return false;
            }
            return execute(user, team.get(), arguments, iridiumTeams);
        }
        return super.execute(user, arguments, iridiumTeams);
    }

    @Override
    protected boolean isCommandValid(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (arguments.length == 1) {
            return true;
        }

        if (user.getUserRank() != Rank.OWNER.getId() && !user.isBypassing()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotDeleteTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        return true;
    }

    @Override
    protected void executeAfterConfirmation(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        if (arguments.length == 1) {
            deleteTeam(user, team, iridiumTeams, true);
        }

        deleteTeam(user, team, iridiumTeams, false);
    }

    private void deleteTeam(U user, T team, IridiumTeams<T, U> iridiumTeams, boolean admin) {
        Player player = user.getPlayer();
        if (!iridiumTeams.getTeamManager().deleteTeam(team, user)) return;

        for (U member : iridiumTeams.getTeamManager().getTeamMembers(team)) {
            member.setTeamID(0);
            Player teamMember = member.getPlayer();
            if (teamMember != null) {
                teamMember.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamDeleted
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%player%", player.getName())
                ));
            }
        }
        if (admin) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().deletedPlayerTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%name%", team.getName())
            ));
        }
    }

}
