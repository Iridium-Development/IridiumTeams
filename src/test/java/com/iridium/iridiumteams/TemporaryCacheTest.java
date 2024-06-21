package com.iridium.iridiumteams;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TemporaryCacheTest {

    @Test
    public void TemporaryCache_get_ShouldCacheValue() {
        TemporaryCache<String, Integer> cache = new TemporaryCache<>();
        assertEquals(42, cache.get("key", Duration.ofSeconds(10), () -> 42));

        int cachedValue = cache.get("key", Duration.ofSeconds(10), () -> {
            fail("Supplier should not be called if value is present in cache.");
            return 0;
        });

        assertEquals(42, cachedValue);
    }

    @Test
    public void TemporaryCache_get_ShouldExpireAfterDuration() {
        TemporaryCache<String, Integer> cache = new TemporaryCache<>();
        cache.get("key", Duration.ofSeconds(-100), () -> 42);

        int cachedValue = cache.get("key", Duration.ofSeconds(10), () -> 69);

        assertEquals(69, cachedValue);
    }

    @Test
    public void TemporaryCache_get_ShouldCacheDifferentValues() {
        TemporaryCache<String, Integer> cache = new TemporaryCache<>();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            cache.get("key-" + i, Duration.ofSeconds(10), () -> finalI);
        }

        for (int i = 0; i < 100; i++) {
            int cachedValue = cache.get("key-" + i, Duration.ofSeconds(10), () -> {
                fail("Supplier should not be called if value is present in cache.");
                return 0;
            });

            assertEquals(i, cachedValue);
        }
    }

}