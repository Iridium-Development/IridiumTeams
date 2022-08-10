package com.iridium.testplugin;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PlaceholderBuilder;
import com.iridium.iridiumteams.TeamChatPlaceholderBuilder;
import com.iridium.iridiumteams.configs.*;
import com.iridium.iridiumteams.managers.MissionManager;
import com.iridium.testplugin.managers.CommandManager;
import com.iridium.testplugin.managers.TeamManager;
import com.iridium.testplugin.managers.UserManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class TestPlugin extends IridiumTeams<TestTeam, User> {
    private static TestPlugin instance;
    private TeamManager teamManager;
    private UserManager userManager;
    private CommandManager commandManager;
    private MissionManager<TestTeam, User> missionManager;

    private final TestEconomyProvider economyProvider = new TestEconomyProvider();

    public TestPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        instance = this;

        this.teamManager = new TeamManager();
        this.userManager = new UserManager();
        this.commandManager = new CommandManager(this, "&c", "iridiumtest");
        this.missionManager = new MissionManager<>(this);

        super.onEnable();
    }

    @Override
    public Economy getEconomy() {
        return economyProvider;
    }

    @Override
    public PlaceholderBuilder<User> getUserPlaceholderBuilder() {
        return new UserPlaceholderBuilder();
    }

    @Override
    public PlaceholderBuilder<TestTeam> getTeamsPlaceholderBuilder() {
        return new TeamPlaceholderBuilder();
    }

    @Override
    public TeamChatPlaceholderBuilder getTeamChatPlaceholderBuilder() {
        return new com.iridium.testplugin.TeamChatPlaceholderBuilder();
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
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public MissionManager<TestTeam, User> getMissionManager() {
        return missionManager;
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
    public Commands<TestTeam, User> getCommands() {
        return new Commands<>();
    }

    @Override
    public BlockValues getBlockValues() {
        return new BlockValues();
    }

    @Override
    public Top<TestTeam> getTop() {
        return new Top<>();
    }

    @Override
    public Permissions getPermissions() {
        return new Permissions();
    }

    @Override
    public Inventories getInventories() {
        return new Inventories();
    }

    @Override
    public Enhancements getEnhancements() {
        return new Enhancements();
    }

    @Override
    public BankItems getBankItems() {
        return new BankItems();
    }

    @Override
    public Missions getMissions() {
        return new Missions();
    }

    public static TestPlugin getInstance() {
        return instance;
    }
}
