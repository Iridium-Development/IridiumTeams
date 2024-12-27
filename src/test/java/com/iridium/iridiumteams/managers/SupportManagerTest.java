package com.iridium.iridiumteams.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.iridium.testplugin.TestPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SupportManagerTest {
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
    public void SupportManager__Stacker_Support_Empty() {
        TestPlugin.getInstance().getSupportManager().registerSupport();
        assertTrue(TestPlugin.getInstance().getSupportManager().getStackerSupport().isEmpty());
    }

    @Test
    public void SupportManager__Spawner_Support_Empty() {
        TestPlugin.getInstance().getSupportManager().registerSupport();
        assertTrue(TestPlugin.getInstance().getSupportManager().getSpawnerSupport().isEmpty());
    }

    @Test
    public void SupportManager__Spawn_Support_Empty() {
        TestPlugin.getInstance().getSupportManager().registerSupport();
        assertTrue(TestPlugin.getInstance().getSupportManager().getSpawnSupport().isEmpty());
    }

    @Test
    public void SupportManager__Provider_List_Empty() {
        TestPlugin.getInstance().getSupportManager().registerSupport();
        assertTrue(TestPlugin.getInstance().getSupportManager().getProviderList().isEmpty());
    }
}