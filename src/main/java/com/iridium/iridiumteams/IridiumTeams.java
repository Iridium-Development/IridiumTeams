package com.iridium.iridiumteams;

import com.iridium.iridiumcore.IridiumCore;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.bank.BankItem;
import com.iridium.iridiumteams.configs.*;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.PotionEnhancementData;
import com.iridium.iridiumteams.listeners.*;
import com.iridium.iridiumteams.managers.*;
import com.iridium.iridiumteams.placeholders.ClipPlaceholderAPI;
import com.iridium.iridiumteams.sorting.TeamSorting;
import de.jeff_media.updatechecker.UpdateChecker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public abstract class IridiumTeams<T extends Team, U extends IridiumUser<T>> extends IridiumCore {

    private final Map<Integer, UserRank> userRanks = new HashMap<>();
    private final Map<String, Permission> permissionList = new HashMap<>();
    private final Map<String, Setting> settingsList = new HashMap<>();
    private final Map<String, Enhancement<?>> enhancementList = new HashMap<>();
    private final List<BankItem> bankItemList = new ArrayList<>();
    private final List<ChatType> chatTypes = new ArrayList<>();
    private final List<TeamSorting<T>> sortingTypes = new ArrayList<>();

    @Setter
    private boolean recalculating = false;

    public IridiumTeams(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        initializePermissions();
        initializeSettings();
        initializeBankItem();
        initializeChatTypes();
        initializeEnhancements();
        initializeSortingTypes();
        recalculateTeams();
        registerPlaceholderSupport();
        getLogger().info("-------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Enabled!");
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

    private void registerPlaceholderSupport() {
        Plugin PlaceholderAPI = getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (PlaceholderAPI != null && PlaceholderAPI.isEnabled()) {
            ClipPlaceholderAPI<T, U> clipPlaceholderAPI = new ClipPlaceholderAPI<>(this);
            if (clipPlaceholderAPI.register()) {
                getLogger().info("Successfully registered Placeholders for PlaceholderAPI.");
            }
        }
    }

    public abstract Economy getEconomy();

    public abstract PlaceholderBuilder<T> getTeamsPlaceholderBuilder();

    public abstract PlaceholderBuilder<U> getUserPlaceholderBuilder();

    public abstract TeamChatPlaceholderBuilder getTeamChatPlaceholderBuilder();

    public abstract TeamManager<T, U> getTeamManager();

    public abstract IridiumUserManager<T, U> getUserManager();

    public abstract CommandManager<T, U> getCommandManager();

    public abstract MissionManager<T, U> getMissionManager();

    public abstract ShopManager<T, U> getShopManager();

    public abstract Configuration getConfiguration();

    public abstract Messages getMessages();

    public abstract Permissions getPermissions();

    public abstract Inventories getInventories();

    public abstract Enhancements getEnhancements();

    public abstract Commands<T, U> getCommands();

    public abstract BlockValues getBlockValues();

    public abstract Top<T> getTop();

    public abstract BankItems getBankItems();

    public abstract Missions getMissions();

    public abstract Shop getShop();

    public abstract Settings getSettings();

    public void recalculateTeams() {
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            ListIterator<Integer> teams = getTeamManager().getTeams().stream().map(T::getId).collect(Collectors.toList()).listIterator();
            boolean locked = false;
            int counter = 0;

            @Override
            public void run() {
                counter++;
                int interval = recalculating ? getConfiguration().forceRecalculateInterval : getConfiguration().recalculateInterval;
                if (counter % interval == 0) {
                    if (locked) return;
                    if (!teams.hasNext()) {
                        teams = getTeamManager().getTeams().stream().map(T::getId).collect(Collectors.toList()).listIterator();
                        if (recalculating) {
                            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                                if (!player.hasPermission(getCommands().recalculateCommand.permission)) continue;
                                player.sendMessage(StringUtils.color(getMessages().calculatingFinished
                                        .replace("%prefix%", getConfiguration().prefix)
                                ));
                            }
                        }
                        recalculating = false;
                    } else {
                        getTeamManager().getTeamViaID(teams.next()).ifPresent(team -> {
                            locked = true;
                            getTeamManager().recalculateTeam(team).thenRun(() -> locked = false);
                        });
                    }
                }
            }
        }, 0, 0);
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBurnListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockExplodeListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockFertilizeListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockFormListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockFromToListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockGrowListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPistonListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockSpreadListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new EnchantItemListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityChangeBlockListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityInteractListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new EntitySpawnListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new FurnaceSmeltListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new LeavesDecayListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCraftListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerExpChangeListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerFishListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new PotionBrewListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerSpawnListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new StructureGrowListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new TeamLevelUpListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new SettingUpdateListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener<>(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBucketListener<>(this), this);
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
        addPermission(PermissionType.TRUST.getPermissionKey(), getPermissions().trust);
        addPermission(PermissionType.KICK.getPermissionKey(), getPermissions().kick);
        addPermission(PermissionType.KILL_MOBS.getPermissionKey(), getPermissions().killMobs);
        addPermission(PermissionType.OPEN_CONTAINERS.getPermissionKey(), getPermissions().openContainers);
        addPermission(PermissionType.PROMOTE.getPermissionKey(), getPermissions().promote);
        addPermission(PermissionType.REDSTONE.getPermissionKey(), getPermissions().redstone);
        addPermission(PermissionType.RENAME.getPermissionKey(), getPermissions().rename);
        addPermission(PermissionType.SETHOME.getPermissionKey(), getPermissions().setHome);
        addPermission(PermissionType.SPAWNERS.getPermissionKey(), getPermissions().spawners);
        addPermission(PermissionType.SETTINGS.getPermissionKey(), getPermissions().settings);
        addPermission(PermissionType.MANAGE_WARPS.getPermissionKey(), getPermissions().manageWarps);
    }

    public void initializeSettings() {
        addSetting(SettingType.TEAM_TYPE.getSettingKey(), getSettings().teamJoining, Arrays.asList("Private", "Public"));
        addSetting(SettingType.VALUE_VISIBILITY.getSettingKey(), getSettings().teamValue, Arrays.asList("Private", "Public"));
        addSetting(SettingType.MOB_SPAWNING.getSettingKey(), getSettings().mobSpawning, Arrays.asList("Enabled", "Disabled"));
        addSetting(SettingType.LEAF_DECAY.getSettingKey(), getSettings().leafDecay, Arrays.asList("Enabled", "Disabled"));
        addSetting(SettingType.ICE_FORM.getSettingKey(), getSettings().iceForm, Arrays.asList("Enabled", "Disabled"));
        addSetting(SettingType.FIRE_SPREAD.getSettingKey(), getSettings().fireSpread, Arrays.asList("Enabled", "Disabled"));
        addSetting(SettingType.CROP_TRAMPLE.getSettingKey(), getSettings().cropTrample, Arrays.asList("Enabled", "Disabled"));
        addSetting(SettingType.WEATHER.getSettingKey(), getSettings().weather, Arrays.asList("Server", "Sunny", "Raining"));
        addSetting(SettingType.TIME.getSettingKey(), getSettings().time, Arrays.asList("Server", "Sunrise", "Day", "Morning", "Noon", "Sunset", "Night", "Midnight"));
        addSetting(SettingType.ENTITY_GRIEF.getSettingKey(), getSettings().entityGrief, Arrays.asList("Enabled", "Disabled"));
        addSetting(SettingType.TNT_DAMAGE.getSettingKey(), getSettings().tntDamage, Arrays.asList("Enabled", "Disabled"));
        addSetting(SettingType.TEAM_VISITING.getSettingKey(), getSettings().visiting, Arrays.asList("Enabled", "Disabled"));

    }

    public void initializeBankItem() {
        addBankItem(getBankItems().experienceBankItem);
        addBankItem(getBankItems().moneyBankItem);
    }

    public void initializeChatTypes() {
        addChatType(new ChatType(getConfiguration().noneChatAlias, player -> null));
        addChatType(new ChatType(getConfiguration().teamChatAlias, player ->
                getTeamManager().getTeamViaID(getUserManager().getUser(player).getTeamID()).map(t ->
                        getTeamManager().getTeamMembers(t).stream().map(U::getPlayer).collect(Collectors.toList())
                ).orElse(null))
        );
    }

    public void initializeEnhancements() {
        for (Map.Entry<String, Enhancement<PotionEnhancementData>> enhancement : getEnhancements().potionEnhancements.entrySet()) {
            addEnhancement(enhancement.getKey(), enhancement.getValue());
        }
        addEnhancement("farming", getEnhancements().farmingEnhancement);
        addEnhancement("spawner", getEnhancements().spawnerEnhancement);
        addEnhancement("experience", getEnhancements().experienceEnhancement);
        addEnhancement("flight", getEnhancements().flightEnhancement);
        addEnhancement("members", getEnhancements().membersEnhancement);
        addEnhancement("warps", getEnhancements().warpsEnhancement);
    }

    public void initializeSortingTypes() {
        addSortingType(getTop().experienceTeamSort);
        addSortingType(getTop().valueTeamSort);
    }

    public void addPermission(String key, Permission permission) {
        permissionList.put(key, permission);
    }

    public void addSetting(String key, Setting setting, List<String> values) {
        if (!setting.enabled) return;
        setting.setValues(values);
        settingsList.put(key, setting);
    }

    public void addBankItem(BankItem bankItem) {
        if (bankItem.isEnabled()) bankItemList.add(bankItem);
    }

    public void addChatType(ChatType chatType) {
        chatTypes.add(chatType);
    }

    public void addEnhancement(String key, Enhancement<?> enhancement) {
        if (!enhancement.enabled) return;
        enhancementList.put(key, enhancement);
    }

    public void addSortingType(TeamSorting<T> sortingType) {
        sortingTypes.add(sortingType);
    }

    public void addBstats(int pluginId) {
        new Metrics(this, pluginId);
    }

    public void startUpdateChecker(int pluginId) {
        if (getConfiguration().updateChecks) {
            UpdateChecker.init(this, pluginId)
                    .checkEveryXHours(24)
                    .setDownloadLink(pluginId)
                    .setColoredConsoleOutput(true)
                    .checkNow();
        }
    }
}
