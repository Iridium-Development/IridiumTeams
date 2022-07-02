package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
public class RenameCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public RenameCommand(List<String> args, String description, String syntax, String permission) {
        super(args, description, syntax, permission);
    }

    @Override
    public void execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length == 0) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.RENAME)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotChangeName
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        String name = String.join(" ", args);
        Optional<T> teamViaName = iridiumTeams.getTeamManager().getTeamViaName(name);
        if (teamViaName.isPresent() && teamViaName.get().getId() != team.getId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameAlreadyExists
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        if (name.length() < iridiumTeams.getConfiguration().minTeamNameLength) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameTooShort
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%min_length%", String.valueOf(iridiumTeams.getConfiguration().minTeamNameLength))
            ));
            return;
        }
        if (name.length() > iridiumTeams.getConfiguration().maxTeamNameLength) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameTooLong
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%max_length%", String.valueOf(iridiumTeams.getConfiguration().maxTeamNameLength))
            ));
            return;
        }
        team.setName(name);
        iridiumTeams.getTeamManager().getTeamMembers(team).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(member ->
                member.sendMessage(StringUtils.color(iridiumTeams.getMessages().nameChanged
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%player%", player.getName())
                        .replace("%name%", name)
                ))
        );
    }

}
