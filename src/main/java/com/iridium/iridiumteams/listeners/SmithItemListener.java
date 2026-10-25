package com.iridium.iridiumteams.listeners;

import com.cryptomorin.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class SmithItemListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorItemSmith(SmithItemEvent event) {
        ItemStack result = event.getCurrentItem();
        if (result == null) return;

        Player player = (Player) event.getWhoClicked();
        U user = iridiumTeams.getUserManager().getUser(player);
        XMaterial material = XMaterial.matchXMaterial(result.getType());

        iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).ifPresent(team ->
            iridiumTeams.getMissionManager().handleMissionUpdate(team, player.getLocation().getWorld(), "SMITH", material.name(), result.getAmount())
        );
    }
}