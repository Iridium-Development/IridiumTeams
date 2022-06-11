package com.iridium.testplugin;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.bank.BankItem;
import com.iridium.iridiumteams.bank.BankResponse;
import com.iridium.iridiumteams.database.TeamBank;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestBankItem extends BankItem {
    public static Map<UUID, Integer> playerBalances;

    public TestBankItem() {
        super("testBankItem", new Item(XMaterial.DIAMOND_BLOCK, 1, "", Collections.emptyList()), 10, true);
        playerBalances = new HashMap<>();
    }

    @Override
    public BankResponse withdraw(Player player, Number amount, TeamBank teamBank, IridiumTeams<?, ?> iridiumTeams) {
        int balance = Math.min(amount.intValue(), (int) teamBank.getNumber());
        if (balance > 0) {
            playerBalances.put(player.getUniqueId(), playerBalances.getOrDefault(player.getUniqueId(), 0) + balance);
            teamBank.setNumber(teamBank.getNumber() - balance);
            return new BankResponse(balance, true);
        }
        return new BankResponse(balance, false);
    }

    @Override
    public BankResponse deposit(Player player, Number amount, TeamBank teamBank, IridiumTeams<?, ?> iridiumTeams) {
        int balance = Math.min(amount.intValue(), playerBalances.getOrDefault(player.getUniqueId(), 0));
        if (balance > 0) {
            playerBalances.put(player.getUniqueId(), playerBalances.getOrDefault(player.getUniqueId(), 0) - balance);
            teamBank.setNumber(teamBank.getNumber() + balance);
            return new BankResponse(balance, true);
        }
        return new BankResponse(balance, false);
    }
}
