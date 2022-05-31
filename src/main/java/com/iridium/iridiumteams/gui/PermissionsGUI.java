package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.GUI;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Permission;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

@Getter
public class PermissionsGUI<T extends Team, U extends IridiumUser<T>> implements GUI {

    private final IridiumTeams<T, U> iridiumTeams;
    private final T team;
    private final int rank;
    private int page;

    public PermissionsGUI(T team, int rank, IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
        this.team = team;
        this.rank = rank;
        this.page = 1;
    }

    public PermissionsGUI(T team, int rank,int page, IridiumTeams<T, U> iridiumTeams) {
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
        InventoryUtils.fillInventory(inventory, iridiumTeams.getInventories().permissionsGUI.background);

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

            U user = iridiumTeams.getUserManager().getUser((Player) event.getWhoClicked());
            if ((user.getUserRank() <= rank && user.getUserRank() != Rank.OWNER.getId()) || !iridiumTeams.getTeamManager().getTeamPermission(team, user, PermissionType.CHANGE_PERMISSIONS) || rank == Rank.OWNER.getId()) {
                event.getWhoClicked().sendMessage(StringUtils.color(iridiumTeams.getMessages().cannotChangePermissions.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            } else {
                boolean allowed = iridiumTeams.getTeamManager().getTeamPermission(team, rank, permission.getKey());
                iridiumTeams.getTeamManager().setTeamPermission(team, rank, permission.getKey(), !allowed);
                event.getWhoClicked().openInventory(getInventory());
            }
            return;
        }
    }
}
