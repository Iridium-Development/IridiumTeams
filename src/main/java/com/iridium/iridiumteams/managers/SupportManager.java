package com.iridium.iridiumteams.managers;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.support.spawners.SpawnerSupport;
import com.iridium.iridiumteams.support.stackers.StackerSupport;
import com.iridium.iridiumteams.support.stackers.RoseStackerSupport;
import com.iridium.iridiumteams.support.spawners.RoseStackerSpawnerSupport;
import com.iridium.iridiumteams.support.stackers.WildStackerSupport;
import com.iridium.iridiumteams.support.spawners.WildStackerSpawnerSupport;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class SupportManager<T extends Team, U extends IridiumUser<T>> {

    private final IridiumTeams<T, U> iridiumTeams;

    public SupportManager(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @lombok.Getter
    private List<StackerSupport> stackerSupport = new ArrayList<>();
    @lombok.Getter
    private List<SpawnerSupport> spawnerSupport = new ArrayList<>();

    public boolean supportedPluginEnabled(String pluginName) {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }

    private void registerBlockStackerSupport() {
        if (supportedPluginEnabled("RoseStacker"))
            stackerSupport.add(new RoseStackerSupport(iridiumTeams));
        if (supportedPluginEnabled("WildStacker"))
            stackerSupport.add(new WildStackerSupport(iridiumTeams));
    }

    private void registerSpawnerSupport() {
        if (supportedPluginEnabled("RoseStacker"))
            spawnerSupport.add(new RoseStackerSpawnerSupport(iridiumTeams));
        if (supportedPluginEnabled("WildStacker"))
            spawnerSupport.add(new WildStackerSpawnerSupport(iridiumTeams));
    }
}