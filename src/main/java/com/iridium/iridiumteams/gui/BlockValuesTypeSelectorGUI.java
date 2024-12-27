package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.BlockValuesTypeSelectorInventoryConfig;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class BlockValuesTypeSelectorGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final IridiumTeams<T, U> iridiumTeams;
    private final String teamArg;

    public BlockValuesTypeSelectorGUI(String teamArg, Player player, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().blockValuesTypeSelectorGUI.background, player, iridiumTeams.getInventories().backButton);
        this.iridiumTeams = iridiumTeams;
        this.teamArg = teamArg;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().blockValuesTypeSelectorGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        BlockValuesTypeSelectorInventoryConfig blockValuesTypeSelectorInventoryConfig = iridiumTeams.getInventories().blockValuesTypeSelectorGUI;
        if (blockValuesTypeSelectorInventoryConfig.blocks.enabled) {
            inventory.setItem(blockValuesTypeSelectorInventoryConfig.blocks.item.slot, ItemStackUtils.makeItem(blockValuesTypeSelectorInventoryConfig.blocks.item));
        }

        if (blockValuesTypeSelectorInventoryConfig.spawners.enabled) {
            inventory.setItem(blockValuesTypeSelectorInventoryConfig.spawners.item.slot, ItemStackUtils.makeItem(blockValuesTypeSelectorInventoryConfig.spawners.item));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);
        BlockValuesTypeSelectorInventoryConfig blockValuesTypeSelectorInventoryConfig = iridiumTeams.getInventories().blockValuesTypeSelectorGUI;

        if (event.getSlot() == blockValuesTypeSelectorInventoryConfig.blocks.item.slot && blockValuesTypeSelectorInventoryConfig.blocks.enabled) {
            iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().blockValueCommand, new String[]{"blocks", teamArg});
        }

        if (event.getSlot() == blockValuesTypeSelectorInventoryConfig.spawners.item.slot && blockValuesTypeSelectorInventoryConfig.spawners.enabled) {
            iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().blockValueCommand, new String[]{"spawners", teamArg});
        }
    }
}
