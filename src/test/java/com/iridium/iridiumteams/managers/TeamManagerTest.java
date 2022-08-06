package com.iridium.iridiumteams.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamManagerTest {

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
    public void getTeamValue() {
        TestTeam team = new TeamBuilder()
                .withBlocks(XMaterial.NETHERITE_BLOCK, 5)
                .withBlocks(XMaterial.DIAMOND_BLOCK, 10)
                .withSpawners(EntityType.PIG, 5)
                .build();
        assertEquals(1350, TestPlugin.getInstance().getTeamManager().getTeamValue(team));
    }
}