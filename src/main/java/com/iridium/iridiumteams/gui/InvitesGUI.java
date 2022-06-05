package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.PagedGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamInvite;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public class InvitesGUI<T extends Team, U extends IridiumUser<T>> extends PagedGUI<TeamInvite> {

    private final T team;
    private final IridiumTeams<T, U> iridiumTeams;

    public InvitesGUI(T team, IridiumTeams<T, U> iridiumTeams) {
        super(1, iridiumTeams.getInventories().invitesGUI.size, iridiumTeams.getInventories().invitesGUI.background, iridiumTeams.getInventories().previousPage, iridiumTeams.getInventories().nextPage);
        this.team = team;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().invitesGUI;
        Inventory inventory = Bukkit.createInventory(this, getSize(), StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public Collection<TeamInvite> getPageObjects() {
        return iridiumTeams.getTeamManager().getTeamInvites(team);
    }

    @Override
    public ItemStack getItemStack(TeamInvite teamInvite) {
        Optional<U> user = iridiumTeams.getUserManager().getUserByUUID(teamInvite.getUser());
        return ItemStackUtils.makeItem(iridiumTeams.getInventories().invitesGUI.item, iridiumTeams.getUserPlaceholderBuilder().getPlaceholders(user));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);
        TeamInvite teamInvite = getItem(event.getSlot());
        if (teamInvite == null) return;

        String username = iridiumTeams.getUserManager().getUserByUUID(teamInvite.getUser()).map(U::getName).orElse("N/A");
        iridiumTeams.getCommands().unInviteCommand.execute(iridiumTeams.getUserManager().getUser((OfflinePlayer) event.getWhoClicked()), team, new String[]{username}, iridiumTeams);
    }
}
