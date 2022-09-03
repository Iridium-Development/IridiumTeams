package com.iridium.iridiumteams.database;

import com.iridium.iridiumteams.Reward;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_rewards")
public class TeamReward extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false, uniqueCombo = true)
    private int id;

    @DatabaseField(columnName = "reward", canBeNull = false, width = 2048)
    private Reward reward;

    public TeamReward(@NotNull Team team, Reward reward) {
        super(team);
        this.reward = reward;
    }
}
