package com.iridium.testplugin;

import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumteams.PlaceholderBuilder;

import java.util.List;
import java.util.Optional;

public class TeamPlaceholderBuilder implements PlaceholderBuilder<TestTeam> {
    @Override
    public List<Placeholder> getPlaceholders(TestTeam testTeam) {
        return List.of(new Placeholder("team_name", testTeam.getName()));
    }

    public List<Placeholder> getDefaultPlaceholders() {
        return List.of(new Placeholder("team_name", "N/A"));
    }

    @Override
    public List<Placeholder> getPlaceholders(Optional<TestTeam> optional) {
        return optional.isPresent() ? getPlaceholders(optional.get()) : getDefaultPlaceholders();
    }
}
