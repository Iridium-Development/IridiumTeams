package com.iridium.iridiumteams.placeholders;

import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.managers.TeamManager;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Placeholders<T extends Team, U extends IridiumUser<T>> {
    private final IridiumTeams<T, U> iridiumTeams;

    public Placeholders(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    public List<Placeholder> getDefaultPlaceholders() {
        return iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(Optional.empty());
    }

    public List<Placeholder> getPlaceholders(@Nullable Player player) {
        U user = player == null ? null : iridiumTeams.getUserManager().getUser(player);
        Optional<T> team = user == null ? Optional.empty() : iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID());
        Optional<T> current = user == null ? Optional.empty() : iridiumTeams.getTeamManager().getTeamViaPlayerLocation(player);
        List<T> topValue = iridiumTeams.getTeamManager().getTeams(TeamManager.SortType.Value, true);
        List<T> topExperience = iridiumTeams.getTeamManager().getTeams(TeamManager.SortType.Experience, true);

        List<Placeholder> placeholders = new ArrayList<>();

        placeholders.addAll(iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(team));
        placeholders.addAll(iridiumTeams.getUserPlaceholderBuilder().getPlaceholders(Optional.ofNullable(user)));
        for (Placeholder placeholder : iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(current)) {
            placeholders.add(new Placeholder("current_" + formatPlaceholderKey(placeholder.getKey()), placeholder.getValue()));
        }

        for (int i = 1; i <= 20; i++) {
            Optional<T> value = topValue.size() >= i ? Optional.of(topValue.get(i - 1)) : Optional.empty();
            Optional<T> experience = topExperience.size() >= i ? Optional.of(topExperience.get(i - 1)) : Optional.empty();
            for (Placeholder placeholder : iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(value)) {
                placeholders.add(new Placeholder("top_value_" + i + "_" + formatPlaceholderKey(placeholder.getKey()), placeholder.getValue()));
            }
            for (Placeholder placeholder : iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(experience)) {
                placeholders.add(new Placeholder("top_experience_" + i + "_" + formatPlaceholderKey(placeholder.getKey()), placeholder.getValue()));
            }
        }

        return placeholders;
    }

    private String formatPlaceholderKey(String key) {
        return key.replace("%", "");
    }

}
