package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.bank.BankItem;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
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
public class ExperienceCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public String adminPermission;

    public ExperienceCommand(List<String> args, String description, String syntax, String permission, String adminPermission) {
        super(args, description, syntax, permission);
        this.adminPermission = adminPermission;
    }

    @Override
    public void execute(U user, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (arguments.length == 3) {
            Optional<T> team = iridiumTeams.getTeamManager().getTeamViaNameOrPlayer(arguments[1]);
            if (!team.isPresent()) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().teamDoesntExistByName
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return;
            }
            int amount;
            try {
                amount = Integer.parseInt(arguments[2]);
            } catch (NumberFormatException exception) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notANumber
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return;
            }
            if (!player.hasPermission(adminPermission)) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noPermission
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                ));
                return;
            }
            switch (arguments[0].toLowerCase()) {
                case "give":
                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().gaveExperience
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                            .replace("%player%", arguments[1])
                            .replace("%amount%", String.valueOf(amount))
                    ));

                    team.get().setExperience(team.get().getExperience() + amount);
                    break;
                case "remove":
                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().removedExperience
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                            .replace("%player%", arguments[1])
                            .replace("%amount%", String.valueOf(Math.min(amount, team.get().getExperience())))
                    ));

                    team.get().setExperience(team.get().getExperience() - amount);
                    break;
                case "set":
                    player.sendMessage(StringUtils.color(iridiumTeams.getMessages().setExperience
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                            .replace("%player%", arguments[1])
                            .replace("%amount%", String.valueOf(Math.max(amount, 0)))
                    ));

                    team.get().setExperience(amount);
                    break;
                default:
                    player.sendMessage(StringUtils.color(syntax
                            .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    ));
            }
            return;
        }
        if (arguments.length != 0) {
            player.sendMessage(StringUtils.color(syntax
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return;
        }
        iridiumTeams.getCommands().infoCommand.execute(user, arguments, iridiumTeams);
    }

    @Override
    public void execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        player.openInventory(new BankGUI<>(team, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
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
