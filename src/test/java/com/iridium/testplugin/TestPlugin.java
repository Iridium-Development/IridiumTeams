package com.iridium.testplugin;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.Configuration;
import com.iridium.iridiumteams.configs.Messages;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.testplugin.managers.CommandManager;
import com.iridium.testplugin.managers.TeamManager;
import com.iridium.testplugin.managers.UserManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.Collections;

public class TestPlugin extends IridiumTeams<Team, IridiumUser<Team>> {
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
        return null;
    }

    @Override
    public Messages getMessages() {
        return null;
    }

    public static TestPlugin getInstance() {
        return instance;
    }
}
