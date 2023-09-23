package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamWarp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WarpsGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final T team;
    private final IridiumTeams<T, U> iridiumTeams;

    public WarpsGUI(T team, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().warpsGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.team = team;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().warpsGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        AtomicInteger atomicInteger = new AtomicInteger(1);
        List<TeamWarp> teamWarps = iridiumTeams.getTeamManager().getTeamWarps(team);
        for (TeamWarp teamWarp : teamWarps) {
            int slot = iridiumTeams.getConfiguration().teamWarpSlots.get(atomicInteger.getAndIncrement());
            ItemStack itemStack = ItemStackUtils.makeItem(iridiumTeams.getInventories().warpsGUI.item, Arrays.asList(
                    new Placeholder("island_name", team.getName()),
                    new Placeholder("warp_name", teamWarp.getName()),
                    new Placeholder("warp_description", teamWarp.getDescription() != null ? teamWarp.getDescription() : ""),
                    new Placeholder("warp_creator", Bukkit.getServer().getOfflinePlayer(teamWarp.getUser()).getName()),
                    new Placeholder("warp_create_time", teamWarp.getCreateTime().format(DateTimeFormatter.ofPattern(iridiumTeams.getConfiguration().dateTimeFormat)))
            ));
            Material material = teamWarp.getIcon().parseMaterial();
            if (material != null) itemStack.setType(material);
            inventory.setItem(slot, itemStack);
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        List<TeamWarp> teamWarps = iridiumTeams.getTeamManager().getTeamWarps(team);
        for (Map.Entry<Integer, Integer> entrySet : iridiumTeams.getConfiguration().teamWarpSlots.entrySet()) {
            if (entrySet.getValue() != event.getSlot()) continue;
            if (teamWarps.size() < entrySet.getKey()) continue;
            TeamWarp teamWarp = teamWarps.get(entrySet.getKey() - 1);
            switch (event.getClick()) {
                case LEFT:
                    iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().warpCommand, new String[]{teamWarp.getName()});
                    return;
                case RIGHT:
                    iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().deleteWarpCommand, new String[]{teamWarp.getName()});
            }
        }
    }
}
