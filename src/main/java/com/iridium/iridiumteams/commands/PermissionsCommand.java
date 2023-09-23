package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.UserRank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.PermissionsGUI;
import com.iridium.iridiumteams.gui.RanksGUI;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class PermissionsCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public PermissionsCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length == 0) {
            player.openInventory(new RanksGUI<>(team, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
            return false;
        }
        String rank = args[0];
        for (Map.Entry<Integer, UserRank> userRank : iridiumTeams.getUserRanks().entrySet()) {
            if (!userRank.getValue().name.equalsIgnoreCase(rank)) continue;
            player.openInventory(new PermissionsGUI<>(team, userRank.getKey(), player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
            return true;
        }
        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().invalidUserRank.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return iridiumTeams.getUserRanks().values().stream().map(userRank -> userRank.name).collect(Collectors.toList());
    }

}
