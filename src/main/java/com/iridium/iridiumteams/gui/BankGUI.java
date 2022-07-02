package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.bank.BankItem;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamBank;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;

@AllArgsConstructor
public class BankGUI<T extends Team, U extends IridiumUser<T>> implements GUI {

    private final T team;
    private final IridiumTeams<T, U> iridiumTeams;

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
        inventory.clear();
        InventoryUtils.fillInventory(inventory, iridiumTeams.getInventories().bankGUI.background);

        for (BankItem bankItem : iridiumTeams.getBankItemList()) {
            TeamBank teamBank = iridiumTeams.getTeamManager().getTeamBank(team, bankItem.getName());
            inventory.setItem(bankItem.getItem().slot, ItemStackUtils.makeItem(bankItem.getItem(), Collections.singletonList(
                    new Placeholder("amount", iridiumTeams.getConfiguration().numberFormatter.format(teamBank.getNumber()))
            )));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        Optional<BankItem> bankItem = iridiumTeams.getBankItemList().stream().filter(item -> item.getItem().slot == event.getSlot()).findFirst();
        if (!bankItem.isPresent()) return;
        U user = iridiumTeams.getUserManager().getUser((OfflinePlayer) event.getWhoClicked());

        switch (event.getClick()) {
            case LEFT:
                iridiumTeams.getCommands().withdrawCommand.execute(user, team, new String[]{bankItem.get().getName(), String.valueOf(bankItem.get().getDefaultAmount())}, iridiumTeams);
                break;
            case RIGHT:
                iridiumTeams.getCommands().depositCommand.execute(user, team, new String[]{bankItem.get().getName(), String.valueOf(bankItem.get().getDefaultAmount())}, iridiumTeams);
                break;
            case SHIFT_LEFT:
                iridiumTeams.getCommands().withdrawCommand.execute(user, team, new String[]{bankItem.get().getName(), String.valueOf(Double.MAX_VALUE)}, iridiumTeams);
                break;
            case SHIFT_RIGHT:
                iridiumTeams.getCommands().depositCommand.execute(user, team, new String[]{bankItem.get().getName(), String.valueOf(Double.MAX_VALUE)}, iridiumTeams);
                break;
        }

        addContent(event.getInventory());
    }
}
