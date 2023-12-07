package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class PurgeCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public String adminPermission;

    public PurgeCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds, String adminPermission) {
        super(args, description, syntax, permission, cooldownInSeconds);
        this.adminPermission = adminPermission;
    }

    @Override
    public boolean execute(U user, String[] arguments, IridiumTeams<T, U> iridiumTeams) {

        double inactivity = iridiumTeams.getConfiguration().purgeInactiveAfter;

        Player player = user.getPlayer();
        // Purges all inactivity
        if(inactivity == 0.0) {
            // send message stating purging inactive is disabled
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().purgeInactive
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }

        int count = 0;
        for(T team : iridiumTeams.getTeamManager().getTeams()) {
            for(U teamMember : iridiumTeams.getTeamManager().getTeamMembers(team)) {
                if(teamMember.getPlayer().isOnline())
                    break;

                if(arguments.length > 1) {
                    inactivity = Double.parseDouble(arguments[0]);
                }

                if((java.lang.System.currentTimeMillis() - teamMember.getPlayer().getLastPlayed()) >= (inactivity * 86400000)) {
                    count++;
                    purge(team, user, iridiumTeams);
                }
            }
        }

        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().purgeComplete
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%amount%", String.valueOf(count))));

        return true;
    }

    public void purge(T team, U user, IridiumTeams<T, U> iridiumTeams) {
        iridiumTeams.getTeamManager().deleteTeam(team, user);
    }
}