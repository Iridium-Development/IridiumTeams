package com.iridium.testplugin;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PlaceholderBuilder;
import com.iridium.iridiumteams.TeamChatPlaceholderBuilder;
import com.iridium.iridiumteams.configs.*;
import com.iridium.iridiumteams.managers.MissionManager;
import com.iridium.iridiumteams.managers.ShopManager;
import com.iridium.testplugin.api.EnhancementUpdateEvent;
import com.iridium.testplugin.managers.CommandManager;
import com.iridium.testplugin.managers.TeamManager;
import com.iridium.testplugin.managers.UserManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class TestPlugin extends IridiumTeams<TestTeam, User> {
    private static TestPlugin instance;
    private TeamManager teamManager;
    private UserManager userManager;
    private CommandManager commandManager;
    private MissionManager<TestTeam, User> missionManager;
    private ShopManager<TestTeam, User> shopManager;

    private Configuration configuration;
    private Messages messages;
    private Commands<TestTeam, User> commands;
    private BlockValues blockValues;
    private Top<TestTeam> top;
    private Permissions permissions;
    private Inventories inventories;
    private Enhancements enhancements;
    private BankItems bankItems;
    private Missions missions;
    private Shop shop;
    private Settings settings;

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
        this.shopManager = new ShopManager<>(this);

        super.onEnable();
    }

    @Override
    public void loadConfigs() {
        this.configuration = new Configuration();
        this.messages = new Messages();
        this.commands = new Commands<>();
        this.blockValues = new BlockValues();
        this.top = new Top<>();
        this.permissions = new Permissions();
        this.inventories = new Inventories();
        this.enhancements = new Enhancements();
        this.bankItems = new BankItems();
        this.missions = new Missions();
        this.shop = new Shop();
        this.settings = new Settings();
        super.loadConfigs();
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

    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public MissionManager<TestTeam, User> getMissionManager() {
        return this.missionManager;
    }

    @Override
    public ShopManager<TestTeam, User> getShopManager() {
        return this.shopManager;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public Messages getMessages() {
        return this.messages;
    }

    public Commands<TestTeam, User> getCommands() {
        return this.commands;
    }

    public BlockValues getBlockValues() {
        return this.blockValues;
    }

    public Top<TestTeam> getTop() {
        return this.top;
    }

    public Permissions getPermissions() {
        return this.permissions;
    }

    public Inventories getInventories() {
        return this.inventories;
    }

    public Enhancements getEnhancements() {
        return this.enhancements;
    }

    public BankItems getBankItems() {
        return this.bankItems;
    }

    public Missions getMissions() {
        return this.missions;
    }

    @Override
    public Shop getShop() {
        return shop;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public void registerListeners() {
        super.registerListeners();
        Bukkit.getPluginManager().registerEvents(new EnhancementUpdateEvent(), this);
    }

    public static TestPlugin getInstance() {
        return instance;
    }
}
