package com.iridium.iridiumteams.managers;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.support.SpawnerMetaSupport;
import com.iridium.iridiumteams.support.SpawnerSupport;
import com.iridium.iridiumteams.support.StackerSupport;
import com.iridium.iridiumteams.support.RoseStackerSupport;
import com.iridium.iridiumteams.support.WildStackerSupport;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class SupportManager<T extends Team, U extends IridiumUser<T>> {

    private final IridiumTeams<T, U> iridiumTeams;

    public SupportManager(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Getter
    private List<StackerSupport> stackerSupport = new ArrayList<>();
    @Getter
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
            spawnerSupport.add(new RoseStackerSupport(iridiumTeams));
        if (supportedPluginEnabled("WildStacker"))
            spawnerSupport.add(new WildStackerSupport(iridiumTeams));
        if(supportedPluginEnabled("SpawnerMeta"))
            spawnerSupport.add(new SpawnerMetaSupport(iridiumTeams));
    }

    public void registerSupport() {
        registerBlockStackerSupport();
        registerSpawnerSupport();
    }
}