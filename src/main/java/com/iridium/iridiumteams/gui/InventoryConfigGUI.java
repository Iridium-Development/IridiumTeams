package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.configs.inventories.InventoryConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class InventoryConfigGUI implements GUI {

    private final InventoryConfig inventoryConfig;

    public InventoryConfigGUI(InventoryConfig inventoryConfig) {
        this.inventoryConfig = inventoryConfig;
    }


    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, inventoryConfig.size, StringUtils.color(inventoryConfig.title));

        addContent(inventory);

        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        InventoryUtils.fillInventory(inventory, inventoryConfig.background);

        inventoryConfig.items.values().forEach(item -> inventory.setItem(item.slot, ItemStackUtils.makeItem(item)));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        for (String command : inventoryConfig.items.keySet()) {
            if (inventoryConfig.items.get(command).slot == event.getSlot()) {
                Bukkit.getServer().dispatchCommand(event.getWhoClicked(), command);
            }
        }
    }
}
