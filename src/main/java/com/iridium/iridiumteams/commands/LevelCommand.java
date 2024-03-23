package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.Placeholder;
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
public class LevelCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public LevelCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();

        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID());

        if (!team.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().dontHaveTeam
                    .replace("%prefix", iridiumTeams.getConfiguration().prefix)));
            return false;
        }

        List<Placeholder> placeholderList = iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(team);
        player.sendMessage(StringUtils.color(StringUtils.getCenteredMessage(StringUtils.processMultiplePlaceholders(iridiumTeams.getConfiguration().teamInfoTitle, placeholderList), iridiumTeams.getConfiguration().teamInfoTitleFiller)));
        for (String line : iridiumTeams.getConfiguration().levelInfo) {
            player.sendMessage(StringUtils.color(StringUtils.processMultiplePlaceholders(line, placeholderList)));
        }
        return true;
    }
}