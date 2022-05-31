
package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class SetPermissionCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public SetPermissionCommand() {
        super(Collections.singletonList("setpermission"), "Set your teams permissions", "%prefix% &7/team setpermission <permission> <role> (true/false)", "");
    }

    @Override
    public void execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 2 && (args.length != 3 || !args[2].equalsIgnoreCase("true") && !args[2].equalsIgnoreCase("false"))) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return;
        }
        Optional<String> permission = iridiumTeams.getPermissionList().keySet().stream()
                .filter(s -> s.equalsIgnoreCase(args[0]))
                .findFirst();
        if (!permission.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().invalidPermission
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        Optional<Integer> rank = iridiumTeams.getUserRanks().entrySet().stream()
                .filter(r -> r.getValue().name.equalsIgnoreCase(args[1]))
                .findAny()
                .map(Map.Entry::getKey);
        if (!rank.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().invalidUserRank
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        boolean allowed = args.length == 2 ? iridiumTeams.getTeamManager().getTeamPermission(team, rank.get(), permission.get()) : args[2].equalsIgnoreCase("true");
        if ((user.getUserRank() <= rank.get() && user.getUserRank() != -1) || !iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.CHANGE_PERMISSIONS) || rank.get() == -1) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotChangePermissions
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        iridiumTeams.getTeamManager().setTeamPermission(team, rank.get(), permission.get(), allowed);
        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().permissionSet
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%permission%", permission.get())
                .replace("%rank%", WordUtils.capitalizeFully(args[1]))
                .replace("%allowed%", String.valueOf(allowed))
        ));

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        // We currently don't want to tab-completion here
        // Return a new List so it isn't a list of online players
        if (args.length == 0) {
            return new ArrayList<>(iridiumTeams.getPermissionList().keySet());
        } else if (args.length == 1) {
            return iridiumTeams.getUserRanks().values().stream().map(userRank -> userRank.name).collect(Collectors.toList());
        }
        return Arrays.asList("true", "false");
    }

}
