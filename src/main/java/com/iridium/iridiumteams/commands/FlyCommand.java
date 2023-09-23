package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class FlyCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public FlyCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();

        boolean flight = !user.isFlying();
        if (args.length == 1) {
            if (!args[0].equalsIgnoreCase("enable") && !args[0].equalsIgnoreCase("disable") && !args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off")) {
                player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
                return false;
            }

            flight = args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on");
        }

        if (!canFly(player, iridiumTeams)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().flightNotActive.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }

        user.setFlying(flight);
        player.setAllowFlight(flight);
        player.setFlying(flight);

        if (flight) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().flightEnabled.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
        } else {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().flightDisabled.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
        }
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender commandSender, IridiumTeams<T, U> iridiumTeams) {
        return true;
    }

    public boolean canFly(Player player, IridiumTeams<T, U> iridiumTeams) {
        U user = iridiumTeams.getUserManager().getUser(player);
        return user.canFly(iridiumTeams);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Arrays.asList("enable", "disable", "on", "off");
    }
}
