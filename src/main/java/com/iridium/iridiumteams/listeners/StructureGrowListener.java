package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.Optional;

@AllArgsConstructor
public class StructureGrowListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onBlockSpread(StructureGrowEvent event) {
        int currentTeam = iridiumTeams.getTeamManager().getTeamViaLocation(event.getLocation()).map(T::getId).orElse(0);
        event.getBlocks().removeIf(blockState -> {
            Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(blockState.getLocation());
            return team.map(T::getId).orElse(currentTeam) != currentTeam;
        });
    }

}