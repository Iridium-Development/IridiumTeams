package com.iridium.iridiumteams.listeners;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpawnerSpawnListenerTest {

    private ServerMock serverMock;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void onSpawnerSpawn__BoosterNotActive() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        Entity entity = playerMock.getWorld().spawnEntity(playerMock.getLocation(), EntityType.ZOMBIE);

        CreatureSpawnerMock creatureSpawnerMock = new CreatureSpawnerMock();
        SpawnerSpawnEvent spawnerSpawnEvent = new SpawnerSpawnEvent(entity, creatureSpawnerMock);
        serverMock.getPluginManager().callEvent(spawnerSpawnEvent);

        assertEquals(4, creatureSpawnerMock.getSpawnCount());
    }

    @Test
    public void onSpawnerSpawn__BoosterActive() {
        TestTeam team = new TeamBuilder().withEnhancement("spawner", 1).build();
        TeamManager.teamViaLocation = Optional.of(team);
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        Entity entity = playerMock.getWorld().spawnEntity(playerMock.getLocation(), EntityType.ZOMBIE);

        CreatureSpawnerMock creatureSpawnerMock = new CreatureSpawnerMock();
        SpawnerSpawnEvent spawnerSpawnEvent = new SpawnerSpawnEvent(entity, creatureSpawnerMock);
        serverMock.getPluginManager().callEvent(spawnerSpawnEvent);

        assertEquals(6, creatureSpawnerMock.getSpawnCount());
    }
}

//TODO move to mockbukkit
class CreatureSpawnerMock implements CreatureSpawner{

    int spawnCount = 4;

    @Override
    public @NotNull EntityType getSpawnedType() {
        return null;
    }

    @Override
    public void setSpawnedType(@NotNull EntityType creatureType) {

    }

    @Override
    public void setCreatureTypeByName(@NotNull String creatureType) {

    }

    @Override
    public @NotNull String getCreatureTypeName() {
        return null;
    }

    @Override
    public int getDelay() {
        return 0;
    }

    @Override
    public void setDelay(int delay) {

    }

    @Override
    public int getMinSpawnDelay() {
        return 0;
    }

    @Override
    public void setMinSpawnDelay(int delay) {

    }

    @Override
    public int getMaxSpawnDelay() {
        return 0;
    }

    @Override
    public void setMaxSpawnDelay(int delay) {

    }

    @Override
    public int getSpawnCount() {
        return spawnCount;
    }

    @Override
    public void setSpawnCount(int spawnCount) {
        this.spawnCount = spawnCount;
    }

    @Override
    public int getMaxNearbyEntities() {
        return 0;
    }

    @Override
    public void setMaxNearbyEntities(int maxNearbyEntities) {

    }

    @Override
    public int getRequiredPlayerRange() {
        return 0;
    }

    @Override
    public void setRequiredPlayerRange(int requiredPlayerRange) {

    }

    @Override
    public int getSpawnRange() {
        return 0;
    }

    @Override
    public void setSpawnRange(int spawnRange) {

    }

    @Override
    public boolean isActivated() {
        return false;
    }

    @Override
    public void resetTimer() {

    }

    @Override
    public void setSpawnedItem(@NotNull ItemStack itemStack) {

    }

    @Override
    public @NotNull PersistentDataContainer getPersistentDataContainer() {
        return null;
    }

    @Override
    public boolean isSnapshot() {
        return false;
    }

    @Override
    public @NotNull Block getBlock() {
        return null;
    }

    @Override
    public @NotNull MaterialData getData() {
        return null;
    }

    @Override
    public @NotNull BlockData getBlockData() {
        return null;
    }

    @Override
    public @NotNull Material getType() {
        return null;
    }

    @Override
    public byte getLightLevel() {
        return 0;
    }

    @Override
    public @NotNull World getWorld() {
        return null;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public @NotNull Location getLocation() {
        return null;
    }

    @Override
    public @Nullable Location getLocation(@Nullable Location loc) {
        return null;
    }

    @Override
    public @NotNull Chunk getChunk() {
        return null;
    }

    @Override
    public void setData(@NotNull MaterialData data) {

    }

    @Override
    public void setBlockData(@NotNull BlockData data) {

    }

    @Override
    public void setType(@NotNull Material type) {

    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean update(boolean force) {
        return false;
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        return false;
    }

    @Override
    public byte getRawData() {
        return 0;
    }

    @Override
    public void setRawData(byte data) {

    }

    @Override
    public boolean isPlaced() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue) {

    }

    @Override
    public @NotNull List<MetadataValue> getMetadata(@NotNull String metadataKey) {
        return null;
    }

    @Override
    public boolean hasMetadata(@NotNull String metadataKey) {
        return false;
    }

    @Override
    public void removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin) {

    }
}