package com.iridium.iridiumteams.support;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.spawn.EssentialsSpawn;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EssentialsSpawnSupport<T extends Team, U extends IridiumUser<T>> implements SpawnSupport<T> {

    private final IridiumTeams<T, U> iridiumTeams;

    EssentialsSpawn essentialsSpawn = (EssentialsSpawn) Bukkit.getPluginManager().getPlugin("EssentialsSpawn");
    Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

    public EssentialsSpawnSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public String supportProvider() {
        return essentialsSpawn.getName();
    }

    @Override
    public Location getSpawn(Player player) {
        if (essentialsSpawn != null && essentials != null) return essentialsSpawn.getSpawn(essentials.getUser(player).getGroup());
        else return Bukkit.getWorlds().get(0).getSpawnLocation();
    }
}
