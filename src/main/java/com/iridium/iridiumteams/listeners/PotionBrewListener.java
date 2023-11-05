
package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

@AllArgsConstructor
public class PotionBrewListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorPotionBrew(BrewEvent event) {
        Bukkit.getScheduler().runTask(iridiumTeams, () -> iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation()).ifPresent(team -> {
            for (int i = 0; i < 3; i++) {
                ItemStack itemStack = event.getContents().getItem(i);
                if (itemStack != null && itemStack.getItemMeta() instanceof PotionMeta) {
                    PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                    iridiumTeams.getMissionManager().handleMissionUpdate(team, event.getBlock().getLocation().getWorld(), "BREW", potionMeta.getBasePotionData().getType() + ":" + (potionMeta.getBasePotionData().isUpgraded() ? 2 : 1), 1);
                }
            }
        }));
    }

}
