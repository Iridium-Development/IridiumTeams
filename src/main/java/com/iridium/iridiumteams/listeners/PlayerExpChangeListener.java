package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.ExperienceEnhancementData;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

@AllArgsConstructor
public class PlayerExpChangeListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    //Could cause dupe's of xp if they have a plugin to deposit xp
    @EventHandler(ignoreCancelled = true)
    public void onPlayerExperienceChange(PlayerExpChangeEvent event) {
        iridiumTeams.getTeamManager().getTeamViaID(iridiumTeams.getUserManager().getUser(event.getPlayer()).getTeamID()).ifPresent(team -> {
            Enhancement<ExperienceEnhancementData> spawnerEnhancement = iridiumTeams.getEnhancements().experienceEnhancement;
            TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(team, "experience");
            ExperienceEnhancementData data = spawnerEnhancement.levels.get(teamEnhancement.getLevel());

            if (!teamEnhancement.isActive(spawnerEnhancement.type)) return;
            if (data == null) return;

            event.setAmount((int) (event.getAmount() * data.experienceModifier));
        });
    }
}
