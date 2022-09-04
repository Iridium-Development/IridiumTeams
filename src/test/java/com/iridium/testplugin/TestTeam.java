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
    public double getValue() {
        return TestPlugin.getInstance().getTeamManager().getTeamValue(this);
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
