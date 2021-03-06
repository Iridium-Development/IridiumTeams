package com.iridium.iridiumteams;

import be.seeseemelk.mockbukkit.ServerMock;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class TeamBuilder {
    private static final AtomicInteger TEAM_ID = new AtomicInteger(1);
    private final TestTeam testTeam;

    public TeamBuilder() {
        int id = TEAM_ID.getAndIncrement();
        this.testTeam = new TestTeam("Team_" + id, id);
        TeamManager.teams.add(testTeam);
    }

    public TeamBuilder(String name) {
        this.testTeam = new TestTeam(name, TEAM_ID.getAndIncrement());
        TeamManager.teams.add(testTeam);
    }

    public TeamBuilder(int id) {
        this.testTeam = new TestTeam("Team_" + id, id);
        TeamManager.teams.add(testTeam);
    }

    public TeamBuilder withLevel(int level) {
        testTeam.setExperience(level * level * level);
        return this;
    }

    public TeamBuilder withEnhancement(String enhancement, int level) {
        TeamEnhancement teamEnhancement = new TeamEnhancement(testTeam, enhancement, level);
        teamEnhancement.setStartTime(LocalDateTime.now().plusHours(1));
        TeamManager.teamEnhancements.put(enhancement, teamEnhancement);
        return this;
    }

    public TeamBuilder withMembers(int amount, ServerMock serverMock) {
        for (int i = 0; i < amount; i++) {
            new UserBuilder(serverMock).withTeam(testTeam).build();
        }
        return this;
    }

    public TeamBuilder withPermission(int rank, PermissionType permissionType, boolean allowed) {
        TestPlugin.getInstance().getTeamManager().setTeamPermission(testTeam, rank, permissionType.getPermissionKey(), allowed);
        return this;
    }

    public TestTeam build() {
        return testTeam;
    }

}
