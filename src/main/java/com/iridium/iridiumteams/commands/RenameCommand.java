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
    public String adminPermission;

    public RenameCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds, String adminPermission) {
        super(args, description, syntax, permission, cooldownInSeconds);
        this.adminPermission = adminPermission;
    }

    @Override
    public boolean execute(U user, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length == 0) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaNameOrPlayer(args[0]);
        if (team.isPresent() && player.hasPermission(adminPermission)) {
            String name = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            if(changeName(team.get(), name, player, iridiumTeams)){
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().changedPlayerName
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%name%", team.get().getName())
                        .replace("%player%", args[0])
                ));
                return true;
            }
            return false;
        }
        return super.execute(user, args, iridiumTeams);
    }

    @Override
    public boolean execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.RENAME)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotChangeName
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        return changeName(team, String.join(" ", arguments), player, iridiumTeams);
    }

    private boolean changeName(T team, String name, Player player, IridiumTeams<T, U> iridiumTeams) {
        Optional<T> teamViaName = iridiumTeams.getTeamManager().getTeamViaName(name);
        if (teamViaName.isPresent() && teamViaName.get().getId() != team.getId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameAlreadyExists
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (name.length() < iridiumTeams.getConfiguration().minTeamNameLength) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameTooShort
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%min_length%", String.valueOf(iridiumTeams.getConfiguration().minTeamNameLength))
            ));
            return false;
        }
        if (name.length() > iridiumTeams.getConfiguration().maxTeamNameLength) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamNameTooLong
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%max_length%", String.valueOf(iridiumTeams.getConfiguration().maxTeamNameLength))
            ));
            return false;
        }
        team.setName(name);
        iridiumTeams.getTeamManager().getTeamMembers(team).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(member ->
                member.sendMessage(StringUtils.color(iridiumTeams.getMessages().nameChanged
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%player%", player.getName())
                        .replace("%name%", name)
                ))
        );
        return true;
    }

}
