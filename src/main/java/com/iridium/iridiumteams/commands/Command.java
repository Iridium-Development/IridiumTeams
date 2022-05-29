package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * Abstract commands used to easily create subcommands.
 */
public abstract class Command<T extends Team, U extends IridiumUser<T>> {

    public final @NotNull List<String> aliases;
    public final @NotNull String description;
    public final @NotNull String permission;
    public final @NotNull String syntax;
    public final boolean enabled;

    public Command(@NotNull List<String> aliases, @NotNull String description, @NotNull String syntax, @NotNull String permission) {
        this.aliases = aliases;
        this.description = description;
        this.syntax = syntax;
        this.permission = permission;
        this.enabled = true;
    }


    public void execute(CommandSender sender, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtils.color(iridiumTeams.getMessages().mustBeAPlayer
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix))
            );
            return;
        }
        execute((U) iridiumTeams.getUserManager().getUser((OfflinePlayer) sender), arguments, iridiumTeams);
    }

    public void execute(U user, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Optional<T> team = iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID());
        if (!team.isPresent()) {
            user.getPlayer().sendMessage(StringUtils.color(iridiumTeams.getMessages().dontHaveTeam
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix))
            );
            return;
        }
        execute(user, team.get(), arguments, iridiumTeams);
    }

    public void execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
    }

    /**
     * Handles tab-completion for this command.
     *
     * @param commandSender The CommandSender which tries to tab-complete
     * @param command       The command
     * @param label         The label of the command
     * @param args          The arguments already provided by the sender
     * @return The list of tab completions for this command
     */
    public abstract List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args);

}
