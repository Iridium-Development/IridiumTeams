package com.iridium.iridiumteams.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.database.TeamMission;
import com.iridium.iridiumteams.missions.MissionData;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MissionManagerTest {

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
    public void handleMissionUpdate__DoesntIncrementsValue__DependencyNotComplete() {
        TestTeam testTeam = new TeamBuilder().build();
        TestPlugin.getInstance().getMissions().missions.get("mine_oak").getMissionData().get(1).getMissionDependencies().add(new MissionData.MissionDependency("test", 1));
        TeamMission teamMission = TestPlugin.getInstance().getTeamManager().getTeamMission(testTeam, "mine_oak");
        World world = serverMock.createWorld(new WorldCreator("world"));
        TestPlugin.getInstance().getConfiguration().whitelistedWorlds = List.of("world");
        assertEquals(0, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());

        TestPlugin.getInstance().getMissionManager().handleMissionUpdate(testTeam, world, "MINE", "OAK_LOG", 1);

        assertEquals(0, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());
    }

    @Test
    public void handleMissionUpdate__IncrementsValues() {
        TestTeam testTeam = new TeamBuilder().build();
        TeamMission teamMission = TestPlugin.getInstance().getTeamManager().getTeamMission(testTeam, "mine_oak");
        World world = serverMock.createWorld(new WorldCreator("world"));
        TestPlugin.getInstance().getConfiguration().whitelistedWorlds = List.of("world");
        assertEquals(0, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());
        TestPlugin.getInstance().getMissionManager().handleMissionUpdate(testTeam, world, "MINE", "OAK_LOG", 1);
        assertEquals(1, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());
    }

    @Test
    public void handleMissionUpdate__CompletesMission() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        TeamMission teamMission = TestPlugin.getInstance().getTeamManager().getTeamMission(testTeam, "mine_oak");
        World world = serverMock.createWorld(new WorldCreator("world"));
        TestPlugin.getInstance().getConfiguration().whitelistedWorlds = List.of("world");
        assertEquals(0, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());
        TestPlugin.getInstance().getMissionManager().handleMissionUpdate(testTeam, world, "MINE", "OAK_LOG", 100);
        assertEquals(0, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());
        assertEquals(2, teamMission.getMissionLevel());
        playerMock.assertSoundHeard(Sound.ENTITY_PLAYER_LEVELUP);
        playerMock.assertSaid(StringUtils.color(
                """
                        %prefix% &7Mission Completed!
                        &c&l* &7+3 Island Experience
                        &c&l* &7+5 Island Crystals
                        &c&l* &7+1000 Money
                        &7/is rewards to redeem rewards"""
                        .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
        assertEquals(1, TestPlugin.getInstance().getTeamManager().getTeamRewards(testTeam).size());
    }

    @Test
    public void handleMissionUpdate__MissionAlreadyCompleted() {
        TestTeam testTeam = new TeamBuilder().build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(testTeam).build();
        TeamMission teamMission = TestPlugin.getInstance().getTeamManager().getTeamMission(testTeam, "mine_oak");
        World world = serverMock.createWorld(new WorldCreator("world"));
        TestPlugin.getInstance().getConfiguration().whitelistedWorlds = List.of("world");
        TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).setProgress(10);
        TestPlugin.getInstance().getMissionManager().handleMissionUpdate(testTeam, world, "MINE", "OAK_LOG", 100);
        assertEquals(10, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void handleMissionUpdate_ShouldNotIncrementValue_WhenMissionIsNotInWhitelistedWorld() {
        TestTeam testTeam = new TeamBuilder().build();
        TeamMission teamMission = TestPlugin.getInstance().getTeamManager().getTeamMission(testTeam, "mine_oak");
        World world = serverMock.createWorld(new WorldCreator("world"));
        TestPlugin.getInstance().getConfiguration().whitelistedWorlds = List.of("differentWorld");
        TestPlugin.getInstance().getMissionManager().handleMissionUpdate(testTeam, world, "MINE", "OAK_LOG", 1);
        assertEquals(0, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());
    }

    @Test
    public void handleMissionUpdate_ShouldIncrementValue_WhenWhitelistedWorldIsEmpty() {
        TestTeam testTeam = new TeamBuilder().build();
        TeamMission teamMission = TestPlugin.getInstance().getTeamManager().getTeamMission(testTeam, "mine_oak");
        World world = serverMock.createWorld(new WorldCreator("world"));
        TestPlugin.getInstance().getConfiguration().whitelistedWorlds = Collections.emptyList();
        TestPlugin.getInstance().getMissionManager().handleMissionUpdate(testTeam, world, "MINE", "OAK_LOG", 1);
        assertEquals(1, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());
    }

    @Test
    public void handleMissionUpdate_ShouldIncrementValue_WhenMissionIsInWhitelistedWorld() {
        TestTeam testTeam = new TeamBuilder().build();
        TeamMission teamMission = TestPlugin.getInstance().getTeamManager().getTeamMission(testTeam, "mine_oak");
        World world = serverMock.createWorld(new WorldCreator("world"));
        TestPlugin.getInstance().getConfiguration().whitelistedWorlds = List.of("world");
        TestPlugin.getInstance().getMissionManager().handleMissionUpdate(testTeam, world, "MINE", "OAK_LOG", 1);
        assertEquals(1, TestPlugin.getInstance().getTeamManager().getTeamMissionData(teamMission, 0).getProgress());
    }
}