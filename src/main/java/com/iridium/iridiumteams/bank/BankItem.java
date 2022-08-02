package com.iridium.iridiumteams.bank;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.TeamBank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class BankItem {

    private String name;
    private Item item;
    private double defaultAmount;
    private boolean enabled;

    public abstract BankResponse withdraw(Player player, Number amount, TeamBank teamBank, IridiumTeams<?, ?> teams);

    public abstract BankResponse deposit(Player player, Number amount, TeamBank teamBank, IridiumTeams<?, ?> teams);

}
