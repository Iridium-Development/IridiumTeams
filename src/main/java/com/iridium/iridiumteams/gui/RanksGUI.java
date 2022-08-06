package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
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

public class RanksGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final IridiumTeams<T, U> iridiumTeams;
    private final T team;

    public RanksGUI(T team, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().ranksGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
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
        super.addContent(inventory);

        for (UserRank userRank : iridiumTeams.getUserRanks().values()) {
            inventory.setItem(userRank.item.slot, ItemStackUtils.makeItem(userRank.item));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        for (Map.Entry<Integer, UserRank> userRank : iridiumTeams.getUserRanks().entrySet()) {
            if (event.getSlot() != userRank.getValue().item.slot) continue;
            event.getWhoClicked().openInventory(new PermissionsGUI<>(team, userRank.getKey(), event.getWhoClicked().getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
            return;
        }
    }
}
