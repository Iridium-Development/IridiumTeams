package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.gui.PagedGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamReward;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class RewardsGUI<T extends Team, U extends IridiumUser<T>> extends PagedGUI<TeamReward> {

    private final IridiumTeams<T, U> iridiumTeams;
    private final T team;

    public RewardsGUI(T team, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(
                1,
                iridiumTeams.getInventories().rewardsGUI.size,
                iridiumTeams.getInventories().rewardsGUI.background,
                iridiumTeams.getInventories().previousPage,
                iridiumTeams.getInventories().nextPage,
                previousInventory,
                iridiumTeams.getInventories().backButton
        );
        this.iridiumTeams = iridiumTeams;
        this.team = team;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);
        Item item = iridiumTeams.getInventories().rewardsGUI.item;
        inventory.setItem(item.slot, ItemStackUtils.makeItem(item));
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().rewardsGUI;
        Inventory inventory = Bukkit.createInventory(this, getSize(), StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public Collection<TeamReward> getPageObjects() {
        return iridiumTeams.getTeamManager().getTeamRewards(team);
    }

    @Override
    public ItemStack getItemStack(TeamReward teamReward) {
        return ItemStackUtils.makeItem(teamReward.getReward().item);
    }

    @Override
    public boolean isPaged() {
        return true;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        if(event.getSlot() == iridiumTeams.getInventories().rewardsGUI.item.slot){
            for(TeamReward teamReward : getPageObjects()){
                iridiumTeams.getTeamManager().claimTeamReward(teamReward, (Player) event.getWhoClicked());
            }
            return;
        }

        TeamReward teamReward = getItem(event.getSlot());
        if (teamReward == null) return;
        iridiumTeams.getTeamManager().claimTeamReward(teamReward, (Player) event.getWhoClicked());
    }
}
