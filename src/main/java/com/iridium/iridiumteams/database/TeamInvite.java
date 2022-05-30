package com.iridium.iridiumteams.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_invites")
public class TeamInvite {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "team_id", canBeNull = false)
    private int teamID;
    @DatabaseField(columnName = "user", canBeNull = false)
    private @NotNull UUID user;

    @DatabaseField(columnName = "inviter", canBeNull = false)
    private @NotNull UUID invitee;

    @DatabaseField(columnName = "time", canBeNull = false)
    private LocalDateTime time;

    public TeamInvite(@NotNull Team team, @NotNull UUID user, @NotNull UUID invitee) {
        this.user = user;
        this.invitee = invitee;
        this.teamID = team.getId();
        this.time = LocalDateTime.now();
    }
}
