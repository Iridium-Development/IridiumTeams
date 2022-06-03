package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@DatabaseTable(tableName = "users")
public class IridiumUser<T extends Team> {

    @DatabaseField(columnName = "uuid", canBeNull = false, id = true)
    private @NotNull UUID uuid;

    @DatabaseField(columnName = "name", canBeNull = false)
    private @NotNull String name;

    @DatabaseField(columnName = "team_id", canBeNull = false)
    private T team;
    @DatabaseField(columnName = "user_rank", canBeNull = false)
    private int userRank;

    @DatabaseField(columnName = "join_time")
    private LocalDateTime joinTime;

    private boolean bypassing;

    public void setTeam(T t) {
        this.team = t;
        setJoinTime(LocalDateTime.now());
        userRank = 1;
    }

    public Optional<T> getTeam() {
        return Optional.ofNullable(team);
    }

    public Player getPlayer() {
        return Bukkit.getServer().getPlayer(uuid);
    }
}
