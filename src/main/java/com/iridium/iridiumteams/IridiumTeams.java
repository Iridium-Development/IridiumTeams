package com.iridium.iridiumteams;

import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.managers.TeamManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

@Getter
public class IridiumTeams<T extends Team, U extends IridiumUser<T>> extends JavaPlugin {

    private static IridiumTeams instance;
    private TeamManager<T, U> teamManager;

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

        this.teamManager = new TeamManager<>();


        registerListeners();
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

    public void registerListeners() {

    }

    public void saveData() {

    }

    public void loadConfigs() {

    }

    public void saveConfigs() {

    }

    public static IridiumTeams getInstance() {
        return instance;
    }

}
