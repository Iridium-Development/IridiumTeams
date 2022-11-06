package com.iridium.testplugin;

import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumteams.PlaceholderBuilder;

import java.util.List;
import java.util.Optional;

public class UserPlaceholderBuilder implements PlaceholderBuilder<User> {
    @Override
    public List<Placeholder> getPlaceholders(User user) {
        return List.of(new Placeholder("player_name", user.getName()));
    }

    public List<Placeholder> getDefaultPlaceholders() {
        return List.of(new Placeholder("player_name", "N/A"));
    }

    @Override
    public List<Placeholder> getPlaceholders(Optional<User> optional) {
        return optional.isPresent() ? getPlaceholders(optional.get()) : getDefaultPlaceholders();
    }
}
