package com.iridium.iridiumteams.configs;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.bank.ExperienceBankItem;
import com.iridium.iridiumteams.bank.MoneyBankItem;

import java.util.Arrays;

public class BankItems {
    public ExperienceBankItem experienceBankItem;
    public MoneyBankItem moneyBankItem;

    public BankItems() {
        this("Team", "&c");
    }

    public BankItems(String team, String color) {
        experienceBankItem = new ExperienceBankItem(100, new Item(XMaterial.EXPERIENCE_BOTTLE, 15, 1, color + "&l" + team + " Experience", Arrays.asList(
                "&7%amount% Experience",
                color + "&l[!] " + color + "Left click to withdraw",
                color + "&l[!] " + color + "Right click to deposit")
        ));
        moneyBankItem = new MoneyBankItem(1000, new Item(XMaterial.PAPER, 11, 1, color + "&l" + team + " Money", Arrays.asList(
                "&7$%amount%",
                color + "&l[!] " + color + "Left click to withdraw",
                color + "&l[!] " + color + "Right click to deposit")
        ));
    }
}
