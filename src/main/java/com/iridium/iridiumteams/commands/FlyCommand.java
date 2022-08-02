package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.EnhancementType;
import com.iridium.iridiumteams.enhancements.FlightEnhancementData;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
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
        Enhancement<FlightEnhancementData> flightEnhancement = iridiumTeams.getEnhancements().flightEnhancement;
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID());
        Optional<T> visitor = iridiumTeams.getTeamManager().getTeamViaLocation(player.getLocation());
        if (team.isPresent()) {
            TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(team.get(), "flight");
            if (flightEnhancement.type != EnhancementType.BOOSTER || teamEnhancement.isActive()) {
                FlightEnhancementData enhancementData = flightEnhancement.levels.get(teamEnhancement.getLevel());
                if (user.canApply(iridiumTeams, team.get(), enhancementData != null ? enhancementData.enhancementAffectsType : Collections.emptyList())) {
                    return true;
                }
            }
        }
        if (visitor.isPresent()) {
            TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(visitor.get(), "flight");
            FlightEnhancementData data = flightEnhancement.levels.get(teamEnhancement.getLevel());
            if (flightEnhancement.type != EnhancementType.BOOSTER || teamEnhancement.isActive()) {
                if (data == null) return false;
                return user.canApply(iridiumTeams, visitor.get(), data.enhancementAffectsType);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return Arrays.asList("enable", "disable", "on", "off");
    }
}
