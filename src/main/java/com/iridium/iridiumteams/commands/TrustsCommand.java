package com.iridium.iridiumteams.commands;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.InvitesGUI;
import com.iridium.iridiumteams.gui.TrustsGUI;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class TrustsCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public TrustsCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        player.openInventory(new TrustsGUI<>(team, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
        return true;
    }

}
