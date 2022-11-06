package com.iridium.iridiumteams.database;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "team_blocks")
public class TeamBlock extends TeamData {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "block", uniqueCombo = true)
    private XMaterial xMaterial;

    @DatabaseField(columnName = "amount", canBeNull = false)
    private int amount;

    public TeamBlock(@NotNull Team team, XMaterial xMaterial, int amount) {
        super(team);
        this.xMaterial = xMaterial;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        setChanged(true);
    }
}
