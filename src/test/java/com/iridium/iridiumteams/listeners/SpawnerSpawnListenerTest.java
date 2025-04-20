package com.iridium.iridiumteams.listeners;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.state.CreatureSpawnerStateMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        CreatureSpawnerStateMock creatureSpawnerMock = new CreatureSpawnerStateMock(Material.SPAWNER);
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

        CreatureSpawnerStateMock creatureSpawnerMock = new CreatureSpawnerStateMock(Material.SPAWNER);
        SpawnerSpawnEvent spawnerSpawnEvent = new SpawnerSpawnEvent(entity, creatureSpawnerMock);
        serverMock.getPluginManager().callEvent(spawnerSpawnEvent);

        assertEquals(6, creatureSpawnerMock.getSpawnCount());
    }
}