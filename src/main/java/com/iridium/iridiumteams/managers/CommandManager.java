package com.iridium.iridiumteams.managers;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumcore.utils.TimeUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.commands.Command;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CommandManager<T extends Team, U extends IridiumUser<T>> implements CommandExecutor, TabCompleter {

    @Getter
    private final List<Command<T, U>> commands = new ArrayList<>();
    @Getter
    private final String command;
    @Getter
    private final String color;
    private final IridiumTeams<T, U> iridiumTeams;

    public CommandManager(IridiumTeams<T, U> iridiumTeams, String color, String command) {
        this.iridiumTeams = iridiumTeams;
        this.command = command;
        this.color = color;
        iridiumTeams.getCommand(command).setExecutor(this);
        iridiumTeams.getCommand(command).setTabCompleter(this);
        registerCommands();
    }

    public void registerCommands() {
        registerCommand(iridiumTeams.getCommands().aboutCommand);
        registerCommand(iridiumTeams.getCommands().createCommand);
        registerCommand(iridiumTeams.getCommands().membersCommand);
        registerCommand(iridiumTeams.getCommands().permissionsCommand);
        registerCommand(iridiumTeams.getCommands().setPermissionCommand);
        registerCommand(iridiumTeams.getCommands().promoteCommand);
        registerCommand(iridiumTeams.getCommands().demoteCommand);
        registerCommand(iridiumTeams.getCommands().helpCommand);
        registerCommand(iridiumTeams.getCommands().reloadCommand);
        registerCommand(iridiumTeams.getCommands().inviteCommand);
        registerCommand(iridiumTeams.getCommands().unInviteCommand);
        registerCommand(iridiumTeams.getCommands().invitesCommand);
        registerCommand(iridiumTeams.getCommands().trustCommand);
        registerCommand(iridiumTeams.getCommands().unTrustCommand);
        registerCommand(iridiumTeams.getCommands().trustsCommand);
        registerCommand(iridiumTeams.getCommands().kickCommand);
        registerCommand(iridiumTeams.getCommands().leaveCommand);
        registerCommand(iridiumTeams.getCommands().deleteCommand);
        registerCommand(iridiumTeams.getCommands().infoCommand);
        registerCommand(iridiumTeams.getCommands().descriptionCommand);
        registerCommand(iridiumTeams.getCommands().renameCommand);
        registerCommand(iridiumTeams.getCommands().setHomeCommand);
        registerCommand(iridiumTeams.getCommands().homeCommand);
        registerCommand(iridiumTeams.getCommands().bypassCommand);
        registerCommand(iridiumTeams.getCommands().transferCommand);
        registerCommand(iridiumTeams.getCommands().joinCommand);
        registerCommand(iridiumTeams.getCommands().bankCommand);
        registerCommand(iridiumTeams.getCommands().depositCommand);
        registerCommand(iridiumTeams.getCommands().withdrawCommand);
        registerCommand(iridiumTeams.getCommands().chatCommand);
        registerCommand(iridiumTeams.getCommands().boostersCommand);
        registerCommand(iridiumTeams.getCommands().upgradesCommand);
        registerCommand(iridiumTeams.getCommands().flyCommand);
        registerCommand(iridiumTeams.getCommands().topCommand);
        registerCommand(iridiumTeams.getCommands().recalculateCommand);
        registerCommand(iridiumTeams.getCommands().warpsCommand);
        registerCommand(iridiumTeams.getCommands().warpCommand);
        registerCommand(iridiumTeams.getCommands().setWarpCommand);
        registerCommand(iridiumTeams.getCommands().deleteWarpCommand);
        registerCommand(iridiumTeams.getCommands().editWarpCommand);
        registerCommand(iridiumTeams.getCommands().missionsCommand);
        registerCommand(iridiumTeams.getCommands().rewardsCommand);
        registerCommand(iridiumTeams.getCommands().experienceCommand);
        registerCommand(iridiumTeams.getCommands().shopCommand);
        registerCommand(iridiumTeams.getCommands().settingsCommand);
    }

    public void registerCommand(Command<T, U> command) {
        if (!command.enabled) return;
        int index = Collections.binarySearch(commands, command, Comparator.comparing(cmd -> cmd.aliases.get(0)));
        commands.add(index < 0 ? -(index + 1) : index, command);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            noArgsDefault(commandSender);
            return true;
        }

        for (Command<T, U> command : commands) {
            // We don't want to execute other commands or ones that are disabled
            if (!command.aliases.contains(args[0])) continue;

            return executeCommand(commandSender, command, Arrays.copyOfRange(args, 1, args.length));
        }

        // Unknown command message
        commandSender.sendMessage(StringUtils.color(iridiumTeams.getMessages().unknownCommand
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
        ));
        return false;
    }

    public boolean executeCommand(CommandSender commandSender, Command<T, U> command, String[] args) {
        if (!command.hasPermission(commandSender, iridiumTeams)) {
            commandSender.sendMessage(StringUtils.color(iridiumTeams.getMessages().noPermission
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        if (command.isOnCooldown(commandSender, iridiumTeams)) {
            Duration remainingTime = command.getCooldownProvider().getRemainingTime(commandSender);
            String formattedTime = TimeUtils.formatDuration(iridiumTeams.getMessages().activeCooldown, remainingTime);

            commandSender.sendMessage(StringUtils.color(formattedTime
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        if (command.execute(commandSender, args, iridiumTeams)) {
            command.getCooldownProvider().applyCooldown(commandSender);
        }
        return true;
    }

    public abstract void noArgsDefault(@NotNull CommandSender commandSender);

    private List<String> getTabComplete(CommandSender commandSender, String[] args) {
        if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (Command<T, U> command : commands) {
                for (String alias : command.aliases) {
                    if (!alias.toLowerCase().startsWith(args[0].toLowerCase())) continue;
                    if (command.hasPermission(commandSender, iridiumTeams)) {
                        result.add(alias);
                    }
                }
            }
            return result.stream().sorted().collect(Collectors.toList());
        }

        for (Command<T, U> command : commands) {
            if (!command.aliases.contains(args[0].toLowerCase())) continue;
            if (command.hasPermission(commandSender, iridiumTeams)) {
                return command.onTabComplete(commandSender, Arrays.copyOfRange(args, 1, args.length), iridiumTeams);
            }
        }

        // We currently don't want to tab-completion here
        // Return a new List so it isn't a list of online players
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command cmd, @NotNull String label, String[] args) {
        List<String> tabComplete = getTabComplete(commandSender, args);
        if (tabComplete == null) return null;
        return tabComplete.stream()
                .filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }
}
