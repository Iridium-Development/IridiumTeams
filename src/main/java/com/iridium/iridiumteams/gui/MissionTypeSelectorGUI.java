package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.MissionTypeSelectorInventoryConfig;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class MissionTypeSelectorGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final IridiumTeams<T, U> iridiumTeams;

    public MissionTypeSelectorGUI(Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().missionTypeSelectorGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().missionTypeSelectorGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);


        MissionTypeSelectorInventoryConfig missionTypeSelectorInventoryConfig = iridiumTeams.getInventories().missionTypeSelectorGUI;
        if (missionTypeSelectorInventoryConfig.daily.enabled) {
            inventory.setItem(missionTypeSelectorInventoryConfig.daily.item.slot, ItemStackUtils.makeItem(missionTypeSelectorInventoryConfig.daily.item));
        }

        if (missionTypeSelectorInventoryConfig.weekly.enabled) {
            inventory.setItem(missionTypeSelectorInventoryConfig.weekly.item.slot, ItemStackUtils.makeItem(missionTypeSelectorInventoryConfig.weekly.item));
        }

        if (missionTypeSelectorInventoryConfig.infinite.enabled) {
            inventory.setItem(missionTypeSelectorInventoryConfig.infinite.item.slot, ItemStackUtils.makeItem(missionTypeSelectorInventoryConfig.infinite.item));
        }

        if (missionTypeSelectorInventoryConfig.once.enabled) {
            inventory.setItem(missionTypeSelectorInventoryConfig.once.item.slot, ItemStackUtils.makeItem(missionTypeSelectorInventoryConfig.once.item));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);
        MissionTypeSelectorInventoryConfig missionTypeSelectorInventoryConfig = iridiumTeams.getInventories().missionTypeSelectorGUI;

        if (event.getSlot() == missionTypeSelectorInventoryConfig.daily.item.slot && missionTypeSelectorInventoryConfig.daily.enabled) {
            iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().missionsCommand, new String[]{"Daily"});
        }

        if (event.getSlot() == missionTypeSelectorInventoryConfig.weekly.item.slot && missionTypeSelectorInventoryConfig.weekly.enabled) {
            iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().missionsCommand, new String[]{"Weekly"});
        }

        if (event.getSlot() == missionTypeSelectorInventoryConfig.infinite.item.slot && missionTypeSelectorInventoryConfig.infinite.enabled) {
            iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().missionsCommand, new String[]{"Infinite"});
        }

        if (event.getSlot() == missionTypeSelectorInventoryConfig.once.item.slot && missionTypeSelectorInventoryConfig.once.enabled) {
            iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().missionsCommand, new String[]{"Once"});
        }
    }
}
