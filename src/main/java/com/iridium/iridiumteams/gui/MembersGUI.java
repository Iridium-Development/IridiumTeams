package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.PagedGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.UserRank;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

public class MembersGUI<T extends Team, U extends IridiumUser<T>> extends PagedGUI<U> {

    private final IridiumTeams<T, U> iridiumTeams;
    private final T team;

    public MembersGUI(T team, IridiumTeams<T, U> iridiumTeams) {
        super(1, iridiumTeams.getInventories().membersGUI.size, iridiumTeams.getInventories().membersGUI.background, iridiumTeams.getInventories().previousPage, iridiumTeams.getInventories().nextPage);
        this.iridiumTeams = iridiumTeams;
        this.team = team;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().membersGUI;
        Inventory inventory = Bukkit.createInventory(this, getSize(), StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public Collection<U> getPageObjects() {
        return iridiumTeams.getTeamManager().getTeamMembers(team);
    }

    @Override
    public ItemStack getItemStack(U user) {
        return ItemStackUtils.makeItem(iridiumTeams.getInventories().membersGUI.item, Arrays.asList(
                new Placeholder("player_name", user.getName()),
                new Placeholder("player_rank", iridiumTeams.getConfiguration().userRanks.getOrDefault(user.getUserRank(), new UserRank("N/A", null)).name),
                new Placeholder("player_join", user.getJoinTime().format(DateTimeFormatter.ofPattern(iridiumTeams.getConfiguration().dateTimeFormat)))
        ));
    }
}
