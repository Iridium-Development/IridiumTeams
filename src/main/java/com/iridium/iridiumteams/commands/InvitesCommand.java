package com.iridium.iridiumteams.commands;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.InvitesGUI;
import org.bukkit.entity.Player;

import java.util.Collections;

public class InvitesCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public InvitesCommand() {
        super(Collections.singletonList("invites"), "View your team invites", "%prefix% &7/team invites", "");
    }

    @Override
    public void execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        player.openInventory(new InvitesGUI<>(team, iridiumTeams).getInventory());
    }

}
