package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.ChatType;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PlayerChatListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        U user = iridiumTeams.getUserManager().getUser(event.getPlayer());
        Optional<T> yourTeam = iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID());
        Optional<ChatType> chatType = iridiumTeams.getChatTypes().stream()
                .filter(type -> type.getAliases().stream().anyMatch(s -> s.equalsIgnoreCase(user.getChatType())))
                .findFirst();
        if (!yourTeam.isPresent() || !chatType.isPresent()) return;
        List<Player> players = chatType.get().getPlayerChat().getPlayers(event.getPlayer().getPlayer());
        if (players == null) return;
        for (Player player : players) {
            if(player == null) return;
            player.sendMessage(StringUtils.color(StringUtils.processMultiplePlaceholders(iridiumTeams.getMessages().chatFormat, iridiumTeams.getTeamChatPlaceholderBuilder().getPlaceholders(event, player))));
        }
        event.getRecipients().clear();
    }

}
