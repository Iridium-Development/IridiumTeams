package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Permission;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class PermissionsGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final IridiumTeams<T, U> iridiumTeams;
    private final T team;
    @Getter
    private final int rank;
    @Getter
    private int page;

    public PermissionsGUI(T team, int rank, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().permissionsGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.iridiumTeams = iridiumTeams;
        this.team = team;
        this.rank = rank;
        this.page = 1;
    }

    public PermissionsGUI(T team, int rank, int page, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().permissionsGUI.background, previousInventory, iridiumTeams.getInventories().backButton);

        this.iridiumTeams = iridiumTeams;
        this.team = team;
        this.rank = rank;
        this.page = page;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, iridiumTeams.getInventories().permissionsGUI.size, StringUtils.color(iridiumTeams.getInventories().permissionsGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        for (Map.Entry<String, Permission> permission : iridiumTeams.getPermissionList().entrySet()) {
            if (permission.getValue().getPage() != page) continue;
            boolean allowed = iridiumTeams.getTeamManager().getTeamPermission(team, rank, permission.getKey());
            inventory.setItem(permission.getValue().getItem().slot, ItemStackUtils.makeItem(permission.getValue().getItem(), Collections.singletonList(new Placeholder("permission", allowed ? iridiumTeams.getPermissions().allowed : iridiumTeams.getPermissions().denied))));
        }

        inventory.setItem(inventory.getSize() - 3, ItemStackUtils.makeItem(iridiumTeams.getInventories().nextPage));
        inventory.setItem(inventory.getSize() - 7, ItemStackUtils.makeItem(iridiumTeams.getInventories().previousPage));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        if (event.getSlot() == iridiumTeams.getInventories().permissionsGUI.size - 7 && page > 1) {
            page--;
            event.getWhoClicked().openInventory(getInventory());
            return;
        }

        if (event.getSlot() == iridiumTeams.getInventories().permissionsGUI.size - 3 && iridiumTeams.getPermissionList().values().stream().anyMatch(permission -> permission.getPage() == page + 1)) {
            page++;
            event.getWhoClicked().openInventory(getInventory());
        }

        for (Map.Entry<String, Permission> permission : iridiumTeams.getPermissionList().entrySet()) {
            if (permission.getValue().getItem().slot != event.getSlot()) continue;
            if (permission.getValue().getPage() != page) continue;

            iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().setPermissionCommand, new String[]{permission.getKey(), iridiumTeams.getUserRanks().get(rank).name});
            return;
        }
    }
}
