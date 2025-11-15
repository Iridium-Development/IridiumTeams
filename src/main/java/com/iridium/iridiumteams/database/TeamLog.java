package com.iridium.iridiumteams.database;

import com.iridium.iridiumteams.LogType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_log")
public class TeamLog {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "team_id", canBeNull = false)
    private int teamID;

    @DatabaseField(columnName = "time", canBeNull = false)
    private LocalDateTime time;

    @DatabaseField(columnName = "user")
    private UUID user;

    /*
        For when there are two users, e.g. on Team Invite, user might be the person who sent the invite, other_user might be who recieved the invite
        This way when we search by /is logs <User> we can search on both users, so we can see the Invite Log for that User aswell
     */
    @DatabaseField(columnName = "other_user")
    private UUID otherUser;

    @DatabaseField(columnName = "action", canBeNull = false)
    private LogType action;

    @DatabaseField(columnName = "description", canBeNull = false)
    private String description;

    public TeamLog(@NotNull Team team, LogType action, String description) {
        this.teamID = team.getId();
        this.time = LocalDateTime.now();
        this.action = action;
        this.description = description;
    }

    public TeamLog(@NotNull Team team, LogType action, String description, UUID user) {
        this(team, action, description);
        this.user = user;
    }

    public TeamLog(@NotNull Team team, LogType action, String description, UUID user, UUID otherUser) {
        this(team, action, description);
        this.user = user;
        this.otherUser = otherUser;
    }

    public TeamLog(TeamLog teamLog, String description){
        this.teamID = teamLog.getTeamID();
        this.time = teamLog.getTime();
        this.action = teamLog.getAction();
        this.description = description;
        this.user = teamLog.getUser();
        this.otherUser = teamLog.getOtherUser();
    }
}
