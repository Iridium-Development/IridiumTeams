package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.PagedGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamTrust;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TrustsGUI<T extends Team, U extends IridiumUser<T>> extends PagedGUI<TeamTrust> {

    private final T team;
    private final IridiumTeams<T, U> iridiumTeams;

    public TrustsGUI(T team, Player player, IridiumTeams<T, U> iridiumTeams) {
        super(
                1,
                iridiumTeams.getInventories().trustsGUI.size,
                iridiumTeams.getInventories().trustsGUI.background,
                iridiumTeams.getInventories().previousPage,
                iridiumTeams.getInventories().nextPage,
                player,
                iridiumTeams.getInventories().backButton
        );
        this.team = team;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().trustsGUI;
        Inventory inventory = Bukkit.createInventory(this, getSize(), StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public Collection<TeamTrust> getPageObjects() {
        return iridiumTeams.getTeamManager().getTeamTrusts(team);
    }

    @Override
    public ItemStack getItemStack(TeamTrust teamTrust) {
        Optional<U> user = iridiumTeams.getUserManager().getUserByUUID(teamTrust.getUser());
        Optional<U> truster = iridiumTeams.getUserManager().getUserByUUID(teamTrust.getTruster());
        List<Placeholder> placeholderList = new ArrayList<>(iridiumTeams.getUserPlaceholderBuilder().getPlaceholders(user));
        placeholderList.add(new Placeholder("trusted_time", teamTrust.getTime().format(DateTimeFormatter.ofPattern(iridiumTeams.getConfiguration().dateTimeFormat))));
        placeholderList.add(new Placeholder("truster", truster.map(U::getName).orElse(iridiumTeams.getMessages().nullPlaceholder)));
        return ItemStackUtils.makeItem(iridiumTeams.getInventories().trustsGUI.item, placeholderList);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        TeamTrust teamTrust = getItem(event.getSlot());
        if (teamTrust == null) return;

        String username = iridiumTeams.getUserManager().getUserByUUID(teamTrust.getUser()).map(U::getName).orElse(iridiumTeams.getMessages().nullPlaceholder);
        iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().unTrustCommand, new String[]{username});
    }
}
