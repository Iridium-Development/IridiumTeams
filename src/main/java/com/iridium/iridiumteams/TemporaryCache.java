package com.iridium.iridiumteams;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class TemporaryCache<Key, Value> {

    private final Map<Key, CachedValue<Value>> cache = new ConcurrentHashMap<>();

    public Value get(Key key, Duration duration, Supplier<Value> valueSupplier) {
        CachedValue<Value> cachedValue = cache.get(key);
        if (cachedValue != null && !cachedValue.hasExpired()) {
            return cachedValue.getValue();
        } else {
            Value value = valueSupplier.get();
            cache.put(key, new CachedValue<>(value, duration));
            return value;
        }
    }

    private static class CachedValue<T> {
        @Getter
        private final T value;
        private final Instant expiryTime;

        public CachedValue(T value, Duration duration) {
            this.value = value;
            this.expiryTime = Instant.now().plus(duration);
        }

        public boolean hasExpired() {
            return Instant.now().isAfter(expiryTime);
        }
    }
}
