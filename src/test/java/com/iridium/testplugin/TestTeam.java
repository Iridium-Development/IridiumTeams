package com.iridium.testplugin;

import com.iridium.iridiumteams.database.Team;

import java.time.LocalDateTime;

public class TestTeam extends Team {
    public TestTeam(String name){
        setName(name);
        setDescription("");
        setCreateTime(LocalDateTime.now());
    }
    public TestTeam(String name, int id){
        setName(name);
        setDescription("");
        setCreateTime(LocalDateTime.now());
        setId(id);
    }

    @Override
    public int getLevel() {
        if (!TestPlugin.getInstance().getConfiguration().isLevelExponential) {
            if (TestPlugin.getInstance().getConfiguration().flatExpRequirement != 0)
                return Math.abs(getExperience() / TestPlugin.getInstance().getConfiguration().flatExpRequirement);

            return getExperience();
        }

        if (TestPlugin.getInstance().getConfiguration().flatExpRequirement != 0) {
            return Math.abs((int) Math.floor(Math.pow(
                    getExperience() / (double) TestPlugin.getInstance().getConfiguration().flatExpRequirement,
                    TestPlugin.getInstance().getConfiguration().curvedExpModifier) + 1));
        }

        return Math.abs((int) Math.floor(Math.pow(getExperience(), TestPlugin.getInstance().getConfiguration().curvedExpModifier) + 1));
    }

    @Override
    public double getValue() {
        return TestPlugin.getInstance().getTeamManager().getTeamValue(this);
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
