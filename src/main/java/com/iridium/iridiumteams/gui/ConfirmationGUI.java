package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class ConfirmationGUI<T extends Team, U extends IridiumUser<T>> implements GUI {

    private final @NotNull Runnable runnable;
    private final IridiumTeams<T, U> iridiumTeams;

    public ConfirmationGUI(@NotNull Runnable runnable, IridiumTeams<T, U> iridiumTeams) {
        this.runnable = runnable;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, iridiumTeams.getInventories().confirmationGUI.size, StringUtils.color(iridiumTeams.getInventories().confirmationGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        inventory.clear();
        InventoryUtils.fillInventory(inventory, iridiumTeams.getInventories().confirmationGUI.background);

        inventory.setItem(iridiumTeams.getInventories().confirmationGUI.no.slot, ItemStackUtils.makeItem(iridiumTeams.getInventories().confirmationGUI.no));
        inventory.setItem(iridiumTeams.getInventories().confirmationGUI.yes.slot, ItemStackUtils.makeItem(iridiumTeams.getInventories().confirmationGUI.yes));
    }

    /**
     * Called when there is a click in this GUI.
     * Cancelled automatically.
     *
     * @param event The InventoryClickEvent provided by Bukkit
     */
    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getSlot() == iridiumTeams.getInventories().confirmationGUI.no.slot) {
            player.closeInventory();
        } else if (event.getSlot() == iridiumTeams.getInventories().confirmationGUI.yes.slot) {
            runnable.run();
            player.closeInventory();
        }
    }
}
