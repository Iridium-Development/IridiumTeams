
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

@AllArgsConstructor
public class PlayerFishListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorPlayerFish(PlayerFishEvent event) {
        Entity caughtEntity = event.getCaught();
        if (caughtEntity == null || event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        U user = iridiumTeams.getUserManager().getUser(event.getPlayer());

        iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).ifPresent(team -> {
            iridiumTeams.getMissionManager().handleMissionUpdate(team, caughtEntity.getLocation().getWorld(), "FISH", ((Item) caughtEntity).getItemStack().getType().name(), 1);
        });

    }

}
