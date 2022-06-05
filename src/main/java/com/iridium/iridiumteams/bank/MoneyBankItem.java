package com.iridium.iridiumteams.bank;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.TeamBank;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

@NoArgsConstructor
public class MoneyBankItem extends BankItem {

    public MoneyBankItem(double defaultAmount, Item item) {
        super("money", item, defaultAmount, true);
    }

    @Override
    public BankResponse withdraw(Player player, Number amount, TeamBank teamBank, IridiumTeams<?, ?> iridiumTeams) {
        double money = Math.min(amount.doubleValue(), teamBank.getNumber());
        if (money > 0) {
            iridiumTeams.getEconomy().depositPlayer(player, money);
            teamBank.setNumber(teamBank.getNumber() - money);
            return new BankResponse(money, true);
        }
        return new BankResponse(money, false);
    }

    @Override
    public BankResponse deposit(Player player, Number amount, TeamBank teamBank, IridiumTeams<?, ?> iridiumTeams) {
        double money = Math.min(amount.doubleValue(), iridiumTeams.getEconomy().getBalance(player));
        if (money > 0) {
            iridiumTeams.getEconomy().withdrawPlayer(player, money);
            teamBank.setNumber(teamBank.getNumber() + money);
            return new BankResponse(money, true);
        }
        return new BankResponse(money, false);
    }

}
