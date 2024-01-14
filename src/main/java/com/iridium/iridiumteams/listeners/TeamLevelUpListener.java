package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Reward;
import com.iridium.iridiumteams.api.TeamLevelUpEvent;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamReward;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TeamLevelUpListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true)
    public void onTeamLevelUp(TeamLevelUpEvent<T, U> event) {
        for (U member : iridiumTeams.getTeamManager().getTeamMembers(event.getTeam())) {
            Player player = member.getPlayer();
            if(player == null) return;
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamLevelUp
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%level%", String.valueOf(event.getTeam().getLevel()))
            ));
        }

        if (event.isFirstTimeAsLevel() && event.getLevel() > 1) {
            Reward reward = null;
            List<Map.Entry<Integer, Reward>> entries = iridiumTeams.getConfiguration().levelRewards.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
            for (Map.Entry<Integer, Reward> entry : entries) {
                if (event.getLevel() % entry.getKey() == 0) {
                    reward = entry.getValue();
                }
            }
            if (reward != null) {
                reward.item.lore = StringUtils.processMultiplePlaceholders(reward.item.lore, iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(event.getTeam()));
                reward.item.displayName = StringUtils.processMultiplePlaceholders(reward.item.displayName, iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(event.getTeam()));
                iridiumTeams.getTeamManager().addTeamReward(new TeamReward(event.getTeam(), reward));
            }
        }
    }
}
