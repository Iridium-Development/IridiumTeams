package com.iridium.iridiumteams;

import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumteams.configs.Configuration;
import com.iridium.iridiumteams.configs.Inventories;
import com.iridium.iridiumteams.configs.Messages;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.managers.IridiumUserManager;
import com.iridium.iridiumteams.managers.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public abstract class IridiumTeams<T extends Team, U extends IridiumUser<T>> extends IridiumCore {

    public IridiumTeams(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onLoad() {
        getDataFolder().mkdir();

        loadConfigs();
        saveConfigs();
    }

    @Override
    public void onEnable() {
        super.onEnable();
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

    public abstract TeamManager<T, U> getTeamManager();

    public abstract IridiumUserManager<T, U> getUserManager();

    public abstract Configuration getConfiguration();

    public abstract Messages getMessages();

    public abstract Inventories getInventories();

    public void registerListeners() {

    }

    public void saveData() {

    }

    public void loadConfigs() {

    }

    public void saveConfigs() {

    }

}
