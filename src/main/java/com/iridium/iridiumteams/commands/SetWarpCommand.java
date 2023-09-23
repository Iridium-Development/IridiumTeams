package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.enhancements.WarpsEnhancementData;
import com.iridium.iridiumteams.utils.LocationUtils;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class SetWarpCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public SetWarpCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1 && args.length != 2) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        if (!LocationUtils.isSafe(player.getLocation(), iridiumTeams)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notSafe
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (iridiumTeams.getTeamManager().getTeamViaLocation(player.getLocation()).map(T::getId).orElse(0) != team.getId()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notInTeamLand
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.MANAGE_WARPS)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotManageWarps
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        WarpsEnhancementData data = iridiumTeams.getEnhancements().warpsEnhancement.levels.get(iridiumTeams.getTeamManager().getTeamEnhancement(team, "warps").getLevel());
        if (iridiumTeams.getTeamManager().getTeamWarps(team).size() >= (data == null ? 0 : data.warps)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().warpLimitReached
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        if (iridiumTeams.getTeamManager().getTeamWarp(team, args[0]).isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().warpAlreadyExists
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        iridiumTeams.getTeamManager().createWarp(team, player.getUniqueId(), player.getLocation(), args[0], args.length == 2 ? args[1] : null);
        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().createdWarp
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%name%", args[0])
        ));

        return true;
    }

}
