package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.api.SettingUpdateEvent;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamLog;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
public class SettingUpdateListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler
    public void onSettingUpdate(SettingUpdateEvent<T, U> event) {
        if (event.getSetting().equalsIgnoreCase(SettingType.TIME.getSettingKey())) {
            iridiumTeams.getTeamManager().getTeamMembers(event.getTeam()).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(player ->
                    iridiumTeams.getTeamManager().sendTeamTime(player)
            );
        }
        if (event.getSetting().equalsIgnoreCase(SettingType.WEATHER.getSettingKey())) {
            iridiumTeams.getTeamManager().getTeamMembers(event.getTeam()).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(player ->
                    iridiumTeams.getTeamManager().sendTeamWeather(player)
            );
        }

        iridiumTeams.getTeamManager().addTeamLog(new TeamLog(
                event.getTeam(),
                event.getUser().getPlayer().getUniqueId(),
                "setting_update",
                1,
                event.getUser().getPlayer().getLocation(),
                LocalDateTime.now(),
                event.getSetting() + ": " + event.getValue()
        ));
    }

}
