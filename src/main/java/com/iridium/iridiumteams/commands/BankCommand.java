package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.bank.BankItem;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamBank;
import com.iridium.iridiumteams.gui.BankGUI;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class BankCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public String adminPermission;

    public BankCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds, String adminPermission) {
        super(args, description, syntax, permission, cooldownInSeconds);
        this.adminPermission = adminPermission;
    }

    @Override
    public boolean execute(U user, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (arguments.length == 4) {
            Optional<T> team = iridiumTeams.getTeamManager().getTeamViaNameOrPlayer(arguments[1]);
            if (!team.isPresent()) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamDoesntExistByName
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return false;
            }
            Optional<BankItem> bankItem = iridiumTeams.getBankItemList().stream()
                    .filter(item -> item.getName().equalsIgnoreCase(arguments[2]))
                    .findAny();
            double amount;
            try {
                amount = Double.parseDouble(arguments[3]);
            } catch (NumberFormatException exception) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notANumber
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return false;
            }
            if (!player.hasPermission(adminPermission)) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noPermission
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return false;
            }
            if (!bankItem.isPresent()) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noSuchBankItem
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return false;
            }
            TeamBank teamBank = iridiumTeams.getTeamManager().getTeamBank(team.get(), bankItem.get().getName());
            switch (arguments[0].toLowerCase()) {
                case "give":
                    teamBank.setNumber(teamBank.getNumber() + amount);

                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().gaveBank
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                            .replace("%player%", arguments[1])
                            .replace("%amount%", String.valueOf(amount))
                            .replace("%item%", bankItem.get().getName())
                    ));
                    break;
                case "remove":
                    teamBank.setNumber(teamBank.getNumber() - amount);

                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().removedBank
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                            .replace("%player%", arguments[1])
                            .replace("%amount%", String.valueOf(amount))
                            .replace("%item%", bankItem.get().getName())
                    ));
                    break;
                case "set":
                    teamBank.setNumber(amount);

                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().setBank
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                            .replace("%player%", arguments[1])
                            .replace("%amount%", String.valueOf(amount))
                            .replace("%item%", bankItem.get().getName())
                    ));
                    break;
                default:
                    player.sendMessage(StringUtils.color(syntax
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    ));
            }
            return true;
        }
        if (arguments.length != 0) {
            player.sendMessage(StringUtils.color(syntax
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }
        return super.execute(user, arguments, iridiumTeams);
    }

    @Override
    public boolean execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        player.openInventory(new BankGUI<>(team, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        if (!commandSender.hasPermission(adminPermission)) return Collections.emptyList();
        switch (args.length) {
            case 1:
                return Arrays.asList("give", "set", "remove");
            case 2:
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            case 3:
                return iridiumTeams.getBankItemList().stream().map(BankItem::getName).collect(Collectors.toList());
            case 4:
                return Arrays.asList("1", "10", "100");
            default:
                return Collections.emptyList();
        }
    }
}
