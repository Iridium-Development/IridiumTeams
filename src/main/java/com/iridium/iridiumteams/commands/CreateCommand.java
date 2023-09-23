package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class CreateCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public CreateCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().alreadyHaveTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        if (args.length < 1) {
            if (iridiumTeams.getConfiguration().createRequiresName) {
                player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
                return false;
            }
            iridiumTeams.getTeamManager().createTeam(player, null).thenAccept(team -> {
                if (team == null) return;
                user.setUserRank(Rank.OWNER.getId());
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamCreated
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                getCooldownProvider().applyCooldown(player);
            });
            return false;
        }

        String teamName = String.join(" ", args);
        if (teamName.length() < iridiumTeams.getConfiguration().minTeamNameLength) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameTooShort
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%min_length%", String.valueOf(iridiumTeams.getConfiguration().minTeamNameLength))
            ));
            return false;
        }
        if (teamName.length() > iridiumTeams.getConfiguration().maxTeamNameLength) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameTooLong
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%max_length%", String.valueOf(iridiumTeams.getConfiguration().maxTeamNameLength))
            ));
            return false;
        }
        if (iridiumTeams.getTeamManager().getTeamViaName(teamName).isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameAlreadyExists
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        iridiumTeams.getTeamManager().createTeam(player, teamName).thenAccept(team -> {
            if (team == null) return;
            user.setUserRank(Rank.OWNER.getId());
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamCreated
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            getCooldownProvider().applyCooldown(player);
        });
        return false;
    }

}
