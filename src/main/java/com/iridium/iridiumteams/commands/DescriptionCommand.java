package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
public class DescriptionCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    private String adminPermission;

    public DescriptionCommand(List<String> args, String description, String syntax, String permission, String adminPermission) {
        super(args, description, syntax, permission);
        this.adminPermission = adminPermission;
    }

    @Override
    public void execute(U user, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length == 0) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaNameOrPlayer(args[0]);
        String description;
        if (team.isPresent() && player.hasPermission(adminPermission)) {
            description = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().changedPlayerDescription
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%name%", team.get().getName())
                    .replace("%description%", description)
            ));
        } else {
            team = iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID());
            if (!team.isPresent()) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().dontHaveTeam
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix))
                );
                return;
            }
            if (!iridiumTeams.getTeamManager().getTeamPermission(team.get(), user, PermissionType.DESCRIPTION)) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotChangeDescription
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return;
            }
            description = String.join(" ", args);
        }
        team.get().setDescription(description);
        iridiumTeams.getTeamManager().getTeamMembers(team.get()).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(member ->
                member.sendMessage(StringUtils.color(iridiumTeams.getMessages().descriptionChanged
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%player%", player.getName())
                        .replace("%description%", description)
                ))
        );
    }

}
