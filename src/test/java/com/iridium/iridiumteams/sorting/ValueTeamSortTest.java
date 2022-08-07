package com.iridium.iridiumteams.sorting;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValueTeamSortTest {

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
    public void getSortedTeams() {
        TestTeam teamA = new TeamBuilder().withBlocks(XMaterial.DIAMOND_BLOCK, 39).build();
        TestTeam teamB = new TeamBuilder().withBlocks(XMaterial.DIAMOND_BLOCK, 10).build();
        TestTeam teamC = new TeamBuilder().withBlocks(XMaterial.DIAMOND_BLOCK, 74).build();
        assertEquals(Arrays.asList(teamC, teamA, teamB), new ValueTeamSort<TestTeam>().getSortedTeams(TestPlugin.getInstance()));
    }

    @Test
    public void getRank() {
        TestTeam teamA = new TeamBuilder().withBlocks(XMaterial.DIAMOND_BLOCK, 39).build();
        TestTeam teamB = new TeamBuilder().withBlocks(XMaterial.DIAMOND_BLOCK, 10).build();
        TestTeam teamC = new TeamBuilder().withBlocks(XMaterial.DIAMOND_BLOCK, 74).build();
        assertEquals(2, new ValueTeamSort<TestTeam>().getRank(teamA, TestPlugin.getInstance()));
        assertEquals(3, new ValueTeamSort<TestTeam>().getRank(teamB, TestPlugin.getInstance()));
        assertEquals(1, new ValueTeamSort<TestTeam>().getRank(teamC, TestPlugin.getInstance()));
    }
}