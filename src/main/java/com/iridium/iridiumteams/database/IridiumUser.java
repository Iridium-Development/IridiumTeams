package com.iridium.iridiumteams.database;

import com.iridium.iridiumteams.IridiumTeams;
import com.j256.ormlite.field.DatabaseField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class IridiumUser<T extends Team> {

    @DatabaseField(columnName = "uuid", canBeNull = false, id = true)
    private @NotNull UUID uuid;

    @DatabaseField(columnName = "name", canBeNull = false)
    private @NotNull String name;

    @Setter(AccessLevel.PRIVATE)
    @DatabaseField(columnName = "team_id", canBeNull = false)
    private int teamID;

    @DatabaseField(columnName = "join_time")
    private long joinTime;

    public Optional<T> getTeam(){
        return IridiumTeams.getInstance().getTeamManager().getTeamViaID(teamID);
    }

    public void setTeam(T t){
        teamID = t.getId();
    }
}
