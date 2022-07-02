package com.iridium.iridiumteams;

import com.iridium.iridiumcore.utils.Placeholder;

import java.util.List;
import java.util.Optional;

public interface PlaceholderBuilder<T> {
    List<Placeholder> getPlaceholders(T t);

    List<Placeholder> getPlaceholders(Optional<T> optional);
}
