package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.sorting.TeamSorting;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private TeamSorting<T> teamSorting;
    private final IridiumTeams<T, U> iridiumTeams;

    public TopGUI(TeamSorting<T> teamSorting, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().topGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.teamSorting = teamSorting;
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

        List<T> teams = teamSorting.getSortedTeams(iridiumTeams);

        for (int rank : iridiumTeams.getConfiguration().teamTopSlots.keySet()) {
            int slot = iridiumTeams.getConfiguration().teamTopSlots.get(rank);
            if (teams.size() >= rank) {
                T team = teams.get(rank - 1);
                inventory.setItem(slot, ItemStackUtils.makeItem(iridiumTeams.getInventories().topGUI.item, iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(team)));
            } else {
                inventory.setItem(slot, ItemStackUtils.makeItem(iridiumTeams.getInventories().topGUI.filler));
            }
        }
    }
}
