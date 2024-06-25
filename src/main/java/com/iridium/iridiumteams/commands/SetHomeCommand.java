package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamLog;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor

public class SetHomeCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public SetHomeCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (iridiumTeams.getTeamManager().getTeamViaLocation(player.getLocation()).map(T::getId).orElse(0) != team.getId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notInTeamLand
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.SETHOME)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotSetHome
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        team.setHome(player.getLocation());
        iridiumTeams.getTeamManager().getTeamMembers(team).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(member ->
                member.sendMessage(StringUtils.color(iridiumTeams.getMessages().homeSet
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%player%", player.getName())
                ))
        );

        iridiumTeams.getTeamManager().addTeamLog(new TeamLog(
                team,
                player.getUniqueId(),
                "set_home",
                1,
                player.getLocation(),
                LocalDateTime.now(),
                String.format("%.0f, %.0f, %.0f", player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ())
        ));
        
        return true;
    }

}
