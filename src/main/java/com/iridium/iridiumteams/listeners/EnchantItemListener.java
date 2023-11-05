
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

@AllArgsConstructor
public class EnchantItemListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorItemEnchant(EnchantItemEvent event) {
        U user = iridiumTeams.getUserManager().getUser(event.getEnchanter());
        XMaterial material = XMaterial.matchXMaterial(event.getItem().getType());
        iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).ifPresent(team -> {
            iridiumTeams.getMissionManager().handleMissionUpdate(team, event.getEnchanter().getLocation().getWorld(), "ENCHANT", material.name(), 1);
        });

    }

}
