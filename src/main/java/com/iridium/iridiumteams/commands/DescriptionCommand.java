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
    public String adminPermission;

    public DescriptionCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds, String adminPermission) {
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
            String description = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            changeDescription(team.get(), description, player, iridiumTeams);
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().changedPlayerDescription
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%name%", team.get().getName())
                    .replace("%description%", description)
            ));
            return true;
        }
        return super.execute(user, args, iridiumTeams);
    }

    @Override
    public boolean execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.DESCRIPTION)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotChangeDescription
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        changeDescription(team, String.join(" ", arguments), player, iridiumTeams);
        return true;
    }

    private void changeDescription(T team, String description, Player player, IridiumTeams<T, U> iridiumTeams) {
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
