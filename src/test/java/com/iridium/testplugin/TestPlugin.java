package com.iridium.testplugin;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.Configuration;
import com.iridium.iridiumteams.configs.Inventories;
import com.iridium.iridiumteams.configs.Messages;
import com.iridium.iridiumteams.configs.Permissions;
import com.iridium.testplugin.managers.CommandManager;
import com.iridium.testplugin.managers.TeamManager;
import com.iridium.testplugin.managers.UserManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.Collections;

public class TestPlugin extends IridiumTeams<TestTeam, User> {
    private static TestPlugin instance;
    private TeamManager teamManager;
    private UserManager userManager;

    private CommandManager commandManager;

    public TestPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        this.teamManager = new TeamManager();
        this.userManager = new UserManager();

        this.commandManager = new CommandManager(this, "&c", "iridiumtest", Collections.emptyList());
    }

    @Override
    public TeamManager getTeamManager() {
        return teamManager;
    }

    @Override
    public UserManager getUserManager() {
        return userManager;
    }

    @Override
    public Configuration getConfiguration() {
        return new Configuration();
    }

    @Override
    public Messages getMessages() {
        return new Messages();
    }

    @Override
    public Permissions getPermissions() {
        return new Permissions();
    }

    @Override
    public Inventories getInventories() {
        return new Inventories();
    }

    public static TestPlugin getInstance() {
        return instance;
    }
}
