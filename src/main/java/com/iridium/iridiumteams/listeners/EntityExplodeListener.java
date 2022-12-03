
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class EntityExplodeListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!iridiumTeams.getConfiguration().preventTntGriefing) return;
        List<MetadataValue> list = event.getEntity().getMetadata("team_spawned");
        int currentTeamId = list.stream().map(MetadataValue::asInt).findFirst().orElse(0);

        event.blockList().removeIf(blockState -> {
            Optional<T> team = iridiumTeams.getTeamManager().getTeamViaLocation(blockState.getLocation());
            return team.map(T::getId).orElse(currentTeamId) != currentTeamId;
        });
    }

}
