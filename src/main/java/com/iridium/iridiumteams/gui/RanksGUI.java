package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.UserRank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RanksGUI<T extends Team, U extends IridiumUser<T>> implements GUI {

    private final IridiumTeams<T, U> iridiumTeams;
    private final T team;

    public RanksGUI(T team, IridiumTeams<T, U> iridiumTeams) {
        this.team = team;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, iridiumTeams.getInventories().ranksGUI.size, StringUtils.color(iridiumTeams.getInventories().ranksGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        InventoryUtils.fillInventory(inventory, iridiumTeams.getInventories().ranksGUI.background);
        for (UserRank userRank : iridiumTeams.getUserRanks().values()) {
            inventory.setItem(userRank.item.slot, ItemStackUtils.makeItem(userRank.item));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        for (Map.Entry<Integer, UserRank> userRank : iridiumTeams.getUserRanks().entrySet()) {
            if (event.getSlot() != userRank.getValue().item.slot) continue;
            event.getWhoClicked().openInventory(new PermissionsGUI<>(team, userRank.getKey(), iridiumTeams).getInventory());
            return;
        }
    }
}
