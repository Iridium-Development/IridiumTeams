package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.sorting.TeamSorting;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
public class TopGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private TeamSorting<T> sortingType;
    private int page = 1;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private final IridiumTeams<T, U> iridiumTeams;

    public TopGUI(TeamSorting<T> sortingType, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().topGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.sortingType = sortingType;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().topGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        List<T> teams = iridiumTeams.getTeamManager().getTeams(sortingType, true);

        for (int rank : iridiumTeams.getConfiguration().teamTopSlots.keySet()) {
            int slot = iridiumTeams.getConfiguration().teamTopSlots.get(rank);
            int actualRank = rank + (iridiumTeams.getConfiguration().teamTopSlots.size() * (page - 1));
            if (teams.size() >= actualRank) {
                T team = teams.get(actualRank - 1);
                inventory.setItem(slot, ItemStackUtils.makeItem(iridiumTeams.getInventories().topGUI.item, iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(team)));
            } else {
                inventory.setItem(slot, ItemStackUtils.makeItem(iridiumTeams.getInventories().topGUI.filler));
            }
        }

        for (TeamSorting<T> sortingType : iridiumTeams.getSortingTypes()) {
            inventory.setItem(sortingType.getItem().slot, ItemStackUtils.makeItem(sortingType.getItem()));
        }

        inventory.setItem(inventory.getSize() - 3, ItemStackUtils.makeItem(iridiumTeams.getInventories().nextPage));
        inventory.setItem(inventory.getSize() - 7, ItemStackUtils.makeItem(iridiumTeams.getInventories().previousPage));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        if (event.getSlot() == iridiumTeams.getInventories().topGUI.size - 7 && page > 1) {
            page--;
            event.getWhoClicked().openInventory(getInventory());
            return;
        }

        if (event.getSlot() == iridiumTeams.getInventories().topGUI.size - 3 && iridiumTeams.getTeamManager().getTeams().size() >= 1 + (iridiumTeams.getConfiguration().teamTopSlots.size() * page)) {
            page++;
            event.getWhoClicked().openInventory(getInventory());
        }

        iridiumTeams.getSortingTypes().stream().filter(sorting -> sorting.item.slot == event.getSlot()).findFirst().ifPresent(sortingType -> {
            this.sortingType = sortingType;
            addContent(event.getInventory());
        });
    }
}
