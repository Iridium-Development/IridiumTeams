package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

import java.util.Optional;

@AllArgsConstructor
public class BlockSpreadListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onBlockSpread(BlockSpreadEvent event) {
        int currentTeam = iridiumTeams.getTeamManager().getTeamViaLocation(event.getSource().getLocation()).map(T::getId).orElse(0);
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation());
        if (team.map(T::getId).orElse(currentTeam) != currentTeam) {
            event.setCancelled(true);
        }
    }

}