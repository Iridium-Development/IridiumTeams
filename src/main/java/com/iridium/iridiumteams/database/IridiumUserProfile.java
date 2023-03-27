package com.iridium.iridiumteams.database;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.enhancements.*;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@DatabaseTable(tableName = "users_profile")
public class IridiumUserProfile<T extends Team> extends DatabaseObject {

    @DatabaseField(columnName = "id", canBeNull = false, generatedId = true)
    private int id;

    @DatabaseField(columnName = "user_uuid", canBeNull = false)
    private @NotNull UUID uuid;

    @DatabaseField(columnName = "profile_name", canBeNull = false)
    private @NotNull String name;

    @DatabaseField(columnName = "team_id")
    private int teamID;
    @DatabaseField(columnName = "user_rank", canBeNull = false)
    private int userRank;

    @DatabaseField(columnName = "join_time")
    private LocalDateTime joinTime;

    private String chatType = "";
    private boolean bypassing;
    private boolean flying;

    

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

    public void setJoinTime(LocalDateTime joinTime) {
        this.joinTime = joinTime;
        setChanged(true);
    }


    public void setTeam(T t) {
        this.teamID = t == null ? 0 : t.getId();
        setJoinTime(LocalDateTime.now());
        userRank = 1;
    }

    public void setBypassing(boolean bypassing) {
        this.bypassing=bypassing;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

}
