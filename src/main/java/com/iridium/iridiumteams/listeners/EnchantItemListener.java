
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
        XMaterial material = XMaterial.matchXMaterial(event.getItem().getType());
        iridiumTeams.getTeamManager().getTeamViaLocation(event.getEnchantBlock().getLocation()).ifPresent(team -> {
            iridiumTeams.getMissionManager().handleMissionUpdate(team, event.getEnchantBlock().getLocation().getWorld().getEnvironment(), "ENCHANT", material.name(), 1);
        });

    }

}
