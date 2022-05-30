package com.iridium.iridiumteams.managers;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.commands.*;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class CommandManager<T extends Team, U extends IridiumUser<T>> implements CommandExecutor, TabCompleter {

    private final List<Command<T, U>> commands = new ArrayList<>();
    private final IridiumTeams<T, U> iridiumTeams;

    public CommandManager(IridiumTeams<T, U> iridiumTeams, String color, String command, List<Command<T, U>> commands) {
        this.iridiumTeams = iridiumTeams;
        iridiumTeams.getCommand(command).setExecutor(this);
        iridiumTeams.getCommand(command).setTabCompleter(this);
        registerCommands(commands, color);
    }

    public void registerCommands(List<Command<T, U>> commands, String color) {
        commands.forEach(this::registerCommand);
        registerCommand(new AboutCommand<>(color));
        registerCommand(new CreateCommand<>());
        registerCommand(new MembersCommand<>());
        registerCommand(new PermissionsCommand<>());
        registerCommand(new SetPermissionCommand<>());
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

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
