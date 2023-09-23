package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.bank.BankItem;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamBank;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;

public class BankGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final T team;
    private final IridiumTeams<T, U> iridiumTeams;

    public BankGUI(T team, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().bankGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.team = team;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().bankGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        for (BankItem bankItem : iridiumTeams.getBankItemList()) {
            TeamBank teamBank = iridiumTeams.getTeamManager().getTeamBank(team, bankItem.getName());
            inventory.setItem(bankItem.getItem().slot, ItemStackUtils.makeItem(bankItem.getItem(), Collections.singletonList(
                    new Placeholder("amount", iridiumTeams.getConfiguration().numberFormatter.format(teamBank.getNumber()))
            )));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        Optional<BankItem> bankItem = iridiumTeams.getBankItemList().stream().filter(item -> item.getItem().slot == event.getSlot()).findFirst();
        if (!bankItem.isPresent()) return;

        switch (event.getClick()) {
            case LEFT:
                iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().withdrawCommand, new String[]{bankItem.get().getName(), String.valueOf(bankItem.get().getDefaultAmount())});
                break;
            case RIGHT:
                iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().depositCommand, new String[]{bankItem.get().getName(), String.valueOf(bankItem.get().getDefaultAmount())});
                break;
            case SHIFT_LEFT:
                iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().withdrawCommand, new String[]{bankItem.get().getName(), String.valueOf(Double.MAX_VALUE)});
                break;
            case SHIFT_RIGHT:
                iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().depositCommand, new String[]{bankItem.get().getName(), String.valueOf(Double.MAX_VALUE)});
                break;
        }

        addContent(event.getInventory());
    }
}
