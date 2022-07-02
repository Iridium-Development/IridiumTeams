package com.iridium.testplugin;

import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumteams.PlaceholderBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TeamPlaceholderBuilder implements PlaceholderBuilder<TestTeam> {
    @Override
    public List<Placeholder> getPlaceholders(TestTeam testTeam) {
        return Collections.emptyList();
    }

    @Override
    public List<Placeholder> getPlaceholders(Optional<TestTeam> testTeam) {
        return Collections.emptyList();
    }
}
