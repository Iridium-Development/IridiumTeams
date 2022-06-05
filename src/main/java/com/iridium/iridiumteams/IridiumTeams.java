package com.iridium.iridiumteams;

import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumteams.bank.BankItem;
import com.iridium.iridiumteams.configs.*;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.listeners.BlockBreakListener;
import com.iridium.iridiumteams.listeners.BlockPlaceListener;
import com.iridium.iridiumteams.listeners.InventoryClickListener;
import com.iridium.iridiumteams.listeners.PlayerJoinListener;
import com.iridium.iridiumteams.managers.CommandManager;
import com.iridium.iridiumteams.managers.IridiumUserManager;
import com.iridium.iridiumteams.managers.TeamManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public abstract class IridiumTeams<T extends Team, U extends IridiumUser<T>> extends IridiumCore {

    private final Map<Integer, UserRank> userRanks = new HashMap<>();
    private final Map<String, Permission> permissionList = new HashMap<>();
    private final List<BankItem> bankItemList = new ArrayList<>();

    public IridiumTeams(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        initializePermissions();
        initializeBankItem();
        getLogger().info("-------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + "Enabled!");
        getLogger().info("");
        getLogger().info("-------------------------------");
    }

    @Override
    public void onDisable() {
        saveData();
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
        getLogger().info("-------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Disabled!");
        getLogger().info("");
        getLogger().info("-------------------------------");
    }

    public abstract Economy getEconomy();

    public abstract PlaceholderBuilder<T> getTeamsPlaceholderBuilder();

    public abstract PlaceholderBuilder<U> getUserPlaceholderBuilder();

    public abstract TeamManager<T, U> getTeamManager();

    public abstract IridiumUserManager<T, U> getUserManager();

    public abstract CommandManager<T, U> getCommandManager();

    public abstract Configuration getConfiguration();

    public abstract Messages getMessages();

    public abstract Permissions getPermissions();

    public abstract Inventories getInventories();

    public abstract Commands<T, U> getCommands();

    public abstract BankItems getBankItems();

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
    }

    public void saveData() {

    }

    public void loadConfigs() {
        userRanks.putAll(getConfiguration().userRanks);
        userRanks.put(Rank.VISITOR.getId(), getConfiguration().visitor);
        userRanks.put(Rank.OWNER.getId(), getConfiguration().owner);
    }

    public void saveConfigs() {

    }

    public void initializePermissions() {
        addPermission(PermissionType.BLOCK_BREAK.getPermissionKey(), getPermissions().blockBreak);
        addPermission(PermissionType.BLOCK_PLACE.getPermissionKey(), getPermissions().blockPlace);
        addPermission(PermissionType.BUCKET.getPermissionKey(), getPermissions().bucket);
        addPermission(PermissionType.CHANGE_PERMISSIONS.getPermissionKey(), getPermissions().changePermissions);
        addPermission(PermissionType.CLAIM.getPermissionKey(), getPermissions().claim);
        addPermission(PermissionType.DEMOTE.getPermissionKey(), getPermissions().demote);
        addPermission(PermissionType.DESCRIPTION.getPermissionKey(), getPermissions().description);
        addPermission(PermissionType.DOORS.getPermissionKey(), getPermissions().doors);
        addPermission(PermissionType.INVITE.getPermissionKey(), getPermissions().invite);
        addPermission(PermissionType.KICK.getPermissionKey(), getPermissions().kick);
        addPermission(PermissionType.KILL_MOBS.getPermissionKey(), getPermissions().killMobs);
        addPermission(PermissionType.OPEN_CONTAINERS.getPermissionKey(), getPermissions().openContainers);
        addPermission(PermissionType.PROMOTE.getPermissionKey(), getPermissions().promote);
        addPermission(PermissionType.REDSTONE.getPermissionKey(), getPermissions().redstone);
        addPermission(PermissionType.RENAME.getPermissionKey(), getPermissions().rename);
        addPermission(PermissionType.SETHOME.getPermissionKey(), getPermissions().setHome);
        addPermission(PermissionType.SPAWNERS.getPermissionKey(), getPermissions().spawners);
        addPermission(PermissionType.UNCLAIM.getPermissionKey(), getPermissions().unclaim);
        addPermission(PermissionType.MANAGE_WARPS.getPermissionKey(), getPermissions().manageWarps);
    }

    public void initializeBankItem() {
        addBankItem(getBankItems().experienceBankItem);
        addBankItem(getBankItems().moneyBankItem);
    }

    public void addPermission(String key, Permission permission) {
        permissionList.put(key, permission);
    }

    public void addBankItem(BankItem bankItem) {
        if (bankItem.isEnabled()) bankItemList.add(bankItem);
    }
}
