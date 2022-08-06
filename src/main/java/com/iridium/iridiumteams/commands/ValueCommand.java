package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class ValueCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public ValueCommand(List<String> args, String description, String syntax, String permission) {
        super(args, description, syntax, permission);
    }

    @Override
    public void execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamValue
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%value%", String.valueOf(iridiumTeams.getTeamManager().getTeamValue(team)))
        ));
    }

}
