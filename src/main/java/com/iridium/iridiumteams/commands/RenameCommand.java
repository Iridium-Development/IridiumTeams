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
public class RenameCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    private String adminPermission;
    public RenameCommand(List<String> args, String description, String syntax, String permission, String adminPermission) {
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
        String name;
        boolean admin = false;

        if (team.isPresent() && player.hasPermission(adminPermission)) {
            name = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            admin = true;
        } else {
            team = iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID());
            if (!team.isPresent()) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().dontHaveTeam
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix))
                );
                return;
            }
            if (!iridiumTeams.getTeamManager().getTeamPermission(team.get(), user, PermissionType.RENAME)) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotChangeName
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return;
            }
            name = String.join(" ", args);
        }

        Optional<T> teamViaName = iridiumTeams.getTeamManager().getTeamViaName(name);
        if (teamViaName.isPresent() && teamViaName.get().getId() != team.get().getId()) {
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
        team.get().setName(name);
        if(admin){
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().changedPlayerName
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%name%", team.get().getName())
                    .replace("%player%", args[0])
            ));
        }
        iridiumTeams.getTeamManager().getTeamMembers(team.get()).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(member ->
                member.sendMessage(StringUtils.color(iridiumTeams.getMessages().nameChanged
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%player%", player.getName())
                        .replace("%name%", name)
                ))
        );
    }

}
