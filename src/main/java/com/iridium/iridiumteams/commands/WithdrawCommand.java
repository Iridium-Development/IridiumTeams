package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.bank.BankItem;
import com.iridium.iridiumteams.bank.BankResponse;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamBank;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class WithdrawCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public WithdrawCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 2) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        Optional<BankItem> bankItem = iridiumTeams.getBankItemList().stream().filter(item -> item.getName().equalsIgnoreCase(args[0])).findFirst();
        if (!bankItem.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noSuchBankItem.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }

        try {
            TeamBank teamBank = iridiumTeams.getTeamManager().getTeamBank(team, bankItem.get().getName());
            BankResponse bankResponse = bankItem.get().withdraw(player, Double.parseDouble(args[1]), teamBank, iridiumTeams);

            player.sendMessage(StringUtils.color((bankResponse.isSuccess() ? iridiumTeams.getMessages().bankWithdrew : iridiumTeams.getMessages().insufficientFundsToWithdraw)
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%amount%", String.valueOf(bankResponse.getAmount()))
                    .replace("%type%", bankItem.get().getName())
            ));
            return true;
        } catch (NumberFormatException exception) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notANumber.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        if (args.length == 1) {
            return iridiumTeams.getBankItemList().stream()
                    .map(BankItem::getName)
                    .collect(Collectors.toList());
        }
        return Arrays.asList("100", "1000", "10000", "100000");
    }

}
