package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@DatabaseTable(tableName = "users_profile")
public class IridiumUserProfile<T extends Team> extends DatabaseObject {

    @DatabaseField(columnName = "id", canBeNull = false, id = true)
    private UUID id;

    @DatabaseField(columnName = "user_uuid", canBeNull = false)
    private @NotNull UUID uuid;

    @DatabaseField(columnName = "profile_name", canBeNull = false)
    private @NotNull String name;

    @DatabaseField(columnName = "team_id")
    private int teamID;
    @DatabaseField(columnName = "user_rank", canBeNull = false)
    private int userRank;

    @DatabaseField(columnName = "create_time")
    private LocalDateTime joinTime;

    @DatabaseField(columnName = "create_time")
    private LocalDateTime createTime;

    public IridiumUserProfile(@NotNull UUID uuid, @NotNull String name) {
        this.id = UUID.randomUUID();
        this.uuid = uuid;
        this.name = name;
        this.createTime = LocalDateTime.now();
    }

    public IridiumUserProfile() {
    }

    public void setUuid(@NotNull UUID uuid) {
        this.uuid = uuid;
        setChanged(true);
    }

    public void setName(@NotNull String name) {
        this.name = name;
        setChanged(true);
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
        setChanged(true);
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
        setChanged(true);
    }

    public void setTeam(T t) {
        this.teamID = t == null ? 0 : t.getId();
        this.joinTime = LocalDateTime.now();
        userRank = 1;
    }

}
