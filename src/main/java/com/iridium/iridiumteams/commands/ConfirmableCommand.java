package com.iridium.iridiumteams.commands;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.ConfirmationGUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ConfirmableCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public final boolean requiresConfirmation;

    public ConfirmableCommand() {
        super();
        this.requiresConfirmation = true;
    }

    public ConfirmableCommand(@NotNull List<String> aliases, @NotNull String description, @NotNull String syntax,
                              @NotNull String permission, long cooldownInSeconds, boolean requiresConfirmation) {
        super(aliases, description, syntax, permission, cooldownInSeconds);
        this.requiresConfirmation = requiresConfirmation;
    }

    @Override
    public final boolean execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        if (!isCommandValid(user, team, arguments, iridiumTeams)) {
            return false;
        }

        if (requiresConfirmation) {
            Player player = user.getPlayer();

            player.openInventory(new ConfirmationGUI<>(() -> {
                executeAfterConfirmation(user, team, arguments, iridiumTeams);
            }, iridiumTeams).getInventory());
            return true;
        }

        executeAfterConfirmation(user, team, arguments, iridiumTeams);
        return true;
    }

    protected abstract boolean isCommandValid(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams);

    protected abstract void executeAfterConfirmation(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams);
}