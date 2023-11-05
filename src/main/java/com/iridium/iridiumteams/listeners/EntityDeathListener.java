
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@AllArgsConstructor
public class EntityDeathListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if(killer==null)return;
        U user = iridiumTeams.getUserManager().getUser(killer);
        iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).ifPresent(team -> {
            iridiumTeams.getMissionManager().handleMissionUpdate(team, killer.getLocation().getWorld(), "KILL", event.getEntityType().name(), 1);
        });

    }

}
