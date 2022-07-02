package com.iridium.testplugin;

import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumteams.PlaceholderBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserPlaceholderBuilder implements PlaceholderBuilder<User> {
    @Override
    public List<Placeholder> getPlaceholders(User user) {
        return Collections.emptyList();
    }

    @Override
    public List<Placeholder> getPlaceholders(Optional<User> optional) {
        return Collections.emptyList();
    }
}
