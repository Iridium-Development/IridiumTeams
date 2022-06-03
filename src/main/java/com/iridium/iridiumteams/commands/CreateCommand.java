package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.CreateCancelledException;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.entity.Player;

import java.util.Collections;

public class CreateCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public CreateCommand() {
        super(Collections.singletonList("create"), "Create a new team", "%prefix% &7/team create <name>", "");
    }

    @Override
    public void execute(U user, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length < 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        if (iridiumTeams.getTeamManager().getTeamViaID(user.getTeam().map(T::getId).orElse(0)).isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().alreadyHaveTeam.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }

        String teamName = String.join(" ", args);
        if (teamName.length() < iridiumTeams.getConfiguration().minTeamNameLength) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameTooShort
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%min_length%", String.valueOf(iridiumTeams.getConfiguration().minTeamNameLength))
            ));
            return;
        }
        if (teamName.length() > iridiumTeams.getConfiguration().maxTeamNameLength) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameTooLong
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%max_length%", String.valueOf(iridiumTeams.getConfiguration().maxTeamNameLength))
            ));
            return;
        }
        if (iridiumTeams.getTeamManager().getTeamViaName(teamName).isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameAlreadyExists.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        try {
            iridiumTeams.getTeamManager().createTeam(player, teamName).thenAccept(team -> {
                user.setUserRank(Rank.OWNER.getId());
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamCreated.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            });
        } catch (CreateCancelledException ignored) {
            //The create command has been cancelled, ignore
        }
    }

}
