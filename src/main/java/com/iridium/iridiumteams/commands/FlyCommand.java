package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.FlightEnhancementData;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class FlyCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public FlyCommand(List<String> args, String description, String syntax, String permission) {
        super(args, description, syntax, permission);
    }

    @Override
    public void execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();

        boolean flight = !user.isFlying();
        if (args.length == 1) {
            if (!args[0].equalsIgnoreCase("enable") && !args[0].equalsIgnoreCase("disable") && !args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off")) {
                player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
                return;
            }

            flight = args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on");
        }

        if (!canFly(player, iridiumTeams)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().flightNotActive.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }

        user.setFlying(flight);
        player.setAllowFlight(flight);
        player.setFlying(flight);

        if (flight) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().flightEnabled.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
        } else {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().flightDisabled.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
        }
    }

    @Override
    public boolean hasPermission(CommandSender commandSender, IridiumTeams<T, U> iridiumTeams) {
        return true;
    }

    public boolean canFly(Player player, IridiumTeams<T, U> iridiumTeams) {
        U user = iridiumTeams.getUserManager().getUser(player);
        if (player.hasPermission(permission)) return true;
        if (user.isBypassing()) return true;
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID());
        Optional<T> visitor = iridiumTeams.getTeamManager().getTeamViaLocation(player.getLocation());
        return canFly(user, team.orElse(null), iridiumTeams) || canFly(user, visitor.orElse(null), iridiumTeams);
    }

    private boolean canFly(U user, T team, IridiumTeams<T, U> iridiumTeams) {
        if (team == null) return false;
        Enhancement<FlightEnhancementData> flightEnhancement = iridiumTeams.getEnhancements().flightEnhancement;
        TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(team, "flight");
        FlightEnhancementData data = flightEnhancement.levels.get(teamEnhancement.getLevel());

        if (!teamEnhancement.isActive(flightEnhancement.type)) return false;
        if (data == null) return false;

        return user.canApply(iridiumTeams, team, data.enhancementAffectsType);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Arrays.asList("enable", "disable", "on", "off");
    }
}
