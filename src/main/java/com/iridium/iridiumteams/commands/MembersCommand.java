package com.iridium.iridiumteams.commands;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.MembersGUI;
import org.bukkit.entity.Player;

import java.util.Collections;

public class MembersCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public MembersCommand() {
        super(Collections.singletonList("members"), "View your team members", "%prefix% &7/team members", "");
    }

    @Override
    public void execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        player.openInventory(new MembersGUI<>(team, iridiumTeams).getInventory());
    }

}
