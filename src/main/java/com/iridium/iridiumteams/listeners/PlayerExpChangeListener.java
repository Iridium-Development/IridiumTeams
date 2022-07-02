package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.EnhancementType;
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
        U user = iridiumTeams.getUserManager().getUser(event.getPlayer());
        iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).ifPresent(team -> {
            Enhancement<ExperienceEnhancementData> experienceEnhancement = iridiumTeams.getEnhancements().experienceEnhancement;
            TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(team, "experience");
            if (!teamEnhancement.isActive() && experienceEnhancement.type == EnhancementType.BOOSTER) return;
            double experienceModifier = experienceEnhancement.levels.get(teamEnhancement.getLevel()).experienceModifier;
            event.setAmount((int) (event.getAmount() * experienceModifier));
        });
    }
}
