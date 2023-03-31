package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.gui.ClosableGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof ClosableGUI) {
            ((ClosableGUI) event.getInventory().getHolder()).onInventoryClose(event);
        }
    }
}
