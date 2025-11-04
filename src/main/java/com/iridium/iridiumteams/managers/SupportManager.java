package com.iridium.iridiumteams.managers;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.support.*;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashSet;

public class SupportManager<T extends Team, U extends IridiumUser<T>> {
    private final IridiumTeams<T, U> iridiumTeams;
    @Getter
    private final HashSet<StackerSupport<T>> stackerSupport = new HashSet<>();
    @Getter
    private final HashSet<SpawnerSupport<T>> spawnerSupport = new HashSet<>();
    @Getter
    private final HashSet<SpawnSupport<T>> spawnSupport = new HashSet<>();
    @Getter
    private final HashSet<String> providerList = new HashSet<>();

    public SupportManager(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    public void registerDefaults() {
        registerBlockStackerSupport();
        registerSpawnerSupport();
        registerSpawnSupport();
    }

    public void registerBlockStackerSupport(StackerSupport<T> stackerSupport) {
        this.stackerSupport.add(stackerSupport);
        this.providerList.add(stackerSupport.supportProvider());
    }

    public void registerSpawnerSupport(SpawnerSupport<T> spawnerSupport) {
        this.spawnerSupport.add(spawnerSupport);
        this.providerList.add(spawnerSupport.supportProvider());
    }

    public void registerSpawnSupport(SpawnSupport<T> spawnSupport) {
        this.spawnSupport.add(spawnSupport);
        this.providerList.add(spawnSupport.supportProvider());
    }

    private void registerBlockStackerSupport() {
        if (isPluginEnabled("RoseStacker")) {
            registerBlockStackerSupport(new RoseStackerSupport<>(iridiumTeams));
        }

        if (isPluginEnabled("WildStacker")) {
            registerBlockStackerSupport(new WildStackerSupport<>(iridiumTeams));
        }

        if (isPluginEnabled("ObsidianStacker")) {
            registerBlockStackerSupport(new ObsidianStackerSupport<>(iridiumTeams));
        }
    }

    private void registerSpawnerSupport() {
        if (isPluginEnabled("RoseStacker")) {
            registerSpawnerSupport(new RoseStackerSupport<>(iridiumTeams));
        }

        if (isPluginEnabled("WildStacker")) {
            registerSpawnerSupport(new WildStackerSupport<>(iridiumTeams));
        }
    }

    private void registerSpawnSupport() {
        if (isPluginEnabled("EssentialsSpawn")) {
            registerSpawnSupport(new EssentialsSpawnSupport<>(iridiumTeams));
        }
    }

    private boolean isPluginEnabled(String pluginName) {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }
}
