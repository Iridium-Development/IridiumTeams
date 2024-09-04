package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.configs.inventories.BlockValuesTypeSelectorInventoryConfig;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamSetting;
import com.iridium.iridiumteams.gui.BlockValueGUI;
import com.iridium.iridiumteams.gui.BlockValuesTypeSelectorGUI;
import com.iridium.iridiumteams.gui.SpawnerValueGUI;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class BlockValueCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public BlockValueCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, String[] args, IridiumTeams<T, U> iridiumTeams) {

        Player player = user.getPlayer();
        Optional<T> team;

        BlockValuesTypeSelectorInventoryConfig blockValuesTypeSelectorInventoryConfig = iridiumTeams.getInventories().blockValuesTypeSelectorGUI;

        String teamArg = args.length > 1 ? args[0] : player.getName();
        team = iridiumTeams.getTeamManager().getTeamViaNameOrPlayer(teamArg);

        if (!team.isPresent() && args.length >= 1) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamDoesntExistByName.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }

        if (!team.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().dontHaveTeam.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }

        TeamSetting teamSetting = iridiumTeams.getTeamManager().getTeamSetting(team.get(), SettingType.VALUE_VISIBILITY.getSettingKey());

        if (teamSetting != null && teamSetting.getValue().equalsIgnoreCase("Private") && !iridiumTeams.getTeamManager().getTeamMembers(team.get()).contains(user) && !user.isBypassing()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamIsPrivate.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }

        if (args.length == 0) {
            player.openInventory(new BlockValuesTypeSelectorGUI<>(teamArg, player, iridiumTeams).getInventory());
            return true;
        }

        switch (args[args.length - 1]) {
            case ("blocks"): {
                if (blockValuesTypeSelectorInventoryConfig.blocks.enabled) {
                    player.openInventory(new BlockValueGUI<>(team.get(), player, iridiumTeams).getInventory());
                    return true;
                }
            }
            case ("spawners"): {
                if (blockValuesTypeSelectorInventoryConfig.spawners.enabled) {
                    player.openInventory(new SpawnerValueGUI<>(team.get(), player, iridiumTeams).getInventory());
                    return true;
                }
            }
            default: {
                player.openInventory(new BlockValuesTypeSelectorGUI<>(teamArg, player, iridiumTeams).getInventory());
                return true;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {

        switch (args.length) {
            case 1:
                return Arrays.asList("blocks", "spawners");
            case 2:
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            default:
                return Collections.emptyList();
        }
    }
}