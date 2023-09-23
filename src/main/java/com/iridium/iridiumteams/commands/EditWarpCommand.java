package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamWarp;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class EditWarpCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public EditWarpCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length < 2) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        if (!iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.MANAGE_WARPS)) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotManageWarps
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        Optional<TeamWarp> teamWarp = iridiumTeams.getTeamManager().getTeamWarp(team, args[0]);
        if (!teamWarp.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().unknownWarp
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        switch (args[1]) {
            case "icon":
                if (args.length != 3) {
                    player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
                    return false;
                }

                Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(args[2]);
                if (!xMaterial.isPresent()) {
                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noSuchMaterial
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    ));
                    return false;
                }
                teamWarp.get().setIcon(xMaterial.get());
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().warpIconSet
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return true;
            case "description":
                if (args.length < 3) {
                    player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
                    return false;
                }

                String description = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                teamWarp.get().setDescription(description);
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().warpDescriptionSet
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return true;
            default:
                player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        List<TeamWarp> teamWarps = iridiumTeams.getTeamManager().getTeamWarps(team);
        switch (args.length) {
            case 1:
                return teamWarps.stream().map(TeamWarp::getName).collect(Collectors.toList());
            case 2:
                return Arrays.asList("icon", "description");
            case 3:
                if (args[1].equalsIgnoreCase("icon")) {
                    return Arrays.stream(XMaterial.values()).map(XMaterial::name).collect(Collectors.toList());
                }
            default:
                return Collections.emptyList();
        }
    }
}
