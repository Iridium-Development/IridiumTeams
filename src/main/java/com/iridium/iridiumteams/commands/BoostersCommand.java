package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.EnhancementType;
import com.iridium.iridiumteams.gui.BoostersGUI;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class BoostersCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public BoostersCommand(List<String> args, String description, String syntax, String permission) {
        super(args, description, syntax, permission);
    }

    @Override
    public void execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length == 0) {
            player.openInventory(new BoostersGUI<>(team, iridiumTeams).getInventory());
            return;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("buy")) {
            String booster = args[1];
            Enhancement<?> enhancement = iridiumTeams.getEnhancementList().get(booster);
            if (enhancement == null || enhancement.type != EnhancementType.BOOSTER) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noSuchBooster
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return;
            }
            TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(team, booster);
            if (teamEnhancement.isActive()) {
                if (enhancement.levels.containsKey(teamEnhancement.getLevel() + 1)) {
                    teamEnhancement.setLevel(teamEnhancement.getLevel() + 1);
                }
            } else {
                teamEnhancement.setStartTime(LocalDateTime.now().plusHours(1));
            }
        }
        player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
    }

}
