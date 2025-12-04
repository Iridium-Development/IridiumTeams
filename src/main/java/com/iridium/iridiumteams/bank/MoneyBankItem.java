package com.iridium.iridiumteams.bank;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.TeamBank;
import lombok.NoArgsConstructor;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

@NoArgsConstructor
public class MoneyBankItem extends BankItem {

    public MoneyBankItem(double defaultAmount, Item item) {
        super("money", item, defaultAmount, true, true);
    }

    public MoneyBankItem(double defaultAmount, Item item, boolean canTransact) {
        super("money", item, defaultAmount, true, canTransact);
    }

    @Override
    public BankResponse withdraw(Player player, Number amount, TeamBank teamBank, IridiumTeams<?, ?> iridiumTeams) {
        if(!this.canTransact) return new BankResponse(teamBank.getNumber(), false);
        double money = Math.min(amount.doubleValue(), teamBank.getNumber());

        if (money > 0) {
            EconomyResponse economyResponse = iridiumTeams.getEconomy().depositPlayer(player, money);
            if (economyResponse.type == EconomyResponse.ResponseType.SUCCESS) {
                teamBank.setNumber(teamBank.getNumber() - money);
                return new BankResponse(money, true);
            }
        }
        return new BankResponse(money, false);
    }

    @Override
    public BankResponse deposit(Player player, Number amount, TeamBank teamBank, IridiumTeams<?, ?> iridiumTeams) {
        if(!this.canTransact) return new BankResponse(iridiumTeams.getEconomy().getBalance(player), false);
        double money = Math.min(amount.doubleValue(), iridiumTeams.getEconomy().getBalance(player));

        if (money > 0) {
            EconomyResponse economyResponse = iridiumTeams.getEconomy().withdrawPlayer(player, money);
            if (economyResponse.type == EconomyResponse.ResponseType.SUCCESS) {
                teamBank.setNumber(teamBank.getNumber() + money);
                return new BankResponse(money, true);
            }
        }
        return new BankResponse(money, false);
    }

}
