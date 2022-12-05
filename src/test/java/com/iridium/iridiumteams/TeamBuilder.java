package com.iridium.iridiumteams;

import be.seeseemelk.mockbukkit.ServerMock;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.database.TeamReward;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.time.LocalDateTime;
import java.util.UUID;
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

    public TeamBuilder withReward(Reward... rewards) {
        for (Reward reward : rewards) {
            TestPlugin.getInstance().getTeamManager().addTeamReward(new TeamReward(testTeam, reward));
        }
        return this;
    }

    public TeamBuilder withMissions(String... missions) {
        for (String mission : missions) {
            TestPlugin.getInstance().getTeamManager().getTeamMission(testTeam, mission);
        }
        return this;
    }

    public TeamBuilder withWarp(String name, String password, Location location) {
        TestPlugin.getInstance().getTeamManager().createWarp(testTeam, UUID.randomUUID(), location, name, password);
        return this;
    }

    public TeamBuilder withExperience(int experience) {
        testTeam.setExperience(experience);
        return this;
    }

    public TeamBuilder withBlocks(XMaterial xMaterial, int amount) {
        TestPlugin.getInstance().getTeamManager().getTeamBlock(testTeam, xMaterial).setAmount(amount);
        return this;
    }

    public TeamBuilder withSpawners(EntityType entityType, int amount) {
        TestPlugin.getInstance().getTeamManager().getTeamSpawners(testTeam, entityType).setAmount(amount);
        return this;
    }

    public TeamBuilder withLevel(int level) {
        testTeam.setExperience(level * level * level);
        return this;
    }

    public TeamBuilder withEnhancement(String enhancement, int level) {
        withEnhancement(enhancement, level, LocalDateTime.now().plusHours(1));
        return this;
    }

    public TeamBuilder withEnhancement(String enhancement, int level, LocalDateTime localDateTime) {
        TeamEnhancement teamEnhancement = new TeamEnhancement(testTeam, enhancement, level);
        teamEnhancement.setExpirationTime(localDateTime);
        TeamManager.teamEnhancements.put(enhancement, teamEnhancement);
        return this;
    }

    public TeamBuilder withBank(String bank, int amount) {
        TestPlugin.getInstance().getTeamManager().getTeamBank(testTeam, bank).setNumber(amount);
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

    public TeamBuilder withSetting(SettingType setting, String value) {
        TestPlugin.getInstance().getTeamManager().getTeamSetting(testTeam, setting.getSettingKey()).setValue(value);
        return this;
    }

    public TestTeam build() {
        return testTeam;
    }

}
