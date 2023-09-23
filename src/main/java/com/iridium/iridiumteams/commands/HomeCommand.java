package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class HomeCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public HomeCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        Location home = team.getHome();
        if (home == null) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().homeNotSet
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (iridiumTeams.getTeamManager().getTeamViaLocation(home).map(T::getId).orElse(0) != team.getId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().homeNotInTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (iridiumTeams.getTeamManager().teleport(player, home, team)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teleportingHome
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
        }
        return true;
    }

}
