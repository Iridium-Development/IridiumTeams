package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
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
    @DatabaseField(columnName = "user_rank", canBeNull = false)
    private int userRank;

    @DatabaseField(columnName = "join_time")
    private LocalDateTime joinTime;

    private boolean bypassing;

    public void setTeam(T t) {
        setJoinTime(LocalDateTime.now());
        teamID = t.getId();
        userRank = 0;
    }

    public Player getPlayer() {
        return Bukkit.getServer().getPlayer(uuid);
    }
}
