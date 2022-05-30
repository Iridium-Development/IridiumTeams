package com.iridium.iridiumteams.managers;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.commands.*;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CommandManager<T extends Team, U extends IridiumUser<T>> implements CommandExecutor, TabCompleter {

    @Getter
    private final List<Command<T, U>> commands = new ArrayList<>();
    @Getter
    private final String command;
    private final IridiumTeams<T, U> iridiumTeams;

    public CommandManager(IridiumTeams<T, U> iridiumTeams, String color, String command) {
        this.iridiumTeams = iridiumTeams;
        this.command = command;
        iridiumTeams.getCommand(command).setExecutor(this);
        iridiumTeams.getCommand(command).setTabCompleter(this);
        registerCommands(color);
    }

    public void registerCommands(String color) {
        registerCommand(new AboutCommand<>(color));
        registerCommand(new CreateCommand<>());
        registerCommand(new MembersCommand<>());
        registerCommand(new PermissionsCommand<>());
        registerCommand(new SetPermissionCommand<>());
        registerCommand(new PromoteCommand<>());
        registerCommand(new DemoteCommand<>());
        registerCommand(new HelpCommand<>());
        registerCommand(new ReloadCommand<>());
        registerCommand(new InviteCommand<>());
        registerCommand(new UnInviteCommand<>());
    }

    public void registerCommand(Command<T, U> command) {
        if (command.enabled) {
            int index = Collections.binarySearch(commands, command, Comparator.comparing(cmd -> cmd.aliases.get(0)));
            commands.add(index < 0 ? -(index + 1) : index, command);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            noArgsDefault(commandSender);
            return true;
        }

        for (Command<T, U> command : commands) {
            // We don't want to execute other commands or ones that are disabled
            if (!(command.aliases.contains(args[0]))) {
                continue;
            }

            // Check permissions
            if (!(commandSender.hasPermission(command.permission) || command.permission.equalsIgnoreCase(""))) {
                // No permissions
                commandSender.sendMessage(StringUtils.color(iridiumTeams.getMessages().noPermission
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
                return false;
            }

            command.execute(commandSender, Arrays.copyOfRange(args, 1, args.length), iridiumTeams);
            return true;
        }

        // Unknown command message
        commandSender.sendMessage(StringUtils.color(iridiumTeams.getMessages().unknownCommand.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
        return false;
    }

    public abstract void noArgsDefault(@NotNull CommandSender commandSender);

    private List<String> getTabComplete(CommandSender commandSender, String[] args) {
        for (Command<T, U> command : commands) {
            if (command.aliases.contains(args[0]) && (
                    commandSender.hasPermission(command.permission) || command.permission.equalsIgnoreCase("")
                            || command.permission.equalsIgnoreCase("IridiumFactions."))) {
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
                .filter(s -> s.toLowerCase().startsWith(args[args.length - 1]))
                .collect(Collectors.toList());
    }
}
