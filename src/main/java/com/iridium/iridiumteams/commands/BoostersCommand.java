package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.EnhancementType;
import com.iridium.iridiumteams.gui.BoostersGUI;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class BoostersCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public BoostersCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length == 0) {
            player.openInventory(new BoostersGUI<>(team, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
            return false;
        }
        if (args.length != 2 || !args[0].equalsIgnoreCase("buy")) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        String booster = args[1];
        Enhancement<?> enhancement = iridiumTeams.getEnhancementList().get(booster);
        if (enhancement == null || enhancement.type != EnhancementType.BOOSTER) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noSuchBooster
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        boolean success = iridiumTeams.getTeamManager().UpdateEnhancement(team, booster, player);
        if (success) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().purchasedBooster
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%booster%", booster)
            ));
        }
        return success;
    }

}
