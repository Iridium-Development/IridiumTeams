package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamSetting;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

@AllArgsConstructor
public class LeavesDecayListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onLeavesDecay(LeavesDecayEvent event) {
        iridiumTeams.getLogger().info("Leaves Decay event called");
        iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation()).ifPresent(team -> {
            TeamSetting teamSetting = iridiumTeams.getTeamManager().getTeamSetting(team, SettingType.LEAF_DECAY.getSettingKey());
            iridiumTeams.getLogger().info("Team Setting: " + teamSetting.getValue());
            if (teamSetting.getValue().equalsIgnoreCase("Disabled")) {
                iridiumTeams.getLogger().info("Cancelling event...");
                event.setCancelled(true);
            }
        });
    }
}
