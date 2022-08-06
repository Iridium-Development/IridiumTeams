package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.ConfirmationGUI;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class DeleteCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public DeleteCommand(List<String> args, String description, String syntax, String permission) {
        super(args, description, syntax, permission);
    }

    @Override
    public void execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (user.getUserRank() != Rank.OWNER.getId() && !user.isBypassing()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotDeleteTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }

        player.openInventory(new ConfirmationGUI<>(() -> {
            iridiumTeams.getTeamManager().deleteTeam(team, user);
            iridiumTeams.getTeamManager().getTeamMembers(team).stream()
                    .map(U::getPlayer)
                    .filter(Objects::nonNull)
                    .forEach(member -> member.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamDeleted
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                            .replace("%player%", player.getName())
                    )));
        }, iridiumTeams).getInventory());
    }

}
