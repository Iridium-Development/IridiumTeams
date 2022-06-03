package com.iridium.iridiumteams.commands;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.InvitesGUI;
import org.bukkit.entity.Player;

import java.util.List;

public class InvitesCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public InvitesCommand(List<String> args, String description, String syntax, String permission){
        super(args, description, syntax, permission);
    }

    @Override
    public void execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        player.openInventory(new InvitesGUI<>(team, iridiumTeams).getInventory());
    }

}
