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
public class TeamInvite<T extends Team> extends TeamData<T> {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;
    @DatabaseField(columnName = "user", canBeNull = false)
    private @NotNull UUID user;

    @DatabaseField(columnName = "inviter", canBeNull = false)
    private @NotNull UUID invitee;

    @DatabaseField(columnName = "time", canBeNull = false)
    private LocalDateTime time;

    public TeamInvite(@NotNull T team, @NotNull UUID user, @NotNull UUID invitee) {
        super(team);
        this.user = user;
        this.invitee = invitee;
        this.time = LocalDateTime.now();
    }
}
