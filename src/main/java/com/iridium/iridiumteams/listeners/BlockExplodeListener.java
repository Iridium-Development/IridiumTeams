
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamSetting;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

import java.util.Optional;

@AllArgsConstructor
public class BlockExplodeListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockExplode(BlockExplodeEvent event) {

        if (!iridiumTeams.getConfiguration().preventTntGriefing) return;
        Optional<T> currentTeam = iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation());

        if (currentTeam.isPresent()) {
            TeamSetting teamSetting = iridiumTeams.getTeamManager().getTeamSetting(currentTeam.get(), SettingType.TNT_DAMAGE.getSettingKey());
            if (teamSetting.getValue().equalsIgnoreCase("Disabled")) {
                event.setCancelled(true);
                return;
            }
        }

        int currentTeamId = currentTeam.map(T::getId).orElse(0);

        event.blockList().removeIf(blockState -> {
            Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(blockState.getLocation());
            return team.map(T::getId).orElse(currentTeamId) != currentTeamId;
        });
    }

}
