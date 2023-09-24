package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.Shop;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ShopOverviewGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {
    private final IridiumTeams<T, U> iridiumTeams;

    public ShopOverviewGUI(Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().shopOverviewGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().shopOverviewGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        for (Shop.ShopCategory category : iridiumTeams.getShop().categories.values()) {
            inventory.setItem(category.item.slot, ItemStackUtils.makeItem(category.item));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        for (Map.Entry<String, Shop.ShopCategory> category : iridiumTeams.getShop().categories.entrySet()) {
            if (event.getSlot() != category.getValue().item.slot) continue;
            event.getWhoClicked().openInventory(new ShopCategoryGUI<>(category.getKey(), event.getWhoClicked().getOpenInventory().getTopInventory(), 1, iridiumTeams).getInventory());
            return;
        }
        super.onInventoryClick(event);
    }
}


