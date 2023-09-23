package com.iridium.iridiumteams.commands;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.MissionTypeSelectorInventoryConfig;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.MissionGUI;
import com.iridium.iridiumteams.gui.MissionTypeSelectorGUI;
import com.iridium.iridiumteams.missions.MissionType;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class MissionsCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public MissionsCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        MissionTypeSelectorInventoryConfig missionTypeSelectorInventoryConfig = iridiumTeams.getInventories().missionTypeSelectorGUI;
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "daily":
                    if (missionTypeSelectorInventoryConfig.daily.enabled) {
                        player.openInventory(new MissionGUI<>(team, MissionType.DAILY, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
                    } else {
                        player.openInventory(new MissionTypeSelectorGUI<>(player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
                    }
                    return true;
                case "weekly":
                    if (missionTypeSelectorInventoryConfig.weekly.enabled) {
                        player.openInventory(new MissionGUI<>(team, MissionType.WEEKLY, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
                    } else {
                        player.openInventory(new MissionTypeSelectorGUI<>(player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
                    }
                    return true;
                case "infinite":
                    if (missionTypeSelectorInventoryConfig.infinite.enabled) {
                        player.openInventory(new MissionGUI<>(team, MissionType.INFINITE, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
                    } else {
                        player.openInventory(new MissionTypeSelectorGUI<>(player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
                    }
                    return true;
                case "once":
                    if (missionTypeSelectorInventoryConfig.once.enabled) {
                        player.openInventory(new MissionGUI<>(team, MissionType.ONCE, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
                    } else {
                        player.openInventory(new MissionTypeSelectorGUI<>(player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
                    }
                    return true;
            }
        }
        player.openInventory(new MissionTypeSelectorGUI<>(player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        MissionTypeSelectorInventoryConfig missionTypeSelectorInventoryConfig = iridiumTeams.getInventories().missionTypeSelectorGUI;
        List<String> missionTypes = new ArrayList<>();
        if (missionTypeSelectorInventoryConfig.daily.enabled) {
            missionTypes.add("Daily");
        }

        if (missionTypeSelectorInventoryConfig.weekly.enabled) {
            missionTypes.add("Weekly");
        }

        if (missionTypeSelectorInventoryConfig.infinite.enabled) {
            missionTypes.add("Infinite");
        }

        if (missionTypeSelectorInventoryConfig.once.enabled) {
            missionTypes.add("Once");
        }
        return missionTypes;
    }
}
