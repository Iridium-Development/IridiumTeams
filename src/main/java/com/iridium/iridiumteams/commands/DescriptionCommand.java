package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class DescriptionCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public DescriptionCommand(List<String> args, String description, String syntax, String permission){
        super(args, description, syntax, permission);
    }

    @Override
    public void execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length == 0) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.DESCRIPTION)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotChangeDescription
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        String description = String.join(" ", args);
        team.setDescription(description);
        iridiumTeams.getTeamManager().getTeamMembers(team).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(member ->
                member.sendMessage(StringUtils.color(iridiumTeams.getMessages().descriptionChanged
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%player%", player.getName())
                        .replace("%description%", description)
                ))
        );
    }

}
