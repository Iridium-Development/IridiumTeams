package com.iridium.iridiumteams.commands;

import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.MissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.MissionGUI;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@NoArgsConstructor
public class MissionsCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public MissionsCommand(List<String> args, String description, String syntax, String permission) {
        super(args, description, syntax, permission);
    }

    @Override
    public void execute(U user, T team, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        player.openInventory(new MissionGUI<>(team, MissionType.DAILY, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
    }

}
